package com.experiment.npe.ui.main.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.app.AppViewModelFactory;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.databinding.FragmentTabBar1Binding;
import com.experiment.npe.ui.login.LoginViewModel;
import com.experiment.npe.ui.main.adapter.ViewPagerBindingAdapter;
import com.experiment.npe.ui.main.vm.ViewPagerViewModel;


import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by goldze on 2018/7/18.
 */

public class TabBar1Fragment extends BaseFragment<FragmentTabBar1Binding, ViewPagerViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_tab_bar_1;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        // 使用 TabLayout 和 ViewPager 相关联
        binding.tabs.setupWithViewPager(binding.viewPager);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
        //给ViewPager设置adapter
        binding.setAdapter(new ViewPagerBindingAdapter());
        viewModel.getAssort();
    }

    @Override
    public ViewPagerViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(ViewPagerViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.itemClickEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String text) {
                ToastUtils.showShort("position：" + text);
            }
        });
    }

}
