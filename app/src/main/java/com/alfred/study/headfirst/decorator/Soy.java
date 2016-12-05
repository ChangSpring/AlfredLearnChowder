package com.alfred.study.headfirst.decorator;

/**
 * Created by Alfred on 2016/12/5.
 */

public class Soy extends CondimentDecorator {
    Beverage mBeverage ;

    public  Soy(Beverage beverage){
        this.mBeverage = beverage;
    }

    public double cost(){
        double cost = mBeverage.cost() + 0.15;
        switch (getSize()){
            case Beverage.TALL:
                cost += 0.10;
                break;
            case Beverage.CENTER:
                cost += 0.15;
                break;
            case Beverage.VENTI:
                cost += 0.20;
                break;

            default:
                break;
        }
        return cost;
    }

    @Override
    public String getDesciption() {
        return mBeverage.getDescription() + "Soy";
    }

    public int getSize(){
        return mBeverage.getSize();
    }
}
