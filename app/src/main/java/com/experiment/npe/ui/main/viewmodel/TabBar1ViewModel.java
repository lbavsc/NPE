package com.experiment.npe.ui.main.viewmodel;

import android.app.Application;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeAssortEntity;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.ui.search.SearchActivity;
import com.experiment.npe.ui.uploadjoke.UploadJokeActivity;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by lbavsc on 20-9-11
 */
public class TabBar1ViewModel extends BaseViewModel<NpeRepository> {
    //    public SingleLiveEvent<String> itemClickEvent = new SingleLiveEvent<>();
    public ObservableField<String> searchText = new ObservableField<>("");
    public SingleLiveEvent<Boolean> onFocusChangeCommand = new SingleLiveEvent<>();
    public static ObservableList<JokeAssortEntity.DataBean> observableList = new ObservableArrayList<>();
    //封装一个界面发生改变的观察者
    public ObservableInt uploadVisibility = new ObservableInt();
    public SingleLiveEvent<JokeItemViewModel> entityJsonLiveData = new SingleLiveEvent<>();
    public SingleLiveEvent<JokeEntity.DataBean> addItemData = new SingleLiveEvent<>();
    public TabBar1ViewModel(@NonNull Application application, NpeRepository repository) {
        super(application, repository);
        if (model.getUserStatus() && model.getUserType()) {
            uploadVisibility.set(View.VISIBLE);
        } else {
            uploadVisibility.set(View.GONE);
        }
    }

    public void getAssort() {
        model.assort()
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在请求...");
                    }
                })
                .subscribe(new DisposableObserver<JokeAssortEntity>() {
                    @Override
                    public void onNext(final JokeAssortEntity response) {
                        for (int i = 0; i < 6; i++) {
                            TabBar1temViewModel itemViewModel = new TabBar1temViewModel(TabBar1ViewModel.this, response.getData().get(i).getAssortName(),
                                    response.getData().get(i).getAssortId());
                            observableList.add(response.getData().get(i));
                            items.add(itemViewModel);
                            if (i == 0) {
                                showJoke(itemViewModel);
                            }


                        }
                        observableList.addAll(response.getData());
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
                        dismissDialog();
                    }
                });
    }

    //给ViewPager添加ObservableList
    public ObservableList<TabBar1temViewModel> items = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    public ItemBinding<TabBar1temViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_tab_bar_1);
    //给ViewPager添加PageTitle
    public final BindingViewPagerAdapter.PageTitles<TabBar1temViewModel> pageTitles = new BindingViewPagerAdapter.PageTitles<TabBar1temViewModel>() {
        @Override
        public CharSequence getPageTitle(int position, TabBar1temViewModel item) {
            return observableList.get(position).getAssortName();
        }
    };
    //ViewPager切换监听
    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public Integer call(Integer index) {
            //取消ExitText焦点
            onFocusChangeCommand.setValue(false);

            //清除页面
            if (index == 5) {
                items.get(4).observableList1.clear();
            } else if (index == 0) {
                items.get(1).observableList1.clear();
            } else {
                items.get(index - 1).observableList1.clear();
                items.get(index + 1).observableList1.clear();
            }
            showJoke(items.get(index));
            return index;
        }
    });


    public BindingCommand onSearchCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (TextUtils.isEmpty(searchText.get())) {
                ToastUtils.showShort("请输入搜索内容");
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("SearchString", searchText.get());
                startActivity(SearchActivity.class, bundle);
                searchText.set("");
                onFocusChangeCommand.setValue(false);
            }
        }
    });

    public BindingCommand onUploadCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!model.getUserStatus()) {
                ToastUtils.showShort("您当前未登录");
                return;
            }
            if (!model.getUserType()) {
                ToastUtils.showShort("您不是管理员");
                return;
            }
            startActivity(UploadJokeActivity.class);
        }
    });

    public NpeRepository getmodel() {
        return model;
    }


    public void showJoke(final TabBar1temViewModel tabBar1temViewModel) {
        int index = tabBar1temViewModel.index;

        model.showJoke(index)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .subscribe(new DisposableObserver<JokeEntity>() {
                    @Override
                    public void onNext(final JokeEntity response) {
                        for (JokeEntity.DataBean dataBean : response.getData()) {
                            JokeItemViewModel jokeItemViewModel = new JokeItemViewModel(TabBar1ViewModel.this, dataBean);
                            tabBar1temViewModel.observableList1.add(jokeItemViewModel);

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
                        dismissDialog();
                    }
                });
    }





}
