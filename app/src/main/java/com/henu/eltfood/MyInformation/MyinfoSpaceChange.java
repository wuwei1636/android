package com.henu.eltfood.MyInformation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.henu.eltfood.DataClass.Account;
import com.henu.eltfood.R;

import java.sql.Connection;
import java.sql.PreparedStatement;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class MyinfoSpaceChange extends AppCompatActivity implements View.OnClickListener {
    Connection con;
    PreparedStatement stmt = null;
    String sql = null;
    String sql1 = null;
    String sql2 = null;
    EditText efir = null;
    EditText esec = null;
    EditText ethr = null;
    TextView back4;
    Button btn1,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfospacechange);
        Bmob.initialize(this, "230f0d485f24c49e8bb460514596714a");

        InitComponent();

        efir.setText(Account.getCurrentUser(Account.class).getAddress());
        back4.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.MyinfnameBack:
                finish();
                break;
            case R.id.sp_change:
                String e1 = efir.getText().toString();
                String e2 = esec.getText().toString();
                String e3 = ethr.getText().toString();
                if(e1.equals("") || e2.equals("")||e3.equals("")){
                    show("请补全信息");
                }else{
                    Account currentuser = Account.getCurrentUser(Account.class);
//                    currentuser.setNickname(e2);
                    currentuser.setAddress(e1);
//                    currentuser.setMobilePhoneNumber(e3);
                    currentuser.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                Snackbar.make(v, "信息修改成功" , Snackbar.LENGTH_LONG).show();
                            }
                            else{
                                Snackbar.make(v, "信息修改失败" , Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                break;
            case R.id.sp_unchange:
                finish();
                break;
            default:
                break;
        }
    }

    public void InitComponent(){
        back4 = (TextView) findViewById(R.id.MyinfnameBack);
        efir = (EditText) findViewById(R.id.sp_fir);
        esec = (EditText) findViewById(R.id.sp_sec);
        ethr = (EditText) findViewById(R.id.sp_thr);
        btn1= (Button) findViewById(R.id.sp_change);
        btn2 = (Button) findViewById(R.id.sp_unchange);
    }

    public void show(String content){
        Toast.makeText(MyinfoSpaceChange.this,content,Toast.LENGTH_LONG).show();
    }

}