package com.experiment.npe.ui.setting.change;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.ResultEntity;
import com.experiment.npe.entity.UserEntity;

import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 更改用户密码ViewModel
 */
public class ChangePasswordViewModel extends BaseViewModel<NpeRepository> {
    public ObservableField<String> oldpassword = new ObservableField<>("");
    public ObservableField<String> newpassword = new ObservableField<>("");
    public ObservableField<String> newpassword2 = new ObservableField<>("");
    private String TAG = "ChangePasswordViewModel";

    public ChangePasswordViewModel(@NonNull Application application, NpeRepository model) {
        super(application, model);
    }

    public BindingCommand backOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onBackPressed();
        }
    });
    public BindingCommand rightIconOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("等待开发");
        }
    });

    /**
     * 确认更改密码按钮事件
     */
    public BindingCommand changePasswordOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!model.getUserStatus()) {
                ToastUtils.showShort("您当前是未登录状态");
                return;
            }
            if (TextUtils.isEmpty(oldpassword.get())) {
                ToastUtils.showShort("旧密码输入为空");
                return;
            }
            if (TextUtils.isEmpty(newpassword.get())) {
                ToastUtils.showShort("新密码输入为空");
                return;
            }
            if (!newpassword.get().equals(newpassword2.get())) {
                ToastUtils.showShort("俩次输入的新密码不一致，请重新输入");
                return;
            }
            if (!oldpassword.get().equals(model.getPassword())) {
                ToastUtils.showShort("旧密码输入错误");
                return;
            }
            changepwd(oldpassword.get(), newpassword.get());
            model.savePassword(newpassword.get());
        }
    });

    /**
     * 实现更改密码接口
     *
     * @param oldpwd
     * @param newpwd
     */
    public void changepwd(String oldpwd, String newpwd) {
        model.updatePassword(model.getUserId(), oldpwd, newpwd)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .subscribe(new DisposableObserver<ResultEntity<UserEntity>>() {
                    @Override
                    public void onNext(final ResultEntity<UserEntity> response) {
                        Log.e(TAG, "onNext: " + response.getCode());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //关闭对话框
                        ToastUtils.showShort("密码更新失败");
                        dismissDialog();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }

                    @Override
                    public void onComplete() {
                        ToastUtils.showShort("密码更新成功");
                        //关闭对话框
                        dismissDialog();
                    }
                });
    }
}
