package com.alfred.chowder.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.alfred.chowder.R;
import com.alfred.chowder.bean.MovieEntity;
import com.alfred.chowder.network.api.IMovieApi;
import com.alfred.chowder.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RxJava 和 Retrofit 结合实践
 * Created by Alfred on 2016/7/8.
 */
public class RxJavaAndRetrofitActivity extends BaseActivity{

	@Bind(R.id.click_rxjava_retrofit_btn)
	Button mClickBtn;

	@Bind(R.id.reault_rxjava_retrofit_tv)
	TextView mReaultTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rxjava_retrofit);

		ButterKnife.bind(this);
	}

	@OnClick(R.id.click_rxjava_retrofit_btn)
	public void onClick(){
		getMovie();
	}

	/**
	 * 原生态Retrofit请求网络
	 */
	private void getMovieOrigin() {
		//注意次数,url的最后以"/"结尾
		String baseUrl = "https://api.douban.com/v2/movie/";

		Retrofit retrofit = new Retrofit.Builder()
				//设置url
				.baseUrl(baseUrl)
				//利用Gson解析数据
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		IMovieApi iMovieApi = retrofit.create(IMovieApi.class);
		Call<MovieEntity> call = iMovieApi.getTopMovieOrgin(0, 10);
		call.enqueue(new Callback<MovieEntity>() {
			@Override
			public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
				mReaultTv.setText(response.body().getCount() + "");
			}

			@Override
			public void onFailure(Call<MovieEntity> call, Throwable t) {
				mReaultTv.setText(t.getMessage());
//			}
			}
		});
	}

	private void getMovie(){
		String baseUrl = "https://api.douban.com/v2/movie/";

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(baseUrl)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();

		IMovieApi iMovieApi = retrofit.create(IMovieApi.class);

		iMovieApi.getTopMovie(0,10)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<MovieEntity>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(MovieEntity movieEntity) {

					}
				});
	}
}
