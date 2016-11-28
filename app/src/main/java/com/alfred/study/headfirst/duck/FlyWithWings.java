package com.alfred.study.headfirst.duck;

/**
 * Created by Alfred on 2016/11/24.
 */

public class FlyWithWings implements FlyBehavior{

    @Override
    public void fly() {
        System.out.print("I'm flying");
    }
}
