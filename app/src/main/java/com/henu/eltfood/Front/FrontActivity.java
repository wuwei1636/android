package com.henu.eltfood.Front;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.henu.eltfood.Front.FrontConsumerOrder.FrontConsumerOrder;
import com.henu.eltfood.Front.GoodsManager.FrontManage;
import com.henu.eltfood.R;

public class FrontActivity extends Fragment {
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.front, null);

        TextView food = view.findViewById(R.id.front_btn1);
        TextView drink = view.findViewById(R.id.front_btn2);
        TextView manage = view.findViewById(R.id.front_btn3);
        TextView corder = view.findViewById(R.id.front_btn4);

        TextView btn21 = view.findViewById(R.id.front_btn21);
        TextView btn22 = view.findViewById(R.id.front_btn22);
        TextView btn23 = view.findViewById(R.id.front_btn23);
        TextView btn24 = view.findViewById(R.id.front_btn24);
        TextView btn25 = view.findViewById(R.id.front_btn25);
        TextView btn31 = view.findViewById(R.id.front_btn31);
        TextView btn32 = view.findViewById(R.id.front_btn32);
        TextView btn33 = view.findViewById(R.id.front_btn33);
        TextView btn34 = view.findViewById(R.id.front_btn34);
        TextView btn35 = view.findViewById(R.id.front_btn35);


        Drawable drawable1 = ResourcesCompat.getDrawable(getResources(), R.drawable.food, null);
        drawable1.setBounds( 5 ,  5 ,  140 ,  140 ); //第一0是距左边距离，第二0是距上边距离，40分别是长宽
        food.setCompoundDrawables(null,drawable1,null,null);

        Drawable drawable2 = ResourcesCompat.getDrawable(getResources(), R.drawable.drink, null);
        drawable2.setBounds( 5 ,  5 ,  140 ,  140 ); //第一0是距左边距离，第二0是距上边距离，40分别是长宽
        drink.setCompoundDrawables(null,drawable2,null,null);

        Drawable drawable3 = ResourcesCompat.getDrawable(getResources(), R.drawable.shangpin, null);
        drawable3.setBounds( 5 ,  5 ,  140 ,  140 ); //第一0是距左边距离，第二0是距上边距离，40分别是长宽
        manage.setCompoundDrawables(null,drawable3,null,null);

        Drawable drawable4 = ResourcesCompat.getDrawable(getResources(), R.drawable.dingdan, null);
        drawable4.setBounds( 5 ,  5 ,  140 ,  140 ); //第一0是距左边距离，第二0是距上边距离，40分别是长宽
        corder.setCompoundDrawables(null,drawable4,null,null);

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FrontFood.class);
                startActivity(intent);
            }
        });
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FrontDrinks.class);
                startActivity(intent);
            }
        });
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ManageShop.class);
                startActivity(intent);
            }
        });
        corder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FrontConsumerOrder.class);
                startActivity(intent);
            }
        });
        btn21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FrontDrinks.class);
                startActivity(intent);
            }
        });
        btn22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FrontFood.class);
                startActivity(intent);
            }
        });
        btn23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FrontFood.class);
                startActivity(intent);
            }
        });
        btn24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FrontFood.class);
                startActivity(intent);
            }
        });
        btn25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FrontFood.class);
                startActivity(intent);
            }
        });
        btn31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FrontFood.class);
                startActivity(intent);
            }
        });
        btn32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FrontFood.class);
                startActivity(intent);
            }
        });
        btn33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FrontFood.class);
                startActivity(intent);
            }
        });
        btn34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FrontFood.class);
                startActivity(intent);
            }
        });
        btn35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FrontFood.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
