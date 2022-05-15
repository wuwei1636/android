package com.henu.eltfood.Main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
}

//public class First_viewActivity extends FragmentActivity {
//    private TabHost mTabHost;
//    private LayoutInflater mInflater;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_first_view);
//
//        mTabHost = findViewById(android.R.id.tabhost);
//        mTabHost.setup();
//        mInflater = LayoutInflater.from(this);
//        mInflater.inflate(R.layout.front,mTabHost.getTabContentView());
//        mInflater.inflate(R.layout.recommend,mTabHost.getTabContentView());
//        mInflater.inflate(R.layout.news,mTabHost.getTabContentView());
//        mInflater.inflate(R.layout.order,mTabHost.getTabContentView());
//        mInflater.inflate(R.layout.myinformation,mTabHost.getTabContentView());
//
//        //设置底部菜单栏
//        mTabHost.addTab(mTabHost.newTabSpec("front").setIndicator(getTabItemView(0)).setContent(R.id.front));
//        mTabHost.addTab(mTabHost.newTabSpec("recommend").setIndicator(getTabItemView(1)).setContent(new Intent(First_viewActivity.this, Test.class)));
//        mTabHost.addTab(mTabHost.newTabSpec("news").setIndicator(getTabItemView(2)).setContent(R.id.news));
//        mTabHost.addTab(mTabHost.newTabSpec("order").setIndicator(getTabItemView(3)).setContent(R.id.order));
//        mTabHost.addTab(mTabHost.newTabSpec("myinformation").setIndicator(getTabItemView(4)).setContent(R.id.myinformation));
//
//        //底部菜单栏当前选项改变时的事件
//        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String s) {
//                Toast.makeText(First_viewActivity.this,s,Toast.LENGTH_SHORT).show();
//                mTabHost.setCurrentTabByTag(s);
//            }
//        });
//        ImageView imageView = findViewById(R.id.imagetest);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(First_viewActivity.this, "home", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private View getTabItemView(int index) {       //设置每个菜单的内容
//        int Image[] = {R.drawable.home, R.drawable.cart_empty, R.drawable.email, R.drawable.land_transportation, R.drawable.account};
//        String text_name[] = {"首页", "推荐", "信息", "订单", "我的"};
//        View view = mInflater.inflate(R.layout.pagebutton, null);
//        ImageView imageView = (ImageView) view.findViewById(R.id.image_icon);
//        if (imageView != null) {
//            imageView.setImageResource(Image[index]);
//        }
//        TextView textView = (TextView) view.findViewById(R.id.text_name);
//        textView.setText(text_name[index]);
//        return view;
//    }
//}