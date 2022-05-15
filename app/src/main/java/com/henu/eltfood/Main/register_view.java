package com.henu.eltfood.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.henu.eltfood.DataSystem.MySQLConnections;
import com.henu.eltfood.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class register_view extends AppCompatActivity {

    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private TextView register_name, register_password1, register_password2;
    boolean register_success = false;
    public Intent intent = null;
    String name_content, password_content1, password_content2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);

        register_name = findViewById(R.id.register_name);
        register_password1 = findViewById(R.id.register_password1);
        register_password2 = findViewById(R.id.register_password2);
        Button back_to_login = findViewById(R.id.back_to_login);
        Button register = findViewById(R.id.register);
        intent = new Intent(register_view.this, MainActivity.class);

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
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            con = MySQLConnections.getConnection();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String sql1 = "select * from account where name=?";
                            name_content = register_name.getText().toString();
                            password_content1 = register_password1.getText().toString();
                            password_content2 = register_password2.getText().toString();
                            if (con != null) {
                                if (name_content.equals(""))
                                {
                                    Looper.prepare();
                                    Toast.makeText(register_view.this, "请填写用户名", Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                }
                                else{
                                    stmt = con.prepareStatement(sql1);
                                    stmt.setString(1, name_content);
                                    con.setAutoCommit(false);
                                    ResultSet rs = stmt.executeQuery();
                                    if (rs.next()) {
                                        Looper.prepare();
                                        Toast.makeText(register_view.this, "该用户名已存在", Toast.LENGTH_LONG).show();
                                        Looper.loop();
                                    }
                                    else if (password_content1.equals("") || password_content2.equals("")){
                                        Looper.prepare();
                                        Toast.makeText(register_view.this, "密码不能为空", Toast.LENGTH_LONG).show();
                                        Looper.loop();
                                    }
                                    else if (!password_content1.equals(password_content2)) {
                                        Looper.prepare();
                                        Toast.makeText(register_view.this, "两次输入密码不同", Toast.LENGTH_LONG).show();
                                        Looper.loop();
                                    }
                                    else{
                                        String sql2 = "insert into account(name,password)  values(?,?)";
                                        stmt = con.prepareStatement(sql2);
                                        stmt.setString(1,name_content);
                                        stmt.setString(2, password_content1);
                                        con.setAutoCommit(false);
                                        stmt.addBatch();
                                        stmt.executeBatch();
                                        con.commit();

                                        register_success = true;
                                        Looper.prepare();
                                        Toast.makeText(register_view.this, "注册成功", Toast.LENGTH_LONG).show();
                                        Looper.loop();

                                        MySQLConnections.closeResource(null,rs,stmt);
                                    }
                                }
                            }
                        }
                        catch (SQLException e) {}
                    }
                }.start();
                try { Thread.sleep(2000); }
                catch (InterruptedException e){}
                if (register_success){
                    intent.putExtra("name",name_content);
                    intent.putExtra("password",password_content1);
                    startActivity(intent);
                }
            }
        });
    }
}