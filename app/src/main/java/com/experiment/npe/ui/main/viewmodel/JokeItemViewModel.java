package com.experiment.npe.ui.main.viewmodel;


import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.experiment.npe.entity.JokeEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class JokeItemViewModel extends ItemViewModel<TabBar1ViewModel> {
    public ObservableField<JokeEntity.DataBean> entity = new ObservableField<>();
    public String TAG = "JokeItemViewModel";

    public JokeItemViewModel(@NonNull TabBar1ViewModel viewModel, JokeEntity.DataBean entity) {
        super(viewModel);
        this.entity.set(entity);
    }
}
