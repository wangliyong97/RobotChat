package com.feng.android.robotchat.model;

public class WeatherUpdate {
    private String loc;

    private String utc;

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }

    @Override
    public String toString() {
        return "WeatherUpdate{" +
                "loc='" + loc + '\'' +
                ", utc='" + utc + '\'' +
                '}';
    }
}
