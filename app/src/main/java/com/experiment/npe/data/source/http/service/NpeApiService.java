package com.experiment.npe.data.source.http.service;

import com.experiment.npe.entity.FavoritesEntity;
import com.experiment.npe.entity.JokeAssortEntity;
import com.experiment.npe.entity.JokeDetailsEntity;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.entity.ResultEntity;
import com.experiment.npe.entity.UserEntity;
import com.experiment.npe.ui.jokedetails.JokeDetailsViewModel;

import java.io.File;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by lbavsc on 20-9-14
 */
public interface NpeApiService {

    /**
     * 注册接口
     *
     * @param userEntity
     * @return
     */
    @POST("user/register")
    Observable<ResultEntity<UserEntity>> registered(@Body UserEntity userEntity);

    /**
     * 登录接口
     *
     * @param phone
     * @param pwd
     * @param status
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<ResultEntity<UserEntity>> login(@Field("phone") String phone, @Field("pwd") String pwd, @Field("status") boolean status);

    /**
     * 获取分类接口
     *
     * @return
     */
    @GET("joke/assort")
    Observable<JokeAssortEntity> assort();

    /**
     * 搜索接口
     *
     * @param searchString
     * @return
     */
    @GET("joke/search")
    Observable<JokeEntity> search(@Query("searchString") String searchString);

    /**
     * 更换头像接口
     *
     * @param body
     * @return
     */
    @POST("user/update/cover")
    Observable<ResultEntity> updateUserIcon(@Body RequestBody body);

    /**
     * 更换密码接口
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @GET("user/update/pwd")
    Observable<ResultEntity<UserEntity>> updatePassword(@Query("userId") String userId, @Query("oldPassword") String oldPassword,
                                                        @Query("newPassword") String newPassword);

    /**
     * 更换用户名接口
     *
     * @param userId
     * @param oldPassword
     * @return
     */
    @GET("user/update/name")
    Observable<ResultEntity<UserEntity>> updateUserName(@Query("userId") String userId, @Query("name") String oldPassword);

    /**
     * 获取分类新闻接口
     *
     * @param assortId
     * @return
     */
    @GET("joke/select/all")
    Observable<JokeEntity> showJoke(@Query("assort") int assortId);

    /**
     * 上传新闻接口
     *
     * @param body
     * @return
     */
    @POST("joke/upload")
    Observable<JokeDetailsEntity> uploadJoke(@Body RequestBody body);

    /**
     * 删除新闻接口
     *
     * @param jokeId
     * @param userId
     * @return
     */
    @GET("joke/delete")
    Observable<JokeEntity> deleteJoke(@Query("jokeId") String jokeId, @Query("userId") String userId);

    /**
     * 获取单条新闻详情接口
     *
     * @param jokeId
     * @param userId
     * @return
     */
    @GET("joke/select/id")
    Observable<JokeDetailsEntity> showJokeDetails(@Query("jokeId") String jokeId, @Query("userId") String userId);

    /**
     * 提交评论接口
     *
     * @param body
     * @return
     */
    @POST("remark/upload")
    Observable<JokeEntity.DataBean> remarkUpload(@Body JokeEntity.DataBean body);

    /**
     * 删除评论接口
     *
     * @param userId
     * @param remarkId
     * @return
     */
    @GET("remark/delete")
    Observable<ResultEntity> deleteRemark(@Query("userId") String userId, @Query("remarkId") String remarkId);

    /**
     * 添加收藏接口
     *
     * @param userId
     * @param jokeId
     * @return
     */
    @GET("user/upload/collection")
    Observable<ResultEntity> addCollection(@Query("userId") String userId, @Query("jokeId") String jokeId);

    /**
     * 删除收藏接口
     *
     * @param userId
     * @param jokeId
     * @return
     */
    @GET("user/delete/collection")
    Observable<ResultEntity> deleteCollection(@Query("userId") String userId, @Query("jokeId") String jokeId);

    /**
     * 获得收藏列表接口
     *
     * @param userId
     * @return
     */
    @GET("joke/select/collection")
    Observable<FavoritesEntity> getCollection(@Query("userId") String userId);
}
