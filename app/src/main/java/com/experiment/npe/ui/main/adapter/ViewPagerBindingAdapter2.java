package com.experiment.npe.ui.main.adapter;


import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;

import com.experiment.npe.databinding.ItemTabBar2Binding;
import com.experiment.npe.ui.main.viewmodel.TabBar2ItemViewModel;

import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;


/**
 * Created by goldze on 2018/6/21.
 */

public class ViewPagerBindingAdapter2 extends BindingViewPagerAdapter<TabBar2ItemViewModel> {

    @Override
    public void onBindBinding(final ViewDataBinding binding, int variableId, int layoutRes, final int position, TabBar2ItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        //这里可以强转成ViewPagerItemViewModel对应的ViewDataBinding，
        ItemTabBar2Binding _binding = (ItemTabBar2Binding) binding;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
