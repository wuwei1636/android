package com.henu.eltfood.DataClass;

import android.graphics.Bitmap;

import cn.bmob.v3.BmobObject;

public class Food extends BmobObject{
    private int foodImg; // 可以是其他数据类型

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    private Bitmap image; // 图片
    private String imageName = "init_img"; // 图片名字
    private String foodName; //商品名字
    private String foodCategory; //商品所属类别
    private String foodPrice; // 商品价格
    private String accountId; // 商品所属哪个商家，其id
    private String shopName;    //属于哪一个店铺
    private String accountAddress; // 此商家的地址

    public Food() {
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Food(int foodImg, String foodName, String foodCategory, String foodPrice) {
        accountAddress = "暂未设置地址";
        this.foodImg = foodImg;
        this.foodName = foodName;
        this.foodCategory = foodCategory;
        this.foodPrice = foodPrice;
    }

    public Food(Bitmap image, String imageName, String foodName, String foodCategory, String foodPrice, String accountId, String accountAddress) {
        this.image = image;
        this.imageName = imageName;
        this.foodName = foodName;
        this.foodCategory = foodCategory;
        this.foodPrice = foodPrice;
        this.accountId = accountId;
        this.accountAddress = accountAddress;
    }

    public Food(String tableName, int foodImg, String imageName, String foodName, String foodCategory, String foodPrice, String accountId, String accountAddress) {
        super(tableName);
        this.foodImg = foodImg;
        this.imageName = imageName;
        this.foodName = foodName;
        this.foodCategory = foodCategory;
        this.foodPrice = foodPrice;
        this.accountId = accountId;
        this.accountAddress = accountAddress;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public Food(int foodImg, String foodName, String foodCategory, String foodPrice, String accountId, String accounAddress) {
        this.foodImg = foodImg;
        this.foodName = foodName;
        this.foodCategory = foodCategory;
        this.foodPrice = foodPrice;
        this.accountId = accountId;
        this.accountAddress = accounAddress;
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

    public String getFooCategory() {
        return foodCategory;
    }

    public void setFooCategory(String fooCategory) {
        this.foodCategory = fooCategory;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }
}
