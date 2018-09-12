package com.feng.android.robotchat.model;

import java.util.List;

public class WeatherResponse {

    List<WeatherHeWeather6> HeWeather6 ;

    public List<WeatherHeWeather6> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<WeatherHeWeather6> heWeather6) {
        HeWeather6 = heWeather6;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "HeWeather6=" + HeWeather6 +
                '}';
    }
}
