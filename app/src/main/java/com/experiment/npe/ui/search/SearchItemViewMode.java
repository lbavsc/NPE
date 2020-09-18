package com.experiment.npe.ui.search;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.utils.RetrofitClient;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by lbavsc on 20-9-18
 */
public class SearchItemViewMode extends ItemViewModel<SearchViewModel> {
    public ObservableField<JokeEntity.DataBean> entity = new ObservableField<>();
    public SearchItemViewMode(@NonNull SearchViewModel viewModel,JokeEntity.DataBean entity) {
        super(viewModel);
        entity.setCoverImg(RetrofitClient.baseUrl+entity.getCoverImg());
        this.entity.set(entity);
    }


}
