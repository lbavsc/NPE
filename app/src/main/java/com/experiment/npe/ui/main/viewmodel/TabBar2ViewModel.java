package com.experiment.npe.ui.main.viewmodel;

import android.app.Application;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableList;

import android.util.Log;
import android.view.View;

import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.FavoritesEntity;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.entity.ResultEntity;
import com.experiment.npe.ui.login.LoginActivity;
import com.experiment.npe.ui.setting.SettingActivity;
import com.experiment.npe.utils.RetrofitClient;

import java.io.File;

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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Query;

/**
 * Created by lbavsc on 20-9-15
 */
public class TabBar2ViewModel extends BaseViewModel<NpeRepository> {
    public SingleLiveEvent<Boolean> requestCameraPermissions = new SingleLiveEvent<>();
    public SingleLiveEvent<FavoritesEntity.DataBean> addItemData = new SingleLiveEvent<>();
    public SingleLiveEvent<FavoritesEntity.DataBean> deleteItemData = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> entityJsonLiveData = new SingleLiveEvent<>();
    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> userIcon = new ObservableField<>("");
    public ObservableField<String> userId = new ObservableField<>("");
    public ObservableInt loginVisibility = new ObservableInt();
    public static int ITEM_POSITION=0;
    public ObservableInt visibility = new ObservableInt();
    public String TAG = "TabBar2ViewModel";
    public Drawable drawableImg;


    public TabBar2ViewModel(@NonNull Application application, NpeRepository repository) {
        super(application, repository);
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawableImg = ContextCompat.getDrawable(application, R.mipmap.ic_launcher);
        userName.set(model.getUserName());

        if (model.getUserIcon().startsWith("img")) {
            userIcon.set(RetrofitClient.baseUrl + model.getUserIcon());
            model.saveUserIcon(RetrofitClient.baseUrl + model.getUserIcon());
        } else {
            userIcon.set(model.getUserIcon());
        }
        userId.set("ID:" + model.getUserId());
        if (model.getUserStatus()) {
            loginVisibility.set(View.GONE);
            visibility.set(View.VISIBLE);
        } else {
            loginVisibility.set(View.VISIBLE);
            visibility.set(View.GONE);
        }

        for (int i = 0; i < 2; i++) {
            TabBar2ItemViewModel itemViewModel = new TabBar2ItemViewModel(this, i);
            items.add(itemViewModel);
            if (i == 0 && model.getUserStatus()) {
                getCollection(itemViewModel);
            }
        }
    }

    public NpeRepository getmodle() {
        return model;
    }

    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(LoginActivity.class);
            finish();
        }
    });


    public BindingCommand loginOutOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            entityJsonLiveData.setValue(model.getUserStatus());
        }
    });

    public BindingCommand onSettingClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(SettingActivity.class);
        }
    });

    public BindingCommand userIconOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            requestCameraPermissions.call();
        }
    });

    public void upUserIcon() {
        String userId = model.getUserId();
        String path = model.getUserIcon();
        File file = new File(path);
        RequestBody fileRQ = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("userId", userId)
                .addFormDataPart("cover", file.getName(), fileRQ)
                .build();
        model.updateUserIcon(body)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .subscribe(new DisposableObserver<ResultEntity>() {
                    @Override
                    public void onNext(final ResultEntity response) {
                        Log.e(TAG, "onNext: " + response.getCode());
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
                        ToastUtils.showShort("头像更换完成");
                        //关闭对话框
                        dismissDialog();
                    }
                });
    }

    //给ViewPager添加ObservableList
    public ObservableList<TabBar2ItemViewModel> items = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    public ItemBinding<TabBar2ItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_tab_bar_2);
    //给ViewPager添加PageTitle
    public final BindingViewPagerAdapter.PageTitles<TabBar2ItemViewModel> pageTitles = new BindingViewPagerAdapter.PageTitles<TabBar2ItemViewModel>() {
        @Override
        public CharSequence getPageTitle(int position, TabBar2ItemViewModel item) {
            if (position == 0) {
                return "收藏";
            } else if (position == 1) {
                return "帖子";
            }
            return null;
        }
    };
    //ViewPager切换监听
    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public Integer call(Integer index) {
            ToastUtils.showShort("ViewPager切换：" + index);
            return index;
        }
    });

    /**
     * 获取收藏列表
     */
    public void getCollection(final TabBar2ItemViewModel itemViewModel) {
        model.getCollection(model.getUserId())
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .subscribe(new DisposableObserver<FavoritesEntity>() {
                    @Override
                    public void onNext(final FavoritesEntity response) {

                        for (FavoritesEntity.DataBean dataBean : response.getData()) {
                            FavoritesitemViewModel favoritesitemViewModel = new FavoritesitemViewModel(TabBar2ViewModel.this, dataBean);
                            itemViewModel.observableList1.add(favoritesitemViewModel);
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
