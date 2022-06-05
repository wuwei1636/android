package com.henu.eltfood.Front;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.henu.eltfood.DataClass.Account;
import com.henu.eltfood.DataClass.Food;
import com.henu.eltfood.DataClass.FoodInTrade;
import com.henu.eltfood.DataSystem.ImgOperation;
import com.henu.eltfood.Front.GoodsManager.FrontManage;
import com.henu.eltfood.R;
import com.henu.eltfood.Recommend.ShopOnline;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class FrontFood extends AppCompatActivity {
    ImageView back; //返回
    Button mainFood; // 选择展示主食
    Button dish; // 选择展示小菜
    Button littleFood; //小吃
    ListView listView; // 用来展示商品的listView
    TextView mian;
    TextView rou;
    TextView soup;
//    List<FoodInformationItem> foodInformationList;
//    List<Food> mainFoodList; //主食的数据
//    List<Food> dishList; // 小菜的数据
//    List<Food> littleFoodList; // 小吃的数据
    List<Food> foodData;
    private FoodAdapter adapter;
    private Bitmap init_img;
    private ImgOperation op;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_food);
        Bmob.initialize(this, "230f0d485f24c49e8bb460514596714a");

        InitComponent();    //把对应的组件找到其id
        Drawable draw1 = ResourcesCompat.getDrawable(getResources(), R.drawable.niuroumian, null);
        draw1.setBounds( 5 ,  5 ,  220 ,  160 ); //第一0是距左边距离，第二0是距上边距离，40分别是长宽
        mian.setCompoundDrawables(null,draw1,null,null);

        Drawable draw2 = ResourcesCompat.getDrawable(getResources(), R.drawable.dapanji, null);
        draw2.setBounds( 5 ,  5 ,  220 ,  160 ); //第一0是距左边距离，第二0是距上边距离，40分别是长宽
        rou.setCompoundDrawables(null,draw2,null,null);

        Drawable draw3 = ResourcesCompat.getDrawable(getResources(), R.drawable.darksoup, null);
        draw3.setBounds( 5 ,  5 ,  220 ,  160 ); //第一0是距左边距离，第二0是距上边距离，40分别是长宽
        soup.setCompoundDrawables(null,draw3,null,null);

        LoadDatas("主食"); //加载Adapter数据
        LoadActivity(); // 加载监听事件

    }

    void addToShoppingCart(Food food) { //加入购物车方法，点击+号时调用
        FoodInTrade foodInTrade = new FoodInTrade(food.getImage(), food.getImageName(),
                food.getFoodName(), food.getFoodCategory(), 1, food.getFoodPrice(),
                food.getAccountId(), food.getAccountAddress(),
                Account.getCurrentUser(Account.class).getUsername(), Account.getCurrentUser(Account.class).getAddress());
        foodInTrade.setCount(0);
        boolean flag = false;
        foodInTrade.setStatus(FoodInTrade.WAIT_FOR_PAID);
        BmobQuery<FoodInTrade> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("foodName", food.getFoodName());
        bmobQuery.findObjects(new FindListener<FoodInTrade>() {
            @Override
            public void done(List<FoodInTrade> list, BmobException e) {
                for(int i = 0; i < list.size(); i++) {
                    if(e == null) {
                        String s1 = list.get(i).getConsumerId();
                        String s2 = Account.getCurrentUser(Account.class).getUsername();
                        if( s1 == s2 && list.get(i).getStatus() == FoodInTrade.WAIT_FOR_PAID) {
                            list.get(i).setCount(list.get(i).getCount() + 1);
                            list.get(i).update(list.get(i).getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                }
                            });
                            Toast.makeText(FrontFood.this, "找到了", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            }
        });
        foodInTrade.setCount(1);
        foodInTrade.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Toast.makeText(FrontFood.this, "已添加到购物车", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void InitComponent() {
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                foodData.get(msg.what).setImage((Bitmap) msg.obj);
                adapter.notifyDataSetChanged();
                System.out.println("第" + String.valueOf(msg.what + 1) + "张图片加载完毕");
            }
        };
        op = new ImgOperation(FrontFood.this);
        init_img = BitmapFactory.decodeResource(FrontFood.this.getResources(),R.drawable.account);
        foodData = new ArrayList<>();
//        foodInformationList = new ArrayList<>(); //先开辟一个地址给它，否则会出现空指针报错
        back = findViewById(R.id.food_back_home);
        mainFood = findViewById(R.id.main_food);
        dish = findViewById(R.id.dish);
        littleFood = findViewById(R.id.little_food);
        listView = findViewById(R.id.front_listview_food);
        mian = findViewById(R.id.front_son_mian);
        rou = findViewById(R.id.front_son_rou);
        soup = findViewById(R.id.front_son_soup);
    }

    private void setEnable(Button btn) {
        List<Button> buttonList=new ArrayList<>();
        if (buttonList.size()==0){
            buttonList.add(mainFood);
            buttonList.add(littleFood);
            buttonList.add(dish);
        }
        for (int i = 0; i <buttonList.size() ; i++) {
            buttonList.get(i).setEnabled(true);
        }
        btn.setEnabled(false);
    }
    void LoadActivity() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mainFood.setOnClickListener(new View.OnClickListener() { //点击了 "主食" 按钮
            @Override
            public void onClick(View view) {
                LoadDatas("主食");
                setEnable(mainFood);
            }
        });
        littleFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadDatas("小吃");
                setEnable(littleFood);
            }
        });
        dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传入不同的FoodInformation的list产生不同的展示效果
                LoadDatas("小菜");
                setEnable(dish);
            }
        });
        mian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShopOnline.class);
                startActivity(intent);
            }
        });
        rou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShopOnline.class);
                startActivity(intent);
            }
        });
        soup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShopOnline.class);
                startActivity(intent);
            }
        });
    }
    void LoadDatas(String category) {
        BmobQuery<Food> foodBmobQuery = new BmobQuery<>();
        foodBmobQuery.addWhereEqualTo("foodCategory", category);
        foodBmobQuery.findObjects(new FindListener<Food>() {
            @Override
            public void done(List<Food> list, BmobException e) {
                if(e == null) {
                    Toast.makeText(FrontFood.this, "共有" + list.size() + "条", Toast.LENGTH_SHORT).show();
                    foodData = new ArrayList<>(list);
                    for (int i = 0; i < foodData.size(); i++) foodData.get(i).setImage(init_img);
                    adapter = new FoodAdapter(FrontFood.this, foodData, FrontFood.this);
                    listView.setAdapter(adapter);

                    LoadImg();
                }
                else {
                    Toast.makeText(FrontFood.this, "失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void LoadImg(){
        new Thread(){
            @Override
            public void run() {

                System.out.println("开始加载图片");

                for (int i = 0; i < foodData.size(); i++){
                    System.out.println("正在加载第" + String.valueOf(i + 1) + "张图片");

                    Bitmap bmp = op.GetBitmap(foodData.get(i).getImageName());
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