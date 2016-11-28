package com.alfred.study.headfirst.duck;

/**
 * Created by Alfred on 2016/11/24.
 */

public abstract class Duck {
    FlyBehavior mFlyBehavior;
    QuackBehavior mQuackBehavior;

    public Duck(){}

    public abstract void display();

    public void performFly(){
        mFlyBehavior.fly();
    }

    public void performQuack(){
        mQuackBehavior.quack();
    }

    public void swim(){
        System.out.println("All ducks float , even decoys");
    }
}
