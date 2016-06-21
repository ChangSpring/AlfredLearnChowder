package com.alfred.chowder.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 */
public class CustomView extends View {

	public CustomView(Context context) {
		this(context, null);
	}

	public CustomView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = new Paint();

		paint.setColor(getResources().getColor(android.R.color.holo_red_dark));
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(10);
//		canvas.drawCircle(300,300,300,paint);
//		canvas.drawRect(100,100,300,300,paint);
//		canvas.drawRoundRect(100, 100, 300, 300, 60, 30, paint);

//		paint.setAntiAlias(true);
//
//		canvas.drawCircle(150,150,300,paint);
//
//		paint.setStyle(Paint.Style.STROKE);
//
//		canvas.drawCircle(150,150,300,paint);
//
//		paint.setStyle(Paint.Style.FILL_AND_STROKE);
//
//		paint.setStrokeWidth(10);
//
//		canvas.drawCircle(150,150,300,paint);
	}
}

