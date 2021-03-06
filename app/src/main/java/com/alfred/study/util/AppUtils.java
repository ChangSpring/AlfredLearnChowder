package com.alfred.study.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.alfred.study.ui.AlfredApplication;

/**
 * 跟app相关的辅助类
 * Created by Alfred on 2015/11/17.
 */
public class AppUtils {

    private AppUtils() {
        /**cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName() {
        try {
            PackageManager packageManager = AlfredApplication.getGlabalContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    AlfredApplication.getGlabalContext().getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本号信息]
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode() {
        try {
            PackageManager packageManager = AlfredApplication.getGlabalContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    AlfredApplication.getGlabalContext().getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
