package com.experiment.npe.ui.setting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.experiment.npe.R;
import com.experiment.npe.app.AppViewModelFactory;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.databinding.ActivitySettingBinding;
import com.experiment.npe.ui.main.activity.MainActivity;
import com.experiment.npe.ui.main.fragment.TabBar2Fragment;
import com.experiment.npe.ui.main.viewmodel.TabBar2ViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BR;

public class SettingActivity extends BaseActivity<ActivitySettingBinding, SettingViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_setting;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public SettingViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SettingViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.entityJsonLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    MaterialDialogUtils.showBasicDialog(SettingActivity.this, "确认退出账号")
                            .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    ToastUtils.showLong("取消");
                                }
                            }).onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            viewModel.loginVisibility.set(View.VISIBLE);
                            viewModel.logOutinVisibility.set(View.GONE);
                            NpeRepository model = viewModel.getmodle();
                            model.saveUserStatus(false);
                            Bundle bundle = new Bundle();
                            bundle.putInt("num", 1);
                            startActivity(MainActivity.class, bundle);
                            finish();
                        }
                    }).show();
                } else {
                    ToastUtils.showShort("您尚未登录");
                }
            }
        });
    }
}