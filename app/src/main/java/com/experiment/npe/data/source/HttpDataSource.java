package com.experiment.npe.data.source;

import com.experiment.npe.entity.JokeAssortEntity;
import com.experiment.npe.entity.JokeDetailsEntity;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.entity.ResultEntity;
import com.experiment.npe.entity.UserEntity;

import java.io.File;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    Observable<JokeEntity> search(@Query("searchString") String searchString);

    Observable<ResultEntity> updateUserIcon(@Body RequestBody body);

    Observable<ResultEntity<UserEntity>> updatePassword(@Query("userId")String userId,@Query("oldPassword")String oldPassword,
                                                        @Query("newPassword")String newPassword);

    Observable<ResultEntity<UserEntity>> updateUserName(@Query("userId")String userId,@Query("name")String newUserName);

    Observable<JokeEntity> showJoke(@Query("assort")int assortId);

    Observable<JokeEntity> uploadJoke(@Body RequestBody body);

    Observable<JokeEntity> deleteJoke(@Query("jokeId")String jokeId,@Query("userId")String userId);

    Observable<JokeDetailsEntity.DataBean> showJokeDetails(@Query("jokeId")String jokeId,@Query("userId")String userId);

    Observable<JokeEntity.DataBean> remarkUpload(@Body JokeEntity.DataBean body);

    Observable<ResultEntity>deleteRemark(@Query("userId")String userId,@Query("remarkId")String remarkId);

    Observable<ResultEntity>addFavorites(@Query("userId")String userId,@Query("jokeId")String jokeId);

    Observable<ResultEntity>deleteFavorites(@Query("userId")String userId,@Query("jokeId")String jokeId);
}
