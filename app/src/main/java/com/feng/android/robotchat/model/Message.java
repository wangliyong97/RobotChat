package com.feng.android.robotchat.model;

import java.util.UUID;

public class Message {

    public static final int TYPE_RECEIVE = 0;   //消息类型：收到消息
    public static final int TYPE_SEND = 1;      //消息类型：发送消息
    private UUID uuid;      //消息的UUID标识
    private String text; //消息的内容
    private String code;
    private int type;       //消息的类型
    private String time;    //消息的时间
    private String picture; //消息中的图片
    private String comment;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Message(){
        this(UUID.randomUUID());
    }

    public Message(UUID uuid){
        this.uuid = uuid;
    }


    public UUID getUuid() {
        return uuid;
    }

    public String getText() {
        return text;
    }

    public void setText(String content) {
        this.text = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



}
