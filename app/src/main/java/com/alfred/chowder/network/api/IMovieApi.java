package com.alfred.chowder.network.api;

import com.alfred.chowder.bean.MovieEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Alfred on 2016/7/8.
 */
public interface IMovieApi {
	@GET("top250")
	Call<MovieEntity> getTopMovieOrgin(@Query("start") int start, @Query("count") int count);

	@GET("top250")
	Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);
}
