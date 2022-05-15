package com.henu.eltfood.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.henu.eltfood.R;

import java.util.List;

public class ContactPersonListAdapter extends BaseAdapter {
    private Context context;
    private List<ContactPerson> contactPersonList;

    public ContactPersonListAdapter(Context context, List<ContactPerson> list) {
        this.context = context;
        this.contactPersonList = list;
    }
    public void setContactPersonList(List<ContactPerson> contactPersonList) {
        this.contactPersonList = contactPersonList;
    }
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
        viewHolder.header.setBackgroundResource(contactPerson.getHeader());
        viewHolder.last_massage.setText(contactPerson.getLast_massage());
        viewHolder.last_massage_time.setText(contactPerson.getLast_massage_time());

        return view;
    }

    public void setDate(int selectedId, ContactPerson contactPerson) {
        contactPersonList.get(selectedId).setLast_massage(contactPerson.getLast_massage());
        contactPersonList.get(selectedId).setLast_massage_time(contactPerson.getLast_massage_time());
    }

    class ViewHolder{
        ImageView header;
        TextView name,last_massage;
        TextView last_massage_time;
    }
}
