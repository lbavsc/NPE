package com.experiment.npe.ui.main.vm;

import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.experiment.npe.R;
import com.experiment.npe.entity.JokeEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;

class ViewPagerItemListViewModel extends ItemViewModel<ViewPagerViewModel> {

    public ObservableField<JokeEntity.DataBean> entity = new ObservableField<>();
    public Drawable drawableImg;

    public ViewPagerItemListViewModel(@NonNull ViewPagerViewModel viewModel, JokeEntity.DataBean entity) {
        super(viewModel);
        this.entity.set(entity);
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawableImg = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.ic_launcher);
    }



}
