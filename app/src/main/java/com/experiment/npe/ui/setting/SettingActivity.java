package com.experiment.npe.ui.setting;

import androidx.lifecycle.ViewModelProviders;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.experiment.npe.R;
import com.experiment.npe.databinding.ActivitySettingBinding;

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