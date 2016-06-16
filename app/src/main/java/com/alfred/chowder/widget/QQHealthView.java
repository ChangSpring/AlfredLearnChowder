package com.alfred.chowder.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
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

	public QQHealthView(Context context) {
		this(context, null);
	}

	public QQHealthView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public QQHealthView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initAttributes(context, attrs, defStyleAttr);
	}

	private void initAttributes(Context context, AttributeSet attributeSet, int defStyleAttr) {
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.QQHealthView, defStyleAttr, 0);

		mTitleColor = typedArray.getColor(R.styleable.QQHealthView_titleColor, Color.BLACK);
		mLineColor = typedArray.getColor(R.styleable.QQHealthView_lineColor, Color.BLUE);

		typedArray.recycle();
	}

	private void init(){

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		//MeasureSpec.EXACTLY：父视图希望子视图的大小是specSize中指定的大小；一般是设置了明确的值或者是MATCH_PARENT
		//MeasureSpec.AT_MOST：子视图的大小最多是specSize中的大小；表示子布局限制在一个最大值内，一般为WARP_CONTENT
		//MeasureSpec.UNSPECIFIED：父视图不对子视图施加任何限制，子视图可以得到任意想要的大小；表示子布局想要多大就多大。
		setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : widthSize * (1 / 2), heightMode == MeasureSpec.EXACTLY ? heightSize : heightSize * (3 / 4));
	}
}

