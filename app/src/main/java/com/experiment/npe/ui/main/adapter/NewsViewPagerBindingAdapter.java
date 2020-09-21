package com.experiment.npe.ui.main.adapter;

import androidx.databinding.ViewDataBinding;

import android.view.ViewGroup;

import com.experiment.npe.databinding.ItemTabBar1Binding;
import com.experiment.npe.ui.main.viewmodel.NewsItemViewModel;

import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;


/**
 * Created by lbavsc on 20-9-11
 */
public class NewsViewPagerBindingAdapter extends BindingViewPagerAdapter<NewsItemViewModel> {

    @Override
    public void onBindBinding(final ViewDataBinding binding, int variableId, int layoutRes, final int position, NewsItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        //这里可以强转成ViewPagerItemViewModel对应的ViewDataBinding，
        ItemTabBar1Binding _binding = (ItemTabBar1Binding) binding;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
