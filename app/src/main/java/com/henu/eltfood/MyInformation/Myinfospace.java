package com.henu.eltfood.MyInformation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.henu.eltfood.R;
import com.henu.eltfood.util.Constant;

public class Myinfospace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfospace);

        TextView back2 = (TextView) findViewById(R.id.back2);
        TextView adname = (TextView) findViewById(R.id.adname);
        TextView adspace = (TextView) findViewById(R.id.adspace);

        adname.setText(Constant.username);
        adspace.setText(Constant.address);

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                finish();
            }
        });
    }
}