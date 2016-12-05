package com.alfred.study.headfirst.decorator;

/**
 * Created by Alfred on 2016/12/5.
 */

public abstract class CondimentDecorator extends Beverage {
    @Override
    public double cost() {
        return 0;
    }

    public abstract String getDesciption();
}
