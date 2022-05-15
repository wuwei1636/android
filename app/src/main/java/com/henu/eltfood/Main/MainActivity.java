package com.henu.eltfood.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.henu.eltfood.DataSystem.MySQLConnections;
import com.henu.eltfood.R;
import com.henu.eltfood.pojo.User;
import com.henu.eltfood.util.Constant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private TextView login_name, login_password;
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = findViewById(R.id.login);
        TextView goto_register = findViewById(R.id.goto_register_view);
        login_name = findViewById(R.id.login_name);
        login_password = findViewById(R.id.login_password);

        Intent intent = super.getIntent();
        login_name.setText(intent.getStringExtra("name"));
        login_password.setText(intent.getStringExtra("password"));

        goto_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, register_view.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("yes");
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            con = MySQLConnections.getConnection();
                            System.out.println("yes");
                        } catch (Exception e) {
                            System.out.println("no");
                            e.printStackTrace();
                        }
                        try {
                            String sql = "select * from account where name=? and password=?";
                            String name_content = login_name.getText().toString();    //用户发送的信息
                            String password_content = login_password.getText().toString();
                            if (name_content.equals("") || name_content == null) {
                                Looper.prepare();
                                Toast.makeText(MainActivity.this, "请填写用户名", Toast.LENGTH_LONG).show();
                                Looper.loop();
                            } else if (password_content.equals("") || password_content == null) {
                                Looper.prepare();
                                Toast.makeText(MainActivity.this, "请填写用户名的密码", Toast.LENGTH_LONG).show();
                                Looper.loop();
                            } else {
                                if (con != null) {
                                    stmt = con.prepareStatement(sql);
                                    stmt.setString(1, name_content);
                                    stmt.setString(2, password_content);
                                    // 关闭事务自动提交 ,这一行必须加上
                                    con.setAutoCommit(false);
                                    ResultSet rs = stmt.executeQuery();//创建数据对象
                                    //清空上次发送的信息
                                    boolean t = rs.next();
                                    if (t) {
                                        user = new User();
                                        user.setId((rs.getInt("id")));
                                        user.setUsername((rs.getString("name")));
                                        user.setPassword((rs.getString("password")));
                                        user.setUsername2((rs.getString("username")));
                                        user.setAddress(rs.getString("address"));
                                        Intent intent = new Intent(MainActivity.this, First_viewActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Looper.prepare();
                                        Toast.makeText(MainActivity.this, "账户不存在", Toast.LENGTH_LONG).show();
                                        Looper.loop();
                                    }
                                    Constant.name = user.getUsername();
                                    Constant.id  = user.getId();
                                    Constant.password = user.getPassword();
                                    Constant.username = user.getUsername2();
                                    Constant.address = user.getAddress();
                                    con.commit();
//                                    rs.close();
//                                    stmt.close();
                                    MySQLConnections.closeResource(null,rs,stmt);
                                }
                            }
                        } catch (SQLException e) {
                        }
                    }
                }.start();
            }
        });
    }
}