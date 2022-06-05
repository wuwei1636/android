package com.henu.eltfood.Message.chat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.henu.eltfood.DataClass.Account;
import com.henu.eltfood.DataSystem.ImgOperation;
import com.henu.eltfood.MyInformation.MyinfoSet;
import com.henu.eltfood.R;
import com.henu.eltfood.util.EMUtil;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ChatActivity extends AppCompatActivity implements EMMessageListener {
    /**
     * 常量
     * MAX_MSG_SIZE : 最大历史信息加载长度
     */
    private final static int MAX_MSG_SIZE = 100;
    /**
     * 界面控件
     */
    private ListView listView = null;
    private ArrayList<EMMessage> massageList = null;
    private MassageListAdapter massageListAdapter = null;
    private Button sendButton = null;
    private EditText editText = null;
    private TextView title = null;
    private Calendar lastMassageTime = null;
    private String userId;

    private String toChatUsername;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        findID();
        setBothName();
        initImg();
        initComponent();
//        getCount();

        loadChatHistory();

//        listView.invalidate();
//        listView.setSelection(massageListAdapter.getCount() - 1);
//        myListView.setSelection(msgListView.getBottom());
//        synchronized (massageListAdapter) {
//            try{
//                massageListAdapter.notifyAll();
//            }catch (InternalError e) {
//
//            }
//        }

    }
    private int getCount() {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername);
        System.out.println(conversation.getUnreadMsgCount());
        return conversation.getUnreadMsgCount();
    }
    /**
     * TODO：根据userid找图片
     */
    private void initImg() {
        ImgOperation operation = new ImgOperation(ChatActivity.this);
        BmobQuery<Account> query = new BmobQuery<>();
        query.findObjects(new FindListener<Account>() {
            @Override
            public void done(List<Account> list, BmobException e) {
                if(e == null) {
                    for(Account act : list) {
                        if(act.getUsername().equals(toChatUsername)) {
                            Bitmap bitmap = operation.GetBitmap(act.getImg());
                            massageListAdapter.setFriendImg(bitmap);
                        }
                        if(act.getUsername().equals(userId)) {
                            Bitmap bitmap = operation.GetBitmap(act.getImg());
                            massageListAdapter.setMyImg(bitmap);
                        }
                    }
                }else {
                    Log.e("elt", "done: 用户获取失败" + e.getMessage());
                }
            }
        });

    }

    /**
     * 获取历史聊天记录
     */
    private void loadChatHistory() {
//        List<EMMessage> messages = conversation.loadMoreMsgFromDB(startMsgId, pagesize);
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername);
        List<EMMessage> messageList = null;
        if(conversation != null)
            messageList = conversation.searchMsgFromDB(System.currentTimeMillis(),
                    conversation.getAllMsgCount(),
                    EMConversation.EMSearchDirection.UP);

        if(messageList != null && messageList.size() >= 1) {
            massageList.addAll(messageList);
            massageListAdapter.notifyDataSetChanged();
        }
//        if(conversation != null) {
//            massageList.addAll(conversation.getAllMessages());
//            massageListAdapter.notifyDataSetChanged();
//        }

    }

    private void setBothName() {
        userId = EMUtil.getCurrentName();
        Intent intent = getIntent();
        toChatUsername = intent.getExtras().getString("username");
        Log.e("elt", "setBothName: " + userId + " " + toChatUsername );
    }

    private void initComponent() {
        // 头标初始化
        title.setText(toChatUsername);
        showInput(editText);
        //  listView 加载适配器
        massageListAdapter = new MassageListAdapter(ChatActivity.this,massageList,userId);
        listView.setAdapter(massageListAdapter);
//        listView.setAdapter(massageListAdapter);
//        listView.setStackFromBottom(true);
        // sendButton 发送功能实现
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sentence = editText.getText().toString();

                if(!sentence.equals("")) {// 不空认为有效
                    EMUtil.sendMassage(sentence, toChatUsername);
                    EMMessage message = EMMessage.createTxtSendMessage(sentence, toChatUsername);
                    massageList.add(message);
                    massageListAdapter.notifyDataSetChanged();
                    lastMassageTime = Calendar.getInstance();// 保存最后一条信息的时间，用于返回时更新 contactList
                }

                editText.setText("");
                massageListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void findID() {
        // 组件初始化
        listView = findViewById(R.id.msg_list_view);
        sendButton = findViewById(R.id.send);
        editText = findViewById(R.id.input_text);
        massageList = new ArrayList<>();
        lastMassageTime = null;
        title = findViewById(R.id.title);
    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public void showInput(final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    // 监听手机返回键
    public void onBackPressed() {
//        Intent intent = new Intent();
//        intent.putExtra("msg",massageListAdapter.getLastMassageContent());
//        intent.putExtra("lastTime",lastMassageTime);
//        if(lastMassageTime != null)
//            setResult(RESULT_OK,intent);
//        else setResult(RESULT_CANCELED,intent);
        finish();
    }




    /**
     * 监听新消息接口
     * @param list
     */
    @Override
    public void onMessageReceived(List<EMMessage> list) {
        //收到消息
        Log.e("elt", "onMessageReceived: 回调开始");
        for(final EMMessage emMessage : list) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String from = emMessage.getFrom();//得到消息的来源，谁发的
                    if (true) {//如果消息是当前聊天对象发送过来的，则显示，否则不显示
//                System.out.println(emMessage.getBody().toString());
                        System.out.println(emMessage.getFrom().toString());
                        massageList.add(emMessage);
                        massageListAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }


}
/* 弃案
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
private void loadData() {
        // userid friendid  we need find all of (userid,friendid) and then the first one represented the msg1

//        new Thread() {
//            List<MsgHolder> msgHolders = new ArrayList<>();// 暂存数据，我们需要对其内信息通过时间戳排序
//            // 当解决了头像传递的问题后，该list可以废弃
//            @Override
//            public void run() {
//                System.out.println("开始导入数据");
//                Connection connections = connectDB();
//
//                if(connections != null) {
//                    askDB(userId, toChatUsername,connections,msgHolders);
//                    askDB(toChatUsername,userId,connections,msgHolders);
//
//                    // sort
//                    Collections.sort(msgHolders);
//                    // load
//                    for(int i = 0;i < msgHolders.size();i ++) {
//                        MsgHolder t = msgHolders.get(i);
//                        if(t.getUserId().equals(userId)) massageList.add(new Massage(t.getContent(),
//                                Massage.TYPE_SEND,R.drawable.waifu));
//                        else massageList.add(new Massage(t.getContent(),
//                                Massage.TYPE_RECEIVED,R.drawable.waifu_4));
//                    }
//                }
//
//            }
//        }.start();

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("完成装载");
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


 */