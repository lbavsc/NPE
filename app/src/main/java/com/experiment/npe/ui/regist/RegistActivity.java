package com.experiment.npe.ui.regist;

import androidx.lifecycle.ViewModelProviders;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.app.AppViewModelFactory;
import com.experiment.npe.databinding.ActivityLoginBinding;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * Created by lbavsc on 20-9-11
 */
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