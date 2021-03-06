package com.experiment.npe.ui.setting;

import android.app.Application;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;

import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.ui.login.LoginActivity;
import com.experiment.npe.ui.main.activity.MainActivity;
import com.experiment.npe.ui.setting.change.ChangePasswordActivity;
import com.experiment.npe.ui.setting.edit.EditInformationActivity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 设置页面ViewModel
 */
public class SettingViewModel extends BaseViewModel<NpeRepository> {
    public ObservableInt loginVisibility = new ObservableInt();
    public ObservableInt logOutinVisibility = new ObservableInt();
    public SingleLiveEvent<Boolean> entityJsonLiveData = new SingleLiveEvent<>();

    public SettingViewModel(@NonNull Application application, NpeRepository repository) {
        super(application, repository);
        if (model.getUserStatus()) {        //判断用户登录状态,显示不同的元素
            loginVisibility.set(View.GONE);
            logOutinVisibility.set(View.VISIBLE);
        } else {
            loginVisibility.set(View.VISIBLE);
            logOutinVisibility.set(View.GONE);
        }

    }

    /**
     * 获得model
     *
     * @return
     */
    public NpeRepository getmodle() {
        return model;
    }

    /**
     * 返回上一页面
     */
    public BindingCommand backOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onBackPressed();
        }
    });

    /**
     * toolbar右面按钮点击事件
     */
    public BindingCommand rightIconOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("等待开发");
        }
    });

    /**
     * 个人信息按钮点击事件
     */
    public BindingCommand accountInformationOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putInt("num", 1);        //启动MainActivity的第2个Fargement
            startActivity(MainActivity.class, bundle);
            finish();

        }
    });

    /**
     * 资料编辑点击事件
     */
    public BindingCommand editInformationOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(EditInformationActivity.class);
        }
    });

    /**
     * 登录按钮点击事件
     */
    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(LoginActivity.class);
            finish();
        }
    });

    /**
     * 登出按钮点击事件
     */
    public BindingCommand loginOutOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            entityJsonLiveData.setValue(model.getUserStatus());
        }
    });

    /**
     * 修改密码点击事件
     */
    public BindingCommand changePasswordOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(ChangePasswordActivity.class);
        }
    });
}
