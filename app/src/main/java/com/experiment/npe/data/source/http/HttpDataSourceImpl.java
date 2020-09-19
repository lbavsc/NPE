package com.experiment.npe.data.source.http;

import com.experiment.npe.data.source.HttpDataSource;
import com.experiment.npe.data.source.http.service.NpeApiService;
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

/**
 * Created by lbavsc on 20-9-14
 */
public class HttpDataSourceImpl implements HttpDataSource {
    private NpeApiService apiService;
    private volatile static HttpDataSourceImpl INSTANCE = null;

    public static HttpDataSourceImpl getInstance(NpeApiService apiService) {
        if (INSTANCE == null) {
            synchronized (HttpDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDataSourceImpl(apiService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private HttpDataSourceImpl(NpeApiService apiService) {
        this.apiService = apiService;
    }

//    @Override
//    public Observable<Object> login() {
//        return Observable.just(new Object()).delay(3, TimeUnit.SECONDS); //延迟3秒
//    }


    @Override
    public Observable<ResultEntity<UserEntity>> login(String phone, String password, boolean status) {
        return apiService.login(phone, password, true);
    }

    @Override
    public Observable<JokeAssortEntity> assort() {
        return apiService.assort();
    }

    @Override
    public Observable<ResultEntity<UserEntity>> registered(UserEntity body) {
        return apiService.registered(body);
    }


    @Override
    public Observable<BaseResponse<UserEntity>> demoGet() {
        return apiService.demoGet();
    }

    @Override
    public Observable<BaseResponse<UserEntity>> demoPost(String catalog) {
        return apiService.demoPost(catalog);
    }

    @Override
    public Observable<JokeEntity> search(String searchString) {
        return apiService.search(searchString);
    }

    @Override
    public Observable<ResultEntity> updateUserIcon(RequestBody body) {
        return apiService.updateUserIcon(body);
    }

    @Override
    public Observable<ResultEntity<UserEntity>> updatePassword(String userId, String oldPassword, String newPassword) {
        return apiService.updatePassword(userId,oldPassword,newPassword);
    }

    @Override
    public Observable<ResultEntity<UserEntity>> updateUserName(String userId, String newUserName) {
        return apiService.updateUserName(userId,newUserName);
    }

    @Override
    public Observable<JokeEntity> showJoke(int assortId) {
        return apiService.showJoke(assortId);
    }

    @Override
    public Observable<JokeEntity> uploadJoke(RequestBody body) {
        return apiService.uploadJoke(body);
    }

    @Override
    public Observable<JokeEntity> deleteJoke(String jokeId, String userId) {
        return apiService.deleteJoke(jokeId,userId);
    }

    @Override
    public Observable<JokeDetailsEntity> showJokeDetails(String jokeId) {
        return apiService.showJokeDetails(jokeId);
    }

    @Override
    public Observable<JokeDetailsEntity> remarkUpload(String userId, String jokeId, String content) {
        return apiService.remarkUpload(userId,jokeId,content);
    }


}
