package com.henu.eltfood.Message.chat;

public class Massage {
    public static final int TYPE_SEND = 1;
    public static final int TYPE_RECEIVED = 0;
    private String content;
    private int type;
    private int header;
    public Massage(String content, int type, int header) {
        this.content = content;
        this.type = type;
        this.header = header;
    }



    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {

        this.type = type;
    }


    public Massage() {
    }




}
