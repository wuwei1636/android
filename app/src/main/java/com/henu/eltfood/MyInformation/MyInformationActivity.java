package com.henu.eltfood.MyInformation;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.henu.eltfood.DataClass.Account;
import com.henu.eltfood.DataSystem.ImgOperation;
import com.henu.eltfood.Front.AddFoods;
import com.henu.eltfood.Front.FrontConsumerOrder.FrontConsumerOrder;
import com.henu.eltfood.Front.FrontDrinks;
import com.henu.eltfood.Front.FrontFood;
import com.henu.eltfood.Front.GoodsManager.FrontManage;
import com.henu.eltfood.Order.Fragment1;
import com.henu.eltfood.Order.Fragment2;
import com.henu.eltfood.Order.Fragment3;
import com.henu.eltfood.Order.Fragment5;
import com.henu.eltfood.Order.Fragment6;
import com.henu.eltfood.R;
import com.henu.eltfood.util.ImgPathOperation;

import java.net.URL;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class MyInformationActivity extends Fragment {

    private TextView MyinfSetting;
    private TextView MyinfNickName;
    private CircleImageView MyinfImg;
    private ImgOperation op;
    private Uri uri = null;
    private Context context;
    //点击页面中间的待付款等事件发生跳转
    /*
    private LinearLayout myinfall;
    private LinearLayout myinfshou;
    private LinearLayout myinfpay;
    private LinearLayout myinfcomment;
    private LinearLayout myinftui;*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myinformation, null);
        context = view.getContext();
        InitComponent(view);
        MyinfNickName.setText(Account.getCurrentUser(Account.class).getNickname());
        MyinfImg.setImageResource(R.drawable.account);
        Handler handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                MyinfImg.setImageBitmap((Bitmap) msg.obj);
                System.out.println("头像更新完毕");
            }
        };


        MyinfImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        //相册
                        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                            //相册中的照片都是存储在SD卡上的，需要申请运行时权限，WRITE_EXTERNAL_STORAGE是危险权限，表示同时授予程序对SD卡的读和写的能力
                            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }else {
                            // 打开相册
                            Intent AlbumIntent = new Intent("android.intent.action.GET_CONTENT", null);
                            AlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                            MyInformationActivity.this.startActivityForResult(AlbumIntent, 1);
                        }

                };
            });

        // 点击设置，跳转到子界面
        MyinfSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), MyinfoSet.class);
                startActivity(it);
            }
        });



        new Thread(){
            @Override
            public void run() {
                System.out.println("开始加载用户头像");
                Bitmap bmp = op.GetBitmap(BmobUser.getCurrentUser(Account.class).getImg());
                Message msg = new Message();
                msg.what = 0;
                msg.obj = bmp;
                handler.sendMessage(msg);
                System.out.println("加载完毕");
            }
        }.start();

        //点击跳转到待收货等页面
        /*
        myinfall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Fragment1.class);
                startActivity(intent);
            }
        });
        myinfpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Fragment2.class);
                startActivity(intent);
            }
        });
        myinfshou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Fragment3.class);
                startActivity(intent);
            }
        });

        myinfcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Fragment5.class);
                startActivity(intent);
            }
        });
        myinftui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Fragment6.class);
                startActivity(intent);
            }
        });*/

        return view;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    public void InitComponent(View view){
        MyinfSetting =  view.findViewById(R.id.MyinfSetting);
        MyinfNickName = view.findViewById(R.id.MyinfNickName);
        MyinfImg = view.findViewById(R.id.MyinfImg);
        op = new ImgOperation(view.getContext());

        //点击页面中间的待付款等事件发生跳转
        /*
        myinfall = view.findViewById(R.id.myinfall);
        myinfshou = view.findViewById(R.id.myinfshou);
        myinfpay = view.findViewById(R.id.myinfpay);
        myinfcomment = view.findViewById(R.id.myinfcomment);
        myinftui = view.findViewById(R.id.myinftui);*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (data != null) uri = data.getData();
            if (uri != null){
                MyinfImg.setImageURI(uri);
                Account account = BmobUser.getCurrentUser(Account.class);
                String imgname = new String(account.getUsername()
                        + String.valueOf(System.currentTimeMillis()));
                account.setImg(imgname);
                account.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null){
                            System.out.println("头像更新成功");
                        }
                        else{
                            System.out.println("头像更新失败");
                        }
                    }
                });
                String file_path = ImgPathOperation.getRealPathFromUri(context, uri);
                op.SaveToLocalAndSQ(file_path, imgname);
            }


        }
}



