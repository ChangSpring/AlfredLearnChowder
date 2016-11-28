package com.alfred.learn.network.request;

import com.alfred.learn.bean.HttpResult;
import com.alfred.learn.bean.Subject;
import com.alfred.learn.network.api.IMovieApi;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Alfred on 16/10/19.
 */

public class HttpUtil {

    private Retrofit retrofit;
    private String url = "https://api.douban.com/v2/movie/";
    private static HttpUtil httpUtil = null;

    private IMovieApi mIMovieApi;

    private final int TIMEOUT_DEFAULT = 10;

    public static HttpUtil getInstance() {
        if (httpUtil == null) {
            synchronized (HttpUtil.class) {
                if (httpUtil == null) {
                    httpUtil = new HttpUtil();
                }
            }
        }
        return httpUtil;
    }

    private HttpUtil() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT_DEFAULT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();

        mIMovieApi = retrofit.create(IMovieApi.class);

    }

    public void getTopMovie(Subscriber<List<Subject>> subscriber, int start, int count) {
        rx.Observable observable = mIMovieApi.getTopMovie(start, count)
                .map(new HttpResultFunc<List<Subject>>());

        toSubscribe(observable, subscriber);
    }

    private <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> tHttpResult) {
//            if (tHttpResult.getResultCode() != 200) {
//                throw new ApiException(tHttpResult.getResultCode(), tHttpResult.getReaultMessage());
//            }
            return tHttpResult.getSubjects();
        }
    }

}
