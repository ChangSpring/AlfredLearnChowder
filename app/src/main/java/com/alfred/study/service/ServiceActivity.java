package com.alfred.study.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alfred.study.R;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.btn_start_service)
    Button startBtn;
    @Bind(R.id.btn_stop_service)
    Button stopBtn;
    @Bind(R.id.btn_bind_service)
    Button bindBtn;
    @Bind(R.id.btn_unbind_service)
    Button unbindBtn;
    @Bind(R.id.tv_content_service)
    TextView contentTv;

    private MyService.MyBinder mMyBinder;

    private ServiceConnection mConnection =new ServiceConnection() {
        /**
         * Activity和Service建立关联时调用
         * @param name
         * @param service
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (MyService.MyBinder) service;
            mMyBinder.startDownload();

        }

        /**
         * Activity和Service解除关联时调用
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        ButterKnife.bind(this);

        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        bindBtn.setOnClickListener(this);
        unbindBtn.setOnClickListener(this);

        Logger.i("Activity onCreate() thread id is " + Thread.currentThread().getId());
        Logger.i("process id is " + Process.myPid());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service:
                Intent startIntent = new Intent(this,MyService.class);
                startService(startIntent);
                break;
            case R.id.btn_stop_service:
                Intent stopIntent = new Intent(this,MyService.class);
                stopService(stopIntent);
                break;
            case R.id.btn_bind_service:
                Intent bindIntent = new Intent(this,MyService.class);
                //BIND_AUTO_CREATE表示在Activity和Service建立关联时自动创建Service,这会使得MyService中的onCreate()方法得到执行,但是onStartCommand()方法不会执行
                bindService(bindIntent,mConnection,BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind_service:
                unbindService(mConnection);
                break;
            default:
                break;
        }
    }
}
