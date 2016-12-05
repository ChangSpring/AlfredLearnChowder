package com.alfred.study.headfirst.decorator;

/**
 * Created by Alfred on 2016/12/5.
 */

public class Decaf extends Beverage {

    public Decaf(){
        mDescription = "Decaf";
    }
    @Override
    public double cost() {
        return 1.05;
    }
}
