package com.experiment.npe.data.source.http;

import com.experiment.npe.data.source.HttpDataSource;
import com.experiment.npe.data.source.http.service.NpeApiService;
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

/**
 * Created by lbavsc on 20-9-14
 * 网络仓库
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
    public Observable<JokeEntity> search(String searchString) {
        return apiService.search(searchString);
    }

    @Override
    public Observable<ResultEntity> updateUserIcon(RequestBody body) {
        return apiService.updateUserIcon(body);
    }

    @Override
    public Observable<ResultEntity<UserEntity>> updatePassword(String userId, String oldPassword, String newPassword) {
        return apiService.updatePassword(userId, oldPassword, newPassword);
    }

    @Override
    public Observable<ResultEntity<UserEntity>> updateUserName(String userId, String newUserName) {
        return apiService.updateUserName(userId, newUserName);
    }

    @Override
    public Observable<JokeEntity> showJoke(int assortId) {
        return apiService.showJoke(assortId);
    }

    @Override
    public Observable<JokeDetailsEntity> uploadJoke(RequestBody body) {
        return apiService.uploadJoke(body);
    }

    @Override
    public Observable<JokeEntity> deleteJoke(String jokeId, String userId) {
        return apiService.deleteJoke(jokeId, userId);
    }

    @Override
    public Observable<JokeDetailsEntity> showJokeDetails(String jokeId, String userId) {
        return apiService.showJokeDetails(jokeId, userId);
    }

    @Override
    public Observable<JokeEntity.DataBean> remarkUpload(JokeEntity.DataBean body) {
        return apiService.remarkUpload(body);
    }

    @Override
    public Observable<ResultEntity> deleteRemark(String userId, String remarkId) {
        return apiService.deleteRemark(userId, remarkId);
    }

    @Override
    public Observable<ResultEntity> addCollection(String userId, String jokeId) {
        return apiService.addCollection(userId, jokeId);
    }

    @Override
    public Observable<ResultEntity> deleteCollection(String userId, String jokeId) {
        return apiService.deleteCollection(userId, jokeId);
    }

    @Override
    public Observable<FavoritesEntity> getCollection(String userId) {
        return apiService.getCollection(userId);
    }


}
