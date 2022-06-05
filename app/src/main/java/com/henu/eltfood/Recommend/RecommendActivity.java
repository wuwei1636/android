package com.henu.eltfood.Recommend;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.henu.eltfood.DataClass.Shop;
import com.henu.eltfood.DataSystem.ImgOperation;
import com.henu.eltfood.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RecommendActivity extends Fragment {

    private RecyclerView recommend_recycler;
    private ArrayList<Recycler_Item> Data = new ArrayList<Recycler_Item>();
    MyStaggeredGridLayoutAdapter adapter;
    Bitmap init_shop_img;
    ImgOperation op;
    Handler handler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recommend, null);
        recommend_recycler = view.findViewById(R.id.recommend_recycler);
        recommend_recycler.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL));
        init_shop_img = BitmapFactory.decodeResource(view.getResources(),R.drawable.account);
        op = new ImgOperation(view.getContext());
        initData();

        recommend_recycler.addItemDecoration(new SpacesItemDecoration(20));
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Data.get(msg.what).ShopImg = (Bitmap)msg.obj;
                adapter.notifyDataSetChanged();
                System.out.println("第" + String.valueOf(msg.what + 1) + "张图片加载完毕");
            }
        };

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Data.clear();
    }

    public void initData() {
        BmobQuery<Shop> bmobQuery= new BmobQuery<Shop>();
        bmobQuery.findObjects(new FindListener<Shop>() {
            @Override
            public void done(List<Shop> list, BmobException e) {
                if (e == null){
                    for (int i = 0; i < list.size(); i++){
                        Recycler_Item recycler_item = new Recycler_Item(list.get(i));
                        recycler_item.ShopImg = init_shop_img;
                        Data.add(recycler_item);
                    }
                    adapter = new MyStaggeredGridLayoutAdapter(RecommendActivity.this.getContext(), Data);
                    recommend_recycler.setAdapter(adapter);
                    LoadImg();
                }
                else{
                    System.out.println(e.getMessage());
                    System.out.println("店铺获取失败");
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

                    Bitmap bmp = op.GetBitmap(Data.get(i).ShopImgName);
                    Message msg = new Message();
                    msg.what = i;
                    msg.obj = bmp;
                    handler.sendMessage(msg);
                }
                System.out.println("所有图片加载完毕");
            }
        }.start();
    }


    class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }
}
