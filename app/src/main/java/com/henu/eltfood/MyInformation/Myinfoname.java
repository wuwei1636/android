package com.henu.eltfood.MyInformation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.henu.eltfood.DataSystem.MySQLConnections;
import com.henu.eltfood.Main.register_view;
import com.henu.eltfood.R;
import com.henu.eltfood.util.Constant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Myinfoname extends AppCompatActivity  {
    Connection con;
    PreparedStatement stmt = null;
    String sql = null;
    String sql1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfoname);

        EditText editq = super.findViewById(R.id.edit1);
        TextView back4 = super.findViewById(R.id.back4);
        Button btn2 = super.findViewById(R.id.mbtn2);

        editq.setText(Constant.username);
        back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            con = MySQLConnections.getConnection();
                        }catch (Exception e){
                            Looper.prepare();
                            Toast.makeText(Myinfoname.this, "连接数据库失败", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            e.printStackTrace();
                        }
                        try{
                            sql1 = "update account set username = ? where id = ?";
                            sql = "select count(1) as sl from text";
                            stmt = con.prepareStatement(sql1);
                        }catch (Exception e){
                            Looper.prepare();
                            Toast.makeText(Myinfoname.this, "预编译失败", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            e.printStackTrace();
                        }
                        try{
                            String fir = editq.getText().toString();
                            stmt.setString(1,fir);
                            stmt.setInt(2, Constant.id);
                            int rs = stmt.executeUpdate();
                            if(rs > 0){
                                Looper.prepare();
                                Toast.makeText(Myinfoname.this,"修改成功", Toast.LENGTH_LONG).show();
                                Looper.loop();
                            }
                            else{
                                Looper.prepare();
                                Toast.makeText(Myinfoname.this, "修改失败", Toast.LENGTH_LONG).show();
                                Looper.loop();
                            }

                        }catch (Exception e){
                            Looper.prepare();
                            Toast.makeText(Myinfoname.this, "获取失败", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            e.printStackTrace();
                        }
                    MySQLConnections.closeResource(con,null,null);
                    }
                }).start();
            }
        });

    }
}