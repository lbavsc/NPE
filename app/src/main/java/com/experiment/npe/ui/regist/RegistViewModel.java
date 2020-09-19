package com.experiment.npe.ui.regist;

import android.app.Application;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.ResultEntity;
import com.experiment.npe.entity.UserEntity;
import com.experiment.npe.ui.login.LoginActivity;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by lbavsc on 20-9-11
 */
public class RegistViewModel extends BaseViewModel<NpeRepository> {
    //手机号的绑定
    public ObservableField<String> userPhone = new ObservableField<>("");
    //用户名的绑定
    public ObservableField<String> userName = new ObservableField<>("");
    //密码的绑定
    public ObservableField<String> password = new ObservableField<>("");
    //用户名清除按钮的显示隐藏绑定
    public ObservableInt clearBtnVisibility = new ObservableInt();

    public RegistViewModel(@NonNull Application application, NpeRepository repository) {
        super(application, repository);
    }

    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
    public BindingCommand clearUserNameOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            userName.set("");
        }
    });

    //清除手机号的点击事件, 逻辑从View层转换到ViewModel层
    public BindingCommand clearUserPhoneOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            userPhone.set("");
        }
    });

    //用户名输入框焦点改变的回调事件
    public BindingCommand<Boolean> onFocusChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public Integer call(Boolean hasFocus) {
            if (hasFocus) {
                clearBtnVisibility.set(View.VISIBLE);
            } else {
                clearBtnVisibility.set(View.INVISIBLE);
            }
            return null;
        }
    });

    //注册按钮的点击事件
    public BindingCommand registOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            login();
        }
    });

    /**
     * 注册操作
     **/
    private void login() {
        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("请输入用户名！");
            return;
        }
        if (TextUtils.isEmpty(userPhone.get())) {
            ToastUtils.showShort("请输入手机号！");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }

        UserEntity body = new UserEntity(userPhone.get(), userName.get(), password.get());

        addSubscribe(model.registered(body)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .doOnSubscribe(RegistViewModel.this) //请求与RegistViewModel周期同步
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .subscribe(new Consumer<ResultEntity<UserEntity>>() {
                               @Override
                               public void accept(ResultEntity<UserEntity> result) throws Exception {
                                   dismissDialog();
                                   //保存账号密码
                                   model.savaUserPhone(userPhone.get());
                                   model.saveUserName(userName.get());
                                   model.savePassword(password.get());
                                   if (result.getCode() == 1001) {
                                       startActivity(LoginActivity.class);
                                       ToastUtils.showShort("注册成功");
                                       finish();
                                   } else {
                                       ToastUtils.showShort(result.getMsg());
                                   }

                                   //关闭页面

                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   dismissDialog();
                                   ToastUtils.showShort("数据发生错误");
                               }
                           }
                )
        );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

