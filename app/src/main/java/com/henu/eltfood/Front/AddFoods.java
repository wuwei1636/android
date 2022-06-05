package com.henu.eltfood.Front;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.henu.eltfood.DataClass.Account;
import com.henu.eltfood.DataClass.Food;
import com.henu.eltfood.DataSystem.ImgOperation;
import com.henu.eltfood.R;
import com.henu.eltfood.util.ImgPathOperation;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class AddFoods extends AppCompatActivity {
    private static final int CHOOSE_PHOTOS = 1;
    private Button submit;
    private EditText name_edit;
    private EditText price_edit;
    private Spinner food_category;
    private ImageView food_img;
    private ImageView back;
    private Food addFood;
    ActivityResultLauncher<Intent> launch;
    Uri uri;
    ImgOperation op;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_foods);
        Bmob.initialize(this, "230f0d485f24c49e8bb460514596714a");
        Init();
        makeListener();
    }
    //初始化组件
    void Init() {
        op = new ImgOperation(AddFoods.this);
        food_img = findViewById(R.id.add_food_image);
        submit = findViewById(R.id.food_info_submit);
        name_edit = findViewById(R.id.food_name_edit);
        price_edit = findViewById(R.id.food_price_edit);
        food_category = findViewById(R.id.food_category_spinner);
        back = findViewById(R.id.add_food_back);

        launch = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent data = result.getData();
                if (result.getResultCode() == RESULT_OK) {
                    // 从相册返回的数据
                    if (data != null) {
                        // 得到图片的全路径
                        uri = data.getData();
                        food_img.setImageURI(uri);
                    }
                }
            }
        });

    }
    //创建监听事件
    void makeListener() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //实现向远端提交数据，或者向本地提交数据
                Intent intent = getIntent();
                if(name_edit.getText().toString().trim().equals("") ||
                        price_edit.getText().toString().trim().equals("")) {
                    setResult(RESULT_CANCELED,intent);
                    finish();
                }
                else {
                    if (uri == null){
                        Toast.makeText(AddFoods.this,"请选择图片",Toast.LENGTH_LONG).show();
                    }
                    else {
                        String imgname = new String(Account.getCurrentUser(Account.class).getUsername()
                                + String.valueOf(System.currentTimeMillis()));
                        String file_path = ImgPathOperation.getRealPathFromUri(AddFoods.this, uri);
                        op.SaveToLocalAndSQ(file_path, imgname);

                        int img = R.drawable.bread; //  此处的为获取图片，但是获取的不一定是图片id，可能是二进制图片资源等等
                        String name = name_edit.getText().toString();
                        String category = food_category.getSelectedItem().toString();
                        String price = price_edit.getText().toString();
                        String shopName = intent.getStringExtra("shopName");
                        Account account = BmobUser.getCurrentUser(Account.class);
                        account.setHaveShop(true);
                        account.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {

                            }
                        });

                        addFood = new Food(op.GetBitmap(imgname), imgname, name, category, price,
                                Account.getCurrentUser(Account.class).getUsername(),
                                Account.getCurrentUser(Account.class).getAddress());
                        addFood.setShopName(shopName);
                        addFood.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(AddFoods.this, "已成功提交！", Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else {
                                    Toast.makeText(AddFoods.this, "提交失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        food_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //相册
                if (ContextCompat.checkSelfPermission(AddFoods.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    //相册中的照片都是存储在SD卡上的，需要申请运行时权限，WRITE_EXTERNAL_STORAGE是危险权限，表示同时授予程序对SD卡的读和写的能力
                    ActivityCompat.requestPermissions(AddFoods.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }else {
                    // 打开相册
                    Intent AlbumIntent = new Intent("android.intent.action.GET_CONTENT", null);
                    AlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    launch.launch(AlbumIntent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uri = null;
    }
}