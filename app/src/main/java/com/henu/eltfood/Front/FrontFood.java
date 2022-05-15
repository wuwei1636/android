package com.henu.eltfood.Front;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.henu.eltfood.R;

import java.util.ArrayList;
import java.util.List;

public class FrontFood extends AppCompatActivity {
    ImageView back;
    Button mainfood;
    Button dish;
    Button little_food;
    ListView listView;
    List<FoodInformation> foodInformationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_food);
        InitComponent();    //把对应的组件找到其id
        LoadDatas(); //加载Adapter数据
        LoadActivity(); // 加载监听事件

        FoodAdapter foodAdapter = new FoodAdapter(FrontFood.this, foodInformationList);
        listView.setAdapter(foodAdapter);
    }

    void InitComponent() {
        foodInformationList = new ArrayList<>(); //先开辟一个地址给它，否则会出现空指针报错
        back = findViewById(R.id.food_back_home);
        mainfood = findViewById(R.id.main_food);
        dish = findViewById(R.id.dish);
        little_food = findViewById(R.id.little_food);
        listView = findViewById(R.id.front_listview_food);
    }

    void LoadActivity() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mainfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodAdapter ftest = new FoodAdapter(FrontFood.this, foodInformationList);
                listView.setAdapter(ftest);
            }
        });
        little_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodAdapter ftest = new FoodAdapter(FrontFood.this, foodInformationList);
                listView.setAdapter(ftest);
            }
        });
        dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传入不同的FoodInformation的list产生不同的展示效果
                FoodAdapter ftest = new FoodAdapter(FrontFood.this, foodInformationList);
                listView.setAdapter(ftest);
            }
        });
    }
    void LoadDatas() {
        int img[] = {R.drawable.bread, R.drawable.frappe};
        String textName[] = {"玛卡巴卡", "一股辟谷"};
        String textC[] = {"主食", "主食"};
        String price[] = {"12", "14"};
        for(int j = 0; j < 8; j++) {
            for(int i = 0; i < img.length; i++) {
                foodInformationList.add(new FoodInformation(img[i], textName[i], textC[i], price[i]));
            }
        }
    }
}