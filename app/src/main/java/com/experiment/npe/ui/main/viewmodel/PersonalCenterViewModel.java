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

/**
 * 个人中心ViewModel
 * Created by lbavsc on 20-9-15
 */
public class PersonalCenterViewModel extends BaseViewModel<NpeRepository> {

    public ItemBinding<PersonalCenterItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_tab_bar_2);    //给ViewPager添加ItemBinding
    public SingleLiveEvent<FavoritesEntity.DataBean> deleteItemData = new SingleLiveEvent<>();                              //删除收藏条目监听
    public ObservableList<PersonalCenterItemViewModel> items = new ObservableArrayList<>();                                 //给ViewPager添加ObservableList
    public SingleLiveEvent<FavoritesEntity.DataBean> addItemData = new SingleLiveEvent<>();                                 //增加收藏条目监听
    public SingleLiveEvent<Boolean> requestCameraPermissions = new SingleLiveEvent<>();                                     //更换头像监听
    public SingleLiveEvent<Boolean> entityJsonLiveData = new SingleLiveEvent<>();                                           //登出按钮监听
    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> userIcon = new ObservableField<>("");
    public ObservableField<String> userId = new ObservableField<>("");
    public ObservableInt loginVisibility = new ObservableInt();
    public ObservableInt visibility = new ObservableInt();
    public String TAG = "TabBar2ViewModel";
    public Drawable drawableImg;





    public PersonalCenterViewModel(@NonNull Application application, NpeRepository repository) {
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

        for (int i = 0; i < 2; i++) {               //创建俩个ViewPaper
            PersonalCenterItemViewModel itemViewModel = new PersonalCenterItemViewModel(this, i);
            items.add(itemViewModel);
            if (i == 0 && model.getUserStatus()) {      //如果是收藏条目则加载收藏列表
                getCollection(itemViewModel);
            }
        }
    }

    /**
     * 获得model
     *
     * @return
     */
    public NpeRepository getmodle() {
        return model;
    }

    /**
     * 登录按钮点击事件
     */
    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(LoginActivity.class);
            finish();
        }
    });


    /**
     * 登出按钮点击事件
     */
    public BindingCommand loginOutOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            entityJsonLiveData.setValue(model.getUserStatus());
        }
    });

    /**
     * 设置按钮点击事件
     */
    public BindingCommand onSettingClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(SettingActivity.class);
        }
    });

    /**
     * 头像点击事件
     */
    public BindingCommand userIconOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            requestCameraPermissions.call();        //启动请求更换头像监听
        }
    });

    /**
     * 更新头像
     */
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
//                        Log.e(TAG, "onNext: " + response.getCode());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //关闭对话框
                        dismissDialog();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);      //输出错误信息
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


    /**
     * 给ViewPager添加PageTitle
     */
    public final BindingViewPagerAdapter.PageTitles<PersonalCenterItemViewModel> pageTitles = new BindingViewPagerAdapter.PageTitles<PersonalCenterItemViewModel>() {
        @Override
        public CharSequence getPageTitle(int position, PersonalCenterItemViewModel item) {
            if (position == 0) {
                return "收藏";
            } else if (position == 1) {
                return "帖子";
            }
            return null;
        }
    };

    /**
     * ViewPager切换监听
     */
    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public Integer call(Integer index) {
            return index;
        }
    });

    /**
     * 获取收藏列表
     */
    public void getCollection(final PersonalCenterItemViewModel itemViewModel) {
        model.getCollection(model.getUserId())
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .subscribe(new DisposableObserver<FavoritesEntity>() {
                    @Override
                    public void onNext(final FavoritesEntity response) {

                        for (FavoritesEntity.DataBean dataBean : response.getData()) {
                            PersonalCenterFavoritesitemViewModel favoritesitemViewModel = new PersonalCenterFavoritesitemViewModel(PersonalCenterViewModel.this, dataBean);
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
