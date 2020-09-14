package com.experiment.npe.ui.search;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.app.AppViewModelFactory;
import com.experiment.npe.databinding.ActivityLoginBinding;
import com.experiment.npe.databinding.ActivitySearchBinding;
import com.experiment.npe.ui.login.LoginViewModel;
import com.experiment.npe.ui.regist.RegistViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchViewModel> {

    public String SearchString;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_search;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        Bundle bundle = this.getIntent().getExtras();
        SearchString = bundle.getString("SearchString");
        viewModel.searchMain(SearchString);

    }

    @Override
    public SearchViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SearchViewModel.class);
    }
}