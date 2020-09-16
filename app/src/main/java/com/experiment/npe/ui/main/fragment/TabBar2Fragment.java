package com.experiment.npe.ui.main.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.app.AppViewModelFactory;
import com.experiment.npe.databinding.FragmentTabBar2Binding;
import com.experiment.npe.ui.main.vm.TabBar2ViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

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



}
