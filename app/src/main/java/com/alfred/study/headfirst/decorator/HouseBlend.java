package com.alfred.study.headfirst.decorator;

/**
 * Created by Alfred on 2016/12/5.
 */

public class HouseBlend extends Beverage {

    public HouseBlend(){
        mDescription = "HouseBlend";
    }

    @Override
    public double cost() {
        return 0.89;
    }
}
