package com.experiment.npe.data.source.http;

import com.experiment.npe.data.source.HttpDataSource;
import com.experiment.npe.data.source.http.service.DemoApiService;
import com.experiment.npe.entity.ResultEntity;
import com.experiment.npe.entity.UserEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import okhttp3.RequestBody;

/**
 * Created by goldze on 2019/3/26.
 */
public class HttpDataSourceImpl implements HttpDataSource {
    private DemoApiService apiService;
    private volatile static HttpDataSourceImpl INSTANCE = null;

    public static HttpDataSourceImpl getInstance(DemoApiService apiService) {
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

    private HttpDataSourceImpl(DemoApiService apiService) {
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


}
