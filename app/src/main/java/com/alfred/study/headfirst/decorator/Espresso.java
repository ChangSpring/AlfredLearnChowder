package com.alfred.study.headfirst.decorator;

/**
 * Created by Alfred on 2016/12/5.
 */

public class Espresso extends Beverage {

    public Espresso(){
        mDescription = "Espresso";
    }
    @Override
    public double cost() {
        return 1.99;
    }
}
