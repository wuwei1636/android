package com.henu.eltfood.Main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.henu.eltfood.Front.FrontActivity;
import com.henu.eltfood.Message.MessageActivity;
import com.henu.eltfood.MyInformation.MyInformationActivity;
import com.henu.eltfood.Order.OrderActivity;
import com.henu.eltfood.R;
import com.henu.eltfood.Recommend.RecommendActivity;
import com.henu.eltfood.util.ActivityCollectorUtil;
import com.henu.eltfood.util.EMUtil;

public class First_viewActivity extends Activity {
    FrontActivity frontActivity;
    RecommendActivity recommendActivity ;
    MessageActivity messageActivity;
    OrderActivity orderActivity;
    MyInformationActivity myInformationActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_view);
        // activity 入栈
        ActivityCollectorUtil.addActivity(First_viewActivity.this);

        frontActivity = new FrontActivity();
        recommendActivity = new RecommendActivity();
         messageActivity = new MessageActivity();
         orderActivity = new OrderActivity();
         myInformationActivity = new MyInformationActivity();
        ImageView imageView1 = findViewById(R.id.img1);
        ImageView imageView2 = findViewById(R.id.img2);
        ImageView imageView3 = findViewById(R.id.img3);
        ImageView imageView4 = findViewById(R.id.img4);
        ImageView imageView5 = findViewById(R.id.img5);

        imageView1.setOnClickListener(PageButtonAction);
        imageView2.setOnClickListener(PageButtonAction);
        imageView3.setOnClickListener(PageButtonAction);
        imageView4.setOnClickListener(PageButtonAction);
        imageView5.setOnClickListener(PageButtonAction);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = frontActivity;
        ft.replace(R.id.fragment, f);
        ft.commit();
    }

    View.OnClickListener PageButtonAction = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment f = null;
            switch (view.getId()) {
                case R.id.img1:
                    f = frontActivity;
                    break;
                case R.id.img2:
                    f = recommendActivity;
                    break;
                case R.id.img3:
                    f = messageActivity;
                    break;
                case R.id.img4:
                    f = orderActivity;
                    break;
                case R.id.img5:
                    f = myInformationActivity;
                    break;
                default:
                    break;
            }
            ft.replace(R.id.fragment, f);
            ft.commit();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(First_viewActivity.this);
    }

    @Override
    public void onBackPressed() {
        if(EMUtil.isLogin()){
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
        }else super.onBackPressed();
    }
}