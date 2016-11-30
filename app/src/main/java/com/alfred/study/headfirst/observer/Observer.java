package com.alfred.study.headfirst.observer;

/**
 * Created by Alfred on 2016/11/29.
 */

public interface Observer {
    void update(float temp, float humidity, float pressure);
}
