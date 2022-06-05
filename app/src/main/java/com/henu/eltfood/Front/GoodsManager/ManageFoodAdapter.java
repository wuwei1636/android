package com.henu.eltfood.Front.GoodsManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.henu.eltfood.DataClass.Food;
import com.henu.eltfood.R;

import java.util.List;

public class ManageFoodAdapter extends BaseAdapter {
    private List<Food> list;
    private Context context;
    private FrontManage frontManage; //获取当前activity，为了调用其中的方法，删除商品

    public ManageFoodAdapter(Context context, List<Food> list, FrontManage frontManage) {
        this.list = list;
        this.context = context;
        this.frontManage = frontManage;
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
        Food food = getItem(i); //获取第 i 个item的数据
        View v;
        ViewHold viewHold;
        if(view == null) {
            v = LayoutInflater.from(context).inflate(R.layout.my_goods_item, null);
            viewHold = new ViewHold();

            viewHold.foodImage = v.findViewById(R.id.manage_food_img);
            viewHold.foodCategory = v.findViewById(R.id.manage_food_category);
            viewHold.foodName = v.findViewById(R.id.manage_food_name);
            viewHold.price = v.findViewById(R.id.manage_food_price);
            viewHold.delete_item = v.findViewById(R.id.delete_this_order);

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
        viewHold.delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    frontManage.deleteGoods(getItem(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }
    private class ViewHold {
        ImageView foodImage;
        TextView foodName;
        TextView foodCategory;
        TextView price;
        Button delete_item;
    }
}
