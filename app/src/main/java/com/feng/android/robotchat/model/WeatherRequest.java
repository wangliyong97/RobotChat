package com.feng.android.robotchat.model;

public class WeatherRequest {
    private String location;
    private String key;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
