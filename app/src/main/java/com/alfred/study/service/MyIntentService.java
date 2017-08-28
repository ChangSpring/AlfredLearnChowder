package com.alfred.study.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

/**
 * 会创建独立的worker线程来处理所有的Intent请求；
 * 会创建独立的worker线程来处理onHandleIntent()方法实现的代码，无需处理多线程问题；
 * 所有请求处理完成后，IntentService会自动停止，无需调用stopSelf()方法停止Service；
 * 为Service的onBind()提供默认实现，返回null；
 * 为Service的onStartCommand提供默认实现，将请求Intent添加到队列中；
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //IntentService会使用单独的线程来执行该方法的代码
        //该方法内执行耗时任务,比如下载文件
        long endTime = System.currentTimeMillis() + 20 * 1000;
        Logger.i("onStart");
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try {
                    wait(endTime - System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Logger.i("耗时任务执行完成");
      
    }
}