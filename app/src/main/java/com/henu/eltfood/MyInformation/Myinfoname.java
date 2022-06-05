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

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class MyinfoName extends AppCompatActivity  {

    private EditText MyinfNameNickNameEditText;
    private TextView MyinfNameBack;
    private Button MyinfNameSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfoname);

        InitComponent();

        MyinfNameNickNameEditText.setText(Account.getCurrentUser(Account.class).getNickname());
        MyinfNameBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        MyinfNameSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NickName = MyinfNameNickNameEditText.getText().toString();
                if (NickName.equals("")){
                    show("昵称不能为空");
                }
                else{
                    Account currentuser = Account.getCurrentUser(Account.class);
                    currentuser.setNickname(NickName);
                    currentuser.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                Snackbar.make(view, "昵称修改成功" , Snackbar.LENGTH_LONG).show();
                            }
                            else{
                                Snackbar.make(view, "昵称修改失败" , Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void InitComponent(){
        MyinfNameNickNameEditText = this.findViewById(R.id.MyinfNameNickNameEditText);
        MyinfNameBack = this.findViewById(R.id.MyinfnameBack);
        MyinfNameSubmit = this.findViewById(R.id.MyinfNameSubmit);
    }

    public void show(String content){
        Toast.makeText(MyinfoName.this,content,Toast.LENGTH_LONG).show();
    }
}