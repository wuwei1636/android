package com.henu.eltfood.Recommend;

import android.graphics.Bitmap;

public class ShopFood {
    public String ImgName;
    public Bitmap Img;
    public String FoodDescription;
    public String price;
    public String count;

    public ShopFood(String imgName, String foodDescription, String price, String count) {
        this.ImgName = imgName;
        this.FoodDescription = foodDescription;
        this.price = price;
        this.count = count;
    }

    public String getImgName() {
        return ImgName;
    }

    public void setImgName(String imgName) {
        ImgName = imgName;
    }

    public Bitmap getImg() {
        return Img;
    }

    public void setImg(Bitmap img) {
        Img = img;
    }

    public String getFoodDescription() {
        return FoodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        FoodDescription = foodDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}