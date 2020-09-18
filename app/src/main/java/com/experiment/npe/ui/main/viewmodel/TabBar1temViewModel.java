package com.experiment.npe.ui.main.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeAssortEntity;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.utils.RetrofitClient;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by lbavsc on 20-9-11
 */

public class TabBar1temViewModel extends ItemViewModel<TabBar1ViewModel> {
    public String text;
    public int index;
    //给ViewPager添加ObservableList
    public ObservableList<JokeItemViewModel> observableList = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    public ItemBinding<JokeItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_tab_bar_1_joke);

    public TabBar1temViewModel(@NonNull TabBar1ViewModel viewModel, String text, int index) {
        super(viewModel);
        this.text = text;
        this.index = index;
        showJoke(index);

    }






    public void showJoke(int index) {
        NpeRepository model = viewModel.getmodel();
        model.showJoke(index)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(viewModel)//请求与ViewModel周期同步
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        viewModel.showDialog("正在请求...");
                    }
                })
                .subscribe(new DisposableObserver<JokeEntity>() {
                    @Override
                    public void onNext(final JokeEntity response) {
                        for (JokeEntity.DataBean dataBean:response.getData()){
                            JokeItemViewModel jokeItemViewModel = new JokeItemViewModel(viewModel, dataBean);
                            observableList.add(jokeItemViewModel);
                        }

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //关闭对话框
                        viewModel.dismissDialog();
                        //请求刷新完成收回
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }

                    @Override
                    public void onComplete() {
                        //关闭对话框
                        viewModel.dismissDialog();
                    }
                });
    }


}
