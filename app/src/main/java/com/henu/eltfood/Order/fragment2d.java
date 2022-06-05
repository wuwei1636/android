package com.henu.eltfood.Order;

public class fragment2d {
    public int FoodImageId;
    public String FoodDescription;
    public double price;
    public int count;

    public fragment2d(int foodImageId, String foodDescription, double price, int count) {
        this.FoodImageId = foodImageId;
        this.FoodDescription = foodDescription;
        this.price = price;
        this.count = count;
    }
}
