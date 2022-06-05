package com.henu.eltfood.Message;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.henu.eltfood.DataClass.Account;
import com.henu.eltfood.DataClass.FoodInTrade;
import com.henu.eltfood.DataSystem.ImgOperation;
import com.henu.eltfood.DataSystem.MySQLConnections;
import com.henu.eltfood.Main.First_viewActivity;
import com.henu.eltfood.Message.chat.ChatActivity;
import com.henu.eltfood.R;
import com.henu.eltfood.util.EMUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MessageActivity extends Fragment {
    private final static int CHAT = 1;

    private final static String TAG = "EMelt-contactlist";

    int selectedId = 0; // 选择的子项id

    private Button addbtn = null;

    private RecyclerView lv = null;

    private List<ContactPerson> contactPersonList = new ArrayList<>();

    private ActivityResultLauncher<Intent> activityResultLauncher = null; // 连接 chat 页面和 contact 界面

    ContactPersonListAdapter contactPersonListAdapter = null;

    boolean threadIsFinished = false;

    private TextView textView = null;
    private View view1;
    LayoutInflater inflater1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message, null);
        findID(view);
        setContactPersonList(view);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) textView.getLayoutParams();
        params.width = width;
        view1 = view;
        inflater1 = inflater;
        return view;
    }

    private void findID(View view) {
//        addbtn = view.findViewById(R.id.add_friend_btn);
        lv = view.findViewById(R.id.contact_list);
        textView = view.findViewById(R.id.xiaoxi);
    }


    public void setContactPersonList(View view) {
        loadData();
        contactPersonListAdapter = new ContactPersonListAdapter(view.getContext(), contactPersonList);
        lv.setAdapter(contactPersonListAdapter);
        lv.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL));


//        lv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(),ChatActivity.class);
//                Bundle bundle = new Bundle();
//
//                bundle.putString("username",contactPersonList.get().getName());
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(view.getContext(),ChatActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("username",contactPersonList.get(i).getName());
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });

    }

    public void loadData() {

        if(EMUtil.isLogin()) Log.i(TAG, "getFriendList: 已经登录");
        MyThread myThread = new MyThread();

        myThread.start();
        while(myThread.isAlive());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        contactPersonList.clear();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 加载列表的线程结束后的回调
     * @param usernames
     */
    public void callBack(List<String> usernames) {
//        if(usernames == null || usernames.size() == 0) {
//            view1 = inflater1.inflate(R.layout.message, null);
//            return;
//        }
        if(usernames != null) {
            for(int i = 0;i < usernames.size();i ++) {
                System.out.print("正在加载第" + i + "位好友：");
                ContactPerson person = new ContactPerson();
                contactPersonList.add(person);
                person.setName(usernames.get(i));
                person.setLast_massage("");
                person.setLast_massage_time("");
                System.out.println("开始getImg");
                getImg(person.getName(),i);
//                person.setHeader(getImg(usernames.get(i)));
                if(person.getHeader() != null)
                    Log.d(TAG, "callBack: 成功获取图片");

            }
            threadIsFinished = true;
            Log.e("EMelt-contactlist", "getFriendList: 成功获取" + usernames.size());
        }
//        contactPersonListAdapter.notify();
    }

    private void getImg(String userID,int pos) {
        ImgOperation operation = new ImgOperation(MessageActivity.this.getContext());
        BmobQuery<Account> query = new BmobQuery<>();
        query.findObjects(new FindListener<Account>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void done(List<Account> list, BmobException e) {
                if(e == null) {
                    for(Account act : list) {
                        if(userID == null) {
                            Log.d(TAG, "done: 已经执行");
                            contactPersonListAdapter.notifyDataSetChanged();
                            continue;
                        }
                        if(act.getUsername().equals(userID)) {
                            System.out.println("找到图片");
                            Bitmap bitmap = operation.GetBitmap(act.getImg());
                            contactPersonList.get(pos).setHeader(bitmap);
                            contactPersonListAdapter.notifyDataSetChanged();
                            if(contactPersonList.get(pos).getHeader() != null) Log.d(TAG, "done: 此时图片不空");
                        }
                    }
                }else {
                    Log.e("elt", "done: 用户获取失败" + e.getMessage());
                }
            }
        });
    }

    /**
     * 加载好友列表的线程
     */
    class MyThread extends Thread {
        List<String > usernames = null;

        @Override
        public void run() {
            try{
                usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                callBack(usernames);
            } catch (HyphenateException e) {
                e.printStackTrace();
                Log.e(TAG, "run: 获取失败" + e.getMessage());
            }

        }
    }
}

/*
 private void initContactActivity(View view) {
        // 装载测试数据
//        loadListDateDemo();
        // init button 写个加好友的弹窗

// 暂时废除
//        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if(result.getData() != null && result.getResultCode() == RESULT_OK) {
//                    // 返回了lastmassage 和时间戳 更新信息和时间 重绘 contact view
//                    Calendar lastTime = (Calendar) result.getData().getExtras().get("lastTime");
//                    // 加入list
//                    int hour = lastTime.get(Calendar.HOUR);
//                    int min = lastTime.get(Calendar.MINUTE);
//
//                    String lastmsg = result.getData().getStringExtra("msg");
//                    ContactPerson contactPerson = contactPersonList.get(selectedId);
//                    contactPerson.setLast_massage(lastmsg);
//                    // 更新
//                    contactPerson.setLast_massage_time(hour + ":" + min);
//                    contactPersonList.set(selectedId,contactPerson);
//                    messageListAdapter.notifyDataSetChanged();
//
//                }else {
//                    System.out.println("妹说就是0卡");
//                }
//            }
//        });
    }

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent result) {
//        super.onActivityResult(requestCode, resultCode, result);
//        if(resultCode == RESULT_OK) {
//            // 返回了lastmassage 和时间戳 更新信息和时间 重绘 contact view
//            Calendar lastTime = (Calendar) result.getExtras().get("lastTime");
//            // 转 ContactPerson
//            int hour = lastTime.get(Calendar.HOUR);
//            int min = lastTime.get(Calendar.MINUTE);
//            String lastmsg = result.getStringExtra("msg");
//            ContactPerson contactPerson = contactPersonList.get(selectedId);
//            if(contactPerson == null) System.out.println("NULL");
//            // 更新list 和 view
//            contactPerson.setLast_massage_time(hour + ":" + min);
//            contactPerson.setLast_massage(lastmsg);
//            contactPersonListAdapter.notifyDataSetChanged();
//        }else {
//            System.out.println("妹说就是0卡");
//        }
//    }

 // Test data
public void loadListDateDemo() {
        new Thread() {
            @Override
            public void run() {
                System.out.println("开始导入数据");
                Connection connections = null;
                try {
                    connections = MySQLConnections.getConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                PreparedStatement stmt = null;
                if (connections != null) {
                    System.out.println("查询中");

                    String sql = "select * from tb_friend where userid=?";
                    String userId = "mxrush";
                    ResultSet res = null;
                    try {
                        stmt = connections.prepareStatement(sql);
                        stmt.setString(1, userId);
                        res = stmt.executeQuery();
                    } catch (SQLException throwables) {
                        System.out.println("查询失败");
                        throwables.printStackTrace();
                    }

                    System.out.println("开始导出数据");
                    while (true) {
                        try {
                            if (!res.next()) break;
                            String friendId = res.getString("friends_id");
                            System.out.println(friendId);
                            ContactPerson contactPerson = new ContactPerson(friendId, "None", "11:11", R.drawable.waifu_6);
                            contactPersonList.add(contactPerson);
                        } catch (SQLException throwables) {
                            System.out.println("导出失败");
                            throwables.printStackTrace();
                        }
                    }
                    try {
                        connections.setAutoCommit(false);
                        stmt.addBatch();
                        stmt.executeBatch();
                        connections.commit();
                        res.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }

        }.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
 */

