package com.experiment.npe.ui.main.vm;

import android.app.Application;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.experiment.npe.R;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.UserEntity;

import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Created by lbavsc on 20-9-15
 */
public class TabBar2ViewModel extends BaseViewModel<NpeRepository> {
    public ObservableField<UserEntity> entity = new ObservableField<>();
    public Drawable drawableImg;
    public TabBar2ViewModel(@NonNull Application application, NpeRepository repository) {
        super(application ,repository);
        this.entity.set(entity.get());
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawableImg = ContextCompat.getDrawable(application, R.mipmap.ic_launcher);
    }
}
