package com.alfred.learn.headfirst.duck;

/**
 * Created by Alfred on 2016/11/24.
 */

public class Quack implements QuackBehavior{
    @Override
    public void quack() {
        System.out.print("quack");
    }
}
