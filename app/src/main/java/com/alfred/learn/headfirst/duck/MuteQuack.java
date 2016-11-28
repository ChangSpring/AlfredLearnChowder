package com.alfred.learn.headfirst.duck;

/**
 * Created by Alfred on 2016/11/24.
 */

public class MuteQuack implements QuackBehavior{
    @Override
    public void quack() {
        System.out.print("<< Silence >>");
    }
}
