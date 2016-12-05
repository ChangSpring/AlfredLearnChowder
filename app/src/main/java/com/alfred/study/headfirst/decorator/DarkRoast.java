package com.alfred.study.headfirst.decorator;

/**
 * Created by Alfred on 2016/12/5.
 */

public class DarkRoast extends Beverage {

    public DarkRoast(){
        mDescription = "DarkRoast";
    }

    @Override
    public double cost() {
        return 0.99;
    }
}
