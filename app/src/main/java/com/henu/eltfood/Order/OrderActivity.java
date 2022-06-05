package com.henu.eltfood.Order;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.henu.eltfood.R;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends Fragment {
    Fragment1 r1;
    Fragment2 r2;
    Fragment3 r3;
    Fragment5 r5;
    Fragment6 r6;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order, null);
        TextView t1 = view.findViewById(R.id.id1);
        TextView t2 = view.findViewById(R.id.id2);
        TextView t3 = view.findViewById(R.id.id3);
        TextView t5 = view.findViewById(R.id.id5);
        TextView t6 = view.findViewById(R.id.id6);
        r1 = new Fragment1();
        r2 = new Fragment2();
        r3 = new Fragment3();
        r5 = new Fragment5();
        r6 = new Fragment6();
        t1.setOnClickListener(PageButtonAction);
        t2.setOnClickListener(PageButtonAction);
        t3.setOnClickListener(PageButtonAction);
        t5.setOnClickListener(PageButtonAction);
        t6.setOnClickListener(PageButtonAction);
        android.app.FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = r1;
        ft.replace(R.id.order_fragment, f);
        ft.commit();

        return view;
    }
    View.OnClickListener PageButtonAction = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment f = null;
            switch (view.getId()) {
                case R.id.id1:
                    f = r1;
                    break;
                case R.id.id2:
                    f = r2;
                    break;
                case R.id.id3:
                    f = r3;
                    break;
                case R.id.id5:
                    f = r5;
                    break;
                case R.id.id6:
                    f = r6;
                    break;
                default:
                    break;
            }
            ft.replace(R.id.order_fragment, f);
            ft.commit();
        }
    };
}