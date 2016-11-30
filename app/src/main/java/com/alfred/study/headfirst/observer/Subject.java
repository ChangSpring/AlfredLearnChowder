package com.alfred.study.headfirst.observer;

/**
 * Created by Alfred on 2016/11/29.
 */

public interface Subject {
    void registerObserver(Observer observer);

    void unRegisterObserver(Observer observer);

    void notifyObservers();

}
