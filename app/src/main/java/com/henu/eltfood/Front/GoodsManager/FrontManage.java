package com.henu.eltfood.Front.GoodsManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.henu.eltfood.DataClass.Account;
import com.henu.eltfood.DataClass.Food;
import com.henu.eltfood.DataSystem.ImgOperation;
import com.henu.eltfood.Front.AddFoods;
import com.henu.eltfood.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class FrontManage extends AppCompatActivity {
    private ImageView back;
    private ImageView add;
    private Intent intent;
    private ListView myGoodsListView;
    private List<Food> myFood; // 存储加载的信息
    private ManageFoodAdapter adapter;
    private String shopName;
    private Bitmap init_img;
    private ImgOperation op;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_manage);
        Bmob.initialize(this, "230f0d485f24c49e8bb460514596714a");

        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                myFood.get(msg.what).setImage((Bitmap) msg.obj);
                adapter.notifyDataSetChanged();
                System.out.println("第" + String.valueOf(msg.what + 1) + "张图片加载完毕");
            }
        };
        InitComponent();
        LoadActivity();
        LoadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myFood.clear();
    }

    void InitComponent() {
        shopName = getIntent().getStringExtra("shopName");
        op = new ImgOperation(FrontManage.this);
        init_img = BitmapFactory.decodeResource(FrontManage.this.getResources(),R.drawable.account);
        intent = new Intent(FrontManage.this, AddFoods.class);
        intent.putExtra("shopName", shopName);
        myFood = new ArrayList<>();
        back = findViewById(R.id.manage_back_home);
        add = findViewById(R.id.add_food);
        myGoodsListView = findViewById(R.id.front_manage_listview);
    }
    void LoadActivity() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityIfNeeded(intent, 1);
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
                    Toast.makeText(FrontManage.this, "已删除！", Toast.LENGTH_SHORT).show();
                    myFood.clear();
                    LoadData();
                }
                else {
                    Toast.makeText(FrontManage.this, "错误:" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void LoadData() {
        BmobQuery<Food> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("accountId", Account.getCurrentUser(Account.class).getUsername());
        bmobQuery.addWhereEqualTo("shopName", shopName);
        bmobQuery.findObjects(new FindListener<Food>() {
            @Override
            public void done(List<Food> list, BmobException e) {
                if(e == null) {
                    myFood = new ArrayList<Food>(list);
                    for (int i = 0; i < myFood.size(); i++) myFood.get(i).setImage(init_img);
                    adapter = new ManageFoodAdapter(FrontManage.this, myFood, FrontManage.this);
                    myGoodsListView.setAdapter(adapter);
                    Toast.makeText(FrontManage.this, "加载完毕！", Toast.LENGTH_SHORT).show();

                    LoadImg();
                }
                else {
                    Toast.makeText(FrontManage.this, "错误" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void LoadImg(){
        new Thread(){
            @Override
            public void run() {

                System.out.println("开始加载图片");

                for (int i = 0; i < myFood.size(); i++){
                    System.out.println("正在加载第" + String.valueOf(i + 1) + "张图片");

                    Bitmap bmp = op.GetBitmap(myFood.get(i).getImageName());
                    Message msg = new Message();
                    msg.what = i;
                    msg.obj = bmp;
                    handler.sendMessage(msg);
                }
                System.out.println("所有图片加载完毕");
            }
        }.start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) { // 还需要有所属商家， 商家地址
            myFood.clear();
            LoadData();
        }
    }
}