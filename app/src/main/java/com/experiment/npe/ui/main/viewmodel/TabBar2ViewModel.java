package com.experiment.npe.ui.main.viewmodel;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.experiment.npe.R;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.ui.login.LoginActivity;
import com.experiment.npe.ui.main.fragment.TabBar2Fragment;
import com.experiment.npe.ui.search.SearchActivity;
import com.experiment.npe.ui.setting.SettingActivity;
import com.experiment.npe.ui.setting.SettingViewModel;
import com.experiment.npe.ui.window.PhotoPopupWindow;
import com.experiment.npe.utils.RetrofitClient;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by lbavsc on 20-9-15
 */
public class TabBar2ViewModel extends BaseViewModel<NpeRepository> {
    public SingleLiveEvent<Boolean> requestCameraPermissions = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> entityJsonLiveData = new SingleLiveEvent<>();
    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> userIcon = new ObservableField<>("");
    public ObservableField<String> userId = new ObservableField<>("");
    public ObservableInt loginVisibility = new ObservableInt();
    public ObservableInt visibility = new ObservableInt();
    public Drawable drawableImg;
    private Uri imageUri;

    public TabBar2ViewModel(@NonNull Application application, NpeRepository repository) {
        super(application, repository);
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawableImg = ContextCompat.getDrawable(application, R.mipmap.ic_launcher);
        userName.set(model.getUserName());
        userIcon.set(RetrofitClient.baseUrl + model.getUserIcon());
        userId.set("ID:" + model.getUserId());
        if (model.getUserStatus()) {
            loginVisibility.set(View.GONE);
            visibility.set(View.VISIBLE);
        } else {
            loginVisibility.set(View.VISIBLE);
            visibility.set(View.GONE);
        }
    }

    public NpeRepository getmodle() {
        return model;
    }

    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(LoginActivity.class);
            finish();
        }
    });


    public BindingCommand loginOutOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            entityJsonLiveData.setValue(model.getUserStatus());
        }
    });

    public BindingCommand onSettingClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(SettingActivity.class);
        }
    });

    public BindingCommand userIconOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            requestCameraPermissions.call();
        }
    });


    public void replaceUserIcon() {

    }

}
