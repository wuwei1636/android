package com.henu.eltfood.Front;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.henu.eltfood.DataClass.Account;
import com.henu.eltfood.DataClass.Food;
import com.henu.eltfood.DataClass.Shop;
import com.henu.eltfood.DataSystem.ImgOperation;
import com.henu.eltfood.Front.GoodsManager.FrontManage;
import com.henu.eltfood.Front.GoodsManager.ManageFoodAdapter;
import com.henu.eltfood.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ManageShop extends AppCompatActivity {
    private ListView shopListView;
    private Button createShopButton;
    private ImageView back;
    private List<Shop> myShopData;
    private ImgOperation op;
    private ManageShopAdapter adapter;
    private Bitmap init_img;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_shop);

        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                myShopData.get(msg.what).setImage((Bitmap) msg.obj);
                adapter.notifyDataSetChanged();
                System.out.println("第" + String.valueOf(msg.what + 1) + "张图片加载完毕");
            }
        };

        InitComponent();
        loadActivity();
        InitData();
    }
    public void InitComponent() {
        op = new ImgOperation(ManageShop.this);
        init_img = BitmapFactory.decodeResource(ManageShop.this.getResources(),R.drawable.account);
        myShopData = new ArrayList<>();
        shopListView = findViewById(R.id.my_shops);
        createShopButton = findViewById(R.id.create_shop);
        back = findViewById(R.id.manage_back_front);
    }
    public void loadActivity() {
        createShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageShop.this, CreateShop.class);
                startActivityIfNeeded(intent, 1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        shopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = view.findViewById(R.id.manageShopName);
                Intent intent = new Intent(ManageShop.this, FrontManage.class);
                intent.putExtra("shopName", textView.getText().toString());
                startActivityIfNeeded(intent, RESULT_OK);
            }
        });
    }
    public void InitData() {
        BmobQuery<Shop> shopBmobQuery = new BmobQuery<>();
        shopBmobQuery.addWhereEqualTo("ShopOwner", BmobUser.getCurrentUser(Account.class).getUsername());
        shopBmobQuery.findObjects(new FindListener<Shop>() {
            @Override
            public void done(List<Shop> list, BmobException e) {
                myShopData = new ArrayList<>(list);
                for (int i = 0; i < myShopData.size(); i++) myShopData.get(i).image = init_img;
                adapter = new ManageShopAdapter(list, ManageShop.this, ManageShop.this);
                shopListView.setAdapter(adapter);

                LoadImg();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) { // 还需要有所属商家， 商家地址
            myShopData.clear();
            InitData();
        }
    }
    public void LoadImg(){
        new Thread(){
            @Override
            public void run() {

                System.out.println("开始加载图片");

                for (int i = 0; i < myShopData.size(); i++){
                    System.out.println("正在加载第" + String.valueOf(i + 1) + "张图片");

                    Bitmap bmp = op.GetBitmap(myShopData.get(i).ShopImgName);
                    Message msg = new Message();
                    msg.what = i;
                    msg.obj = bmp;
                    handler.sendMessage(msg);
                }
                System.out.println("所有图片加载完毕");
            }
        }.start();

    }

    public void deleteShop(Shop s) throws InterruptedException {
        Shop shop = s;
        BmobQuery<Food> foodBmobQuery = new BmobQuery<>();
        foodBmobQuery.addWhereEqualTo("shopName", shop.getShopName());
        foodBmobQuery.findObjects(new FindListener<Food>() {
            @Override
            public void done(List<Food> list, BmobException e) {
                for(int i = 0; i < list.size(); i++){
                    try {
                        deleteGoods(list.get(i));
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }
        });
        System.out.println(shop.getShopImgName());
        op.Delete(shop.getShopImgName());
        shop.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(ManageShop.this, "已删除！", Toast.LENGTH_SHORT).show();
                    myShopData.clear();
                    InitData();
                } else {
                    Toast.makeText(ManageShop.this, "错误:" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void deleteGoods(Food f) throws InterruptedException {
        Food food = f;
        System.out.println(food.getImageName());
        op.Delete(food.getImageName());
        food.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null) {
                }
                else {
                }
            }
        });
    }
}