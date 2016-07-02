package com.alfred.chowder.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 贝赛尔曲线的学习
 * Created by Alfred on 2016/6/28.
 */
public class BesselCurveView extends View{
	private Paint mPaint;
	private Path mPath;

	private int xWidth = 100;
	private int yHeight = 100;

	public BesselCurveView(Context context) {
		this(context,null);
	}

	public BesselCurveView(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}

	public BesselCurveView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	/**
	 *	初始化对象
	 */
	private void init(){
		mPaint = new Paint();
		mPath = new Path();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//设置绘制风格为空心
		mPaint.setStyle(Paint.Style.STROKE);
		//设置空心边框的宽度
		mPaint.setStrokeWidth(10);

		//重置path
		mPath.reset();


		//绘制二阶贝塞尔曲线
		//移动到指定位置
		mPath.moveTo(100,100);
		//其中 x1,y1 的坐标就是图中小圆点的位置，也就是控制点的坐标 x2,y2 的坐标就是图中曲线右边终点的位置坐标。
		mPath.quadTo(xWidth,yHeight,700,500);
		canvas.drawPath(mPath,mPaint);
		//设置绘制风格为实心
		mPaint.setStyle(Paint.Style.FILL);

		//绘制小圆点
		canvas.drawCircle(xWidth,yHeight,100,mPaint);


	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				xWidth = x;
				yHeight = y;
				postInvalidate();
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		return true;
	}
}
