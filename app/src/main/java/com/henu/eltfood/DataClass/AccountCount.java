package com.henu.eltfood.DataClass;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class AccountCount extends BmobObject {
     public int cnt = 0;

    public void update_cnt(){
        this.update("cfd2d37961", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    System.out.println("同步成功");
                }
                else{
                    System.out.println("同步失败"+ e.getMessage());
                }
            }
        });
    }
    public void load_cnt(){
        BmobQuery<AccountCount> bmobQuery = new BmobQuery<AccountCount>();
        bmobQuery.getObject("cfd2d37961", new QueryListener<AccountCount>() {
            @Override
            public void done(AccountCount account_count, BmobException e) {
                if (e == null){
                    cnt = account_count.cnt;
                    System.out.println("加载数据成功");
                    System.out.println(cnt);
                }
                else{
                    System.out.println("数据加载失败" + e.getMessage());
                }
            }
        });
    }
}
