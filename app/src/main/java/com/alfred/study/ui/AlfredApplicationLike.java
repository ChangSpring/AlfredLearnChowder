package com.alfred.study.ui;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.alfred.study.BuildConfig;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.app.TinkerServerManager;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

/**
 * 自定义ApplicationLike类.
 *
 * 注意：这个类是Application的代理类，以前所有在Application的实现必须要全部拷贝到这里<br/>
 *
 * @author wenjiewu
 * @since 2016/11/7
 */
public class AlfredApplicationLike extends DefaultApplicationLike {

    private static Context mGlabalContext;

    public static final String TAG = AlfredApplicationLike.class.getName();

    public AlfredApplicationLike(Application application, int tinkerFlags,
                                 boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                                 long applicationStartMillisTime, Intent tinkerResultIntent, Resources[] resources,
                                 ClassLoader[] classLoader, AssetManager[] assetManager) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent, resources, classLoader,
                assetManager);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mGlabalContext = getApplication();
        // 设置开发设备
        Bugly.setIsDevelopmentDevice(getApplication(), BuildConfig.LOG_DEBUG);
        //初始化bugly
        Bugly.init(getApplication(), BuildConfig.BUGLY_APP_ID,BuildConfig.LOG_DEBUG);

//        TinkerManager.installTinker(this);
        TinkerServerManager.installTinkerServer(getApplication(), Tinker.with(getApplication()),1,BuildConfig.THINKER_PATCH_APP_KEY,BuildConfig.THINKER_PATCH_APP_VERSION,"umeng");
        TinkerServerManager.checkTinkerUpdate(false);
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        //安装tinker
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    public static Context getGlabalContext() {
        return mGlabalContext;
    }
}
