package com.henu.eltfood.Front;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.henu.eltfood.R;

public class FrontManage extends AppCompatActivity {
    ImageView back;
    ImageView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_manage);
        InitComponent();
        LoadActivity();
    }
    void InitComponent() {
        back = findViewById(R.id.manage_back_home);
        add = findViewById(R.id.add_food);
    }
    void LoadActivity() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrontManage.this, AddFoods.class);
                startActivity(intent);
            }
        });
    }
}