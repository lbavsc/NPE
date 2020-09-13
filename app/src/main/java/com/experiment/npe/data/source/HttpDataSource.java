package com.experiment.npe.data.source;

import com.experiment.npe.entity.JokeAssortEntity;
import com.experiment.npe.entity.ResultEntity;
import com.experiment.npe.entity.UserEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import okhttp3.RequestBody;
import retrofit2.http.Body;

/**
 * Created by goldze on 2019/3/26.
 */
public interface HttpDataSource {
    //登录
    Observable<ResultEntity<UserEntity>> login(String phone, String password, boolean status);

    Observable<JokeAssortEntity> assort();
    //注册
    Observable<ResultEntity<UserEntity>> registered(@Body UserEntity body);

    Observable<BaseResponse<UserEntity>> demoGet();

    Observable<BaseResponse<UserEntity>> demoPost(String catalog);


}
