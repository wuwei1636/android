package com.henu.eltfood.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.henu.eltfood.DataClass.FoodInTrade;
import com.henu.eltfood.R;
import com.henu.eltfood.Recommend.MyStaggeredGridLayoutAdapter;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class fragment2Adapter extends RecyclerView.Adapter {
    ArrayList<FoodInTrade> list;
    Context context;
    int layout;

    public fragment2Adapter(Context context,int id, ArrayList<FoodInTrade> list){
        this.context = context;
        this.list = list;
        this.layout = id;
    }


    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View Myview = LayoutInflater.from(context).inflate(layout,null);
        return  new MyViewHolder(Myview);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        FoodInTrade tp = list.get(position);
        myViewHolder.food_image.setImageBitmap(tp.getImage());
        myViewHolder.food_description.setText(tp.getFoodName());
        myViewHolder.food_price.setText(String.format("%.2f",Double.parseDouble(tp.getFoodPrice())));
        myViewHolder.food_count.setText(String.valueOf(tp.getCount()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView food_image;
        TextView food_description;
        TextView food_price;
        TextView food_count;
        public MyViewHolder(View itemView) {
            super(itemView);
            food_image = itemView.findViewById(R.id.food_image1);
            food_description = itemView.findViewById(R.id.food_description1);
            food_price = itemView.findViewById(R.id.food_price1);
            food_count = itemView.findViewById(R.id.food_count1);
        }
    }
}
