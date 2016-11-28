package com.alfred.study.network.api;

import com.alfred.study.bean.HttpResult;
import com.alfred.study.bean.Subject;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Alfred on 2016/7/8.
 */
public interface IMovieApi {
//	@GET("top250")
//	Call<MovieEntity> getTopMovieOrigin(@Query("start") int start, @Query("count") int count);
//
//	@GET("top250")
//	Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

	@GET("top250")
    rx.Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
