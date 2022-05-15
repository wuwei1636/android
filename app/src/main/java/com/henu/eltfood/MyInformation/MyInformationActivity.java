package com.henu.eltfood.MyInformation;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.henu.eltfood.R;
import com.henu.eltfood.util.Constant;

public class MyInformationActivity extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myinformation, null);

        TextView shezhi = (TextView) view.findViewById(R.id.shezhi);

        TextView mname = (TextView)view.findViewById(R.id.mname);
        mname.setText(Constant.username);

        // 点击设置，跳转到子界面
        shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), Myinfoset.class);
                startActivity(it);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

    }


}



