package com.alfred.study.headfirst.decorator;


/**
 * Created by Alfred on 2016/12/5.
 */

public abstract class Beverage {
    String mDescription = "Unknown Beverage";
    int mSize = 0;

    public static final int TALL = 0;
    public static final int CENTER = 1;
    public static final int VENTI = 2;

    protected String getDescription() {
        return mDescription;
    }

    protected int getSize() {
        return mSize;
    }


    protected void setSize(int size) {
        this.mSize = size;
    }

    public abstract double cost();
}
