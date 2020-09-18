package com.experiment.npe.ui.main.viewmodel;

import android.app.Application;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import android.database.Observable;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.view.View;

import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeAssortEntity;
import com.experiment.npe.ui.search.SearchActivity;

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

    public TabBar1ViewModel(@NonNull Application application, NpeRepository repository) {
        super(application, repository);
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
                        for (int i = 0; i < response.getData().size(); i++) {
                            TabBar1temViewModel itemViewModel = new TabBar1temViewModel(TabBar1ViewModel.this, response.getData().get(i).getAssortName(),
                                    response.getData().get(i).getAssortId());
                            observableList.add(response.getData().get(i));
                            items.add(itemViewModel);
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
        public String call(Integer index) {
            onFocusChangeCommand.setValue(false);
            ToastUtils.showShort("ViewPager切换：" + index);
            return null;
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

    public NpeRepository getmodel(){
        return model;
    }


}
