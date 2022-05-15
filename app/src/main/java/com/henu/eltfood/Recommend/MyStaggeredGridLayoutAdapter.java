package com.henu.eltfood.Recommend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.henu.eltfood.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.Inflater;

public class MyStaggeredGridLayoutAdapter extends RecyclerView.Adapter {
    private OnItemClickListener mListener;//声明一个接口的引用;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(Mycontext, position);
//                Mycontext.startActivity(new Intent(Mycontext,ShopOnline.class));
//                Toast.makeText(Mycontext, "asknd", Toast.LENGTH_SHORT).show();
            }
        });
        myViewHolder.MyImageView.setImageResource(data_item.image_source);
//        myViewHolder.MyImageView.getLayoutParams().height = (200 + new Random().nextInt(500));
        myViewHolder.MyTextView.setText(data_item.textview_content);
    }

    @Override
    public int getItemCount() {
        if (Mydata != null) return Mydata.size();
        return 0;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView MyImageView;
        public TextView MyTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            MyImageView = (ImageView) itemView.findViewById(R.id.imageView);
            MyTextView = (TextView) itemView.findViewById(R.id.textView);
        }
    }

    public  void setOnItemClickListner(OnItemClickListener onItemClickListner){
        this.mListener=onItemClickListner;
    }
    public interface OnItemClickListener {
        void onClick(Context context, int pos);
    }
}

