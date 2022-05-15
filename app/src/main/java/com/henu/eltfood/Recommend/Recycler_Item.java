package com.henu.eltfood.Recommend;

import android.widget.TextView;

public class Recycler_Item {
    public int image_source;
    public String textview_content;

    public Recycler_Item(int image_source, String textview_content) {
        this.image_source = image_source;
        this.textview_content = textview_content;
    }
}