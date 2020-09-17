package com.experiment.npe.app;

import android.annotation.SuppressLint;
import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.ui.login.LoginViewModel;
import com.experiment.npe.ui.main.viewmodel.TabBar1ViewModel;
import com.experiment.npe.ui.main.viewmodel.TabBar2ViewModel;
import com.experiment.npe.ui.regist.RegistViewModel;
import com.experiment.npe.ui.search.SearchViewModel;
import com.experiment.npe.ui.setting.SettingViewModel;


/**
 * Created by goldze on 2019/3/26.
 */
public class AppViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile AppViewModelFactory INSTANCE;
    private final Application mApplication;
    private final NpeRepository mRepository;

    public static AppViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (AppViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppViewModelFactory(application, Injection.provideDemoRepository());
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private AppViewModelFactory(Application application, NpeRepository repository) {
        this.mApplication = application;
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistViewModel.class)) {
            return (T) new RegistViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(TabBar1ViewModel.class)) {
            return (T) new TabBar1ViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(TabBar2ViewModel.class)) {
            return (T) new TabBar2ViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(SettingViewModel.class)) {
            return (T) new SettingViewModel(mApplication, mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}



