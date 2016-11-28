package com.alfred.study.headfirst.duck;

/**
 * Created by Alfred on 2016/11/24.
 */

public class MallardDuck extends Duck{
    public MallardDuck(){
        mFlyBehavior = new FlyWithWings();
        mQuackBehavior = new MuteQuack();
    }
    @Override
    public void display() {
        System.out.print("I'm a model duck !");
    }
}
