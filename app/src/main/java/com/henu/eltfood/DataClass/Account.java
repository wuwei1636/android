package com.henu.eltfood.DataClass;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class Account extends BmobUser {

    private int id = 0;
    // 昵称
    private String nickname;
    // 所在地址 （送餐地址）
    private String address;
    // 是否是店主
    private boolean isHaveShop;
    // 拥有的店铺名称
    private String shopName;
    // 图片名称
    private String img;

    public Account() {
        this.img = "init_img";
    }
    public Account(String userName, String password) {
        super.setUsername(userName);
        super.setPassword(password);
        this.img = "init_img";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return super.getUsername();
    }

    public void setUsername(String username) {
        super.setUsername(username);
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isHaveShop() {
        return isHaveShop;
    }

    public void setHaveShop(boolean haveShop) {
        isHaveShop = haveShop;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
