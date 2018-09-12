package com.feng.android.robotchat.model;

public class Cookbook {
    private String name;
    private String info;
    private String icon;
    private String detailurl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDetailurl() {
        return detailurl;
    }

    public void setDetailurl(String detailurl) {
        this.detailurl = detailurl;
    }

    @Override
    public String toString() {
        return "名称:" + name + '\n' +
                "  信息：" + info + '\n' +
                "  链接：" + detailurl + '\n';
    }
}
