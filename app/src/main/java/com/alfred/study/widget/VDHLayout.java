package com.alfred.study.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Alfred on 2017/1/12.
 */

public class VDHLayout extends LinearLayout {
    private ViewDragHelper mViewDragHelper;

    private View mDragView;
    private View mAutoBackView;
    private View mEdgeTrackerView;

    private Point mAutoBackOriginPos = new Point();

    public VDHLayout(Context context) {
        this(context, null);
    }

    public VDHLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public VDHLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        //创建ViewDragHelper的实例,第一个参数是ViewGroup,第二个参数就是滑动灵敏度
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            /**
             * 根据返回结果决定当前child是否可以拖拽
             *
             * @param child     当前被拖拽的view
             * @param pointerId 区分多点触摸的id
             * @return
             */
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mDragView || child == mAutoBackView;
            }

            /**
             * @param child
             * @param left  代表你将要移动到的位置的坐标,返回值就是最终确定的移动的位置,
             *              判断如果这个坐标在layout之内,那我们就返回这个坐标值，
             *              不能让他超出这个范围, 除此之外就是如果你的layout设置了padding的话，
             *              让子view的活动范围在padding之内的
             * @param dx
             * @return
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
//                final int leftBound = getPaddingLeft();
//                final int rightBound = getWidth() - child.getWidth() - leftBound;
//
//                return Math.min(Math.max(left,leftBound),rightBound);
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
//                final int topBound = getPaddingTop();
//                final int bottomBound = getHeight() - child.getHeight() - topBound;
//
//                return Math.min(Math.max(top,topBound),bottomBound);

                return top;
            }

            /**
             * 返回拖拽的范围,不对拖拽进行真正的限制,仅仅决定了动画的执行速度
             *
             * @param child
             * @return
             */
            @Override
            public int getViewHorizontalDragRange(View child) {
                return super.getViewHorizontalDragRange(child);
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return super.getViewVerticalDragRange(child);
            }

            /**
             * 位置改变时回调，常用于滑动是更改scale进行缩放等效果
             *
             * @param changedView
             * @param left
             * @param top
             * @param dx          横向滑动的加速度
             * @param dy
             */
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            /**
             * 拖动结束后调用,也就是手指释放的时候回调
             *
             * @param releasedChild
             * @param xvel          水平方向的速度  向右为正
             * @param yvel          竖直方向的速度  向下为正
             */

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
//                super.onViewReleased(releasedChild, xvel, yvel);
                if (releasedChild == mAutoBackView) {
                    //手指释放可以自动回去
                    mViewDragHelper.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                    invalidate();
                }
            }

            /**
             * 在边界拖动时回调,该方法一帮要结合ViewDragHelper一个属性一起使用才有效果，通过设置setEdgeTrackingEnabled(int edgeFlags)
             * ，edgeFlags有五个选项分别是EDGE_LEFT、EDGE_RIGHT、EDGE_TOP、EDGE_BOTTOM和EDGE_ALL
             * @param edgeFlags
             * @param pointerId
             */
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
//                super.onEdgeDragStarted(edgeFlags, pointerId);
                mViewDragHelper.captureChildView(mEdgeTrackerView,pointerId);
            }
        });

        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mAutoBackOriginPos.x = mAutoBackView.getLeft();
        mAutoBackOriginPos.y = mAutoBackView.getTop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }
}