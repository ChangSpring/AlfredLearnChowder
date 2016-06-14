package com.alfred.chowder.global;

import android.app.Application;
import android.content.Context;

/**
 * Created by Alfred on 2016/6/14.
 */
public class MyApplication extends Application{

	private static Context mGlabalContext ;

	@Override
	public void onCreate() {
		super.onCreate();

		mGlabalContext = getApplicationContext();
	}

	public static Context getGlabalContext() {
		return mGlabalContext;
	}
}
