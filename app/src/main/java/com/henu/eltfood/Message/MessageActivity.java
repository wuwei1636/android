package com.henu.eltfood.Message;

import static android.app.Activity.RESULT_OK;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.henu.eltfood.DataSystem.MySQLConnections;
import com.henu.eltfood.Message.chat.ChatActivity;
import com.henu.eltfood.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.StreamSupport;

public class MessageActivity extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message, null);
        initContactActivity(view);
        return view;
    }

    private ListView lv = null;
    private List<ContactPerson> contactPersonList = new ArrayList<>();
    private ActivityResultLauncher<Intent> activityResultLauncher = null; // 连接 chat 页面和 contact 界面
    ContactPersonListAdapter contactPersonListAdapter = null;
    int selectedId = 0; // 选择的子项id
    private final int CHAT = 1;
    private void initContactActivity(View view) {
        // 装载测试数据
        loadListDateDemo();
        // init lv
        contactPersonListAdapter = new ContactPersonListAdapter(view.getContext(), contactPersonList);
        lv = view.findViewById(R.id.contact_list);
        lv.setAdapter(contactPersonListAdapter);
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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 1. 跳转聊天页面，跳入后载入数据，需要有历史信息，自己和对方的头像，id信息。
                // 2. 返回时返回最后一条 massage ，更新重绘该子项（交由adapter完成）
                Intent intent = new Intent();
                selectedId = i;
                intent.setClass(getActivity(), ChatActivity.class);
                startActivityForResult(intent,CHAT);
//                activityResultLauncher.launch(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);

        if(resultCode == RESULT_OK) {
            // 返回了lastmassage 和时间戳 更新信息和时间 重绘 contact view
            Calendar lastTime = (Calendar) result.getExtras().get("lastTime");
            // 转 ContactPerson
            int hour = lastTime.get(Calendar.HOUR);
            int min = lastTime.get(Calendar.MINUTE);
            String lastmsg = result.getStringExtra("msg");
            ContactPerson contactPerson = contactPersonList.get(selectedId);
            if(contactPerson == null) System.out.println("NULL");
            // 更新list 和 view
            contactPerson.setLast_massage_time(hour + ":" + min);
            contactPerson.setLast_massage(lastmsg);
            contactPersonListAdapter.notifyDataSetChanged();
        }else {
            System.out.println("妹说就是0卡");
        }
    }

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
//        for(int i = 0;i < 5;i ++) {
//            ContactPerson contactPerson = new ContactPerson("张三","订单已发送！","11:11",R.drawable.waifu_6);
//            contactPersonList.add(contactPerson);
//        }
    }
}
