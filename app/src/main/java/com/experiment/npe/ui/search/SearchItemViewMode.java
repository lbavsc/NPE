package com.experiment.npe.ui.search;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.utils.RetrofitClient;

import java.sql.Date;
import java.sql.Timestamp;

import me.goldze.mvvmhabit.base.ItemViewModel;

/**
 * Created by lbavsc on 20-9-18
 */
public class SearchItemViewMode extends ItemViewModel<SearchViewModel> {
    public ObservableField<JokeEntity.DataBean> entity = new ObservableField<>();
    public String TAG = "SearchItemViewMode";
    public SearchItemViewMode(@NonNull SearchViewModel viewModel, JokeEntity.DataBean entity) {
        super(viewModel);
        entity.setCoverImg(RetrofitClient.baseUrl + entity.getCoverImg());
        this.entity.set(entity);
    }


}
