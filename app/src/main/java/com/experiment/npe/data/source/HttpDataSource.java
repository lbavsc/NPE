package com.experiment.npe.data.source;

import com.experiment.npe.entity.JokeAssortEntity;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.entity.ResultEntity;
import com.experiment.npe.entity.UserEntity;

import java.io.File;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by lbavsc on 20-9-14
 */
public interface HttpDataSource {
    //登录
    Observable<ResultEntity<UserEntity>> login(String phone, String password, boolean status);

    Observable<JokeAssortEntity> assort();
    //注册
    Observable<ResultEntity<UserEntity>> registered(@Body UserEntity body);

    Observable<BaseResponse<UserEntity>> demoGet();

    Observable<BaseResponse<UserEntity>> demoPost(String catalog);

    Observable<JokeEntity>search(@Query("searchString") String searchString);

    @Multipart
    @POST("update/img")
    Observable<BaseResponse> updateUserIcon(@Part("coverFileName") String description,
                                            @Part("file\"; coverFileName=\"image.png\"") RequestBody imgs);

}
