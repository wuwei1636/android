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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.henu.eltfood.DataClass.Account;
import com.henu.eltfood.DataClass.FoodInTrade;
import com.henu.eltfood.DataSystem.ImgOperation;
import com.henu.eltfood.Front.GoodsManager.FrontManage;
import com.henu.eltfood.Main.MainActivity;
import com.henu.eltfood.R;
import com.henu.eltfood.Recommend.ShopFood;
import com.henu.eltfood.Recommend.ShopFoodAdapter;
import com.henu.eltfood.util.EMUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class Fragment2 extends Fragment {

    int num; // 记录商品的数量
    String username = BmobUser.getCurrentUser(Account.class).getUsername();
    TextView allprice;
    Button submit,calculate;
    RecyclerView recyclerView;
    fragment2Adapter myadapter;
    private Bitmap init_img;
    private ImgOperation op;
    Handler handler;
    double sum = 0.0;
    ArrayList<FoodInTrade> selectedItem;
    ArrayList<FoodInTrade> inTradeData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2,container,false);

        Bmob.initialize(getContext(), "230f0d485f24c49e8bb460514596714a");

        recyclerView = view.findViewById(R.id.fragmentlist1);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL));
        allprice = view.findViewById(R.id.all_price1);
        submit = view.findViewById(R.id.submit1);
        calculate = view.findViewById(R.id.calculate1);
        selectedItem = new ArrayList<>();

        op = new ImgOperation(view.getContext());
        init_img = BitmapFactory.decodeResource(view.getContext().getResources(),R.drawable.account);
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                inTradeData.get(msg.what).setImage((Bitmap) msg.obj);
                myadapter.notifyDataSetChanged();
                System.out.println("第" + String.valueOf(msg.what + 1) + "张图片加载完毕");
            }
        };

        initdata();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
                dialg();
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = recyclerView.getChildCount();
                for(int i = 0;i <n;i ++){
                    View fir = (View) recyclerView.getChildAt(i);
                    CheckBox checkBox = fir.findViewById(R.id.cbn);
                    if(checkBox.isChecked()){
                        checkBox.setChecked(false);
                    }
                }
            }
        });

        return view;
    }

    // 确认付款弹窗
    public  void dialg(){
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setMessage("请付款 "+String.format("%.2f",sum)+" 元");
        builder.setPositiveButton("确认付款", new DialogInterface.
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

    // 确认付款后的事件
    public void changedata(){
        BmobQuery<FoodInTrade> fd = new BmobQuery<FoodInTrade>();
        fd.addWhereEqualTo("consumername", username);
        fd.addWhereNotEqualTo("count",0);
        fd.addWhereEqualTo("status",FoodInTrade.WAIT_FOR_PAID);
        fd.findObjects(new FindListener<FoodInTrade>() {
            @Override
            public void done(List<FoodInTrade> list, BmobException e) {
                int n = recyclerView.getChildCount();
                for(int i = 0;i <n;i ++){
                    String objid = list.get(i).getObjectId();
                    View fir = (View) recyclerView.getChildAt(i);
                    CheckBox checkBox = fir.findViewById(R.id.cbn);
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
        f.setStatus(FoodInTrade.PAID);
        f.setCount(num);
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
    // 点击事件，获取准备付款的金额
    public void check(){
        sum = 0.0;
        int n = recyclerView.getChildCount();
        selectedItem.clear();
        for(int i = 0;i <n;i ++){
            View fir = (View) recyclerView.getChildAt(i);
            CheckBox checkBox = fir.findViewById(R.id.cbn);
            TextView tp1 = fir.findViewById(R.id.food_count1);
            TextView tp2 = fir.findViewById(R.id.food_price1);
            if(checkBox.isChecked()){
                selectedItem.add(inTradeData.get(i));   //获取所有选中的数据，存储形式为 List<FoodInTrade>
                int count = Integer.valueOf(tp1.getText().toString());
                num = count;
                Double price = Double.valueOf(tp2.getText().toString());
                sum += price;
            }
        }
        allprice.setText(String.format("%.2f",sum));
    }

    // 初始化所有的订单
    public void initdata(){
        BmobQuery<FoodInTrade> fd = new BmobQuery<FoodInTrade>();
        fd.addWhereEqualTo("consumername", username);
        System.out.println(username);
        fd.addWhereNotEqualTo("count",0);
        fd.addWhereEqualTo("status",FoodInTrade.WAIT_FOR_PAID);
        fd.findObjects(new FindListener<FoodInTrade>() {
            @Override
            public void done(List<FoodInTrade> object, BmobException e) {
                if (e == null) {
                    inTradeData = new ArrayList<>(object);
                    for (int i = 0; i < inTradeData.size(); i++) inTradeData.get(i).setImage(init_img);
                    myadapter = new fragment2Adapter(Fragment2.this.getContext(),
                            R.layout.fragment2_a,inTradeData);
                    recyclerView.setAdapter(myadapter);
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

                for (int i = 0; i < inTradeData.size(); i++){
                    System.out.println("正在加载第" + String.valueOf(i + 1) + "张图片");

                    Bitmap bmp = op.GetBitmap(inTradeData.get(i).getImageName());
                    System.out.println(inTradeData.get(i).getImageName());
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

