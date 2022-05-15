package com.henu.eltfood.Front;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.henu.eltfood.R;

public class FrontConsumerOrder extends AppCompatActivity {
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_consumer_order);
        LoadActivity(); //为界面的所有需要增加监听事件的组件加载监听事件
    }
    void LoadActivity() {
        back = findViewById(R.id.corder_back_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}