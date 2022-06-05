package com.henu.eltfood.Recommend;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.henu.eltfood.DataClass.Food;
import com.henu.eltfood.DataClass.FoodInTrade;
import com.henu.eltfood.DataClass.Shop;
import com.henu.eltfood.DataSystem.ImgOperation;
import com.henu.eltfood.R;
import com.henu.eltfood.util.EMUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class ShopOnline extends AppCompatActivity {
    TextView AllPrice;
    Button submit, calculate;
    ListView ShopListView;
    ShopFoodAdapter madapter;
    ArrayList<ShopFood> Data = new ArrayList<ShopFood>();
    Bitmap init_bmp;
    Handler handler;
    ImgOperation op = new ImgOperation(ShopOnline.this);
    String shopId;
    boolean DataLoadSuccess = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_online);
        Bmob.initialize(this, "230f0d485f24c49e8bb460514596714a");

        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Data.get(msg.what).setImg((Bitmap) msg.obj);
                madapter.notifyDataSetChanged();
                System.out.println("第" + String.valueOf(msg.what + 1) + "张图片加载完毕");
            }
        };

        InitComponent();
        DataLoadSuccess = false;
        InitData();

        Handler handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Data.get(msg.what).Img = (Bitmap) msg.obj;
                madapter.notifyDataSetChanged();
                System.out.println("第" + String.valueOf(msg.what + 1) + "张图片加载完毕");
            }
        };

        new Thread(){
            @Override
            public void run() {

                while(DataLoadSuccess == false){int cnt = 0; cnt += 1;}
                System.out.println("开始加载图片");

                for (int i = 0; i < Data.size(); i++){
                    System.out.println("正在加载第" + String.valueOf(i + 1) + "张图片");

                    Bitmap bmp = op.GetBitmap(Data.get(i).ImgName);
                    Message msg = new Message();
                    msg.what = i;
                    msg.obj = bmp;
                    handler.sendMessage(msg);
                }
                System.out.println("所有图片加载完毕");
            }
        }.start();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = ShopListView.getChildCount();
                for(int i = 0;i < n ;i ++){
                    double sum = 0.0;
                    View tip = (View) ShopListView.getChildAt(i);
                    TextView tp = tip.findViewById(R.id.food_description);
                    TextView tp1 = tip.findViewById(R.id.food_count);
                    TextView tp2 = tip.findViewById(R.id.food_price);
                    String name = EMUtil.getCurrentName();

                    // 获取图片路径

                    String icon_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/1.jpg";

                    View imageview = tip.findViewById(R.id.food_image);
                    imageview.setDrawingCacheEnabled(true);
                    String imgname = Data.get(i).ImgName;

                    int count1 = Integer.valueOf(tp1.getText().toString());
                    Double price  = Double.valueOf(tp2.getText().toString());
                    sum += count1 * price;
                    addfood(tp.getText().toString(),sum,count1,name,imgname,FoodInTrade.WAIT_FOR_PAID);

                    imageview.setDrawingCacheEnabled(false);
                }
                Toast.makeText(ShopOnline.this,"订单已经添加到了购物车，请前往购物车付款",Toast.LENGTH_LONG).show();;
                finish();
            }
        });
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double sum = 0.0;
                int n = ShopListView.getChildCount();
                for (int i = 0; i < n; i++){
                    View tpfood = (View) ShopListView.getChildAt(i);
                    TextView tp1 = tpfood.findViewById(R.id.food_count);
                    TextView tp2 = tpfood.findViewById(R.id.food_price);

                    int count = Integer.valueOf(tp1.getText().toString());
                    Double price = Double.valueOf(tp2.getText().toString());
                    sum += count * price;
                }
                AllPrice.setText(String.format("%.2f", sum));
            }
        });
    }
    public void InitData(){
        Intent intent = super.getIntent();
        shopId = intent.getStringExtra("shopId");
        String shopName = intent.getStringExtra("ShopName");
        BmobQuery<Food> bmobQuery = new BmobQuery<Food>();
        bmobQuery.addWhereEqualTo("shopName",shopName);
        bmobQuery.findObjects(new FindListener<Food>() {
            @Override
            public void done(List<Food> list, BmobException e) {
                if (e == null){
                    for(Food food : list){
                        ShopFood shopfood = new ShopFood(food.getImageName(),
                                food.getFoodName(),food.getFoodPrice(),"0");
                        shopfood.Img = init_bmp;
                        Data.add(shopfood);
                    }
                    madapter = new ShopFoodAdapter(ShopOnline.this,R.layout.shop_food,Data);
                    ShopListView.setAdapter(madapter);
                    DataLoadSuccess = true;
                    System.out.println("数据加载成功");
                    LoadImg();
                }
            }
        });
    }

    public void InitComponent() {
        init_bmp = BitmapFactory.decodeResource(ShopOnline.this.getResources(),R.drawable.account);
        ShopListView = this.findViewById(R.id.ShopListView);
        AllPrice = this.findViewById(R.id.all_price);
        submit = this.findViewById(R.id.submit);
        calculate = this.findViewById(R.id.calculate);
    }

    public void addfood(String foodname,Double price,int count,String name,String imgname,int id){
        FoodInTrade foodInTrade = new FoodInTrade();
        foodInTrade.setStatus(id);
        foodInTrade.setFoodName(foodname);
        foodInTrade.setConsumername(name);
        foodInTrade.setImageName(imgname);
        foodInTrade.setFoodPrice(String.valueOf(price));
        foodInTrade.setCount(count);
        foodInTrade.setShopId(shopId);
        foodInTrade.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {

                } else {
                    Toast.makeText(ShopOnline.this,"网络错误",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void LoadImg() {
        new Thread() {
            @Override
            public void run() {

                System.out.println("开始加载图片");

                for (int i = 0; i < Data.size(); i++) {
                    System.out.println("正在加载第" + String.valueOf(i + 1) + "张图片");

                    Bitmap bmp = op.GetBitmap(Data.get(i).getImgName());
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
    protected void onDestroy() {
        super.onDestroy();
        Data.clear();
    }
}
