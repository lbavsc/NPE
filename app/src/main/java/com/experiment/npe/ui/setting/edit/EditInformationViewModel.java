package com.experiment.npe.ui.setting.edit;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.ResultEntity;
import com.experiment.npe.entity.UserEntity;

import java.security.PublicKey;

import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 修改用户名ViewModel
 * Created by lbavsc on 20-9-17
 */
public class EditInformationViewModel extends BaseViewModel<NpeRepository> {
    public ObservableField<String> oldUserName = new ObservableField<>("");
    public ObservableField<String> newUserName = new ObservableField<>("");
    public String TAG = "EditInformationViewModel";

    public EditInformationViewModel(@NonNull Application application, NpeRepository model) {
        super(application, model);
        oldUserName.set(model.getUserName());
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
     * 确认修改用户名按钮事件
     */
    public BindingCommand eiitOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!model.getUserStatus()) {
                ToastUtils.showShort("您当前是未登录状态");
                return;
            }
            if (TextUtils.isEmpty(newUserName.get())) {
                ToastUtils.showShort("请输入新用户名");
                return;
            }
            updateUserName(newUserName.get());
            model.saveUserName(newUserName.get());
        }
    });

    /**
     * 实现修改用户名接口
     * @param newUserName
     */
    public void updateUserName(String newUserName) {
        model.updateUserName(model.getUserId(), newUserName)
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
                        ToastUtils.showShort("用户名更新失败");
                        dismissDialog();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }

                    @Override
                    public void onComplete() {
                        ToastUtils.showShort("用户名更新成功");
                        //关闭对话框
                        dismissDialog();
                    }
                });
    }
}
