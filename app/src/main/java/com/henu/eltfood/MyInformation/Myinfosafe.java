package com.henu.eltfood.MyInformation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.henu.eltfood.R;
import com.henu.eltfood.DataClass.Account;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class MyinfoSafe extends AppCompatActivity {

    private EditText MyinfSafePassword1;
    private EditText MyinfSafePassword2;
    private Button MyinfSafeSubmit;
    private TextView MyinfSafeBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfosafe);

        InitComponent();

        MyinfSafeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        MyinfSafeSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password_content1 = MyinfSafePassword1.getText().toString();
                String password_content2 = MyinfSafePassword2.getText().toString();
                if (password_content1.equals("") || password_content2.equals("")){
                    show("密码不能为空");
                }
                else if (!password_content1.equals(password_content2)){
                    show("密码不一致");
                }
                else{
                    Account currentuser = Account.getCurrentUser(Account.class);
                    currentuser.setPassword(password_content1);
                    currentuser.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                Snackbar.make(view, "密码修改成功" , Snackbar.LENGTH_LONG).show();
                            }
                            else{
                                Snackbar.make(view, "密码修改失败" , Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }

    public void InitComponent() {
        MyinfSafePassword1 = super.findViewById(R.id.MyinfSafePassword1);
        MyinfSafePassword2 = super.findViewById(R.id.MyinfSafePassword2);
        MyinfSafeSubmit = super.findViewById(R.id.MyinfSafeSubmit);
        MyinfSafeBack = super.findViewById(R.id.MyinfSafeBack);
    }

    public void show(String content){
        Toast.makeText(MyinfoSafe.this,content,Toast.LENGTH_LONG).show();
    }
}