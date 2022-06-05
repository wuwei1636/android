package com.henu.eltfood.Message.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.henu.eltfood.R;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;

public class MassageListAdapter extends BaseAdapter {
    private static final int TYPE_RECEIVE = 0;
    private static final int TYPE_SEND = 1;

    private Context context;
    private ArrayList<EMMessage> emMessages;
    private String myName;

    private Bitmap myImg;

    private Bitmap friendImg;

    public Bitmap getMyImg() {
        return myImg;
    }

    public void setMyImg(Bitmap myImg) {
        this.myImg = myImg;
    }

    public Bitmap getFriendImg() {
        return friendImg;
    }

    public void setFriendImg(Bitmap friendImg) {
        this.friendImg = friendImg;
    }


    public Context getContext() {
        return context;
    }

    public ArrayList<EMMessage> getEmMessages() {
        return emMessages;
    }

    public void setEmMessages(ArrayList<EMMessage> emMessages) {
        this.emMessages = emMessages;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MassageListAdapter(Context context, ArrayList<EMMessage> emMessages, String myName) {
        this.context = context;
        this.emMessages = emMessages;
        this.myName = myName;
    }

    @Override
    public int getCount() {
        return emMessages.size();
    }

    @Override
    public Object getItem(int i) {
        return emMessages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    // getItemViewType 和 getViewTypeCount 很重要 如果不重写，会发生子项错位
    @Override
    public int getItemViewType(int i) {
        EMMessage emMessage = emMessages.get(i);
        String from = emMessage.getFrom();//消息来源
        if(!from.equals(myName)){//对方发的消息，放左边
            return TYPE_RECEIVE;
        }else {
            return TYPE_SEND;
        }
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

            if(getItemViewType(i) == TYPE_RECEIVE) {
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

        EMMessage massage = emMessages.get(i);
        viewHolder.massage.setText(gao(massage.getBody().toString()));
//        viewHolder.header.setBackgroundResource(R.drawable.waifu_6);

        if(getItemViewType(i) == TYPE_RECEIVE)
            viewHolder.header.setImageBitmap(friendImg);
        else if(getItemViewType(i) == TYPE_SEND)
            viewHolder.header.setImageBitmap(myImg);
//        view.invalidate();
        return view;
    }

    /**
     * 消除 txt:"···”
     * @param s
     * @return
     */
    private String gao(String s) {
        String str = null;
        if(s.length() < 5) str = s;
        if(s.charAt(0) == 't' && s.charAt(1) == 'x' && s.charAt(2) == 't'
                && s.charAt(3) == ':' && s.charAt(4) == '\"' && s.charAt(s.length() - 1) == '\"')
            str = s.substring(5,s.length() - 1);
        return str;
    }

    class ViewHolder{
        ImageView header;
        TextView massage;
    }

    public void addMassage(EMMessage massage) {
        emMessages.add(massage);
        return;
    }

    public String getLastMassageContent() {
        int position = getCount() - 1;
        return emMessages.get(position).getBody().toString();
    }
}
