package com.alfred.study.headfirst.decorator;

import android.util.Log;

/**
 * Created by Alfred on 2016/12/5.
 */

public class StarbuzzCoffee {
    private static final String TAG = StarbuzzCoffee.class.getSimpleName();

    public static void main(String args[]) {
        Beverage beverage = new Espresso();
        Log.i(TAG, beverage.getDescription() + "$" + beverage.cost());

        Beverage beverage1 = new DarkRoast();
        beverage1 = new Mocha(beverage1);
        beverage1 = new Mocha(beverage1);
        beverage1 = new Whip(beverage1);
        Log.i(TAG, beverage1.getDescription() + "$" + beverage1.cost());

        Beverage beverage2 = new HouseBlend();
        beverage2 = new Soy(beverage2);
        beverage2 = new Mocha(beverage2);
        beverage2 = new Whip(beverage2);
        Log.i(TAG, beverage2.getDescription() + "$" + beverage2.cost());
    }
}
