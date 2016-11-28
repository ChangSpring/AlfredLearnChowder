package com.alfred.study.headfirst.duck;

/**
 * Created by Alfred on 2016/11/24.
 */

public class MiniDuckSimulator {
    public static void main(String[] args){
        Duck mallard = new MallardDuck();
        mallard.performFly();
        mallard.performQuack();
    }
}
