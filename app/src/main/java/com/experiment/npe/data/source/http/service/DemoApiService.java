package com.experiment.npe.data.source.http.service;

import com.experiment.npe.entity.ResultEntity;
import com.experiment.npe.entity.UserEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by goldze on 2017/6/15.
 */

public interface DemoApiService {
    @GET("action/apiv2/banner?catalog=1")
    Observable<BaseResponse<UserEntity>> demoGet();

    @FormUrlEncoded
    @POST("action/apiv2/banner")
    Observable<BaseResponse<UserEntity>> demoPost(@Field("catalog") String catalog);

    @POST("user/register")
    Observable<ResultEntity<UserEntity>> registered(@Body UserEntity userEntity);

    @FormUrlEncoded
    @POST("user/login")
    Observable<ResultEntity<UserEntity>> login(@Field("phone") String phone, @Field("pwd") String pwd, @Field("status") boolean status);
}
