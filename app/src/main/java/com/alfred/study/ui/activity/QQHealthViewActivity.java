package com.alfred.study.ui.activity;

import android.os.Bundle;

import com.alfred.study.R;
import com.alfred.study.ui.base.BaseActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alfred on 2016/7/1.
 */
public class QQHealthViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq_health);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
