package com.alfred.study.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.alfred.study.R;
import com.alfred.study.bean.Subject;
import com.alfred.study.network.request.HttpUtil;
import com.alfred.study.network.subscriber.ProgressSubscriber;
import com.alfred.study.ui.base.BaseActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * RxJava 和 Retrofit 结合实践
 * Created by Alfred on 2016/7/8.
 */
public class RxJavaAndRetrofitActivity extends BaseActivity {

    @Bind(R.id.click_rxjava_retrofit_btn)
    Button mClickBtn;

    @Bind(R.id.result_rxjava_retrofit_tv)
    TextView mResultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_retrofit);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.click_rxjava_retrofit_btn)
    public void onClick() {
        getMovie();
    }

    private void getMovie() {
        HttpUtil.getInstance().getTopMovie(new ProgressSubscriber<List<Subject>>(this) {
            @Override
            public void next(List<Subject> subjects) {
                mResultTv.setText(subjects.toString());
            }
        }, 0, 10);
    }


    /**
     * 原生态Retrofit请求网络
     */
//	private void getMovieOrigin() {
//		//注意次数,url的最后以"/"结尾
//		String baseUrl = "https://api.douban.com/v2/movie/";
//
//		Retrofit retrofit = new Retrofit.Builder()
//				//设置url
//				.baseUrl(baseUrl)
//				//利用Gson解析数据
//				.addConverterFactory(GsonConverterFactory.create())
//				.build();
//
//		IMovieApi iMovieApi = retrofit.create(IMovieApi.class);
//		Call<MovieEntity> call = iMovieApi.getTopMovie(0, 10);
//		call.enqueue(new Callback<MovieEntity>() {
//			@Override
//			public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
//				mReaultTv.setText(response.body().getCount() + "");
//			}
//
//			@Override
//			public void onFailure(Call<MovieEntity> call, Throwable t) {
//				mReaultTv.setText(t.getMessage());
////			}
//			}
//		});
//	}

//	private void getMovie(){
//		String baseUrl = "https://api.douban.com/v2/movie/";
//
//		Retrofit retrofit = new Retrofit.Builder()
//				.baseUrl(baseUrl)
//				.addConverterFactory(GsonConverterFactory.create())
//				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//				.build();
//
//		IMovieApi iMovieApi = retrofit.create(IMovieApi.class);
//
//		iMovieApi.getTopMovie(0,10)
//				.subscribeOn(Schedulers.io())
//				.observeOn(AndroidSchedulers.mainThread())
//				.subscribe(new Subscriber<MovieEntity>() {
//					@Override
//					public void onCompleted() {
//
//					}
//
//					@Override
//					public void onError(Throwable e) {
//
//					}
//
//					@Override
//					public void onNext(MovieEntity movieEntity) {
//
//					}
//				});
//	}
}
