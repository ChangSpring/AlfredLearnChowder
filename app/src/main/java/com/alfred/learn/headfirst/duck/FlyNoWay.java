package com.alfred.learn.headfirst.duck;

/**
 * Created by Alfred on 2016/11/24.
 */

public class FlyNoWay implements FlyBehavior {
    @Override
    public void fly() {
        System.out.print("I can't fly");
    }
}
