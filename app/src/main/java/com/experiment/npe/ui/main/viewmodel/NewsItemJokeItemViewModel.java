package com.experiment.npe.ui.main.viewmodel;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.ui.jokedetails.JokeDetailsActivity;
import com.experiment.npe.utils.RetrofitClient;

import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 新闻条目ViewModel
 */
public class NewsItemJokeItemViewModel extends ItemViewModel<NewsViewModel> {
    public ObservableField<JokeEntity.DataBean> entity = new ObservableField<>();
    public String TAG = "JokeItemViewModel";
    NpeRepository model = viewModel.getmodel();

    public NewsItemJokeItemViewModel(@NonNull NewsViewModel viewModel, JokeEntity.DataBean entity) {
        super(viewModel);
        if (entity.getCoverImg().startsWith("img")) {       //判断图片路径是否为云端路径
            entity.setCoverImg(RetrofitClient.baseUrl + entity.getCoverImg());
        } else {
            entity.setCoverImg(entity.getCoverImg());       //不是则加载本地保存的头像
        }

        this.entity.set(entity);
    }

    /**
     * 获得条目下标
     * @return
     */
    public int getPosition() {
        model.saveJokeIndex(viewModel.items.get(model.getAssortIndex()).getItemPosition(this));
        return model.getJokeIndex();
    }


    /**
     * 条目点击事件
     */
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle mBundle = new Bundle();
            mBundle.putString("jokeId", entity.get().getJokeId());
            viewModel.startActivity(JokeDetailsActivity.class, mBundle);
        }
    });

    /**
     * 条目长按事件
     */
    public BindingCommand itemLongClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getPosition();
            if (!model.getUserStatus()) {
                ToastUtils.showShort("您当前未登录");
                return;
            }
            if (!model.getUserId().equals(entity.get().getUserId()) && !model.getUserType()) {
                ToastUtils.showShort("您无删除此条新闻的权限");
                return;
            }
            if (model.getUserId().equals(entity.get().getUserId()) || model.getUserType()) {
                viewModel.entityJsonLiveData.setValue(NewsItemJokeItemViewModel.this);
            }
        }
    });


    /**
     * 删除接口的实现
     */
    public void deteleJoke() {
        model.deleteJoke(entity.get().getJokeId(), model.getUserId())
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(viewModel)//请求与ViewModel周期同步
                .subscribe(new DisposableObserver<JokeEntity>() {
                    @Override
                    public void onNext(final JokeEntity response) {

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
}
