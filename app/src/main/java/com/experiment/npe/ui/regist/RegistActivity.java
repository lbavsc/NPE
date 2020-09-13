package com.experiment.npe.ui.regist;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.app.AppViewModelFactory;
import com.experiment.npe.databinding.ActivityLoginBinding;
import com.experiment.npe.ui.login.LoginViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class RegistActivity extends BaseActivity<ActivityLoginBinding, RegistViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_regist;
    }

    @Override
    public int initVariableId() {
        return BR.regist_viewModel;
    }

    @Override
    public RegistViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(RegistViewModel.class);
    }
}