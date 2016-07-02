package com.alfred.chowder.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
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
	private Paint mTextPaint;
	private Path mPath;
	private Paint mGrayColumnPaint;
	private Paint mBlueColumnPaint;
	private Paint mArcPaint;
	private AnimatorSet mAnimatorSet;

	//柱的宽度
	private int mWidthColumn;
	//柱的高度
	private int[] mColumnHeight = {6541, 2345, 1543, 7654, 2345, 9642, 9000};
	private int mColumnHeightMax;
	private float mColumnCell;

	private int mCurrentWalkNumber;
	private int mCurrentRank;

	private float mArcNumber;
	private float mWeavX, mWeavY;


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
		mLineColor = typedArray.getColor(R.styleable.QQHealthView_lineColor, getResources().getColor(R.color.colorQQHealthBlue));

		typedArray.recycle();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mBlueColumnPaint = new Paint();
		mBlueColumnPaint.setAntiAlias(true);
		mGrayColumnPaint = new Paint();
		mGrayColumnPaint.setAntiAlias(true);
		mPath = new Path();
		mArcPaint = new Paint();
		mAnimatorSet = new AnimatorSet();

		mAnimatorSet = new AnimatorSet();

		findMaxNumber(mColumnHeight);

	}

	private void findMaxNumber(int[] numbers) {
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < numbers.length - 1 - i; j++) {
				if (numbers[j] > numbers[j + 1]) {
					mColumnHeightMax = numbers[j];
				}
			}
		}
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
		startAnim();
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
//		Log.i("QQHealthView", "width = " + mViewWidth);
//		Log.i("QQHealthView", "Height = " + mViewHeight);
		mPath.arcTo(mViewWidth * 1 / 4, mViewWidth * 1 / 4, mViewWidth * 3 / 4, mViewWidth * 3 / 4, 135, 270, false);
		canvas.drawPath(mPath, mArcPaint);

		mArcPaint.setColor(getResources().getColor(R.color.colorQQHealthBlue));
		mPath.reset();

		Log.e("QQHealthView", "mArcNumber = " + mArcNumber);
		mPath.arcTo(mViewWidth / 4, mViewWidth / 4, mViewWidth * 3 / 4, mViewWidth * 3 / 4, 135, mArcNumber, false);
		canvas.drawPath(mPath, mArcPaint);

		mPaint.reset();
		mPaint.setAntiAlias(true);
		mPaint.setColor(getResources().getColor(R.color.colorQQHealthBlue));
		mPaint.setTextAlign(Paint.Align.CENTER);
		mPaint.setTextSize(mViewWidth / 10);
		canvas.drawText(String.valueOf(mCurrentWalkNumber), mViewWidth / 2, mViewWidth / 2 + 20, mPaint);

		mPaint.setTextSize(mViewWidth / 15);
		canvas.drawText(String.valueOf(mCurrentRank), mViewWidth / 2, mViewWidth * 3 / 4 - 15, mPaint);

		mPaint.setTextSize(mViewWidth / 25);
		mPaint.setColor(Color.GRAY);
		canvas.drawText("截止24:00已走", mViewWidth / 2, mViewWidth * 3 / 8 + 10, mPaint);

		canvas.drawText("好友平均走5678步", mViewWidth / 2, mViewWidth * 5 / 8 + 10, mPaint);
		canvas.drawText("第", mViewWidth / 2 - 60, mViewWidth * 3 / 4 - 15, mPaint);
		canvas.drawText("名", mViewWidth / 2 + 60, mViewWidth * 3 / 4 - 15, mPaint);

		mPaint.setTextAlign(Paint.Align.LEFT);
		canvas.drawText("最近7天", mViewWidth / 15, mViewWidth, mPaint);
		mPaint.setTextAlign(Paint.Align.RIGHT);
		canvas.drawText("平均6789步/天", mViewWidth * 14 / 15, mViewWidth, mPaint);

		mPaint.reset();
		mPath.reset();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(2);
		mPaint.setColor(Color.GRAY);
		mPath.moveTo(mViewWidth / 15, mViewWidth + 100);
		mPath.lineTo(mViewWidth * 14 / 15, mViewWidth + 100);
		//TODO 需要学习
		mPaint.setPathEffect(new DashPathEffect(new float[]{5, 5}, 1));
		canvas.drawPath(mPath, mPaint);

		mWidthColumn = mViewWidth * 13 / 15 / 21;
//		Log.i("QQHealthView", "width column = " + mWidthColumn);
//		Log.i("QQHealthView", "mColumnHeightMax = " + mColumnHeightMax);
		mColumnCell = (float) 150 / mColumnHeightMax;

		mPaint.reset();
		mPath.reset();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(mViewWidth / 25);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);

		mBlueColumnPaint.setColor(getResources().getColor(R.color.colorQQHealthBlue));
		mBlueColumnPaint.setStyle(Paint.Style.STROKE);
		mBlueColumnPaint.setStrokeWidth(mViewWidth / 25);
		mBlueColumnPaint.setStrokeJoin(Paint.Join.ROUND);
		mBlueColumnPaint.setStrokeCap(Paint.Cap.ROUND);

		mGrayColumnPaint.setColor(Color.GRAY);
		mGrayColumnPaint.setStyle(Paint.Style.STROKE);
		mGrayColumnPaint.setStrokeWidth(mViewWidth / 25);
		mGrayColumnPaint.setStrokeJoin(Paint.Join.ROUND);
		mGrayColumnPaint.setStrokeCap(Paint.Cap.ROUND);

		mTextPaint.setColor(Color.GRAY);
		mTextPaint.setTextAlign(Paint.Align.CENTER);
		mTextPaint.setTextSize(mViewWidth / 25);
		for (int i = 0; i < 7; i++) {

//			Log.i("QQHealthView", "i = " + i);
//			Log.i("QQHealthView", "mViewWidth = " + mViewWidth);
//			Log.i("QQHealthView", "mWidthColumn = " + mWidthColumn);
//			Log.i("QQHealthView", "mColumnCell = " + mColumnCell);
//			Log.i("QQHealthView", "mColumnHeight[i] = " + mColumnHeight[i]);
//
//			Log.i("QQHealthView", "mViewWidth / 15 + (2 * i + 1) * mWidthColumn = " + (mViewWidth / 15 + (2 * i + 1) * mWidthColumn));
//			Log.i("QQHealthView", "mViewWidth + 100 + 50 * 2 = " + (mViewWidth + 100 + 50 * 2));
//			Log.i("QQHealthView", "mViewWidth + 100 + 50 * 2 - mColumnHeight[i] * mColumnCell= " + (mViewWidth + 100 + 50 * 2 - mColumnHeight[i] * mColumnCell));

			if (i == 0) {
				mPath.moveTo(mViewWidth / 15 + mWidthColumn, mViewWidth + 100 + 50 * 2);
				mPath.lineTo(mViewWidth / 15 + mWidthColumn, mViewWidth + 100 + 50 * 2 - mColumnHeight[i] * mColumnCell);
			} else {
				mPath.moveTo(mViewWidth / 15 + (3 * i + 1) * mWidthColumn, mViewWidth + 100 + 50 * 2);
				mPath.lineTo(mViewWidth / 15 + (3 * i + 1) * mWidthColumn, mViewWidth + 100 + 50 * 2 - mColumnHeight[i] * mColumnCell);
			}
//			Log.i("QQHealthView", "mColumnHeight[i] * mColumnCell = " + mColumnHeight[i] * mColumnCell);
//			Log.i("QQHealthView", "mColumnHeight[i] * mColumnCell = " + (mColumnHeight[i] * mColumnCell >= 50 * 2) + "");
			if (mColumnHeight[i] * mColumnCell >= 50 * 2) {
				canvas.drawPath(mPath, mBlueColumnPaint);
			} else {
				canvas.drawPath(mPath, mGrayColumnPaint);
			}
			canvas.drawText("0" + (i + 1) + "日", mViewWidth / 15 + (3 * i + 1) * mWidthColumn + (mWidthColumn / 2), mViewWidth + 100 + 50 * 2 + 75, mTextPaint);

		}

		mPath.reset();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(getResources().getColor(R.color.colorQQHealthBlue));
		mPath.moveTo(0, mViewHeight);
		mPath.lineTo(0, mViewHeight * 5 / 6);
		mPath.cubicTo(mWeavX, mWeavY, mViewWidth * 3 / 4, mViewHeight * 10 / 11, mViewWidth, mViewHeight * 5 / 6);
		mPath.lineTo(mViewWidth, mViewHeight);
		mPath.lineTo(0, mViewHeight);

		canvas.drawPath(mPath, mPaint);

		mPaint.setColor(Color.WHITE);
		mPaint.setTextSize(mViewWidth / 25);
		canvas.drawText("张泉 获得今日冠军", 100, mViewHeight * 11 / 12 + 15, mPaint);
		canvas.drawText("查看", mViewWidth - 180, mViewHeight * 11 / 12 + 15, mPaint);
	}

	private void startAnim() {
		//步数动画的实现
		ValueAnimator walkAnimator = ValueAnimator.ofInt(0, mColumnHeight[3]);
		walkAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				mCurrentWalkNumber = (int) animation.getAnimatedValue();
				postInvalidate();
			}
		});
		//排名动画的实现
		ValueAnimator rankAnimator = ValueAnimator.ofInt(0, 15);
		rankAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				mCurrentRank = (int) animation.getAnimatedValue();
				postInvalidate();
			}
		});

		Log.e("QQHealthView", "270 / mColumnHeightMax * mColumnHeight[3]) = " + 270 / mColumnHeightMax * mColumnHeight[3]);
		;
		//圆弧动画的实现
		ValueAnimator arcAnimator = ValueAnimator.ofFloat(0, (float) 270 / mColumnHeightMax * mColumnHeight[3]);
		arcAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				mArcNumber = (float) animation.getAnimatedValue();
				postInvalidate();
			}
		});

		//水波纹动画的实现
		ValueAnimator weavXAnimator = ValueAnimator.ofFloat(mViewWidth * 1 / 10, mViewWidth * 2 / 10);
		weavXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				mWeavX = (float) animation.getAnimatedValue();
				postInvalidate();
			}
		});
		ValueAnimator weavYAnimator = ValueAnimator.ofFloat(mViewHeight * 10 / 12, mViewHeight * 11 / 12);
		weavYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				mWeavY = (float) animation.getAnimatedValue();
				postInvalidate();
			}
		});


		mAnimatorSet.setDuration(3000);
		mAnimatorSet.playTogether(walkAnimator, rankAnimator, arcAnimator, weavXAnimator, weavYAnimator);
		mAnimatorSet.start();

	}
}

