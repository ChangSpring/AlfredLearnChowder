package com.alfred.study.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alfred.study.R;
import com.alfred.study.bean.Feed;
import com.alfred.study.ui.base.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alfred on 2016/10/28.
 */

public class TimerActivity extends BaseActivity {

    @Bind(R.id.textView_timer_tv)
    TextView tvShow;
    private int i = 0;
    private int TIME = 1000;

    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        ButterKnife.bind(this);
//        handler.postDelayed(runnable, TIME); //每隔1s执行
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                uploadGrabbingData();
                new Thread() {
                    @Override
                    public void run() {
//                        test();
                    }
                }.start();
            }
        });

        try {

            Type type = new TypeToken<List<Feed>>() {
            }.getType();
            List<Feed> feeds = new Gson().fromJson(new JSONArray(("\"feeds\": [ { \"following\": 0, \"comments\": [], \"caption\": \"不准抖腿\uD83D\uDE44\", \"verified\": false, " +
                    "\"tags\": " +
                    "[], \"time\": \"2016-12-19 17:59:27\", \"timestamp\": 1482141567898, \"type\": 1, \"user_id\": 326138021, \"comment_count\": 17, " +
                    "\"photo_status\": 0, \"photo_id\": 1389734423, \"exp_tag\": \"1_i/1554222928285143045_h85\", \"user_sex\": \"F\", \"us_m\": 0, " +
                    "\"view_count\": 19202, \"like_count\": 171, \"unlike_count\": 0, \"forward_count\": 0, \"ext_params\": { \"mtype\": 3, \"color\": " +
                    "\"a39d87\", \"w\": 368, \"sound\": 7569, \"h\": 640, \"interval\": 29, \"video\": 7508 }]")).toString(), type);
            Log.i("TimerActivity","feeds = " + feeds);
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, TIME);
                tvShow.setText(Integer.toString(i++));
                System.out.println("do...");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("exception...");
            }
        }
    };

//    private void uploadGrabbingData() {
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        RequestBody requestBody = new FormBody.Builder()
//                .add("urls", "Hello")
//                .build();
//        Request request = new Request.Builder()
//                .url("http://huanyu.yongche.com/car/car!insertCar.action?app_name=didi&ctime=2016年11月17日%2015:18:30")
//                .post(requestBody)
//                .build();
//        Log.i("TimerActivity", request + request.toString() + requestBody);
//
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
////                if (null != response.cacheResponse()) {
////                    String str = response.cacheResponse().toString();
////                    Log.i("wangshu", "cache---" + str);
////                } else {
////                    response.body().string();
////                    String str = response.networkResponse().toString();
////                    Log.i("wangshu", "network---" + str);
////                }
//                Log.i("TimerActivity", "response body = " + response.body().string());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//
//    }

    private void test() {
        String url = "http://180.186.38.200/rest/n/feed/list";
        com.squareup.okhttp.OkHttpClient okHttpClient = new com.squareup.okhttp.OkHttpClient();
        com.squareup.okhttp.RequestBody requestBody = new FormEncodingBuilder()
                .add("mod", "samsung(SM-N9006)")
                .add("lon", "116.310034")
                .add("country_code", "CN")
                .add("did", "ANDROID_dc3fb38e9cf445c0")
                .add("app", "0")
                .add("net", "WIFI")
                .add("oc", "QQBROWSER")
                .add("ud", "0")
                .add("c", "QQBROWSER")
                .add("sys", "ANDROID_5.0")
                .add("appver", "4.53.0.2894")
                .add("language", "zh-cn")
                .add("lat", "39.983433")
                .add("ver", "4.53")
                .add("id", "30")
                .add("token", "")
                .add("pv", "false")
                .add("client_key", "3c2cd3f3")
                .add("count", "20")
                .add("page", "1")
                .add("type", "7")
                .add("os", "android")
                .add("sig", "4a6d020e443f70a527e5fb19881d7ac3")
                .build();

        com.squareup.okhttp.Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "kwai-android")
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept-Language", "zh-cn")
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Accept-Encoding", "gzip")
                .addHeader("Content-length", "113")
                .addHeader("host", "180.186.38.200")
                .post(requestBody)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                Log.i("TimerActivity", response.body().string() + "");
            } else {
                Log.i("TimerActivity", response + "");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
