package com.alfred.study.ui;

import android.app.Application;
import android.content.Context;

import com.alfred.study.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Alfred on 16/7/19.
 */
public class AlfredApplication extends Application {

    private static Context mGlabalContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mGlabalContext = getApplicationContext();
        //初始化bugly
        CrashReport.initCrashReport(mGlabalContext, BuildConfig.BUGLY_APP_ID,BuildConfig.LOG_DEBUG);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

    public static Context getGlabalContext() {
        return mGlabalContext;
    }

}
