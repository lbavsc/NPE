package com.experiment.npe.ui.search;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.ui.jokedetails.JokeDetailsActivity;
import com.experiment.npe.utils.RetrofitClient;

import java.sql.Date;
import java.sql.Timestamp;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * 搜索展示条目ViewModel
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

    /**
     * 条目点击事件
     */
    public BindingCommand onClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle mBundle = new Bundle();
            mBundle.putString("jokeId", entity.get().getJokeId());
            viewModel.startActivity(JokeDetailsActivity.class, mBundle);
        }
    });


}
