package com.henu.eltfood.Order;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.henu.eltfood.DataClass.FoodInTrade;
import com.henu.eltfood.DataSystem.ImgOperation;
import com.henu.eltfood.R;
import com.henu.eltfood.Recommend.ShopFood;
import com.henu.eltfood.util.EMUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class Fragment3 extends Fragment {
    String username = EMUtil.getCurrentName();
    TextView allprice;
    Button submit,calculate;
    RecyclerView recycleview;
    int sum = 0;
    ArrayList<FoodInTrade> Data = new ArrayList<FoodInTrade>();
    Handler handler;
    fragment3Adapter myadapter;
    private Bitmap init_img;
    ImgOperation op;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3,container,false);

        Bmob.initialize(getContext(), "230f0d485f24c49e8bb460514596714a");

        op = new ImgOperation(Fragment3.this.getContext());
        init_img = BitmapFactory.decodeResource(view.getContext().getResources(),R.drawable.account);
        recycleview = view.findViewById(R.id.fragmentlist2);
        recycleview.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL));
        allprice = view.findViewById(R.id.all_price2);
        submit = view.findViewById(R.id.submit2);
        calculate = view.findViewById(R.id.calculate2);

        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Data.get(msg.what).setImage((Bitmap) msg.obj);
                myadapter.notifyDataSetChanged();
                System.out.println("第" + String.valueOf(msg.what + 1) + "张图片加载完毕");
            }
        };
        initdata();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
                dialg1();
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = recycleview.getChildCount();
                for(int i = 0;i <n;i ++){
                    View fir = (View) recycleview.getChildAt(i);
                    CheckBox checkBox = fir.findViewById(R.id.cbn2);
                    if(checkBox.isChecked()){
                        checkBox.setChecked(false);
                    }
                }
            }
        });

        return view;
    }


    // 确认收货弹窗
    public  void dialg1(){
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setMessage("确认已收货嘛");
        builder.setPositiveButton("确认收货", new DialogInterface.
                OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                changedata();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.
                OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.create().show();
    }

    // 确认收货后的事件
    public void changedata(){
        BmobQuery<FoodInTrade> fd = new BmobQuery<FoodInTrade>();
        fd.addWhereEqualTo("consumername", username);
        fd.addWhereNotEqualTo("count",0);
        fd.addWhereEqualTo("status",FoodInTrade.PAID);
        fd.findObjects(new FindListener<FoodInTrade>() {
            @Override
            public void done(List<FoodInTrade> list, BmobException e) {
                int n = recycleview.getChildCount();
                for(int i = 0;i <n;i ++){
                    String objid = list.get(i).getObjectId();
                    View fir = (View) recycleview.getChildAt(i);
                    CheckBox checkBox = fir.findViewById(R.id.cbn2);
                    if(checkBox.isChecked()){
                        update(objid);
                    }
                }
            }
        });

    }
    // 更新物品的状态
    private void update(String mObjectId) {
        FoodInTrade f = new FoodInTrade();
        f.setStatus(FoodInTrade.SEND);
        f.update(mObjectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    initdata();
                } else {
                }
            }
        });
    }
    // 点击事件，获取准备收货的产品数量
    public void check(){
        sum = 0;
        int n = recycleview.getChildCount();
        for(int i = 0;i <n;i ++){
            View fir = (View) recycleview.getChildAt(i);
            CheckBox checkBox = fir.findViewById(R.id.cbn2);
            if(checkBox.isChecked()){
                sum+=1;
            }
        }
        allprice.setText(String.valueOf(sum));
    }

    // 初始化所有的订单
    public void initdata(){
        BmobQuery<FoodInTrade> fd = new BmobQuery<FoodInTrade>();
        fd.addWhereEqualTo("consumername", username);
        fd.addWhereNotEqualTo("count",0);
        fd.addWhereEqualTo("status",FoodInTrade.PAID);
        fd.findObjects(new FindListener<FoodInTrade>() {
            @Override
            public void done(List<FoodInTrade> object, BmobException e) {
                if (e == null) {
                    Data = new ArrayList<FoodInTrade>(object);
                    System.out.println(Data.size());
                    for (int i = 0; i < Data.size(); i++) Data.get(i).setImage(init_img);
                    myadapter = new fragment3Adapter(getContext(), R.layout.fragment3_a, Data);
                    recycleview.setAdapter(myadapter);
                    LoadImg();

                } else {

                }
            }
        });
    }

    public void LoadImg(){
        new Thread(){
            @Override
            public void run() {
                System.out.println("开始加载图片");

                for (int i = 0; i < Data.size(); i++){
                    System.out.println("正在加载第" + String.valueOf(i + 1) + "张图片");

                    Bitmap bmp = op.GetBitmap(Data.get(i).getImageName());
                    System.out.println(Data.get(i).getImageName());
                    Message msg = new Message();
                    msg.what = i;
                    msg.obj = bmp;
                    handler.sendMessage(msg);
                }
                System.out.println("所有图片加载完毕");
            }
        }.start();

    }

}

