package com.experiment.npe.ui.main.vm;

import android.app.Application;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.experiment.npe.R;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.UserEntity;
import com.experiment.npe.ui.login.LoginActivity;
import com.experiment.npe.ui.login.LoginViewModel;
import com.experiment.npe.ui.main.fragment.TabBar2Fragment;
import com.experiment.npe.utils.RetrofitClient;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by lbavsc on 20-9-15
 */
public class TabBar2ViewModel extends BaseViewModel<NpeRepository> {
    public SingleLiveEvent entityJsonLiveData = new SingleLiveEvent<>();
    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> userIcon = new ObservableField<>("");
    public ObservableField<String> userId = new ObservableField<>("");
    public ObservableInt loginVisibility = new ObservableInt();
    public ObservableInt visibility = new ObservableInt();

    public Drawable drawableImg;

    public TabBar2ViewModel(@NonNull Application application, NpeRepository repository) {
        super(application, repository);
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawableImg = ContextCompat.getDrawable(application, R.mipmap.ic_launcher);
        userName.set(model.getUserName());
        userIcon.set(RetrofitClient.baseUrl + model.getUserIcon());
        userId.set(model.getUserId());
        if (model.getUserStatus()) {
            loginVisibility.set(View.GONE);
            visibility.set(View.VISIBLE);
        } else {
            loginVisibility.set(View.VISIBLE);
            visibility.set(View.GONE);
        }
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
            if (model.getUserStatus()){
                loginVisibility.set(View.VISIBLE);
                visibility.set(View.GONE);
                model.saveUserStatus(false);
            }else {
                ToastUtils.showShort("您当前处于未登录状态");
            }
        }
    });


}
