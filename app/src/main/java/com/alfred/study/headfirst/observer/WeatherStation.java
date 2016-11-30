package com.alfred.study.headfirst.observer;

/**
 * Created by Alfred on 2016/11/29.
 */

public class WeatherStation {
    public static void main(String[] args){
        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay mCurrentConditionsDisplay = new CurrentConditionsDisplay(weatherData);
        weatherData.setMeasurements(80,65,39.0f);
    }
}
