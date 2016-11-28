package com.alfred.study.headfirst.duck;

/**
 * Created by Alfred on 2016/11/24.
 */

public class Squeak implements QuackBehavior {
    @Override
    public void quack() {
        System.out.print("Squeak");
    }
}
