package com.experiment.npe.ui.uploadjoke;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.experiment.npe.R;
import com.experiment.npe.app.AppViewModelFactory;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.databinding.ActivityUploadJokeBinding;
import com.experiment.npe.entity.JokeEntity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BR;

/**
 * 上传新闻页面
 */
public class UploadJokeActivity extends BaseActivity<ActivityUploadJokeBinding, UploadJokeViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_upload_joke;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public UploadJokeViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(UploadJokeViewModel.class);
    }

    private JokeEntity.DataBean entity = new JokeEntity.DataBean();


    @Override
    public void initData() {
        super.initData();
        viewModel.setFormEntity(entity);
    }

    @Override
    public void initViewObservable() {
        //注册监听相机权限的请求
        viewModel.requestCameraPermissions.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                requestCameraPermissions();
            }

        });
    }

    /**
     * 请求相机权限
     */
    private void requestCameraPermissions() {
        //请求打开相机权限
        RxPermissions rxPermissions = new RxPermissions(UploadJokeActivity.this);
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            PictureSelector
                                    .create(UploadJokeActivity.this, PictureSelector.SELECT_REQUEST_CODE)
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
                    entity.setCoverImg(pictureBean.getPath());
                    viewModel.imgUrl.set(pictureBean.getPath());
                    viewModel.uploadImgButtonVisibility.set(View.GONE);
                    viewModel.uploadImgVisibility.set(View.VISIBLE);
                } else {
                    viewModel.imgUrl.set(pictureBean.getUri().getPath());
                    entity.setCoverImg(pictureBean.getUri().getPath());
                    viewModel.uploadImgButtonVisibility.set(View.GONE);
                    viewModel.uploadImgVisibility.set(View.VISIBLE);
                }
            }
        }
    }
}