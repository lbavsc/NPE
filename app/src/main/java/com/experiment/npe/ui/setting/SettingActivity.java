package com.experiment.npe.ui.setting;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.experiment.npe.R;
import com.experiment.npe.app.AppViewModelFactory;
import com.experiment.npe.databinding.ActivityLoginBinding;
import com.experiment.npe.databinding.ActivitySearchBinding;
import com.experiment.npe.databinding.ActivitySettingBinding;
import com.experiment.npe.ui.login.LoginViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;
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


}