package com.henu.eltfood.Recommend;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.henu.eltfood.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopOnline extends AppCompatActivity {
    TextView AllPrice;
    Button submit, calculate;
    ListView ShopListView;
    ArrayList<ShopFood> Data = new ArrayList<ShopFood>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_online);

        ShopListView = this.findViewById(R.id.ShopListView);
        AllPrice = this.findViewById(R.id.all_price);
        submit = this.findViewById(R.id.submit);
        calculate = this.findViewById(R.id.calculate);

        InitData();
        ShopFoodAdapter madapter = new ShopFoodAdapter(ShopOnline.this, R.layout.shop_food, Data);
        ShopListView.setAdapter(madapter);

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
        Data.add(new ShopFood(R.drawable.a1, "事物垃圾", (double) 100.1, 0));
        Data.add(new ShopFood(R.drawable.a1, "事物垃圾", (double) 100.1, 0));
        Data.add(new ShopFood(R.drawable.a1, "事物垃圾", (double) 100.1, 0));
        Data.add(new ShopFood(R.drawable.a1, "事物垃圾", (double) 100.1, 0));
        Data.add(new ShopFood(R.drawable.a1, "事物垃圾", (double) 100.1, 0));
        Data.add(new ShopFood(R.drawable.a1, "事物垃圾", (double) 100.1, 0));
        Data.add(new ShopFood(R.drawable.a1, "事物垃圾", (double) 100.1, 0));
        Data.add(new ShopFood(R.drawable.a1, "事物垃圾", (double) 100.1, 0));
        Data.add(new ShopFood(R.drawable.a1, "事物垃圾", (double) 100.1, 0));

    }
}
