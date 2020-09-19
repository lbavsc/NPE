package com.experiment.npe.ui.jokedetails;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeDetailsEntity;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.utils.RetrofitClient;

import java.util.Collections;
import java.util.Date;

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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class JokeDetailsViewModel extends BaseViewModel<NpeRepository> {
    public ObservableField<String> userIcon = new ObservableField<>("");
    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> userId = new ObservableField<>("");
    public ObservableField<String> jokeImg = new ObservableField<>("");
    public ObservableField<String> jokeContent = new ObservableField<>("");
    public ObservableField<String> jokeTime = new ObservableField<>("");
    public ObservableField<String> jokeSource = new ObservableField<>("");
    public Drawable commentDrawableImg;
    public ObservableList<JokeDetailsEntity> entity = new ObservableArrayList<>();
    public ObservableField<String> jokeTitle = new ObservableField<>("");
    public SingleLiveEvent<Boolean> entityJsonLiveData = new SingleLiveEvent<>();
    public JokeDetailsViewModel(@NonNull Application application, NpeRepository model) {
        super(application, model);
        Log.e("TAG", model.getUserIcon());
        userName.set(model.getUserName());
        userId.set("ID:"+model.getUserId());
        if (model.getUserIcon().startsWith("img")) {
            userIcon.set(RetrofitClient.baseUrl + model.getUserIcon());
        } else {
            userIcon.set(model.getUserIcon());
        }
    }

    public BindingCommand backOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onBackPressed();
        }
    });

    //获得新闻数据
    public void JokeDetails(final String jokeId) {
        model.showJokeDetails(jokeId)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在请求...");
                    }
                })
                .subscribe(new DisposableObserver<JokeDetailsEntity>() {
                    @Override
                    public void onNext(JokeDetailsEntity response) {
                        entity.add(response);
                        jokeTitle.set(response.getData().getTitle());
                        jokeImg.set(RetrofitClient.baseUrl+response.getData().getCoverImg());
                        jokeContent.set(response.getData().getContent());
                        jokeTime.set(response.getData().getPostTime());
                        jokeSource.set("\t\t来源："+response.getData().getSource());
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
                        //关闭对话框
//                        entityJsonLiveData.call();
                        dismissDialog();
                    }
                });
    }

    //评论接口
    public void remarkLoad(String content) {
        JokeEntity.DataBean body = new JokeEntity.DataBean(model.getUserId(), entity.get(0).getData().getJokeId(), content);
        model.remarkUpload(body)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .subscribe(new DisposableObserver<JokeEntity.DataBean>() {
                    @Override
                    public void onNext(final JokeEntity.DataBean response) {

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
                        ToastUtils.showShort("新闻上传完成");
                        //关闭对话框
                        dismissDialog();
                    }
                });
    }

}
