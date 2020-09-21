package com.experiment.npe.ui.main.viewmodel;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.data.NpeRepository;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 收藏/浏览记录页面ViewModel
 */
public class PersonalCenterItemViewModel extends ItemViewModel<PersonalCenterViewModel> {
    public int index;
    private NpeRepository model = viewModel.getmodle();
    public ObservableInt textVisibility = new ObservableInt();
    public ObservableField<String> text = new ObservableField<>();
    public ObservableInt recyclerViewVisibility = new ObservableInt();
    public ObservableList<PersonalCenterFavoritesitemViewModel> observableList1 = new ObservableArrayList<>();    //给ViewPager添加ObservableList
    public ItemBinding<PersonalCenterFavoritesitemViewModel> itemBinding1 = ItemBinding.of(BR.viewModel, R.layout.item_tab_bar_2_list);    //给ViewPager添加ItemBinding

    public PersonalCenterItemViewModel(@NonNull PersonalCenterViewModel viewModel, int index) {
        super(viewModel);
        this.index = index;
        init();

    }

    //初始化展示信息
    public void init() {
        if (index == 0 && model.getUserStatus()) {
            recyclerViewVisibility.set(View.VISIBLE);
            textVisibility.set(View.GONE);
        } else {
            recyclerViewVisibility.set(View.GONE);
            textVisibility.set(View.VISIBLE);
            text.set("暂未开发");
        }
        if (!model.getUserStatus()) {
            text.set("您当前尚未登录");
        }
    }

    /**
     * 获得条目下标
     *
     * @param favoritesitemViewModel
     * @return
     */
    public int getItemPosition(PersonalCenterFavoritesitemViewModel favoritesitemViewModel) {
        return observableList1.indexOf(favoritesitemViewModel);
    }

    /**
     * 删除条目
     *
     * @param
     */
    public void deleteItem(PersonalCenterFavoritesitemViewModel favoritesitemViewModel) {
        observableList1.remove(favoritesitemViewModel);
    }

    public void deleteItem(int index) {
        observableList1.remove(index);
    }

    /**
     * 增加条目
     *
     * @param
     */
    public void addItem(PersonalCenterFavoritesitemViewModel favoritesitemViewModel) {
        observableList1.add(0, favoritesitemViewModel);
    }

}
