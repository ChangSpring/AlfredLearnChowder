package com.alfred.chowder.widget;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.alfred.chowder.R;

/**
 * 仿QQ中的健康计步
 */
public class QQHealthView extends View {

	/***/
	private int mTitleSize;
	private int mTitleColor;
	private int mLineColor;
	/**
	 * 控件的宽度
	 */
	private int mViewWidth;
	/**
	 * 控件的高度
	 */
	private int mViewHeight;
	/**
	 * 背景左上角右上角的圆角半径
	 */
	private int mRadiuBg;

	private Paint mPaint;
	private Path mPath;
	private Paint mArcPaint;
	private AnimatorSet mAnimatorSet;

	public QQHealthView(Context context) {
		this(context, null);
	}

	public QQHealthView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public QQHealthView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initAttributes(context, attrs, defStyleAttr);
		init();
	}

	private void initAttributes(Context context, AttributeSet attributeSet, int defStyleAttr) {
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.QQHealthView, defStyleAttr, 0);

		mTitleColor = typedArray.getColor(R.styleable.QQHealthView_titleColor, Color.BLACK);
		mLineColor = typedArray.getColor(R.styleable.QQHealthView_lineColor, Color.BLUE);

		typedArray.recycle();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPath = new Path();
		mArcPaint = new Paint();
		mAnimatorSet = new AnimatorSet();

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width, height;
		//MeasureSpec.EXACTLY：父视图希望子视图的大小是specSize中指定的大小；一般是设置了明确的值或者是MATCH_PARENT
		//MeasureSpec.AT_MOST：子视图的大小最多是specSize中的大小；表示子布局限制在一个最大值内，一般为WARP_CONTENT
		//MeasureSpec.UNSPECIFIED：父视图不对子视图施加任何限制，子视图可以得到任意想要的大小；表示子布局想要多大就多大。
		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {
			width = widthSize * 1 / 2;
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = heightSize * 3 / 4;
		}
		mViewWidth = width;
		mViewHeight = height;
		setMeasuredDimension(width, height);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		//绘制底部的背景
		mRadiuBg = mViewWidth / 20;
		mPath.moveTo(0, mViewHeight);
		mPath.lineTo(0, mRadiuBg);
		mPath.quadTo(0, 0, mRadiuBg, 0);
		mPath.lineTo(mViewWidth - mRadiuBg, 0);
		mPath.quadTo(mViewWidth, 0, mViewWidth, mRadiuBg);
		mPath.lineTo(mViewWidth, mViewHeight);
		mPath.lineTo(0, mViewHeight);

		mPaint.setColor(Color.WHITE);
		canvas.drawPath(mPath, mPaint);

		mPath.reset();
		mPaint.reset();

		mArcPaint.setColor(getResources().getColor(R.color.cccccc));
		mArcPaint.setStrokeWidth(mViewWidth / 20);
		mArcPaint.setAntiAlias(true);
		mArcPaint.setDither(true);
		mArcPaint.setStrokeJoin(Paint.Join.ROUND);
		mArcPaint.setStrokeCap(Paint.Cap.ROUND);
		mArcPaint.setStyle(Paint.Style.STROKE);

//		mPath.arcTo(100, 100, 500, 500,140, 270, false);
		Log. i("QQHealthView","width = " + mViewWidth);
		Log. i("QQHealthView","Height = " + mViewHeight);
		mPath.arcTo(mViewWidth * 1 / 4, mViewWidth * 1 / 4, mViewWidth * 3 / 4, mViewWidth * 3 / 4, 135, 270, false);
		canvas.drawPath(mPath, mArcPaint);

		mArcPaint.setColor(Color.BLUE);
		mPath.reset();

		mPath.arcTo(mViewWidth / 4, mViewWidth / 4, mViewWidth * 3 / 4, mViewWidth * 3 / 4, 135, 10, false);
		canvas.drawPath(mPath, mArcPaint);

		mPaint.reset();
		mPaint.setColor(Color.BLUE);
		mPaint.setTextSize(mViewWidth /10);
		canvas.drawText("99999",mViewWidth * 3 /8 ,mViewWidth * 1 / 2 +20,mPaint);


	}
}

