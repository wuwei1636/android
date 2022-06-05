package com.henu.eltfood.Message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.henu.eltfood.Message.chat.ChatActivity;
import com.henu.eltfood.R;

import java.util.List;

public class ContactPersonListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ContactPerson> contactPersonList;



    public ContactPersonListAdapter(Context context, List<ContactPerson> list) {
        this.context = context;
        this.contactPersonList = list;
    }
    public void setContactPersonList(List<ContactPerson> contactPersonList) {
        this.contactPersonList = contactPersonList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View Myview = LayoutInflater.from(context).inflate(R.layout.message_item,null);
        return  new ViewHolder(Myview);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ViewHolder myholder = (ViewHolder) holder;
        ContactPerson person = contactPersonList.get(position);
        myholder.header.setImageBitmap(person.getHeader());
        if(person.getHeader() != null) System.out.println("成功setBitmap");
        myholder.name.setText(person.getName());
        myholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("username",contactPersonList.get(position).getName());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactPersonList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView header;
        TextView name,last_massage;
        TextView last_massage_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            name = itemView.findViewById(R.id.name);
            last_massage = itemView.findViewById(R.id.last_massage);
            last_massage_time = itemView.findViewById(R.id.last_massage_time);
        }
    }
}
/*
    @Override
    public int getCount() {
        return contactPersonList.size();
    }

    @Override
    public Object getItem(int i) {
        return contactPersonList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null) {// 当前视图未创建
            viewHolder = new ViewHolder();// 打包view
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            view = layoutInflater.inflate(R.layout.message_item,null);
            viewHolder.name = view.findViewById(R.id.name);
            viewHolder.last_massage = view.findViewById(R.id.last_massage);
            viewHolder.header = view.findViewById(R.id.header);
            viewHolder.last_massage_time = view.findViewById(R.id.last_massage_time);
            view.setTag(viewHolder);// 打标记
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // 为当前view各组件 装载数据，通过 viewHolder实现
        ContactPerson contactPerson = contactPersonList.get(i);
        viewHolder.name.setText(contactPerson.getName());
        viewHolder.header.setImageBitmap(contactPerson.getHeader());
//        viewHolder.header.setBackgroundResource(contactPerson.getHeader());
        viewHolder.last_massage.setText(contactPerson.getLast_massage());
        viewHolder.last_massage_time.setText(contactPerson.getLast_massage_time());

        return view;
    }

 */