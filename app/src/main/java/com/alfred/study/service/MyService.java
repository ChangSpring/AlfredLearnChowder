package com.alfred.study.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;

import com.orhanobut.logger.Logger;

public class MyService extends Service {
    private MyBinder mMyBinder = new MyBinder();

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.i("onCreate");
        Logger.i("Service onCreate() thread id is " + Thread.currentThread().getId());
        Logger.i("process id is " + Process.myPid());

//        try {
//            Thread.sleep(60000);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i("onStartCommand");
        new Thread(new Runnable() {
            @Override
            public void run() {
                //开始执行后台任务
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 如果我们既点击了Start Service按钮，又点击了Bind Service按钮会怎么样呢？
     * 不管你是单独点击Stop Service按钮还是Unbind Service按钮，Service都不会被销毁，必要将两个按钮都点击一下，Service才会被销毁。
     * 也就是说，点击Stop Service按钮只会让Service停止，点击Unbind Service按钮只会让Service和Activity解除关联，一个Service必须要在既没有和任何Activity关联又处理停止状态的时候才会被销毁
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i("onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMyBinder;
    }

    class MyBinder extends Binder{

        public void startDownload(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //执行具体的下载任务
                    Logger.i("start download ...");
                }
            }).start();
        }
    }
}
