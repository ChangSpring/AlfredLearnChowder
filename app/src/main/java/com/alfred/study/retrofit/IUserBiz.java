package com.alfred.study.retrofit;

import com.alfred.study.bean.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Alfred on 2017/6/8.
 */

public interface IUserBiz {
    @GET("users")
    retrofit2.Call<List<User>> getUsers();


    /**
     * <p>
     * 动态Url的访问@Path
     * </p>
     * //用于访问zhy的信息
     * http://192.168.1.102:8080/springmvc_users/user/zhy
     * //用于访问lmj的信息
     * http://192.168.1.102:8080/springmvc_users/user/lmj
     */
    @GET("{username}")
    Call<User> getUser(@Path("username") String username);

    /**
     * <p>
     * 查询参数的设置@Query
     * </p>
     * http://baseurl/users?sortby=username
     * http://baseurl/users?sortby=id
     */
    @GET("users")
    Call<List<User>> getUsersBySort(@Query("sortby") String sort);

    /**
     * POST请求提的方式向服务器传入json字符串@Body
     */
    @POST("add")
    Call<List<User>> addUser(@Body User user);

    /**
     * 表单的方式传递键值对@FormUrlEncoded
     */
    @POST("login")
    @FormUrlEncoded
    Call<User> login(@Field("username") String username,@Field("password") String passwor);

    /**
     * 单文件上传@Multipart
     */
    @Multipart
    @POST("register")
    Call<User> regitsterUser(@Part MultipartBody.Part photo, @Part("username")RequestBody username,@Part("password") RequestBody password);

    /**
     * 多文件上传@PartMap
     */
    Call<User> registerUser(@PartMap Map<String,RequestBody> params,@Part("password") RequestBody password);

















}
