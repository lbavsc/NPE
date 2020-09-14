package com.experiment.npe.data.source.http.service;

import com.experiment.npe.entity.JokeAssortEntity;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.entity.ResultEntity;
import com.experiment.npe.entity.UserEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lbavsc on 20-9-14
 */
public interface NpeApiService {
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

    @GET("joke/assort")
    Observable<JokeAssortEntity> assort();

    @GET("joke/search")
    Observable<JokeEntity>search(@Query("searchString") String searchString);
}
