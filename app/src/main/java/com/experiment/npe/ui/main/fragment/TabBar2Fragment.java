package com.experiment.npe.ui.main.fragment;

import android.Manifest;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.ObservableField;

import android.app.PictureInPictureParams;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
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
import com.experiment.npe.ui.main.adapter.ViewPagerBindingAdapter2;
import com.experiment.npe.ui.main.viewmodel.TabBar2ViewModel;
import com.google.android.material.tabs.TabLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wildma.pictureselector.FileUtils;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lbavsc on 20-9-11
 */
public class TabBar2Fragment extends BaseFragment<FragmentTabBar2Binding, TabBar2ViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_tab_bar_2;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public TabBar2ViewModel initViewModel() {

        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(TabBar2ViewModel.class);
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
    }

    @Override
    public void initData() {

        // 使用 TabLayout 和 ViewPager 相关联
        binding.tabs.setupWithViewPager(binding.viewPager);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
        //给ViewPager设置adapter
        binding.setAdapter(new ViewPagerBindingAdapter2());
        NpeRepository model = viewModel.getmodle();
        if (model.getUserStatus()) {
            viewModel.loginVisibility.set(View.GONE);
            viewModel.visibility.set(View.VISIBLE);
        } else {
            viewModel.loginVisibility.set(View.VISIBLE);
            viewModel.visibility.set(View.GONE);
        }
    }

    /**
     * 请求相机权限
     */
    private void requestCameraPermissions() {
        //请求打开相机权限
        RxPermissions rxPermissions = new RxPermissions(TabBar2Fragment.this);
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            PictureSelector
                                    .create(TabBar2Fragment.this, PictureSelector.SELECT_REQUEST_CODE)
                                    .selectPicture(true);
                        } else {
                            ToastUtils.showShort("权限被拒绝");
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        NpeRepository model = viewModel.getmodle();
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                if (pictureBean.isCut()) {
                    model.saveUserIcon(pictureBean.getPath());
                    viewModel.userIcon.set(pictureBean.getPath());
                    viewModel.upUserIcon();
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
        viewModel.userName.set(model.getUserName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FileUtils.deleteAllCacheImage(this.getContext());
    }


}
