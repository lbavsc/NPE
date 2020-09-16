package com.experiment.npe.ui.main.viewmodel;

import android.support.annotation.NonNull;

import me.goldze.mvvmhabit.base.ItemViewModel;

/**
 * Created by lbavsc on 20-9-11
 */

public class TabBar1temViewModel extends ItemViewModel<TabBar1ViewModel> {
    public String text;

    public TabBar1temViewModel(@NonNull TabBar1ViewModel viewModel, String text) {
        super(viewModel);
        this.text = text;
    }



}
