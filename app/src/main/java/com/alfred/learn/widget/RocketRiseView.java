package com.alfred.learn.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
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

	private int mArcWidth;
	private int mArcHeight;

	private AnimatorSet mAnimSet;
	private boolean mIsSuccess;

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
		mAnimSet = new AnimatorSet();
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

		//设置火箭的绘制风格
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(5);
		mPaint.setColor(Color.BLUE);
		mPaint.setAntiAlias(true);

		//控制火箭的移动范围
		if (mXCurrent < mWidth * 1 / 10) {
			mXCurrent = mWidth * 1 / 10;
		}
		if (mXCurrent > mWidth * 9 / 10) {
			mXCurrent = mXCurrent * 9 / 10;
		}

//		if (mYCurrent < mHeight * 1 / 10) {
//			mYCurrent = mHeight * 1 / 10;
//		}
		if (mYCurrent > mHeight * 8 / 10) {
			mYCurrent = mHeight * 8 / 10;
		}

		if (mYCurrent > mHeight * 7 / 10 && mXCurrent < mWidth * 4 / 10) {
			mYCurrent = mHeight * 7 / 10;
		}
		if (mYCurrent > mHeight * 7 / 10 && mXCurrent > mWidth * 6 / 10) {
			mYCurrent = mHeight * 7 / 10;
		}

		//开始绘制火箭
		mPath.moveTo(mXCurrent, mYCurrent);
		mPath.lineTo(mXCurrent + 50, mYCurrent);
		mPath.lineTo(mXCurrent + 50, mYCurrent - 150);
		mPath.moveTo(mXCurrent,mYCurrent - 150);
		mPath.lineTo(mXCurrent,mYCurrent);
		mPath.moveTo(mXCurrent - 25, mYCurrent - 150);
		mPath.lineTo(mXCurrent + 75,mYCurrent - 150);
		mPath.lineTo(mXCurrent + 25,mYCurrent - 250);
		mPath.lineTo(mXCurrent - 25,mYCurrent - 150);

		canvas.drawPath(mPath, mPaint);

		//绘制发射台(二阶贝塞尔曲线)
		mPaint.setStrokeWidth(10);
		mPath.reset();

		mPath.moveTo(mWidth * 1 / 10, mHeight * 7 / 10);
		//当火箭移动到指定的位置时才会引起发射台弯曲,这里进行判断
		if (mYCurrent > mHeight * 7 / 10 && mXCurrent > mWidth * 4 / 10 && mXCurrent < mWidth * 6 / 10) {
			Log.i("RocketRiseView", "弯曲");
			mArcHeight = mYCurrent + (mYCurrent - mHeight * 7 / 10);
		} else {
			Log.i("RocketRiseView", "不弯曲");
			mArcHeight = mHeight * 7 / 10;
		}
		mArcWidth = mXCurrent * 5 / 10;

		mPath.quadTo(mArcWidth, mArcHeight, mWidth * 9 / 10, mHeight * 7 / 10);

		canvas.drawPath(mPath, mPaint);

		//绘制发射成功后的文字
		mPaint.reset();
		if (mIsSuccess && mYCurrent < 0) {
			mPaint.setTextSize(80);
			mPaint.setColor(Color.RED);
			mPaint.getTextBounds("火箭发射成功", 0, 6, mRect);
			canvas.drawText("火箭发射成功", mWidth * 1 / 2 - mRect.width() / 2, mHeight * 1 / 2 + mRect.height() * 1 / 2, mPaint);
		}
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
				if (mYCurrent > mHeight * 7 / 10 && mXCurrent > mWidth * 4 / 10 && mXCurrent < mWidth * 6 / 10) {
					startAnim();
				}
				break;
		}
		return true;
	}

	private void startAnim() {
		//动画实现
		ValueAnimator animator = ValueAnimator.ofInt(mYCurrent, -mHeight/8);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				mYCurrent = (int) animation.getAnimatedValue();
				postInvalidate();
			}
		});
		mAnimSet.setDuration(1200);
		mAnimSet.play(animator);
		mAnimSet.start();
		mIsSuccess = true;
	}
}
