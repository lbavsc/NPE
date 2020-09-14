package com.experiment.npe.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.experiment.npe.BR;
import com.experiment.npe.R;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * Created by lbavsc on 20-9-11
 */
public class TabBar2Fragment extends BaseFragment {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_tab_bar_2;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

}
