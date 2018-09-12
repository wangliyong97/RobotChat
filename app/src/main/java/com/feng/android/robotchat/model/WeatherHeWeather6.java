package com.feng.android.robotchat.model;

public class WeatherHeWeather6 {

    private WeatherBasic basic;
    private WeatherUpdate update;
    private WeatherNow now;
    private String status;

    public WeatherBasic getBasic() {
        return basic;
    }

    public void setBasic(WeatherBasic basic) {
        this.basic = basic;
    }

    public WeatherUpdate getUpdate() {
        return update;
    }

    public void setUpdate(WeatherUpdate update) {
        this.update = update;
    }

    public WeatherNow getNow() {
        return now;
    }

    public void setNow(WeatherNow now) {
        this.now = now;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WeatherHeWeather6{" +
                "basic=" + basic +
                ", update=" + update +
                ", now=" + now +
                ", status='" + status + '\'' +
                '}';
    }
}
