package com.henu.eltfood.Recommend;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.henu.eltfood.R;

import java.util.ArrayList;

public class ShopFoodAdapter extends BaseAdapter
{
    ArrayList<ShopFood> data;
    Context context;
    int layout;
    @Override
    public int getCount() {
        //getCount方法是程序在加载显示到ui上时就要先读取的，这里获得的值决定了listview显示多少行
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public ShopFoodAdapter(Context context, int id, ArrayList<ShopFood> obj)
    {
        this.context = context;
        this.data = obj;
        this.layout = id;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ShopFood tpfood = (ShopFood) data.get(position);
        if (convertView == null)
        {
            convertView=LayoutInflater.from(context).inflate(layout,null);
        }
        ImageView food_image = convertView.findViewById(R.id.food_image);
        TextView food_description = convertView.findViewById(R.id.food_description);
        TextView food_price = convertView.findViewById(R.id.food_price);
        TextView food_count = convertView.findViewById(R.id.food_count);
        Button button_add = convertView.findViewById(R.id.food_count_add);
        Button button_sub = convertView.findViewById(R.id.food_count_sub);

        food_image.setImageResource(tpfood.FoodImageId);
        food_description.setText(tpfood.FoodDescription);
        food_price.setText(String.valueOf(tpfood.price));
        food_count.setText(String.valueOf(tpfood.count));

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int now_count = new Integer(Integer.valueOf(food_count.getText().toString()));
                if (now_count < 99)
                {
                    food_count.setText(String.valueOf(now_count + 1));
                }
            }
        });

        button_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int now_count = new Integer(Integer.valueOf(food_count.getText().toString()));
                if (now_count > 0)
                {
                    food_count.setText(String.valueOf(now_count - 1));
                }
            }
        });
        return convertView;
    }
}

