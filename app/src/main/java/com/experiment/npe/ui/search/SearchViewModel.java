package com.experiment.npe.ui.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeEntity;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 搜索节目ViewModel
 */
public class SearchViewModel extends BaseViewModel<NpeRepository> {

    public ObservableList<SearchItemViewMode> observableList = new ObservableArrayList<>();                              //给RecyclerView添加ObservableList
    public ItemBinding<SearchItemViewMode> itemBinding = ItemBinding.of(BR.viewMode, R.layout.item_search_activity);     //给RecyclerView添加ItemBinding

    public SearchViewModel(@NonNull Application application, NpeRepository model) {
        super(application, model);
    }

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
     * toolbar右面按钮点击事件
     */
    public BindingCommand rightIconOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("等待开发");
        }
    });

    /**
     * 搜索接口实现
     *
     * @param string
     */
    public void searchMain(String string) {
        model.search(string)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在请求...");
                    }
                })
                .subscribe(new DisposableObserver<JokeEntity>() {
                    @Override
                    public void onNext(JokeEntity response) {
                        if (response.getData() == null || response.getData().size() == 0) {
                            ToastUtils.showShort("查询不到您所需的新闻，请返回重新搜索");
                            return;
                        }
                        for (JokeEntity.DataBean itemsEntity : response.getData()) {
                            SearchItemViewMode searchItemViewMode = new SearchItemViewMode(SearchViewModel.this, itemsEntity);
                            observableList.add(searchItemViewMode);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //关闭对话框
                        dismissDialog();
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
