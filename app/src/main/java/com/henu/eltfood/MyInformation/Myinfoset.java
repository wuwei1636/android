package com.henu.eltfood.MyInformation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.henu.eltfood.DataClass.Account;
import com.henu.eltfood.R;
import com.henu.eltfood.util.ActivityCollectorUtil;
import com.henu.eltfood.util.EMUtil;

import cn.bmob.v3.BmobUser;

;

public class MyinfoSet extends AppCompatActivity {

    private TextView MyinfSetNickName;
    private TextView MyinfSetBack;
    private LinearLayout nsafe;
    private LinearLayout nnmame;
    private LinearLayout nspace;
    private Button MyinfSetLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfoset);
        ActivityCollectorUtil.addActivity(MyinfoSet.this);

        InitComponent();
        MyinfSetNickName.setText(BmobUser.getCurrentUser(Account.class).getNickname());

        MyinfSetLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EMUtil.loginOut();
                BmobUser.logOut();
                ActivityCollectorUtil.finishAllActivity();
            }
        });
        MyinfSetBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        nsafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1 = new Intent(MyinfoSet.this, MyinfoSafe.class);
                startActivity(it1);
            }
        });
        nnmame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it2 = new Intent(MyinfoSet.this, MyinfoName.class);
                startActivity(it2);
            }
        });
        nspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it3 = new Intent(MyinfoSet.this, MyinfoSpace.class);
                startActivity(it3);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(MyinfoSet.this);
    }

    public void InitComponent(){
        // 账号安全  昵称  收货地址
        MyinfSetNickName = super.findViewById(R.id.tex1);
        MyinfSetBack = (TextView)findViewById(R.id.back1);
        nsafe = (LinearLayout) findViewById(R.id.nsafe);
        nnmame = (LinearLayout) findViewById(R.id.nname);
        nspace = (LinearLayout) findViewById(R.id.nplace);
        MyinfSetLogout = this.findViewById(R.id.mbtn1);
    }

}