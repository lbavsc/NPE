package com.experiment.npe.ui.uploadjoke;

import android.app.Application;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeDetailsEntity;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.ui.jokedetails.JokeDetailsActivity;
import com.experiment.npe.ui.jokedetails.JokeDetailsViewModel;
import com.experiment.npe.ui.main.activity.MainActivity;
import com.experiment.npe.ui.main.fragment.TabBar1Fragment;
import com.experiment.npe.ui.main.viewmodel.JokeItemViewModel;
import com.experiment.npe.ui.main.viewmodel.TabBar1ViewModel;
import com.experiment.npe.utils.RetrofitClient;
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
import me.goldze.mvvmhabit.bus.Messenger;
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
    public ObservableField<String> jokeContent = new ObservableField<>("");
    public ObservableField<String> jokeSource = new ObservableField<>("");
    public String TAG = "UploadJokeViewModel";

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
        assortDatas.add(new SpinnerItemData("军事", 1));
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
        public Integer call(IKeyAndValue iKeyAndValue) {
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
            uploadJoke();
        }
    });

    public void uploadJoke() {
        if (!model.getUserStatus()) {
            ToastUtils.showShort("您当前未登录");
            return;
        }
        if (!model.getUserType()) {
            ToastUtils.showShort("您没有此权限");
            return;
        }
        if (TextUtils.isEmpty(jokeTitle.get())) {
            ToastUtils.showShort("请输入标题");
            return;
        }
        if (TextUtils.isEmpty(jokeContent.get())) {
            ToastUtils.showShort("请输入新闻内容");
            return;
        }
        if (entity.getCoverImg() == null) {
            ToastUtils.showShort("请选择图片");
            return;
        }
        if (TextUtils.isEmpty(jokeSource.get())) {
            jokeSource.set(model.getUserName());
        }
        if (jokeTitle.get().length() > 50) {
            ToastUtils.showShort("标题最多为50字");
        }
        if (jokeContent.get().length() > 2000) {
            ToastUtils.showShort("内容最多为2000字");
        }

        Long timeStamp = System.currentTimeMillis();
        String userId = model.getUserId();
        String path = entity.getCoverImg();
        File file = new File(path);
        RequestBody fileRQ = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("userId", userId)
                .addFormDataPart("assortId", String.valueOf(entity.getAssortId()))
                .addFormDataPart("title", jokeTitle.get())
                .addFormDataPart("content", jokeContent.get())
                .addFormDataPart("source", jokeSource.get())
                .addFormDataPart("postTime", String.valueOf(timeStamp))
                .addFormDataPart("cover", file.getName(), fileRQ)
                .build();
        Log.e("TAG", body.toString());
        model.uploadJoke(body)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .subscribe(new DisposableObserver<JokeDetailsEntity>() {
                    @Override
                    public void onNext(final JokeDetailsEntity response) {
                        entity.setJokeId(response.getData().getJokeId());
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
                        JokeEntity.DataBean dataBean = new JokeEntity.DataBean();
                        dataBean.setCoverImg(entity.getCoverImg());

                        dataBean.setTitle(jokeTitle.get());
                        dataBean.setContent(jokeContent.get());
                        dataBean.setPostTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
                        dataBean.setCoverImg(entity.getCoverImg());
                        dataBean.setJokeId(entity.getJokeId());
                        Messenger.getDefault().send(dataBean, TabBar1Fragment.TOKEN_TabBar1Fragment_REFRESH);
                        jokeTitle.set("");
                        jokeContent.set("");
                        jokeSource.set("");
                        entity.setCoverImg(null);
                        uploadImgButtonVisibility.set(View.VISIBLE);
                        uploadImgVisibility.set(View.GONE);
                        //关闭对话框
                        ToastUtils.showShort("新闻提交成功");
                        dismissDialog();
                    }
                });
    }

    public NpeRepository getmodle() {
        return model;
    }

    public void addJoke() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
