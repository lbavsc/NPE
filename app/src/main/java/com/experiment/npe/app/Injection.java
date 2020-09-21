package com.experiment.npe.app;

import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.data.source.HttpDataSource;
import com.experiment.npe.data.source.LocalDataSource;
import com.experiment.npe.data.source.http.HttpDataSourceImpl;
import com.experiment.npe.data.source.http.service.NpeApiService;
import com.experiment.npe.data.source.local.LocalDataSourceImpl;
import com.experiment.npe.utils.RetrofitClient;


/**
 * 注入全局的数据仓库
 * Created by lbavsc on 20-9-14
 */
public class Injection {
    public static NpeRepository provideDemoRepository() {
        //网络API服务
        NpeApiService apiService = RetrofitClient.getInstance().create(NpeApiService.class);
        //网络数据源
        HttpDataSource httpDataSource = HttpDataSourceImpl.getInstance(apiService);
        //本地数据源
        LocalDataSource localDataSource = LocalDataSourceImpl.getInstance();
        //两条分支组成一个数据仓库
        return NpeRepository.getInstance(httpDataSource, localDataSource);
    }
}
