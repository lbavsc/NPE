package com.experiment.npe.data;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.experiment.npe.data.source.HttpDataSource;
import com.experiment.npe.data.source.LocalDataSource;
import com.experiment.npe.entity.FavoritesEntity;
import com.experiment.npe.entity.JokeAssortEntity;
import com.experiment.npe.entity.JokeDetailsEntity;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.entity.ResultEntity;
import com.experiment.npe.entity.UserEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.http.BaseResponse;
import okhttp3.RequestBody;

/**
 * Model层，统一模块的数据仓库，包含网络数据和本地数据
 * Created by lbavsc on 20-9-14
 */
public class NpeRepository extends BaseModel implements HttpDataSource, LocalDataSource {
    private volatile static NpeRepository INSTANCE = null;
    private final HttpDataSource mHttpDataSource;

    private final LocalDataSource mLocalDataSource;

    private NpeRepository(@NonNull HttpDataSource httpDataSource,
                          @NonNull LocalDataSource localDataSource) {
        this.mHttpDataSource = httpDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static NpeRepository getInstance(HttpDataSource httpDataSource,
                                            LocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (NpeRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NpeRepository(httpDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public Observable<ResultEntity<UserEntity>> login(String phone, String password, boolean status) {
        return mHttpDataSource.login(phone, password, true);
    }

    @Override
    public Observable<JokeAssortEntity> assort() {
        return mHttpDataSource.assort();
    }

    @Override
    public Observable<ResultEntity<UserEntity>> registered(UserEntity body) {
        return mHttpDataSource.registered(body);
    }

    @Override
    public Observable<BaseResponse<UserEntity>> demoGet() {
        return mHttpDataSource.demoGet();
    }

    @Override
    public Observable<BaseResponse<UserEntity>> demoPost(String catalog) {
        return mHttpDataSource.demoPost(catalog);
    }

    @Override
    public Observable<JokeEntity> search(String searchString) {
        return mHttpDataSource.search(searchString);
    }

    @Override
    public Observable<ResultEntity> updateUserIcon(RequestBody body) {
        return mHttpDataSource.updateUserIcon(body);
    }

    @Override
    public Observable<ResultEntity<UserEntity>> updatePassword(String userId, String oldPassword, String newPassword) {
        return mHttpDataSource.updatePassword(userId, oldPassword, newPassword);
    }

    @Override
    public Observable<ResultEntity<UserEntity>> updateUserName(String userId, String newUserName) {
        return mHttpDataSource.updateUserName(userId, newUserName);
    }

    @Override
    public Observable<JokeEntity> showJoke(int assortId) {
        return mHttpDataSource.showJoke(assortId);
    }

    @Override
    public Observable<JokeEntity> uploadJoke(RequestBody body) {
        return mHttpDataSource.uploadJoke(body);
    }

    @Override
    public Observable<JokeEntity> deleteJoke(String jokeId, String userId) {
        return mHttpDataSource.deleteJoke(jokeId, userId);
    }

    @Override
    public Observable<JokeDetailsEntity> showJokeDetails(String jokeId,String userId) {
        return mHttpDataSource.showJokeDetails(jokeId,userId);
    }

    @Override
    public Observable<JokeEntity.DataBean> remarkUpload(JokeEntity.DataBean body) {
        return mHttpDataSource.remarkUpload(body);
    }

    @Override
    public Observable<ResultEntity> deleteRemark(String userId, String remarkId) {
        return mHttpDataSource.deleteRemark(userId, remarkId);
    }

    @Override
    public Observable<ResultEntity> addCollection(String userId, String jokeId) {
        return mHttpDataSource.addCollection(userId, jokeId);
    }

    @Override
    public Observable<ResultEntity> deleteCollection(String userId, String jokeId) {
        return mHttpDataSource.deleteCollection(userId,jokeId);
    }

    @Override
    public Observable<FavoritesEntity> getCollection(String userId) {
        return mHttpDataSource.getCollection(userId);
    }


    @Override
    public void saveUserId(String userId) {
        mLocalDataSource.saveUserId(userId);
    }

    @Override
    public void saveUserIcon(String userIcon) {
        mLocalDataSource.saveUserIcon(userIcon);
    }

    @Override
    public void saveUserType(boolean userType) {
        mLocalDataSource.saveUserType(userType);
    }

    @Override
    public void savaUserPhone(String userPhone) {
        mLocalDataSource.savaUserPhone(userPhone);
    }

    @Override
    public void saveUserName(String userName) {
        mLocalDataSource.saveUserName(userName);
    }

    @Override
    public void savePassword(String password) {
        mLocalDataSource.savePassword(password);
    }

    @Override
    public void saveUserStatus(boolean userStatus) {
        mLocalDataSource.saveUserStatus(userStatus);
    }

    @Override
    public void saveJokeIndex(int index) {
        mLocalDataSource.saveJokeIndex(index);
    }

    @Override
    public void saveAssortIndex(int index) {
        mLocalDataSource.saveAssortIndex(index);
    }

    @Override
    public String getUserPhone() {
        return mLocalDataSource.getUserPhone();
    }

    @Override
    public String getUserName() {
        return mLocalDataSource.getUserName();
    }

    @Override
    public String getPassword() {
        return mLocalDataSource.getPassword();
    }

    @Override
    public String getUserId() {
        return mLocalDataSource.getUserId();
    }

    @Override
    public String getUserIcon() {
        return mLocalDataSource.getUserIcon();
    }

    @Override
    public boolean getUserType() {
        return mLocalDataSource.getUserType();
    }

    @Override
    public boolean getUserStatus() {
        return mLocalDataSource.getUserStatus();
    }

    @Override
    public int getJokeIndex() {
        return mLocalDataSource.getJokeIndex();
    }

    @Override
    public int getAssortIndex() {
        return mLocalDataSource.getAssortIndex();
    }

}
