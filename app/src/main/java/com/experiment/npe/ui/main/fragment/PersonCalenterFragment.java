package com.experiment.npe.ui.main.fragment;

import android.Manifest;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.app.AppViewModelFactory;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.databinding.FragmentTabBar2Binding;
import com.experiment.npe.entity.FavoritesEntity;
import com.experiment.npe.ui.main.adapter.PersonCalenterViewPagerBindingAdapter;
import com.experiment.npe.ui.main.viewmodel.PersonalCenterFavoritesitemViewModel;
import com.experiment.npe.ui.main.viewmodel.PersonalCenterViewModel;
import com.google.android.material.tabs.TabLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wildma.pictureselector.FileUtils;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 个人中心
 * Created by lbavsc on 20-9-11
 */
public class PersonCalenterFragment extends BaseFragment<FragmentTabBar2Binding, PersonalCenterViewModel> {
    public static final String TOKEN_AddTabBar2Fragment_REFRESH = "token_AddTabBar2Fragment_refresh";
    public static final String TOKEN_DeleteTabBar2Fragment_REFRESH = "token_DeleteTabBar2Fragment_refresh";

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_tab_bar_2;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public PersonalCenterViewModel initViewModel() {

        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(PersonalCenterViewModel.class);
    }

    @Override
    public void initData() {

        // 使用 TabLayout 和 ViewPager 相关联
        binding.tabs.setupWithViewPager(binding.viewPager);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
        //给ViewPager设置adapter
        binding.setAdapter(new PersonCalenterViewPagerBindingAdapter());
        NpeRepository model = viewModel.getmodle();
        //判断是否登录,来达成页面的变换
        if (model.getUserStatus()) {
            viewModel.loginVisibility.set(View.GONE);
            viewModel.visibility.set(View.VISIBLE);
        } else {
            viewModel.loginVisibility.set(View.VISIBLE);
            viewModel.visibility.set(View.GONE);
        }

        //接收收藏条目信息
        Messenger.getDefault().register(this, PersonCalenterFragment.TOKEN_AddTabBar2Fragment_REFRESH, FavoritesEntity.DataBean.class, new BindingConsumer<FavoritesEntity.DataBean>() {
            @Override
            public Integer call(FavoritesEntity.DataBean s) {
                viewModel.addItemData.setValue(s);
                return null;
            }
        });

        //接收删除收藏条目信息
        Messenger.getDefault().register(this, PersonCalenterFragment.TOKEN_DeleteTabBar2Fragment_REFRESH, FavoritesEntity.DataBean.class, new BindingConsumer<FavoritesEntity.DataBean>() {
            @Override
            public Integer call(FavoritesEntity.DataBean s) {
                viewModel.deleteItemData.setValue(s);
                return null;
            }
        });
    }


    @Override
    public void initViewObservable() {
        //注册监听登出按钮
        viewModel.entityJsonLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    MaterialDialogUtils.showBasicDialog(getContext(), "确认退出账号")
                            .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    ToastUtils.showLong("取消");
                                }
                            }).onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            viewModel.loginVisibility.set(View.VISIBLE);
                            viewModel.visibility.set(View.GONE);
                            viewModel.items.get(0).text.set("您当前未登录");
                            viewModel.items.get(0).recyclerViewVisibility.set(View.GONE);
                            viewModel.items.get(0).textVisibility.set(View.VISIBLE);
                            NpeRepository model = viewModel.getmodle();
                            model.saveUserStatus(false);
                        }
                    }).show();
                } else {
                    ToastUtils.showShort("您尚未登录");
                }
            }
        });

        //注册监听相机权限的请求
        viewModel.requestCameraPermissions.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                requestCameraPermissions();
            }

        });

        //注册监听增加收藏条目的请求
        viewModel.addItemData.observe(this, new Observer<FavoritesEntity.DataBean>() {
            @Override
            public void onChanged(FavoritesEntity.DataBean dataBean) {
                PersonalCenterFavoritesitemViewModel favoritesitemViewModel = new PersonalCenterFavoritesitemViewModel(viewModel, dataBean);
                viewModel.items.get(0).addItem(favoritesitemViewModel);
            }
        });

        //注册监听删除收藏条目的请求
        viewModel.deleteItemData.observe(this, new Observer<FavoritesEntity.DataBean>() {
            @Override
            public void onChanged(FavoritesEntity.DataBean dataBean) {
                PersonalCenterFavoritesitemViewModel favoritesitemViewModel = new PersonalCenterFavoritesitemViewModel(viewModel, dataBean);
                for (PersonalCenterFavoritesitemViewModel favoritesitemViewModel1 : viewModel.items.get(0).observableList1) {
                    if (favoritesitemViewModel.entity.get().getJokeId().equals(favoritesitemViewModel1.entity.get().getJokeId())) {
                        viewModel.items.get(0).deleteItem(viewModel.items.get(0).getItemPosition(favoritesitemViewModel1));
                        return;
                    }
                }
            }
        });
    }


    /**
     * 请求相机权限
     */
    private void requestCameraPermissions() {
        //请求打开相机权限
        RxPermissions rxPermissions = new RxPermissions(PersonCalenterFragment.this);
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            PictureSelector
                                    .create(PersonCalenterFragment.this, PictureSelector.SELECT_REQUEST_CODE)
                                    .selectPicture(true);
                        } else {
                            ToastUtils.showShort("权限被拒绝");
                        }
                    }
                });
    }

    //PictureSelector回调在此获得
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        NpeRepository model = viewModel.getmodle();
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                if (pictureBean.isCut()) {
                    model.saveUserIcon(pictureBean.getPath());          //本地保存用户头像
                    viewModel.userIcon.set(pictureBean.getPath());      //设置头像展示
                    viewModel.upUserIcon();                             //上传头像到服务器
                } else {
                    model.saveUserIcon(pictureBean.getUri().getPath());
                    viewModel.userIcon.set(pictureBean.getUri().getPath());
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        NpeRepository model = viewModel.getmodle();
        viewModel.userName.set(model.getUserName());        //为了更改名字后进行刷新,每次进入页面重新设置用户名,
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FileUtils.deleteAllCacheImage(this.getContext());       //清除图片缓存
    }


}
