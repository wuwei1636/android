package com.henu.eltfood.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.henu.eltfood.DataClass.Account;
import com.henu.eltfood.R;
import com.henu.eltfood.util.EMUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class MainActivity extends AppCompatActivity {
    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private TextView login_name, login_password;
    private Account account = null;
    private Button login;
    private TextView goto_register;
    ActivityResultLauncher<Intent> launcher = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Bmob.initialize(this, "230f0d485f24c49e8bb460514596714a");

        init_component();
        checkUserState();
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        if (result.getResultCode() == RESULT_OK) {
                            login_name.setText(data.getStringExtra("name"));
                            login_password.setText(data.getStringExtra("password"));
                        }
                    }
                });

        goto_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, register_view.class);
//                startActivityIfNeeded(intent, RESULT_OK);
                launcher.launch(intent2);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("yes");
                String name_content = login_name.getText().toString();    //用户发送的信息
                String password_content = login_password.getText().toString();
                if (name_content.equals("")) {
                    show("请填写用户名");
                } else if (password_content.equals("")) {
                    show("请填写用户名的密码");
                } else {
                    // 环信登录
                    EMlogin();

                    BmobUser.loginByAccount(name_content, password_content, new LogInListener<Account>() {
                        @Override
                        public void done(Account account, BmobException e) {
                            if (e == null) {
                                startActivity(new Intent(MainActivity.this,First_viewActivity.class));
                            } else {
                                Snackbar.make(view, "登录失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });

//                    if(Account.getCurrentUser(Account.class).getUsername() == null) {
//                        Log.e("elt_login", "onClick: 用户为空");
//                    }
//                    Log.e("elt_login", "onClick: 当前用户是" + EMClient.getInstance().getCurrentUser() +
//                            "手写的获取的是：" + Account.getCurrentUser(Account.class).getUsername());
                }
            }
        });
    }

    private void checkUserState() {
        if(EMUtil.isLogin()) {
            Intent intent = new Intent(MainActivity.this, First_viewActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 环信登录
     */
    private void EMlogin() {
        if(EMUtil.isLogin()) EMUtil.loginOut();
        EMUtil.EMsignin(login_name.getText().toString(),login_password.getText().toString());
    }

    public void init_component(){
        login = findViewById(R.id.login);
        goto_register = findViewById(R.id.goto_register_view);
        login_name = findViewById(R.id.login_name);
        login_password = findViewById(R.id.login_password);
    }
    public void show(String content) {
        Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
    }


}