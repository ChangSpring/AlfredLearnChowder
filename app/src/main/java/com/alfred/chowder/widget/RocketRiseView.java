package com.alfred.chowder.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 火箭上升的view,类似于各大手机卫士的清理小火箭
 * Created by Alfred on 2016/6/29.
 */
public class RocketRiseView extends View {

	/**
	 * 控件的宽度
	 */
	private int mWidth;
	/**
	 * 控件的高度
	 */
	private int mHeight;

	private Paint mPaint;
	private Path mPath;
	private Rect mRect;

	/**
	 * 当前火箭位置x坐标
	 */
	private int mXCurrent;
	/**
	 * 当前火箭位置y坐标
	 */
	private int mYCurrent;

	public RocketRiseView(Context context) {
		this(context, null);
	}

	public RocketRiseView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RocketRiseView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPath = new Path();
		mRect = new Rect();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width, height;

		//如果布局中设置view的宽度为固定值,那么布局的宽度就取固定值,如果设置为match_parent,那就是取父布局大小
		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {
			//如果设置view为wrap_content,那么就设置为布局宽度的1/2
			width = widthSize / 2;
		}

		//如果布局中设置view的高度为固定值,那么布局的高度就取固定值,如果设置为match_parent,那就是取父布局大小
		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			//如果设置view为wrap_content,那么就设置为布局高度的3/4
			height = heightSize * 3 / 4;
		}

		mWidth = width;
		mHeight = height;

		mXCurrent = mWidth / 2;
		mYCurrent = mHeight / 2;

		setMeasuredDimension(width, height);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		//绘制火箭
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(5);


		mPath.moveTo(mXCurrent / 2, mYCurrent / 2);
		mPath.lineTo(mXCurrent / 2 - 100, mYCurrent / 2 + 100);
		mPath.moveTo(mXCurrent / 2, mYCurrent / 2);
		mPath.lineTo(mXCurrent / 2 + 100, mYCurrent / 2 + 100);
		mPath.moveTo(mXCurrent / 2 - 50, mYCurrent / 2 + 50);
		mPath.lineTo(mXCurrent / 2 - 50, mYCurrent / 2 + 150);
		mPath.lineTo(mXCurrent / 2 + 45, mYCurrent / 2 + 150);
		mPath.lineTo(mXCurrent / 2 + 45, mYCurrent / 2 + 50);
		canvas.drawPath(mPath, mPaint);

		//绘制发射台(二阶贝塞尔曲线)
		mPaint.setStrokeWidth(5);
		mPath.reset();

		mPath.moveTo(10, mHeight - 20);
//		mPath.quadTo(mXCurrent, mYCurrent - 50, mWidth - 10, mHeight - 20);

		canvas.drawPath(mPath, mPaint);

		//绘制发射成功后的文字

	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				mXCurrent = x;
				mYCurrent = y;
				postInvalidate();
				break;
			case MotionEvent.ACTION_UP:
				//开始火箭上升的动画
				break;
		}
		return true;
	}
}
