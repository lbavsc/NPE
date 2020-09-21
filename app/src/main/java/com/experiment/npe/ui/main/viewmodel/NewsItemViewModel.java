package com.experiment.npe.ui.main.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.experiment.npe.BR;
import com.experiment.npe.R;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 新闻页面ViewModel
 * Created by lbavsc on 20-9-11
 */

public class NewsItemViewModel extends ItemViewModel<NewsViewModel> {
    public String text;
    public int index;

    //给ViewPager添加ObservableList
    public ObservableList<NewsItemJokeItemViewModel> observableList1 = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    public ItemBinding<NewsItemJokeItemViewModel> itemBinding1 = ItemBinding.of(BR.viewModel, R.layout.item_tab_bar_1_joke);

    public NewsItemViewModel(@NonNull NewsViewModel viewModel, String text, int index) {
        super(viewModel);
        this.text = text;
        this.index = index;
    }

    /**
     * 获得条目下标
     * @param jokeItemViewModel
     * @return
     */
    public int getItemPosition(NewsItemJokeItemViewModel jokeItemViewModel) {
        return observableList1.indexOf(jokeItemViewModel);
    }

    /**
     * 删除条目
     *
     * @param
     */
    public void deleteItem(NewsItemJokeItemViewModel jokeItemViewModel) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        observableList1.remove(jokeItemViewModel);
    }

    /**
     * 增加条目
     *
     * @param
     */
    public void addItem(NewsItemJokeItemViewModel jokeItemViewModel) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        observableList1.add(0,jokeItemViewModel);
    }

}
