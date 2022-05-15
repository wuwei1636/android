package com.henu.eltfood.Message.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.henu.eltfood.R;

import java.util.List;

public class MassageListAdapter extends BaseAdapter {
    private Context context;

    public void setMassageList(List<Massage> massageList) {
        this.massageList = massageList;
    }

    private List<Massage> massageList;

    public void setContext(Context context) {
        this.context = context;
    }

    public MassageListAdapter(Context context, List<Massage> massageList) {
        this.context = context;
        this.massageList = massageList;
    }

    @Override
    public int getCount() {
        return massageList.size();
    }

    @Override
    public Object getItem(int i) {
        return massageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    // getItemViewType 和 getViewTypeCount 很重要 如果不重写，会发生子项错位
    @Override
    public int getItemViewType(int i) {
        return massageList.get(i).getType();
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null) {// 当前视图未创建
            viewHolder = new ViewHolder();// 打包view
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);

            if(getItemViewType(i) == Massage.TYPE_RECEIVED) {
                // 左视图
                view = layoutInflater.inflate(R.layout.chat_item_left,null);
            }else {
                // 右视图
                view = layoutInflater.inflate(R.layout.chat_item_right,null);
            }

            viewHolder.header = view.findViewById(R.id.chat_header);
            viewHolder.massage = view.findViewById(R.id.chat_text);
            view.setTag(viewHolder);// 打标记
        }else {
            viewHolder =  (ViewHolder)view.getTag();
        }
        // 为当前view各组件 装载数据，通过 viewHolder实现

        Massage massage = massageList.get(i);
        viewHolder.massage.setText(massage.getContent());
        viewHolder.header.setBackgroundResource(massage.getHeader());
        return view;
    }
    class ViewHolder{
        ImageView header;
        TextView massage;
    }

    public void addMassage(Massage massage) {
        massageList.add(massage);
        return;
    }

    public String getLastMassageContent() {
        int position = getCount() - 1;
        return massageList.get(position).getContent();
    }
}
