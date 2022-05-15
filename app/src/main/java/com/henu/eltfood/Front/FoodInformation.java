package com.henu.eltfood.Front;

import android.widget.ImageView;
import android.widget.TextView;

public class FoodInformation {
    public int img;
    public String name;
    public String category;
    public String price;

    public FoodInformation(int img, String name, String category, String price) {
        this.img = img;
        this.category = category;
        this.name = name;
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
