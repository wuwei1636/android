package com.henu.eltfood.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.henu.eltfood.DataClass.Account;
import com.henu.eltfood.DataClass.AccountCount;
import com.henu.eltfood.R;
import com.henu.eltfood.util.EMUtil;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class register_view extends AppCompatActivity {

    private TextView register_name, register_password1, register_password2;
    private Account account; //当前注册的Account
    public Intent intent = null;
    private AccountCount account_count;
    String name_content, password_content1, password_content2;
    Button back_to_login, register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);
        Bmob.initialize(this, "230f0d485f24c49e8bb460514596714a");

        init_component();
        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register_view.this, MainActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_content = register_name.getText().toString();
                password_content1 = register_password1.getText().toString();
                password_content2 = register_password2.getText().toString();

                if (name_content.equals(""))
                {
                    show("请填写用户名");
                }
                else if (password_content1.equals("") || password_content2.equals("")){
                    show("密码不能为空");
                }
                else if (!password_content1.equals(password_content2)) {
                    show("两次密码不同");
                }
                else{
                    account = new Account(name_content, password_content1);
                    account.setId(account_count.cnt + 1);
                    account.setNickname("用户" + String.valueOf(account.getId()));
                    account.signUp(new SaveListener<Account>() {
                        @Override
                        public void done(Account account, BmobException e) {
                            if (e == null) {
                                show("注册成功");
                                EMregister();
                                account_count.cnt = account_count.cnt + 1;
                                account_count.update_cnt();
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
                                register_view.this.getIntent().putExtra("name", name_content);
                                register_view.this.getIntent().putExtra("password", password_content1);
                                register_view.this.setResult(RESULT_OK, register_view.this.getIntent());
                                register_view.this.finish();
                            } else {
                                Snackbar.make(view, "尚未失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 环信用户注册
     */
    private void EMregister() {
        EMUtil.EMsignup(name_content,password_content1);
    }
    public void init_component(){
        register_name = findViewById(R.id.register_name);
        register_password1 = findViewById(R.id.register_password1);
        register_password2 = findViewById(R.id.register_password2);
        back_to_login = findViewById(R.id.back_to_login);
        register = findViewById(R.id.register);
        intent = new Intent(register_view.this, MainActivity.class);
        account_count = new AccountCount();
        account_count.load_cnt();
    }

    public void show(String content){
        Toast.makeText(register_view.this,content,Toast.LENGTH_LONG).show();
    }



}