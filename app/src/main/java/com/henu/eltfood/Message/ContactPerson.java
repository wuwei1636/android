package com.henu.eltfood.Message;

public class ContactPerson {
    private String name = "None";
    private String last_massage = "None";
    private String last_massage_time = "XX:XX";
    private int header ;

    public ContactPerson(String name, String last_massage, String last_massage_time, int header) {
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

    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }


    public String getLast_massage_time() {
        return last_massage_time;
    }

    public void setLast_massage_time(String last_massage_time) {
        this.last_massage_time = last_massage_time;
    }
}
