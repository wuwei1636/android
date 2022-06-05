package com.henu.eltfood.Recommend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.henu.eltfood.Message.chat.ChatActivity;
import com.henu.eltfood.R;
import com.henu.eltfood.util.EMUtil;

import java.util.ArrayList;

public class MyStaggeredGridLayoutAdapter extends RecyclerView.Adapter {
//    private OnItemClickListener mListener;//声明一个接口的引用;
    private Context Mycontext;
    private ArrayList<Recycler_Item> Mydata;
    MyStaggeredGridLayoutAdapter (Context tp1, ArrayList<Recycler_Item> tp2){
        this.Mycontext = tp1;
        this.Mydata = tp2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View Myview = LayoutInflater.from(Mycontext).inflate(R.layout.recycler_item,null);
        return  new MyViewHolder(Myview);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Recycler_Item data_item = Mydata.get(position);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mListener.onClick(Mycontext, position);

                Intent intent = new Intent(Mycontext, ShopOnline.class);
                intent.putExtra("ShopName", Mydata.get(position).ShopName);
                intent.putExtra("shopId", Mydata.get(position).shopId);
                Mycontext.startActivity(intent);
            }
        });
        myViewHolder.ShopCustomerService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String toChatUsername = data_item.ShopOwner;
                    EMUtil.addFriend(toChatUsername);
                    Intent intent = new Intent(Mycontext, ChatActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username",toChatUsername);
                    intent.putExtras(bundle);
                    Mycontext.startActivity(intent);
            }
        });
        myViewHolder.ShopImg.setImageBitmap(data_item.ShopImg);
        myViewHolder.ShopName.setText(data_item.ShopName);
        myViewHolder.ShopGrade.setText(data_item.ShopGrade);
        myViewHolder.ShopComment.setText(data_item.ShopComment);
        myViewHolder.ShopPrice.setText(data_item.ShopPrice);
    }

    @Override
    public int getItemCount() {
        if (Mydata != null) return Mydata.size();
        return 0;
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView ShopImg;
        public TextView ShopName;
        public TextView ShopGrade;
        public TextView ShopPrice;
        public TextView ShopComment;
        public TextView ShopCustomerService;

        public MyViewHolder(View itemView) {
            super(itemView);
            ShopImg = (ImageView) itemView.findViewById(R.id.ShopImg);
            ShopName = (TextView) itemView.findViewById(R.id.ShopName);
            ShopGrade = (TextView) itemView.findViewById(R.id.ShopGrade);
            ShopPrice = (TextView) itemView.findViewById(R.id.ShopPrice);
            ShopComment = (TextView) itemView.findViewById(R.id.ShopComment);
            ShopCustomerService = (TextView) itemView.findViewById(R.id.ShopCustomerService);
        }
    }

}

