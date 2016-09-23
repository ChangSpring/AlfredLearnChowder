package com.alfred.chowder.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.alfred.chowder.bean.AnimationPoint;
import com.alfred.chowder.ui.animation.ColorEvaluator;
import com.alfred.chowder.ui.animation.PointEvaluator;

/**
 * Created by alfred on 16/8/10.
 */
public class MyAnimView extends View {

    private AnimationPoint currentPoint;
    //画笔
    private Paint mPaint;
    private String color;

    private static final float RADIUS = 50f;

    public MyAnimView(Context context) {
        super(context);
    }

    public MyAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    public MyAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new AnimationPoint(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimtion();
        } else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    private void startAnimtion() {
//        AnimationPoint startPoint = new AnimationPoint(RADIUS,RADIUS);
//        AnimationPoint endPoint = new AnimationPoint(getWidth() - RADIUS,getHeight() - RADIUS);
        AnimationPoint startPoint = new AnimationPoint(getWidth() / 2 - RADIUS, RADIUS);
        AnimationPoint endPoint = new AnimationPoint(getWidth() / 2 - RADIUS, getHeight() - RADIUS);
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
//        animator.setRepeatCount(5);
//        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentPoint = (AnimationPoint) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        //Interpolator 补间器 控制动画的变化速率
        //AccelerateInterpolator 加速
//        animator.setInterpolator(new AccelerateInterpolator(1.5f));
        //AccelerateDecelerateInterpolator 先加速后减速
        //加速落地之后反弹再次落下
        animator.setInterpolator(new BounceInterpolator());
        ObjectAnimator animator1 = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(), "#0000FF", "#FF0000");
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator).with(animator1);
        animatorSet.setDuration(5000);
        animatorSet.start();
    }
}
