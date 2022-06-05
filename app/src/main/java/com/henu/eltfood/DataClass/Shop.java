package com.henu.eltfood.DataClass;

import android.graphics.Bitmap;

import cn.bmob.v3.BmobObject;

public class Shop extends BmobObject {
    public String ShopOwner = "店铺拥有者";

    public Bitmap image;
    public String ShopImgName = "init_img";
    public String ShopName = "商店名字";
    public String ShopPriceInf = "店铺价格信息，多少起送";
    public String ShopGrade = "5.0";
    public String ShopComment = "商店评论";

    public Shop(){}

    public Shop(String shopOwner, String shopImgName,
                String shopName, String shopPriceInf,
                String shopGrade, String shopComment) {
        ShopOwner = shopOwner;
        ShopImgName = shopImgName;
        ShopName = shopName;
        ShopPriceInf = shopPriceInf;
        ShopGrade = shopGrade;
        ShopComment = shopComment;
    }

    public String getShopOwner() {
        return ShopOwner;
    }

    public void setShopOwner(String shopOwner) {
        ShopOwner = shopOwner;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getShopImgName() {
        return ShopImgName;
    }

    public void setShopImgName(String shopImgName) {
        ShopImgName = shopImgName;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getShopPriceInf() {
        return ShopPriceInf;
    }

    public void setShopPriceInf(String shopPriceInf) {
        ShopPriceInf = shopPriceInf;
    }

    public String getShopGrade() {
        return ShopGrade;
    }

    public void setShopGrade(String shopGrade) {
        ShopGrade = shopGrade;
    }

    public String getShopComment() {
        return ShopComment;
    }

    public void setShopComment(String shopComment) {
        ShopComment = shopComment;
    }
}
