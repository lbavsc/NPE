package com.experiment.npe.ui.main.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.experiment.npe.BR;
import com.experiment.npe.R;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by lbavsc on 20-9-11
 */

public class TabBar1temViewModel extends ItemViewModel<TabBar1ViewModel> {
    public String text;
    public int index;
    //给ViewPager添加ObservableList
    public ObservableList<TabBar1temViewModel> observableList = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    public ItemBinding<TabBar1temViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_tab_bar_1);

    public TabBar1temViewModel(@NonNull TabBar1ViewModel viewModel, String text, int index) {
        super(viewModel);
        this.text = text;
        this.index=index;
    }




}
