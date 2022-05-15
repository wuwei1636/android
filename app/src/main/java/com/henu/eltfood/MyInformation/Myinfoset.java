package com.henu.eltfood.MyInformation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.henu.eltfood.DataSystem.MySQLConnections;
import com.henu.eltfood.R;
import com.henu.eltfood.pojo.User;
import com.henu.eltfood.util.Constant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Myinfoset extends AppCompatActivity {

    Connection con;
    PreparedStatement stmt = null;
    String sql = null;
    String sql1 = null;
    User user = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfoet);

        TextView tex1 = super.findViewById(R.id.tex1);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    con = MySQLConnections.getConnection();
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    sql = "select * from account where name = ?";
                    stmt = con.prepareStatement(sql);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    stmt.setString(1,Constant.name);
                    ResultSet rs = stmt.executeQuery();
                    while(rs.next()){
                        user = new User();
                        user.setId((rs.getInt("id")));
                        user.setUsername((rs.getString("name")));
                        user.setPassword((rs.getString("password")));
                        user.setUsername2((rs.getString("username")));
                    }
                    String name2 = user.getUsername2();
                    tex1.setText(name2);

                }catch (Exception e){
                    e.printStackTrace();
                }


                MySQLConnections.closeResource(con,null,null);
            }
        }).start();

        // 账号安全  昵称  收货地址
        TextView back1 = (TextView)findViewById(R.id.back1);
        LinearLayout nsafe = (LinearLayout) findViewById(R.id.nsafe);
        LinearLayout nnmame = (LinearLayout) findViewById(R.id.nname);
        LinearLayout nspace = (LinearLayout) findViewById(R.id.nplace);

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it0 = Myinfoset.super.getIntent();
                finish();
            }
        });
        nsafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1 = new Intent(Myinfoset.this,Myinfosafe.class);
                startActivity(it1);
            }
        });
        nnmame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                Intent it2 = new Intent(Myinfoset.this,Myinfoname.class);
                startActivity(it2);
            }
        });
        nspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it3 = new Intent(Myinfoset.this,Myinfospace.class);
                startActivity(it3);
            }
        });

    }
}