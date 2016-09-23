package com.alfred.chowder.ui.animation;

import android.animation.TypeEvaluator;
import android.util.Log;

import com.alfred.chowder.bean.AnimationPoint;

/**
 * Created by alfred on 16/8/10.
 */
public class PointEvaluator implements TypeEvaluator {

    private static final String TAG = "PointEvaluator";

    @Override
    public Object evaluate(float v, Object o, Object t1) {
        //v是指动画的完成度 o是指动画的初始值 t1是指动画的结束值
        AnimationPoint startPoint = (AnimationPoint) o;
        AnimationPoint endPoint = (AnimationPoint) t1;
        Log.i(TAG,"start point x = " + startPoint.getX());
        Log.i(TAG,"start point y = " + startPoint.getY());
        Log.i(TAG,"end point x = " + endPoint.getX());
        Log.i(TAG,"end point y = " + endPoint.getY());
        float x = startPoint.getX() + v * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + v * (endPoint.getY() - startPoint.getY());
        return new AnimationPoint(x, y);
    }
}
