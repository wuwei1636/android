package com.henu.eltfood.DataClass;

import android.graphics.Bitmap;

import cn.bmob.v3.BmobObject;

public class FoodInTrade extends BmobObject { //food in trade 正在交易中的食品
    public static final int WAIT_FOR_PAID = 0;
    public static final int PAID = 1;
    public static final int SEND = 2;
    public static final int RECEIVED = 3;
    private int status; //订单状态

    private int foodImg; // 可以是其他数据类型
    private Bitmap image; // 图片
    private String imageName; // 图片名字
    private String foodName;  // 食物名字
    private String foodCategory;  //
    private int count ; // 食物的数量

    private String foodPrice;  // 食物价格
    private String shopId;    // 店铺id

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    private String shopAddress; // 店铺地址
    private String consumername; // 用户id
    private String consumerId;
    private String consumerAddress;  // 用户地址


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public FoodInTrade(Bitmap image, String imageName, String foodName, String foodCategory, int count, String foodPrice, String shopId, String shopAddress, String consumerId, String consumerAddress) {
        this.image = image;
        this.imageName = imageName;
        this.foodName = foodName;
        this.foodCategory = foodCategory;
        this.count = count;
        this.foodPrice = foodPrice;
        this.shopId = shopId;
        this.shopAddress = shopAddress;
        this.consumername = consumerId;
        this.consumerAddress = consumerAddress;
    }


    public FoodInTrade(Bitmap image, String foodName, String foodCategory, String foodPrice) {
        consumername = "未设置";
        consumerAddress = "未设置";
        this.image = image;
        this.foodName = foodName;
        this.foodCategory = foodCategory;
        this.foodPrice = foodPrice;
    }

    public FoodInTrade() {

    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public FoodInTrade(int foodImg, String foodName, String foodCategory, String foodPrice) {
        consumername = "未设置";
        consumerAddress = "未设置";
        this.foodImg = foodImg;
        this.foodName = foodName;
        this.foodCategory = foodCategory;
        this.foodPrice = foodPrice;
    }

    public FoodInTrade(int foodImg, String foodName, String foodCategory, String foodPrice, String shopId, String shopAddress, String consumerId, String consumerAddress) {
        this.foodImg = foodImg;
        this.foodName = foodName;
        this.foodCategory = foodCategory;
        this.foodPrice = foodPrice;
        this.shopId = shopId;
        this.shopAddress = shopAddress;
        this.consumername = consumerId;
        this.consumerAddress = consumerAddress;
    }



    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(int foodImg) {
        this.foodImg = foodImg;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getConsumername() {
        return consumername;
    }

    public void setConsumername(String consumername) {
        this.consumername = consumername;
    }

    public String getConsumerAddress() {
        return consumerAddress;
    }

    public void setConsumerAddress(String consumerAddress) {
        this.consumerAddress = consumerAddress;
    }

}
