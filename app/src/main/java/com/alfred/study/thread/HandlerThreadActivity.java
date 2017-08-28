package com.alfred.study.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alfred.study.R;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HandlerThreadActivity extends AppCompatActivity {

    private Handler mSubHandler;

    private Handler mUIHandler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Logger.i("msg = " + msg.obj);
                    break;
            }
        }
    };

    private Handler.Callback mSubCallBack = new Handler.Callback(){
        @Override
        public boolean handleMessage(Message msg) {
            //实现自己的消息处理
            switch (msg.what){
                case 0:
                    Message message = new Message();
                    message.what = 0;
                    message.obj = System.currentTimeMillis();
                    mUIHandler.sendMessage(message);
                    break;
                default:
                    break;
            }
            return false;

        }
    };

    @Bind(R.id.btn_exec_handler_thread)
    Button execBtn;
    @Bind(R.id.tv_content_handler_thread)
    TextView contentTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);

        ButterKnife.bind(this);

        //第一步:创建handlerThread的实例
        HandlerThread handlerThread = new HandlerThread("handlerThread");

        //第二步:启动HandlerThread线程
        handlerThread.start();

        //第三步:构建循环消息处理机制
        mSubHandler = new Handler(handlerThread.getLooper(),mSubCallBack);

        //这三个步骤不能颠倒,因为在第三步中handlerThread.getLooper()中需要首先判断线程是否存活,如果不存活的话,然后null.
        //Handler需要的looper实例是在thread的run()方法中创建的,所以说必须先调用thread.start()方法 然后再创建handler实例.

        execBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //投放异步耗时任务到HandlerThread中去
                mSubHandler.sendEmptyMessage(0);
            }
        });

    }
}
