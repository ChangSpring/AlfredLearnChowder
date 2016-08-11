package com.alfred.chowder.ui;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by JiaM on 16/7/19.
 */
public class AlfredApplication extends Application {

    private static Context mGlabalContext;

    @Override
    public void onCreate() {
        /*
        try {
            Class.forName("android.os.AsyncTask");
        } catch (Throwable ignore) {
        }*/
        super.onCreate();

        mGlabalContext = getApplicationContext();
        Log.i("fadfad","fdasfdsa");
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
