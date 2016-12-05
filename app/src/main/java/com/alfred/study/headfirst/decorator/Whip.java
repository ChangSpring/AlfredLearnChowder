package com.alfred.study.headfirst.decorator;

/**
 * Created by Alfred on 2016/12/5.
 */

public class Whip extends CondimentDecorator {
    public Beverage mBeverage;

    public Whip(Beverage beverage) {
        this.mBeverage = beverage;
    }

    public double cost() {
        return mBeverage.cost() + .10;
    }

    @Override
    public String getDesciption() {
        return mBeverage.getDescription() + "Whip";
    }
}
