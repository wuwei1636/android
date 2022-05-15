package com.henu.eltfood.Recommend;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.henu.eltfood.R;

import java.util.ArrayList;

public class RecommendActivity extends Fragment {

    private RecyclerView recommend_recycler;
    private ArrayList<Recycler_Item> Data = new ArrayList<Recycler_Item>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recommend, null);

            recommend_recycler = view.findViewById(R.id.recommend_recycler);
            initData();
            recommend_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            MyStaggeredGridLayoutAdapter adapter = new MyStaggeredGridLayoutAdapter(view.getContext(), Data);
            adapter.setOnItemClickListner(new MyStaggeredGridLayoutAdapter.OnItemClickListener() {
                @Override
                public void onClick(Context context, int pos) {
                    Intent intent = new Intent(context, ShopOnline.class);
                    startActivity(intent);
                }
            });
            recommend_recycler.setAdapter(adapter);
            recommend_recycler.addItemDecoration(new SpacesItemDecoration(20));
        return view;
    }
    public void initData() {
        Data.add(new Recycler_Item(R.drawable.a1, "a1"));
        Data.add(new Recycler_Item(R.drawable.b2, "b1"));
        Data.add(new Recycler_Item(R.drawable.c3, "c1"));
        Data.add(new Recycler_Item(R.drawable.d4, "d1"));
        Data.add(new Recycler_Item(R.drawable.e5, "e1"));
        Data.add(new Recycler_Item(R.drawable.f6, "f1"));
        Data.add(new Recycler_Item(R.drawable.g7, "g1"));
        Data.add(new Recycler_Item(R.drawable.a1, "h8"));

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
