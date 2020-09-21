package com.experiment.npe.app;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.ui.jokedetails.JokeDetailsViewModel;
import com.experiment.npe.ui.login.LoginViewModel;
import com.experiment.npe.ui.main.viewmodel.NewsViewModel;
import com.experiment.npe.ui.main.viewmodel.PersonalCenterViewModel;
import com.experiment.npe.ui.regist.RegistViewModel;
import com.experiment.npe.ui.search.SearchViewModel;
import com.experiment.npe.ui.setting.SettingViewModel;
import com.experiment.npe.ui.setting.change.ChangePasswordViewModel;
import com.experiment.npe.ui.setting.edit.EditInformationViewModel;
import com.experiment.npe.ui.uploadjoke.UploadJokeViewModel;


/**
 * Created by lbavsc on 20-9-10
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
        } else if (modelClass.isAssignableFrom(NewsViewModel.class)) {
            return (T) new NewsViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(PersonalCenterViewModel.class)) {
            return (T) new PersonalCenterViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(SettingViewModel.class)) {
            return (T) new SettingViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(EditInformationViewModel.class)) {
            return (T) new EditInformationViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(ChangePasswordViewModel.class)) {
            return (T) new ChangePasswordViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(UploadJokeViewModel.class)) {
            return (T) new UploadJokeViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(JokeDetailsViewModel.class)) {
            return (T) new JokeDetailsViewModel(mApplication, mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}



