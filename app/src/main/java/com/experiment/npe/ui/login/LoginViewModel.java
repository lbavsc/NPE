package com.experiment.npe.ui.login;

import android.app.Application;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.view.View;

import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.ResultEntity;
import com.experiment.npe.entity.UserEntity;
import com.experiment.npe.ui.main.activity.MainActivity;
import com.experiment.npe.ui.regist.RegistActivity;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by lbavsc on 20-9-11
 */
public class LoginViewModel extends BaseViewModel<NpeRepository> {
    //手机号的绑定
    public ObservableField<String> userPhone = new ObservableField<>("");
    //密码的绑定
    public ObservableField<String> password = new ObservableField<>("");
    //用户名清除按钮的显示隐藏绑定
    public ObservableInt clearBtnVisibility = new ObservableInt();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();
    }

    public LoginViewModel(@NonNull Application application, NpeRepository repository) {
        super(application, repository);
        //从本地取得数据绑定到View层
        userPhone.set(model.getUserPhone());
        password.set(model.getPassword());
    }

    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
    public BindingCommand clearUserPhoneOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            userPhone.set("");
        }
    });
    //密码显示开关  (你可以尝试着狂按这个按钮,会发现它有防多次点击的功能)
    public BindingCommand passwordShowSwitchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //让观察者的数据改变,逻辑从ViewModel层转到View层，在View层的监听则会被调用
            uc.pSwitchEvent.setValue(uc.pSwitchEvent.getValue() == null || !uc.pSwitchEvent.getValue());
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
    //登录按钮的点击事件
    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            login();
        }
    });

    //跳转到注册页面
    public BindingCommand registOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(RegistActivity.class);
        }
    });

    /**
     * 登陆操作
     **/
    private void login() {
        if (TextUtils.isEmpty(userPhone.get())) {
            ToastUtils.showShort("请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }

        //RaJava模拟登录
        addSubscribe(model.login(userPhone.get(), password.get(), true)
                        .compose(RxUtils.schedulersTransformer()) //线程调度
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
                                //保存用户信息
                                if (result.getCode() == 1001) {
                                    model.savaUserPhone(result.getData().getPhone());
                                    model.savePassword(result.getData().getPassword());
                                    model.saveUserName(result.getData().getName());
                                    model.saveUserIcon(result.getData().getIcon());
                                    model.saveUserId(result.getData().getUserId());
                                    model.saveUserType(result.getData().isType());
                                    model.saveUserStatus(result.getData().isStatus());
                                    ToastUtils.showShort(result.getMsg());
                                    startActivity(MainActivity.class);
                                    finish();
                                } else {
                                    ToastUtils.showShort(result.getMsg());
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dismissDialog();
                                ToastUtils.showShort("数据发生错误");
                            }
                        })
        );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
