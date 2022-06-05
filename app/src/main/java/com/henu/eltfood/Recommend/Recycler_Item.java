package com.henu.eltfood.Recommend;

import android.graphics.Bitmap;

import com.henu.eltfood.DataClass.Shop;

public class Recycler_Item {
    public String ShopOwner;
    public Bitmap ShopImg;
    public String ShopImgName;
    public String ShopName;
    public String ShopGrade;
    public String ShopPrice;
    public String ShopComment;
    public String shopId;

    public Recycler_Item(){}

    public Recycler_Item(Shop shop){
        this.ShopOwner = shop.ShopOwner;
        this.ShopComment = shop.ShopComment;
        this.ShopGrade = shop.ShopGrade;
        this.ShopPrice = shop.ShopPriceInf;
        this.ShopImgName = shop.ShopImgName;
        this.ShopName = shop.ShopName;
        this.shopId = shop.ShopOwner;
    }

    public Recycler_Item(String shopOwner, Bitmap shopImg,
                         String shopImgName, String shopName,
                         String shopGrade, String shopPrice,
                         String shopComment) {
        ShopOwner = shopOwner;
        ShopImg = shopImg;
        ShopImgName = shopImgName;
        ShopName = shopName;
        ShopGrade = shopGrade;
        ShopPrice = shopPrice;
        ShopComment = shopComment;
    }



}