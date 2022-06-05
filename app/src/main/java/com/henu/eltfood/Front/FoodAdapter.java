package com.henu.eltfood.Front;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.henu.eltfood.DataClass.Food;
import com.henu.eltfood.R;

import java.util.List;

public class FoodAdapter extends BaseAdapter {
    private List<Food> list;
    private Context context;
    private FrontFood frontFood;
    private FrontDrinks frontDrinks;

    public FoodAdapter(Context context, List<Food> list, FrontFood frontFood) {
        frontDrinks = null;
        this.list = list;
        this.context = context;
        this.frontFood = frontFood;
    }

    public FoodAdapter(Context context, List<Food> list, FrontDrinks frontDrinks) {
        frontFood = null;
        this.list = list;
        this.context = context;
        this.frontDrinks = frontDrinks;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Food getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Food food = (Food) getItem(i); //获取第 i 个item的数据
        View v;
        ViewHold viewHold;
        if(view == null) {
            v = LayoutInflater.from(context).inflate(R.layout.food_information, null);
            viewHold = new ViewHold();

            viewHold.foodImage = v.findViewById(R.id.food_img);
            viewHold.foodCategory = v.findViewById(R.id.food_category);
            viewHold.foodName = v.findViewById(R.id.food_name);
            viewHold.price = v.findViewById(R.id.front_food_price);
            viewHold.imageButton = v.findViewById(R.id.select_add);

            v.setTag(viewHold);
        }
        else {
            v = view;
            viewHold = (ViewHold) v.getTag();
        }
        viewHold.foodImage.setImageBitmap(food.getImage());
        viewHold.foodCategory.setText(food.getFooCategory());
        viewHold.foodName.setText(food.getFoodName());
        viewHold.price.setText(food.getFoodPrice());
        viewHold.imageButton.setOnClickListener(new View.OnClickListener() { //点击对应的item中的+号，产生相应的事件
            @Override
            public void onClick(View view) {
                if(frontFood != null) {
                    frontFood.addToShoppingCart(getItem(i));
                }
                else {
                    frontDrinks.addToShoppingCart(getItem(i));
                }
                Toast.makeText(context, "已添加商品:" + getItem(i).getFoodName(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
    private class ViewHold {
        ImageView foodImage;
        TextView foodName;
        TextView foodCategory;
        TextView price;
        ImageButton imageButton;
    }
}
