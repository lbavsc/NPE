package com.experiment.npe.ui.main.viewmodel;

import android.app.Application;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.view.View;

import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeAssortEntity;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.ui.search.SearchActivity;
import com.experiment.npe.ui.uploadjoke.UploadJokeActivity;

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
public class NewsViewModel extends BaseViewModel<NpeRepository> {

    public ObservableInt uploadVisibility = new ObservableInt();                                                //上传新闻按钮可见性
    public ObservableField<String> searchText = new ObservableField<>("");                                //搜索框输入内容
    public ObservableList<NewsItemViewModel> items = new ObservableArrayList<>();                               //给ViewPager添加ObservableList
    public SingleLiveEvent<Boolean> onFocusChangeCommand = new SingleLiveEvent<>();                             //取消ExitText焦点监听
    public SingleLiveEvent<JokeEntity.DataBean> addItemData = new SingleLiveEvent<>();                          //增加题目监听
    public SingleLiveEvent<NewsItemJokeItemViewModel> entityJsonLiveData = new SingleLiveEvent<>();             //删除监听
    public static ObservableList<JokeAssortEntity.DataBean> observableList = new ObservableArrayList<>();       //新闻分类列表
    public ItemBinding<NewsItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_tab_bar_1);  //给ViewPager添加ItemBinding


    public NewsViewModel(@NonNull Application application, NpeRepository repository) {
        super(application, repository);
        if (model.getUserStatus() && model.getUserType()) {
            uploadVisibility.set(View.VISIBLE);
        } else {
            uploadVisibility.set(View.GONE);
        }
    }

    /**
     * 获得model
     *
     * @return
     */
    public NpeRepository getmodel() {
        return model;
    }

    /**
     * 获得新闻分类
     */
    public void getAssort() {
        model.assort()
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .subscribe(new DisposableObserver<JokeAssortEntity>() {
                    @Override
                    public void onNext(final JokeAssortEntity response) {
                        for (int i = 0; i < 6; i++) {
                            NewsItemViewModel itemViewModel = new NewsItemViewModel(NewsViewModel.this, response.getData().get(i).getAssortName(),
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


    /**
     * 给ViewPager添加PageTitle
     */
    public final BindingViewPagerAdapter.PageTitles<NewsItemViewModel> pageTitles = new BindingViewPagerAdapter.PageTitles<NewsItemViewModel>() {
        @Override
        public CharSequence getPageTitle(int position, NewsItemViewModel item) {
            return observableList.get(position).getAssortName();
        }
    };

    /**
     * ViewPager切换监听
     */
    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public Integer call(Integer index) {
            //取消ExitText焦点
            onFocusChangeCommand.setValue(false);
            //清除页面内容
            if (index == 5) {
                items.get(4).observableList1.clear();
            } else if (index == 0) {
                items.get(1).observableList1.clear();
            } else {
                items.get(index - 1).observableList1.clear();
                items.get(index + 1).observableList1.clear();
            }
            showJoke(items.get(index));     //请求页面内容
            return index;
        }
    });

    /**
     * 搜索按钮点击事件
     */
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

    /**
     * 上传按钮点击事件
     */
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


    /**
     * 显示新闻
     *
     * @param tabBar1temViewModel
     */
    public void showJoke(final NewsItemViewModel tabBar1temViewModel) {
        int index = tabBar1temViewModel.index;

        model.showJoke(index)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .subscribe(new DisposableObserver<JokeEntity>() {
                    @Override
                    public void onNext(final JokeEntity response) {
                        for (JokeEntity.DataBean dataBean : response.getData()) {
                            NewsItemJokeItemViewModel jokeItemViewModel = new NewsItemJokeItemViewModel(NewsViewModel.this, dataBean);
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
