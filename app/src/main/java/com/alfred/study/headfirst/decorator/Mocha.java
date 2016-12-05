package com.alfred.study.headfirst.decorator;

/**
 * Created by Alfred on 2016/12/5.
 */

public class Mocha extends CondimentDecorator {
    Beverage mBeverage;

    public Mocha(Beverage beverage){
        this.mBeverage = beverage;
    }

    public double cost(){
        return mBeverage.cost() + 0.20;
    }

    @Override
    public String getDesciption() {
        return mBeverage.getDescription() + "Mocha";
    }
}
