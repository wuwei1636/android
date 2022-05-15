package com.henu.eltfood.Front;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.henu.eltfood.R;

import java.util.ArrayList;
import java.util.List;

public class FrontDrinks extends AppCompatActivity {
    ImageView back;
    ListView listView;
    Button milk, juice, soda, liquor; //牛奶，果汁，汽水，酒
    List<FoodInformation> foodInformationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_drinks);
        InitComponent();
        LoadActivity();
        LoadDatas();

        FoodAdapter foodAdapter = new FoodAdapter(FrontDrinks.this, foodInformationList);
        listView.setAdapter(foodAdapter);
    }

    void InitComponent() {
        foodInformationList = new ArrayList<>();
        listView = findViewById(R.id.front_listview_drink);
        back = findViewById(R.id.drink_back_home);
        milk = findViewById(R.id.front_milk);
        juice = findViewById(R.id.front_juice);
        soda = findViewById(R.id.front_soda);
        liquor = findViewById(R.id.front_liquor);
    }

    void LoadActivity() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传入不同的FoodInformation的list产生不同的展示效果
                FoodAdapter ftest = new FoodAdapter(FrontDrinks.this, foodInformationList);
                listView.setAdapter(ftest);
            }
        });
        juice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传入不同的FoodInformation的list产生不同的展示效果
                FoodAdapter ftest = new FoodAdapter(FrontDrinks.this, foodInformationList);
                listView.setAdapter(ftest);
            }
        });
        soda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传入不同的FoodInformation的list产生不同的展示效果
                FoodAdapter ftest = new FoodAdapter(FrontDrinks.this, foodInformationList);
                listView.setAdapter(ftest);
            }
        });
        liquor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传入不同的FoodInformation的list产生不同的展示效果
                FoodAdapter ftest = new FoodAdapter(FrontDrinks.this, foodInformationList);
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