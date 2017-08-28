package com.alfred.study.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alfred.study.R;
import com.alfred.study.bean.User;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    IUserBiz mIUserBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mIUserBiz = retrofit.create(IUserBiz.class);
        Call<List<User>> call = mIUserBiz.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Logger.i("normalGet : " + response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }

    private void okHttpTest(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("")
                .build();

        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

            }
        });
    }

    /**
     * 单文件上传
     */
    private void uploadFile(){
//        File file  = new File(Environment.getExternalStorageDirectory(),"icon.png");
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"),file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("photos","icon.png",requestBody);
//        Call<User> call = mIUserBiz.registerUser(part,RequestBody.create(null,"abd"),RequestBody.create(null,"123"));
    }
}
