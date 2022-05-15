package com.henu.eltfood.Recommend;

import android.widget.Button;

public class ShopFood {
    public int FoodImageId;
    public String FoodDescription;
    public double price;
    public int count;

    public ShopFood(int foodImageId, String foodDescription, double price, int count) {
        this.FoodImageId = foodImageId;
        this.FoodDescription = foodDescription;
        this.price = price;
        this.count = count;
    }
}