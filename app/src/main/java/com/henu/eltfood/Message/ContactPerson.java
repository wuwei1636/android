package com.henu.eltfood.Message;

import android.graphics.Bitmap;

public class ContactPerson {
    private String name = "None";
    private String last_massage = "";
    private String last_massage_time = "";
    private Bitmap header ;

    public ContactPerson(String name, String last_massage, String last_massage_time, Bitmap header) {
        this.name = name;
        this.last_massage = last_massage;
        this.last_massage_time = last_massage_time;
        this.header = header;
    }

    public ContactPerson(){ }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_massage() {
        return last_massage;
    }

    public void setLast_massage(String last_massage) {
        this.last_massage = last_massage;
    }

    public Bitmap getHeader() {
        return header;
    }

    public void setHeader(Bitmap header) {
        this.header = header;
    }


    public String getLast_massage_time() {
        return last_massage_time;
    }

    public void setLast_massage_time(String last_massage_time) {
        this.last_massage_time = last_massage_time;
    }
}
