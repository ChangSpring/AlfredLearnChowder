package com.alfred.study.widget;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alfred.study.R;


/**
 * Created by Alfred on 2017/1/4.
 */

public class PopupToast extends PopupWindow {
    private TextView alertTv;

    private int width, height;
    private Handler mHandler;


    public PopupToast(Context context) {
        width = ViewGroup.LayoutParams.MATCH_PARENT;
        height = ViewGroup.LayoutParams.WRAP_CONTENT;

        alertTv = (TextView) LayoutInflater.from(context).inflate(R.layout.popup_toast, null);
        setContentView(alertTv);
        setWidth(width);
        setHeight(height);
//        setOutsideTouchable(true);
//        setFocusable(true);
        update();
        setAnimationStyle(R.style.PopupAnimation);

        mHandler = new Handler();

    }

    public void setText(String content) {
        alertTv.setText(content);
        update(alertTv, width, height);
    }

    public void setBackGround(int color) {
        alertTv.setBackgroundColor(color);
        update(alertTv, width, height);
    }

    public void show(View view) {
        if (!isShowing()) {
            showAsDropDown(view);
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 500);
    }
}

