package com.henu.eltfood.MyInformation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.henu.eltfood.DataClass.Account;
import com.henu.eltfood.R;

public class MyinfoSpace extends AppCompatActivity {

    private TextView MyinfSpaceBack;
    private TextView adname;
    private TextView adspace;
    private ImageView change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfospace);

        InitComponent();
        adname.setText(Account.getCurrentUser(Account.class).getNickname());
        adspace.setText(Account.getCurrentUser(Account.class).getAddress());

        MyinfSpaceBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MyinfoSpace.this, MyinfoSpaceChange.class);
                startActivity(it);

            }
        });
    }

    public void InitComponent(){
        MyinfSpaceBack = (TextView) findViewById(R.id.back2);
        adname = (TextView) findViewById(R.id.adname);
        adspace = (TextView) findViewById(R.id.adspace);
        change = (ImageView) findViewById(R.id.change);
    }
}