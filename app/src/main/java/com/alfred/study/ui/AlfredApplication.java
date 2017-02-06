package com.alfred.study.ui;

import android.app.Application;
import android.content.Context;

/**
 * Created by Alfred on 16/7/19.
 */
public class AlfredApplication extends Application {
    private static Context mGlabalContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mGlabalContext = this;
    }

    public static Context getGlabalContext() {
        return mGlabalContext;
    }
}
