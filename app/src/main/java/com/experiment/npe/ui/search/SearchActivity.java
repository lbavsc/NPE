package com.experiment.npe.ui.search;

import androidx.lifecycle.ViewModelProviders;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.app.AppViewModelFactory;
import com.experiment.npe.databinding.ActivitySearchBinding;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 搜索页面
 */
public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchViewModel> {

    public String SearchString;         //搜索关键词

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_search;
    }

    @Override
    public int initVariableId() {
        return BR.viewMode;
    }

    @Override
    public void initData() {
        viewModel.searchMain(SearchString);

    }

    @Override
    public void initParam() {                           //获得上一页面传输过来的Bundle
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            SearchString = bundle.getString("SearchString");
        }

    }

    @Override
    public SearchViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SearchViewModel.class);
    }
}