package com.alfred.learn.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.alfred.learn.R;

/**
 * 仿QQ中的健康计步
 */
public class QQHealthView extends View {

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
	/**
	 * 主色调
	 */
	private int mMainColor;

	/**
	 * 步数里程大小
	 */
	private float mArcNumber;

	/**
	 * 步数里程指示器的画笔
	 */
	private Paint mArcPaint;
	/**
	 * 步数和名次的画笔
	 */
	private Paint mTextMainPaint;
	/**
	 * 其他灰色字体的画笔
	 */
	private Paint mTextMinorPaint;
	/**
	 * 虚线画笔
	 */
	private Paint mDashedPaint;
	/**
	 * 底部波纹画笔
	 */
	private Paint mRipplePaint;

	/**
	 * 背景色的画笔
	 */
	private Paint mBgPaint;
	private Path mPath;
	/**
	 * 柱子的画笔
	 */
	private Paint mColumnPaint;

	private AnimatorSet mAnimatorSet;

	//柱的宽度
	private int mWidthColumn;
	//柱的高度
	private int[] mColumnHeight = {6541, 2345, 1543, 7654, 2345, 9642, 9000};
	//柱列表的最大值
	private int mColumnHeightMax;
	private float mColumnCell;

	private int mCurrentWalkNumber;
	private int mCurrentRank;

	private float mWeavX, mWeavY;

	private RectF mRectF;


	public QQHealthView(Context context) {
		this(context, null);
	}

	public QQHealthView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public QQHealthView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
//		initAttributes(context, attrs, defStyleAttr);
		init(context);
	}

//	private void initAttributes(Context context, AttributeSet attributeSet, int defStyleAttr) {
//		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.QQHealthView, defStyleAttr, 0);
//
//		mTitleColor = typedArray.getColor(R.styleable.QQHealthView_titleColor, Color.BLACK);
//		mLineColor = typedArray.getColor(R.styleable.QQHealthView_lineColor, getResources().getColor(R.color.colorQQHealthBlue));
//
//		typedArray.recycle();
//	}

	private void init(Context context) {
		mMainColor = ContextCompat.getColor(context, R.color.colorQQHealthBlue);

		mBgPaint = new Paint();
		mBgPaint.setAntiAlias(true);

		mDashedPaint = new Paint();
		mDashedPaint.setStyle(Paint.Style.STROKE);
		mDashedPaint.setStrokeWidth(2);
		mDashedPaint.setColor(Color.GRAY);

		mTextMainPaint = new Paint();
		mTextMainPaint.setAntiAlias(true);
		mTextMainPaint.setColor(mMainColor);

		mTextMinorPaint = new Paint();
		mTextMinorPaint.setAntiAlias(true);
		mTextMinorPaint.setColor(Color.GRAY);

		mColumnPaint = new Paint();
		mColumnPaint.setAntiAlias(true);
		mColumnPaint.setStyle(Paint.Style.STROKE);
		mColumnPaint.setStrokeJoin(Paint.Join.ROUND);
		mColumnPaint.setStrokeCap(Paint.Cap.ROUND);

		mRipplePaint = new Paint();
		mRipplePaint.setAntiAlias(true);
		mRipplePaint.setStyle(Paint.Style.FILL);
		mRipplePaint.setColor(mMainColor);

		mPath = new Path();

		mArcPaint = new Paint();
		//设置计步里程指示器的底色
		mArcPaint.setColor(getResources().getColor(R.color.cccccc));
		//设置是否使用抗锯齿功能，会消耗较大资源，绘制图形速度会变慢
		mArcPaint.setAntiAlias(true);
		//设置是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
		mArcPaint.setDither(true);
		//设置绘制时各图形的结合方式，如平滑效果等
		mArcPaint.setStrokeJoin(Paint.Join.ROUND);
		//当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式Cap.ROUND,或方形样式Cap.SQUARE
		mArcPaint.setStrokeCap(Paint.Cap.ROUND);
		//画出的图形是空心的
		mArcPaint.setStyle(Paint.Style.STROKE);

		mAnimatorSet = new AnimatorSet();

		mAnimatorSet = new AnimatorSet();


		findMaxNumber(mColumnHeight);

	}

	/**
	 * 计算数组中的最大值
	 */

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
			width = widthSize / 2;
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = heightSize * 3 / 4;
		}
		mViewWidth = width;
		mViewHeight = height;
		setMeasuredDimension(width, height);
		//开始动画
		startAnim();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		//绘制底部的背景
		//左上角,右上角为圆角,左下角右下角为直角
		mRadiuBg = mViewWidth / 20;
		mPath.moveTo(0, mViewHeight);
		mPath.lineTo(0, mRadiuBg);
		//画圆弧
		mPath.quadTo(0, 0, mRadiuBg, 0);
		mPath.lineTo(mViewWidth - mRadiuBg, 0);
		//画圆弧
		mPath.quadTo(mViewWidth, 0, mViewWidth, mRadiuBg);
		mPath.lineTo(mViewWidth, mViewHeight);
		mPath.lineTo(0, mViewHeight);

		//背景色为白色
		mBgPaint.setColor(Color.WHITE);
		canvas.drawPath(mPath, mBgPaint);

		//重置
		mPath.reset();
//		mPath.arcTo(100, 100, 500, 500,140, 270, false);
//		Log.i("QQHealthView", "width = " + mViewWidth);
//		Log.i("QQHealthView", "Height = " + mViewHeight);

//		mPath.arcTo(mViewWidth * 1 / 4, mViewWidth * 1 / 4, mViewWidth * 3 / 4, mViewWidth * 3 / 4, 135, 270, false);
//		canvas.drawPath(mPath, mArcPaint);
		//绘制计步指示器底色
		mRectF = new RectF(mViewWidth * 4, mViewWidth * 8, mViewWidth * 3 / 4, mViewWidth * 5 / 8);
		canvas.drawArc(mRectF, 135, 270, false, mArcPaint);

//		Log.e("QQHealthView", "mArcNumber = " + mArcNumber);
//		mPath.arcTo(mViewWidth / 4, mViewWidth / 4, mViewWidth * 3 / 4, mViewWidth * 3 / 4, 135, mArcNumber, false);
//		canvas.drawPath(mPath, mArcPaint);

		mArcPaint.setColor(mMainColor);
		//设置paint画笔的粗细
		mArcPaint.setStrokeWidth(mViewWidth / 20);
		//绘制计步指示器实际走的步数
		canvas.drawArc(mRectF, 135, mArcNumber, false, mArcPaint);

		//绘制总共走的步数
		mTextMainPaint.setTextAlign(Paint.Align.CENTER);
		mTextMainPaint.setTextSize(mViewWidth / 10);
		canvas.drawText(String.valueOf(mCurrentWalkNumber), mViewWidth / 2, mViewWidth / 2 + 20, mTextMainPaint);

		//绘制名次
		mTextMainPaint.setTextSize(mViewWidth / 15);
		canvas.drawText(String.valueOf(mCurrentRank), mViewWidth / 2, mViewWidth * 3 / 4 - 15, mTextMainPaint);

		//绘制圈内其他的文字
		mTextMinorPaint.setTextSize(mViewWidth / 25);
		mTextMinorPaint.setColor(Color.GRAY);
		canvas.drawText("截止24:00已走", mViewWidth / 2, mViewWidth * 3 / 8 + 10, mTextMinorPaint);
		canvas.drawText("好友平均走5678步", mViewWidth / 2, mViewWidth * 5 / 8 + 10, mTextMinorPaint);
		canvas.drawText("第", mViewWidth / 2 - 60, mViewWidth * 3 / 4 - 15, mTextMinorPaint);
		canvas.drawText("名", mViewWidth / 2 + 60, mViewWidth * 3 / 4 - 15, mTextMinorPaint);

		//绘制圈外的文字
		mTextMinorPaint.setTextAlign(Paint.Align.LEFT);
		canvas.drawText("最近7天", mViewWidth / 15, mViewWidth, mTextMinorPaint);
		mTextMinorPaint.setTextAlign(Paint.Align.RIGHT);
		canvas.drawText("平均6789步/天", mViewWidth * 14 / 15, mViewWidth, mTextMinorPaint);

		//绘制虚线
		mPath.moveTo(mViewWidth / 15, mViewWidth + 100);
		mPath.lineTo(mViewWidth * 14 / 15, mViewWidth + 100);
		/**链接 <a>http://www.cnblogs.com/tianzhijiexian/p/4297783.html</a>*/
		mDashedPaint.setPathEffect(new DashPathEffect(new float[]{5, 5}, 1));
		canvas.drawPath(mPath, mDashedPaint);

		//21代表把view总宽度(左边去掉1/15,右边去掉1/15,剩下13/15)分成21份,两个柱子之间的宽度为两个柱子的宽度
		mWidthColumn = mViewWidth * 13 / 15 / 21;
//		Log.i("QQHealthView", "width column = " + mWidthColumn);
//		Log.i("QQHealthView", "mColumnHeightMax = " + mColumnHeightMax);
		//设置柱子的最大高度为150
		mColumnCell = (float) 150 / mColumnHeightMax;

		mPath.reset();

		mColumnPaint.setStrokeWidth(mViewWidth / 25);

		mTextMinorPaint.setTextAlign(Paint.Align.CENTER);
		mTextMinorPaint.setTextSize(mViewWidth / 25);
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
				mColumnPaint.setColor(mMainColor);
			} else {
				mColumnPaint.setColor(Color.GRAY);
			}
			canvas.drawPath(mPath, mColumnPaint);
			canvas.drawText("0" + (i + 1) + "日", mViewWidth / 15 + (3 * i + 1) * mWidthColumn + (mWidthColumn / 2), mViewWidth + 100 + 50 * 2 + 75, mTextMinorPaint);

		}

		mPath.reset();

		//绘制底部波纹
		mPath.moveTo(0, mViewHeight);
		mPath.lineTo(0, mViewHeight * 5 / 6);
		mPath.cubicTo(mWeavX, mWeavY, mViewWidth * 3 / 4, mViewHeight * 10 / 11, mViewWidth, mViewHeight * 5 / 6);
		mPath.lineTo(mViewWidth, mViewHeight);
		mPath.lineTo(0, mViewHeight);

		canvas.drawPath(mPath, mRipplePaint);

		mTextMinorPaint.setColor(Color.WHITE);
		canvas.drawText("张泉 获得今日冠军", 100, mViewHeight * 11 / 12 + 15, mTextMinorPaint);
		canvas.drawText("查看", mViewWidth - 180, mViewHeight * 11 / 12 + 15, mTextMinorPaint);
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

