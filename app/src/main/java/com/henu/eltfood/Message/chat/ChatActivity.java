package com.henu.eltfood.Message.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.henu.eltfood.DataSystem.MySQLConnections;
import com.henu.eltfood.R;
import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ListView listView = null;
    private List<Massage> massageList = null;
    private MassageListAdapter massageListAdapter = null;
    private Button sendButton = null;
    private EditText editText = null;
    private Calendar lastMassageTime = null;
    private int myImage = R.drawable.waifu;// 应当从 "我的"界面 获取头像
    // there we need a function that gets two userid from some Activity!!
    private String userId = "mxrush";
    private String friendId = "xbb";

    private final static int MAX_MSG_SIZE = 100;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        // 组件初始化
        listView = findViewById(R.id.msg_list_view);
        sendButton = findViewById(R.id.send);
        editText = findViewById(R.id.input_text);
        massageList = new ArrayList<>();
        lastMassageTime = null;

        // 测试用数据
        loadData();

        // listView 加载适配器
        massageListAdapter = new MassageListAdapter(ChatActivity.this,massageList);
        listView.setAdapter(massageListAdapter);

        // sendButton 发送功能实现
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sentence = editText.getText().toString();
                Massage massage = new Massage(sentence,Massage.TYPE_SEND,myImage);
                List<MsgHolder> list = new ArrayList<>();
                if(!massage.getContent().equals("")) {// 不空认为有效
                    new Thread() {
                        @Override
                        public void run() {
                            sendDB(userId,friendId,massage,System.currentTimeMillis());
                            Connection connection = connectDB();

                            askDB(userId,friendId,connection,list);
                            askDB(friendId,userId,connection,list);
//                            System.out.println("列表大小 ： " + list.size());

                        }
                    }.start();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Collections.sort(list);
                    /// 先情空
//                    massageList.clear();
                    // load
                    System.out.println("开始重新装载数据,数据量为" + list.size());
                    for(int i = 0;i < list.size();i ++) {
                        MsgHolder t = list.get(i);
                        System.out.println(t.getContent());
                        if(t.getUserId().equals(userId)) massageList.add(new Massage(t.getContent(),
                                Massage.TYPE_SEND,R.drawable.waifu));
                        else massageList.add(new Massage(t.getContent(),
                                Massage.TYPE_RECEIVED,R.drawable.waifu_4));
                    }
                    massageListAdapter.setMassageList(massageList);

                    lastMassageTime = Calendar.getInstance();// 保存最后一条信息的时间，用于返回时更新 contactList
                }

                editText.setText("");
                massageListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void sendDB(String userId, String friendId, Massage massage, long currentTimeMillis) {
        new Thread() {
            @Override
            public void run() {
                String sql = "insert into tb_chat_record(user_id,friend_id,content,time)  values(?,?,?,?)";
                Connection connection = connectDB();
                try {
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.setString(1,userId);
                    stmt.setString(2,friendId);
                    stmt.setString(3,massage.getContent());
                    stmt.setString(4, String.valueOf(currentTimeMillis));
                    connection.setAutoCommit(false);
                    stmt.addBatch();
                    stmt.executeBatch();
                    connection.commit();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 监听手机返回键
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("msg",massageListAdapter.getLastMassageContent());
        intent.putExtra("lastTime",lastMassageTime);
        if(lastMassageTime != null)
            setResult(RESULT_OK,intent);
        else setResult(RESULT_CANCELED,intent);
        finish();
    }

    private void loadData() {
        // userid friendid  we need find all of (userid,friendid) and then the first one represented the msg1

        new Thread() {
            List<MsgHolder> msgHolders = new ArrayList<>();// 暂存数据，我们需要对其内信息通过时间戳排序
            // 当解决了头像传递的问题后，该list可以废弃
            @Override
            public void run() {
                System.out.println("开始导入数据");
                Connection connections = connectDB();

                if(connections != null) {
                    askDB(userId,friendId,connections,msgHolders);
                    askDB(friendId,userId,connections,msgHolders);

                    // sort
                    Collections.sort(msgHolders);
                    // load
                    for(int i = 0;i < msgHolders.size();i ++) {
                        MsgHolder t = msgHolders.get(i);
                        if(t.getUserId().equals(userId)) massageList.add(new Massage(t.getContent(),
                                Massage.TYPE_SEND,R.drawable.waifu));
                        else massageList.add(new Massage(t.getContent(),
                                Massage.TYPE_RECEIVED,R.drawable.waifu_4));
                    }
                }

            }
        }.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("完成装载");
//        Massage massage1 = new Massage("欢迎光临！",Massage.TYPE_SEND, R.drawable.waifu);
//        Massage massage2 = new Massage("你好",Massage.TYPE_RECEIVED, R.drawable.waifu_4);
//        for(int i = 0;i < 2;i ++) {
//            if(i % 2 == 0) massageList.add(massage1);
//            else massageList.add(massage2);
//        }
    }
    Connection connectDB () {
        Connection connections = null;
        try {
            connections = MySQLConnections.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connections;
    }

    void askDB(String userId,String friendId,Connection connections,List<MsgHolder> msgHolders) {
        System.out.println("查询开始");
        int cnt = 0;
        if(connections != null) System.out.println("数据库连接一切正常");
        PreparedStatement stmt = null;
        String sql = "select * from tb_chat_record where user_id=? and friend_id=?";
        ResultSet res = null;
        try {
            stmt = connections.prepareStatement(sql);
            stmt.setString(1,userId);
            stmt.setString(2,friendId);
            res = stmt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        while (true) {
            try {
                if (res == null || !res.next() || cnt > MAX_MSG_SIZE) break;
                cnt ++;
                String uId = res.getString("user_id");
                String fId = res.getString("friend_id");
                String content = res.getString("content");
                Long time = Long.valueOf(res.getString("time"));
                msgHolders.add(new MsgHolder(uId,fId,content,time));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println("PP "  + msgHolders.size());
    }

    class MsgHolder implements Comparable{
        String userId;
        String friendId;

        public String getUserId() {
            return userId;
        }

        public String getFriendId() {
            return friendId;
        }

        public String getContent() {
            return content;
        }

        public Long getTime() {
            return time;
        }

        String content;
        Long time;

        public MsgHolder(String userId, String friendId, String content, long time) {
            this.userId = userId;
            this.friendId = friendId;
            this.content = content;
            this.time = time;
        }

        @Override
        public int compareTo(Object o) {
            MsgHolder msg = (MsgHolder) o;
            if(this.time > msg.time) return 1;
            return -1;
        }
    }
}
