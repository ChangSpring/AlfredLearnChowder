package com.alfred.study.widget;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Alfred on 2017/2/7.
 */

public class LeftDrawerLayout extends ViewGroup {
    private View mLeftMenuView;
    private View mContentView;

    private ViewDragHelper mViewDragHelper;

    /**
     * drawer显示出来的占自身的百分比
     */
    private float mLeftMenuOnScreen;
    /**
     * drawer离父容器右边的最小外边距
     */
    private int mMinDrawerMargin;

    private static final int MIN_DRAWER_MARGIN = 64;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final String TAG = LeftDrawerLayout.class.getSimpleName();

    public LeftDrawerLayout(Context context) {
        this(context, null);
    }

    public LeftDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LeftDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        final float density = getResources().getDisplayMetrics().density;
        final float minVel = MIN_FLING_VELOCITY * density;
        mMinDrawerMargin = (int) (MIN_DRAWER_MARGIN * density + 0.5f);
        Log.i(TAG,"density = " + density + "minVel = " + minVel + "minDrawerMargin = " + mMinDrawerMargin);

        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {

            /**
             *
             * @param changedView
             * @param left 负值
             * @param top
             * @param dx
             * @param dy
             */
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                final int childWidth = changedView.getWidth();
                float offset = (float) (childWidth + left) / childWidth;
                Log.i(TAG,"left  = " + left + "  offset = " + offset);
                mLeftMenuOnScreen = offset;
                changedView.setVisibility(offset == 0 ? View.GONE : View.VISIBLE);
                invalidate();
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                final int childWidth = releasedChild.getWidth();
                float offset = (childWidth + releasedChild.getLeft()) * 1.0f / childWidth;
                mViewDragHelper.settleCapturedViewAt(xvel > 0 || xvel == 0 && offset > 0.5f ? 0 : -childWidth, releasedChild.getTop());
                invalidate();
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                mViewDragHelper.captureChildView(mLeftMenuView, pointerId);
            }

            /**
             * 返回view的移动范围
             * @param child
             * @return
             */
            @Override
            public int getViewHorizontalDragRange(View child) {
                return mLeftMenuView == child ? child.getWidth() : 0;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return super.getViewVerticalDragRange(child);
            }

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mLeftMenuView;
            }

            /**
             * 控制移动时候的移动范围
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                Log.i(TAG,"clamp left = " + left);
                return Math.max(-child.getWidth(), Math.min(left, 0));
//                return left;
            }
        });

        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        mViewDragHelper.setMinVelocity(minVel);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthSize, heightSize);

        View leftMenuView = getChildAt(1);
        MarginLayoutParams layoutParams = (MarginLayoutParams) leftMenuView.getLayoutParams();
        final int drawerWidthSpec = getChildMeasureSpec(widthMeasureSpec, mMinDrawerMargin + layoutParams.leftMargin + layoutParams.rightMargin, layoutParams
                .width);
        final int drawerHeightSpec = getChildMeasureSpec(heightMeasureSpec, layoutParams.topMargin + layoutParams.bottomMargin, layoutParams.height);

        leftMenuView.measure(drawerWidthSpec, drawerHeightSpec);

        View contentView = getChildAt(0);
        layoutParams = (MarginLayoutParams) contentView.getLayoutParams();
        final int contentWidthSpec = MeasureSpec.makeMeasureSpec(widthSize - layoutParams.leftMargin - layoutParams.rightMargin, MeasureSpec.EXACTLY);
        final int contentHeightSpec = MeasureSpec.makeMeasureSpec(heightSize - layoutParams.topMargin - layoutParams.bottomMargin, MeasureSpec.EXACTLY);
        contentView.measure(contentWidthSpec, contentHeightSpec);

        mLeftMenuView = leftMenuView;
        mContentView = contentView;
        Log.i(TAG,"measure width = " + leftMenuView.getWidth());

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View menuView = mLeftMenuView;
        View contentView = mContentView;

        MarginLayoutParams layoutParams = (MarginLayoutParams) contentView.getLayoutParams();
        contentView.layout(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.leftMargin + contentView.getMeasuredWidth(), layoutParams.topMargin
                + contentView.getMeasuredHeight());

        layoutParams = (MarginLayoutParams) menuView.getLayoutParams();

        final int menuWidth = menuView.getMeasuredWidth();
        int childLeft = -menuWidth + (int) (menuWidth * mLeftMenuOnScreen);
        menuView.layout(childLeft, layoutParams.topMargin, childLeft + menuWidth, layoutParams.topMargin + menuView.getMeasuredHeight());
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

    public void closeDrawer(){
        View menuView = mLeftMenuView;
        mLeftMenuOnScreen = 0.f;
        mViewDragHelper.smoothSlideViewTo(menuView,-menuView.getWidth(),menuView.getTop());
        invalidate();
    }

    public void openDrawer(){
        View menuView = mLeftMenuView;
        mLeftMenuOnScreen = 1.0f;
        mViewDragHelper.smoothSlideViewTo(menuView,0,menuView.getTop());
        invalidate();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }
}
