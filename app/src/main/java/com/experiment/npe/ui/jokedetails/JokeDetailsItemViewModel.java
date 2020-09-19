package com.experiment.npe.ui.jokedetails;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeDetailsEntity;
import com.experiment.npe.entity.JokeEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class JokeDetailsItemViewModel extends ItemViewModel<JokeDetailsViewModel> {
    NpeRepository model;
    public ObservableField<JokeDetailsEntity.DataBean.RemarksBean> entity = new ObservableField<>();
    public ObservableField<String> img = new ObservableField<>("");
    public ObservableField<String> text = new ObservableField<>("");
    public JokeDetailsItemViewModel(@NonNull JokeDetailsViewModel viewModel, JokeDetailsEntity.DataBean.RemarksBean entity) {
        super(viewModel);
        this.entity.set(entity);
        model = viewModel.getmodel();
        img.set(model.getUserIcon());
        text.set(model.getUserName());
    }
}
