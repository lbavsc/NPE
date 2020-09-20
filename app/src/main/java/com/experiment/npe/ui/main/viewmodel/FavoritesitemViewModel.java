package com.experiment.npe.ui.main.viewmodel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.experiment.npe.entity.FavoritesEntity;
import com.experiment.npe.ui.jokedetails.JokeDetailsActivity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class FavoritesitemViewModel extends ItemViewModel<TabBar2ViewModel> {
    public ObservableField<FavoritesEntity.DataBean> entity = new ObservableField<>();

    public FavoritesitemViewModel(@NonNull TabBar2ViewModel viewModel, FavoritesEntity.DataBean entity) {
        super(viewModel);
        this.entity.set(entity);
    }

    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle mBundle = new Bundle();
            mBundle.putString("jokeId", entity.get().getJokeId());
            viewModel.startActivity(JokeDetailsActivity.class, mBundle);
        }
    });
}
