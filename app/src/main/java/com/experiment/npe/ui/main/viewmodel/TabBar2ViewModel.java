package com.experiment.npe.ui.main.viewmodel;

import android.app.Application;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;

import com.experiment.npe.R;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeAssortEntity;
import com.experiment.npe.entity.ResultEntity;
import com.experiment.npe.ui.login.LoginActivity;
import com.experiment.npe.ui.setting.SettingActivity;
import com.experiment.npe.utils.RetrofitClient;

import java.io.File;
import java.io.IOException;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;

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
    public String TAG = "TabBar2ViewModel";
    public Drawable drawableImg;


    public TabBar2ViewModel(@NonNull Application application, NpeRepository repository) {
        super(application, repository);
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawableImg = ContextCompat.getDrawable(application, R.mipmap.ic_launcher);
        userName.set(model.getUserName());

        if (model.getUserIcon().startsWith("img")) {
            userIcon.set(RetrofitClient.baseUrl + model.getUserIcon());
        } else {
            userIcon.set(model.getUserIcon());
        }
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
//            upUserIcon();

        }
    });

    public void upUserIcon() {
        String userId = model.getUserId();
        String path = model.getUserIcon();
        File file = new File(path);
        Log.e("TAG", path);
        RequestBody fileRQ = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("userId", userId)
                .addFormDataPart("cover", file.getName(), fileRQ)
                .build();
        model.updateUserIcon(body)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .subscribe(new DisposableObserver<ResultEntity>() {
                    @Override
                    public void onNext(final ResultEntity response) {
                        Log.e(TAG, "onNext: " + response.getCode());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //关闭对话框
                        dismissDialog();
                        //请求刷新完成收回
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }

                    @Override
                    public void onComplete() {
                        ToastUtils.showShort("头像更换完成");
                        //关闭对话框
                        dismissDialog();
                    }
                });
    }

}
