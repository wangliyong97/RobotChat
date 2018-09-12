package com.feng.android.robotchat.model;

import java.util.List;

public class ResponseMessageCook {
    private String code;
    private String text;
    private String url;
    private List<Cookbook> list;

    public List<Cookbook> getList() {
        return list;
    }

    public void setList(List<Cookbook> list) {
        this.list = list;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
