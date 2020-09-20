package com.experiment.npe.ui.main.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeAssortEntity;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.utils.RetrofitClient;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by lbavsc on 20-9-11
 */

public class TabBar1temViewModel extends ItemViewModel<TabBar1ViewModel> {
    public String text;
    public int index;

    //给ViewPager添加ObservableList
    public ObservableList<JokeItemViewModel> observableList1 = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    public ItemBinding<JokeItemViewModel> itemBinding1 = ItemBinding.of(BR.viewModel, R.layout.item_tab_bar_1_joke);

    public TabBar1temViewModel(@NonNull TabBar1ViewModel viewModel, String text, int index) {
        super(viewModel);
        this.text = text;
        this.index = index;
    }


    public int getItemPosition(JokeItemViewModel jokeItemViewModel) {
        return observableList1.indexOf(jokeItemViewModel);
    }

    /**
     * 删除条目
     *
     * @param
     */
    public void deleteItem(JokeItemViewModel jokeItemViewModel) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        observableList1.remove(jokeItemViewModel);
    }

    /**
     * 增加条目
     *
     * @param
     */
    public void addItem(JokeItemViewModel jokeItemViewModel) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        observableList1.add(0,jokeItemViewModel);
    }

}
