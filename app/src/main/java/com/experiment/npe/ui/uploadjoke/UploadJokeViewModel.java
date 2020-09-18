package com.experiment.npe.ui.uploadjoke;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.entity.ResultEntity;
import com.experiment.npe.utils.SpinnerItemData;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.binding.viewadapter.spinner.IKeyAndValue;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadJokeViewModel extends BaseViewModel<NpeRepository> {
    public SingleLiveEvent<Boolean> requestCameraPermissions = new SingleLiveEvent<>();
    public JokeEntity.DataBean entity;
    public List<IKeyAndValue> assortDatas;
    public ObservableField<String> imgUrl = new ObservableField<>("");
    public ObservableInt uploadImgButtonVisibility = new ObservableInt();
    public ObservableInt uploadImgVisibility = new ObservableInt();
    public ObservableField<String> jokeTitle = new ObservableField<>("");
    public ObservableField<String> jokeCenter = new ObservableField<>("");
    public ObservableField<String> jokeSource = new ObservableField<>("");
    public String TAG="UploadJokeViewModel";

    public UploadJokeViewModel(@NonNull Application application, NpeRepository model) {
        super(application, model);
        uploadImgButtonVisibility.set(View.VISIBLE);
        uploadImgVisibility.set(View.GONE);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //assortDatas 一般可以从本地Sqlite数据库中取出数据字典对象集合，让该对象实现IKeyAndValue接口
        assortDatas = new ArrayList<>();
        assortDatas.add(new SpinnerItemData("旅游", 1));
        assortDatas.add(new SpinnerItemData("娱乐", 2));
        assortDatas.add(new SpinnerItemData("科技", 3));
        assortDatas.add(new SpinnerItemData("财经", 4));
        assortDatas.add(new SpinnerItemData("国际", 5));
        assortDatas.add(new SpinnerItemData("时尚", 6));
        assortDatas.add(new SpinnerItemData("健康", 7));
        assortDatas.add(new SpinnerItemData("软件", 8));
    }

    public void setFormEntity(JokeEntity.DataBean entity) {
        if (this.entity == null) {
            this.entity = entity;
        }
    }

    //分类选择的监听
    public BindingCommand<IKeyAndValue> onAssortCommand = new BindingCommand<>(new BindingConsumer<IKeyAndValue>() {
        @Override
        public String call(IKeyAndValue iKeyAndValue) {
            entity.setAssortId(iKeyAndValue.getValue());
            return null;
        }
    });
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

    public BindingCommand uploadImgButtonOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            requestCameraPermissions.call();
        }
    });

    public BindingCommand uploadJokeOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
//            Log.e("TAG", "tltle:\t" + jokeTitle.get());
//            Log.e("TAG", "conter:\t" + jokeCenter.get());
//            Log.e("TAG", "source:\t" + jokeSource.get());
//            Log.e("TAG", "assort:\t" + entity.getAssortId());
//            Log.e("TAG", "IMG" + entity.getCoverImg());
            uploadJoke();
        }
    });

    public void uploadJoke(){
        Long timeStamp = System.currentTimeMillis();
        String userId = model.getUserId();
        Log.e("TAG", "userId:\t"+userId);
        Log.e("TAG", "tltle:\t" + jokeTitle.get());
        Log.e("TAG", "conter:\t" + jokeCenter.get());
        Log.e("TAG", "source:\t" + jokeSource.get());
        Log.e("TAG", "assort:\t" + entity.getAssortId());
        Log.e("TAG", "IMG\t" + entity.getCoverImg());
        String path=entity.getCoverImg();
        File file = new File(path);
        RequestBody fileRQ = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("userId", userId)
                .addFormDataPart("assortId", String.valueOf(entity.getAssortId()))
                .addFormDataPart("title",jokeTitle.get())
                .addFormDataPart("content",jokeCenter.get())
                .addFormDataPart("source",jokeSource.get())
                .addFormDataPart("postTime", String.valueOf(timeStamp))
                .addFormDataPart("cover", file.getName(), fileRQ)
                .build();
        Log.e("TAG", body.toString());
        model.uploadJoke(body)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .subscribe(new DisposableObserver<JokeEntity>() {
                    @Override
                    public void onNext(final JokeEntity response) {
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

    public NpeRepository getmodle() {
        return model;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
