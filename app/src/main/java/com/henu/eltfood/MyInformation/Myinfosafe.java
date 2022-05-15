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
import com.henu.eltfood.R;
import com.henu.eltfood.pojo.User;
import com.henu.eltfood.util.Constant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Myinfosafe extends AppCompatActivity {

    Connection con;
    PreparedStatement stmt = null;
    String sql = null;
    String sql1 = null;
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfosafe);


        EditText pwd1 = super.findViewById(R.id.pwd1);
        EditText pwd2 = super.findViewById(R.id.pwd2);
        Button btn3  = super.findViewById(R.id.mbtn3);
        TextView back3 = super.findViewById(R.id.back3);

        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                finish();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String pd1 = pwd1.getText().toString();
                        String pd2 = pwd2.getText().toString();

                        if(pd1 == null){
                            Toast.makeText(Myinfosafe.this, "请输入密码", Toast.LENGTH_LONG).show();
                        }else if(pd2 == null){
                            Toast.makeText(Myinfosafe.this, "请输入确认密码", Toast.LENGTH_LONG).show();
                        }else if(!pd1.equals(pd2)){
                            Toast.makeText(Myinfosafe.this, "两次密码应一致", Toast.LENGTH_LONG).show();
                        }else {
                            try{
                                con = MySQLConnections.getConnection();
                            }catch (Exception e){
                                Looper.prepare();
                                Toast.makeText(Myinfosafe.this, "连接数据库失败", Toast.LENGTH_LONG).show();
                                Looper.loop();
                                e.printStackTrace();
                            }
                            try{
                                sql1 = "update account set password = ? where id = ?";
                                sql = "select count(1) as sl from text";
                                stmt = con.prepareStatement(sql1);
                            }catch (Exception e){
                                Looper.prepare();
                                Toast.makeText(Myinfosafe.this, "预编译失败", Toast.LENGTH_LONG).show();
                                Looper.loop();
                                e.printStackTrace();
                            }
                            try{
                                String fir = pwd1.getText().toString();
                                stmt.setString(1,fir);
                                stmt.setInt(2, Constant.id);
                                int rs = stmt.executeUpdate();
                                if(rs > 0){
                                    Looper.prepare();
                                    Toast.makeText(Myinfosafe.this,"修改成功", Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                }
                                else{
                                    Looper.prepare();
                                    Toast.makeText(Myinfosafe.this, "修改失败", Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                }

                            }catch (Exception e){
                                Looper.prepare();
                                Toast.makeText(Myinfosafe.this, "获取失败", Toast.LENGTH_LONG).show();
                                Looper.loop();
                                e.printStackTrace();
                            }
                            MySQLConnections.closeResource(con,null,null);
                        }
                    }

                }).start();
            }
        });

    }
}