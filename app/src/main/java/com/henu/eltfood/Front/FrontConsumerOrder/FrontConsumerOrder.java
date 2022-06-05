package com.henu.eltfood.Front.FrontConsumerOrder;

import android.content.res.Resources;
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
import androidx.appcompat.app.AppCompatActivity;

import com.henu.eltfood.DataClass.Account;
import com.henu.eltfood.DataClass.FoodInTrade;
import com.henu.eltfood.DataSystem.ImgOperation;
import com.henu.eltfood.Front.GoodsManager.FrontManage;
import com.henu.eltfood.Front.GoodsManager.ManageFoodAdapter;
import com.henu.eltfood.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class FrontConsumerOrder extends AppCompatActivity {
    ImageView back;
    ListView consumerOrderList;
    List<FoodInTrade> orderList; //存储顾客的订单
    OrderAdapter orderAdapter;
    Resources res = null;
    private Bitmap init_img;
    private ImgOperation op;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_consumer_order);
        Bmob.initialize(this, "230f0d485f24c49e8bb460514596714a");
        Init();
        LoadListener(); //为界面的所有需要增加监听事件的组件加载监听事件
        LoadData();
    }

    void Init() {
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                orderList.get(msg.what).setImage((Bitmap) msg.obj);
                orderAdapter.notifyDataSetChanged();
                System.out.println("第" + String.valueOf(msg.what + 1) + "张图片加载完毕");
            }
        };
        op = new ImgOperation(FrontConsumerOrder.this);
        init_img = BitmapFactory.decodeResource(FrontConsumerOrder.this.getResources(),R.drawable.account);
        orderList = new ArrayList<>();
        back = findViewById(R.id.corder_back_home);
        consumerOrderList = findViewById(R.id.consumer_order_list);
        res = getResources();
    }

    void LoadListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    //定义发送订单的方法(删除item项的方法)
    public void sendOrder(FoodInTrade foodInTrade) throws InterruptedException {
        foodInTrade.setStatus(FoodInTrade.SEND);
        foodInTrade.update(foodInTrade.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null) {
                    Toast.makeText(FrontConsumerOrder.this, "订单已发送", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(FrontConsumerOrder.this, "失败：" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Thread.sleep(500);
        LoadData();
    }

    void LoadData() {
        BmobQuery<FoodInTrade> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("shopId", Account.getCurrentUser(Account.class).getUsername());
        bmobQuery.addWhereLessThan("status", FoodInTrade.SEND);
        bmobQuery.addWhereGreaterThan("status", FoodInTrade.WAIT_FOR_PAID);
        bmobQuery.findObjects(new FindListener<FoodInTrade>() {
            @Override
            public void done(List<FoodInTrade> list, BmobException e) {
//                Log.i("加载:", "" + list.size());
                orderList = new ArrayList<>(list);
                for (int i = 0; i < orderList.size(); i++) orderList.get(i).setImage(init_img);
                orderAdapter = new OrderAdapter(orderList,FrontConsumerOrder.this, FrontConsumerOrder.this);
                consumerOrderList.setAdapter(orderAdapter);

                LoadImg();
            }
        });
    }
    public void LoadImg(){
        new Thread(){
            @Override
            public void run() {

                System.out.println("开始加载图片");

                for (int i = 0; i < orderList.size(); i++){
                    System.out.println("正在加载第" + String.valueOf(i + 1) + "张图片");

                    Bitmap bmp = op.GetBitmap(orderList.get(i).getImageName());
                    Message msg = new Message();
                    msg.what = i;
                    msg.obj = bmp;
                    handler.sendMessage(msg);
                }
                System.out.println("所有图片加载完毕");
            }
        }.start();

    }
}