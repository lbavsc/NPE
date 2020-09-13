package com.experiment.npe.ui.main.vm;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeAssortEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by lbavsc on 2019/9/10.
 */

public class ViewPagerViewModel extends BaseViewModel<NpeRepository> {
    public SingleLiveEvent<String> itemClickEvent = new SingleLiveEvent<>();
    public static ObservableList<JokeAssortEntity.DataBean> observableList = new ObservableArrayList<>();

    public ViewPagerViewModel(@NonNull Application application, NpeRepository repository) {
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
                            ViewPagerItemViewModel itemViewModel = new ViewPagerItemViewModel(ViewPagerViewModel.this, response.getData().get(i).getAssortName());
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
    public ObservableList<ViewPagerItemViewModel> items = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    public ItemBinding<ViewPagerItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_viewpager);
    //给ViewPager添加PageTitle
    public final BindingViewPagerAdapter.PageTitles<ViewPagerItemViewModel> pageTitles = new BindingViewPagerAdapter.PageTitles<ViewPagerItemViewModel>() {
        @Override
        public CharSequence getPageTitle(int position, ViewPagerItemViewModel item) {
            return observableList.get(position).getAssortName();
        }
    };
    //ViewPager切换监听
    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer index) {
            ToastUtils.showShort("ViewPager切换：" + index);
        }
    });
}
