package com.experiment.npe.ui.main.viewmodel;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.experiment.npe.BR;
import com.experiment.npe.R;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class TabBar2ItemViewModel extends ItemViewModel<TabBar2ViewModel> {
    public int index;
    public ObservableInt recyclerViewVisibility = new ObservableInt();
    public ObservableInt textVisibility = new ObservableInt();
    //给ViewPager添加ObservableList
    public ObservableList<JokeItemViewModel> observableList1 = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    public ItemBinding<JokeItemViewModel> itemBinding1 = ItemBinding.of(BR.viewModel, R.layout.item_tab_bar_2);

    public TabBar2ItemViewModel(@NonNull TabBar2ViewModel viewModel, int index) {
        super(viewModel);
        this.index = index;
        if (index == 0) {
            recyclerViewVisibility.set(View.VISIBLE);
            textVisibility.set(View.GONE);
        } else if (index == 2) {
            recyclerViewVisibility.set(View.GONE);
            textVisibility.set(View.VISIBLE);
        }
    }
}
