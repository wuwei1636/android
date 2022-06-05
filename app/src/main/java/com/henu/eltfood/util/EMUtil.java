package com.henu.eltfood.util;

import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;

public class EMUtil {
    public static final String TAG = "EMUtil";
    /**
     * 环信注册，只做数据同步作用
     */
    public static void EMsignup(String name,String pw) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(name,pw);
                    Log.e("EMelt", "注册成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e("EMelt", "注册失败" + e.getErrorCode() + " " +
                            e.getMessage());
                }
            }
        }).start();
    }
    /**
     * 环信登录，只做数据同步使用
     */
    public static void EMsignin(String name, String pw) {

        EMClient.getInstance().login(name, pw, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("EMelt","登录成功");
            }

            @Override
            public void onError(int i, String s) {
                Log.e("EMelt","登录失败 " + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
    /**
     * 退出登录
     */
    public static void loginOut() {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
////                        Toast.makeText(activity, "您已退出登录！", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, final String message) {
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(activity, "退出失败，" + message, Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });
    }
    /**
     * 登录状态判断
     */
    public static boolean isLogin() {
        return EMClient.getInstance().isLoggedIn();
    }

    /**
     * 单聊信息发送
     */
    public static void sendMassage(String content, String toChatUsername) {

        //创建一条文本消息，`content` 为消息文字内容，`toChatUsername` 为对方用户或者群聊的 ID，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        //设置消息类型，即设置`Message` 类的 `ChatType` 属性。该属性的值为 `Chat`、`GroupChat` 和 `ChatRoom`，表明该消息是单聊，群聊或聊天室消息，默认为单聊。若为群聊，设置 `ChatType` 为 `GroupChat`。
        message.setChatType(EMMessage.ChatType.Chat);
        //发送消息时可以设置 `EMCallBack` 的实例，获得消息发送的状态。可以在该回调中更新消息的显示状态。例如消息发送失败后的提示等等。
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "发送成功");
            }
            @Override
            public void onError(int code, String error) {
                Log.e(TAG, "onError: 发送失败" + error );
            }
            @Override
            public void onProgress(int progress, String status) {
            }

        });
        //发送消息。
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 获取当前用户名
     * @return
     */
    public static String getCurrentName() {
        return EMClient.getInstance().getCurrentUser().toString();
    }


    /**
     * 添加好友
     * 添加好友后，只有当对方在线时，才会显示到好友列表中
     * @param toAddUsername
     */
    public static void addFriend(String toAddUsername) {
        try {
            EMClient.getInstance().contactManager().addContact(toAddUsername,"无");
            Log.i(TAG, "addFriend: 加好友成功");
        } catch (HyphenateException e) {
            e.printStackTrace();
            Log.e(TAG, "addFriend: 加好友失败 " + e.getMessage() );
        }
    }

}
