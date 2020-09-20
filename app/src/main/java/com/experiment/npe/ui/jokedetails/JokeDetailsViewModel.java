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

import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeDetailsEntity;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.entity.ResultEntity;
import com.experiment.npe.ui.main.viewmodel.JokeItemViewModel;
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
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class JokeDetailsViewModel extends BaseViewModel<NpeRepository> {

    public Drawable commentDrawableImg;
    public ObservableInt favoritesVisibility = new ObservableInt();
    public ObservableInt deletefavoritesVisibility = new ObservableInt();
    public ObservableField<String> userId = new ObservableField<>("");
    public ObservableField<String> jokeImg = new ObservableField<>("");
    public ObservableField<String> jokeTime = new ObservableField<>("");
    public ObservableField<String> userIcon = new ObservableField<>("");
    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> jokeTitle = new ObservableField<>("");
    public ObservableField<String> jokeSource = new ObservableField<>("");
    public ObservableList<JokeDetailsEntity> entity = new ObservableArrayList<>();
    public ObservableField<String> jokeContent = new ObservableField<>("");
    public ObservableList<JokeDetailsItemViewModel> observableList = new ObservableArrayList<>();
    public SingleLiveEvent<JokeDetailsItemViewModel> deleteItemLiveData = new SingleLiveEvent<>();
    public SingleLiveEvent<JokeDetailsItemViewModel> addItemLiveData = new SingleLiveEvent<>();
    public ObservableList<JokeDetailsEntity.DataBean.RemarksBean> remark = new ObservableArrayList<>();
    public ItemBinding<JokeDetailsItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_joke_details_remark);

    public JokeDetailsViewModel(@NonNull Application application, NpeRepository model) {
        super(application, model);
        Log.e("TAG", model.getUserIcon());
        userName.set(model.getUserName());
        userId.set("ID:" + model.getUserId());
        if (model.getUserStatus()){
            if (model.getUserIcon().startsWith("img")) {
                userIcon.set(RetrofitClient.baseUrl + model.getUserIcon());
            } else {
                userIcon.set(model.getUserIcon());
            }
        }

    }

    /**
     * 获得model对象
     *
     * @return
     */
    public NpeRepository getmodel() {
        return model;
    }

    /**
     * 收藏按钮可见性
     */
    public void isFavorites(boolean favorites) {
        if (favorites) {
            favoritesVisibility.set(View.GONE);
            deletefavoritesVisibility.set(View.VISIBLE);
        } else {
            favoritesVisibility.set(View.VISIBLE);
            deletefavoritesVisibility.set(View.GONE);
        }
    }

    /**
     * 收藏按钮
     */
    public BindingCommand favoritesOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!model.getUserStatus()) {
                ToastUtils.showShort("您当前未登录");
                return;
            }
            isFavorites(true);
            addCollection();
        }
    });

    /**
     * 删除收藏按钮
     */
    public BindingCommand deleteFavoritesOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!model.getUserStatus()) {
                ToastUtils.showShort("您当前未登录");
                return;
            }
            isFavorites(false);
            deleteCollection();
        }
    });
    /**
     * 返回上一页
     */
    public BindingCommand backOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onBackPressed();
        }
    });

    /**
     * 获得新闻数据
     *
     * @param jokeId
     */
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
                        jokeImg.set(RetrofitClient.baseUrl + response.getData().getCoverImg());
                        jokeContent.set(response.getData().getContent());
                        jokeTime.set(response.getData().getPostTime());
                        jokeSource.set("\t\t来源：" + response.getData().getSource());

                        //评论
                        remark.addAll(response.getData().getRemarks());
                        for (JokeDetailsEntity.DataBean.RemarksBean remarksBean : remark) {
                            JokeDetailsItemViewModel jokeDetailsItemViewModel = new JokeDetailsItemViewModel(JokeDetailsViewModel.this, remarksBean);
                            observableList.add(jokeDetailsItemViewModel);

                        }
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

    /**
     * 添加评论接口
     *
     * @param content
     */
    public void remarkLoad(final String content) {
        JokeEntity.DataBean body = new JokeEntity.DataBean(model.getUserId(), entity.get(0).getData().getJokeId(), content);
        model.remarkUpload(body)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .subscribe(new DisposableObserver<JokeEntity.DataBean>() {
                    @Override
                    public void onNext(final JokeEntity.DataBean response) {
                        JokeDetailsEntity.DataBean.RemarksBean remarksBean = new JokeDetailsEntity.DataBean.RemarksBean();
                        JokeDetailsEntity.DataBean.RemarksBean.UserBean userBean=new JokeDetailsEntity.DataBean.RemarksBean.UserBean();
                        remarksBean.setContent(content);
                        remarksBean.setUserId(model.getUserId());
                        remarksBean.setJokeId(entity.get(0).getData().getJokeId());
                        userBean.setIcon(model.getUserIcon());
                        userBean.setName(model.getUserName());
                        remarksBean.setUser(userBean);
                        remarksBean.setPostTime("刚刚");
                        JokeDetailsItemViewModel jokeDetailsItemViewModel = new JokeDetailsItemViewModel(JokeDetailsViewModel.this, remarksBean);
                        addItemLiveData.setValue(jokeDetailsItemViewModel);
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
                        ToastUtils.showShort("评论提交完成");
                        //关闭对话框
                        dismissDialog();
                    }
                });
    }


    /**
     * 删除评论接口
     *
     * @param index
     */
    public void deleteRemark(int index) {
        Log.e("TAG", remark.get(index).getRemarkId());
        model.deleteRemark(model.getUserId(), remark.get(index).getRemarkId())
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .subscribe(new DisposableObserver<ResultEntity>() {
                    @Override
                    public void onNext(final ResultEntity response) {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }

                    @Override
                    public void onComplete() {
                        //关闭对话框
                        ToastUtils.showShort("删除完成");
                    }
                });
    }

    /**
     * 添加收藏接口
     */
    public void addCollection() {
        model.addCollection(model.getUserId(), entity.get(0).getData().getJokeId())
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .subscribe(new DisposableObserver<ResultEntity>() {
                    @Override
                    public void onNext(final ResultEntity response) {
                        if (response.getCode() == 1002) {
                            ToastUtils.showShort("您已经收藏过了");
                            isFavorites(true);
                        } else if (response.getCode() == 1001) {
                            ToastUtils.showShort("收藏成功");
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }

                    @Override
                    public void onComplete() {
                        //关闭对话框

                    }
                });
    }

    /**
     * 删除收藏接口
     */
    public void deleteCollection() {
        model.deleteCollection(model.getUserId(), entity.get(0).getData().getJokeId())
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .subscribe(new DisposableObserver<ResultEntity>() {
                    @Override
                    public void onNext(final ResultEntity response) {
                        ToastUtils.showShort("删除成功");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }

                    @Override
                    public void onComplete() {
                        //关闭对话框

                    }
                });
    }

    /**
     * 删除条目
     *
     * @param
     */
    public void deleteItem(JokeDetailsItemViewModel jokeDetailsItemViewModel) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        observableList.remove(jokeDetailsItemViewModel);
    }

    /**
     * 增加条目
     *
     * @param
     */
    public void addItem(JokeDetailsItemViewModel jokeDetailsItemViewModel) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        observableList.add(0, jokeDetailsItemViewModel);
    }

    /**
     * 获取条目下标
     *
     * @param jokeDetailsItemViewModel
     * @return
     */
    public int getItemPosition(JokeDetailsItemViewModel jokeDetailsItemViewModel) {
        return observableList.indexOf(jokeDetailsItemViewModel);
    }


}
