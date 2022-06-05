package com.henu.eltfood.Front.FrontConsumerOrder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.henu.eltfood.DataClass.FoodInTrade;
import com.henu.eltfood.R;

import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private List<FoodInTrade> datalist;
    private Context context;
    private FrontConsumerOrder frontConsumerOrder; //获得FrontConsumerOrder对象，调用其中的删除item项的方法

    public OrderAdapter(List<FoodInTrade> datalist, Context context, FrontConsumerOrder frontConsumerOrder) {
        this.datalist = datalist;
        this.context = context;
        this.frontConsumerOrder = frontConsumerOrder;
    }

    public void setDatalist(List<FoodInTrade> datalist) {
        this.datalist = datalist;
    }

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public FoodInTrade getItem(int i) {
        return datalist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FoodInTrade orderItem = (FoodInTrade) getItem(i); //获取第 i 个item的数据
        View v;
        ViewHold viewHold;
        if(view == null) {
            v = LayoutInflater.from(context).inflate(R.layout.consumer_order_item, null);
            viewHold = new ViewHold();

            viewHold.foodImage = v.findViewById(R.id.co_food_img); // 图片
            viewHold.foodCategory = v.findViewById(R.id.co_food_category); // 食物类别
            viewHold.foodName = v.findViewById(R.id.co_food_name); // 食物名称
            viewHold.price = v.findViewById(R.id.co_front_food_price); // 食物价格
            viewHold.send = v.findViewById(R.id.send_this_order); // 发送订单
            viewHold.consumer_id = v.findViewById(R.id.consumer_id); // 消费者的id
            viewHold.address = v.findViewById(R.id.address); // 消费者的地址

            viewHold.send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                    此处实现发送订单功能
                    将该条 FoodInTrade项从数据库中删除，或者标记为已完成
                    */
                    Toast.makeText(context, "订单已发送！", Toast.LENGTH_SHORT).show();
                    try {
                        frontConsumerOrder.sendOrder(getItem(i));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            v.setTag(viewHold);
        }
        else {
            v = view;
            viewHold = (ViewHold) v.getTag();
        }
        viewHold.foodImage.setImageBitmap(orderItem.getImage());
        viewHold.foodCategory.setText(orderItem.getFoodCategory());
        viewHold.foodName.setText(orderItem.getFoodName());
        viewHold.price.setText(orderItem.getFoodPrice());
        viewHold.consumer_id.setText("顾客：" + orderItem.getConsumername());
        viewHold.address.setText(orderItem.getConsumerAddress());
        return v;
    }

    private class ViewHold {
        ImageView foodImage;
        TextView foodName;
        TextView foodCategory;
        TextView price;
        TextView consumer_id;
        TextView address;
        Button send;
    }
}
