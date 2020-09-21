package com.experiment.npe.ui.main.fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.ui.main.adapter.NewsViewPagerBindingAdapter;
import com.experiment.npe.ui.main.viewmodel.NewsItemJokeItemViewModel;
import com.experiment.npe.ui.main.viewmodel.NewsViewModel;
import com.google.android.material.tabs.TabLayout;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.app.AppViewModelFactory;
import com.experiment.npe.databinding.FragmentTabBar1Binding;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 新闻页面
 * Created by lbavsc on 20-9-11
 */
public class NewsFragment extends BaseFragment<FragmentTabBar1Binding, NewsViewModel> {
    public static final String TOKEN_TabBar1Fragment_REFRESH = "token_tabbar1fragment_refresh";

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_tab_bar_1;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initData() {
        final NpeRepository model = viewModel.getmodel();
        // 使用 TabLayout 和 ViewPager 相关联
        binding.tabs.setupWithViewPager(binding.viewPager);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
        //给ViewPager设置adapter
        binding.setAdapter(new NewsViewPagerBindingAdapter());

        //监听键盘回车事件
        binding.searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    viewModel.onSearchCommand.execute();
                    viewModel.searchText.set("");
                    binding.searchText.clearFocus();
                    return true;
                }
                return false;
            }
        });
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                model.saveAssortIndex(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });
        viewModel.getAssort();
        Messenger.getDefault().register(this, NewsFragment.TOKEN_TabBar1Fragment_REFRESH, JokeEntity.DataBean.class, new BindingConsumer<JokeEntity.DataBean>() {
            @Override
            public Integer call(JokeEntity.DataBean s) {
                Log.e("TAG", s.toString());
                viewModel.addItemData.setValue(s);
                return null;
            }
        });
    }

    @Override
    public NewsViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(NewsViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.onFocusChangeCommand.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean) {
                    binding.searchText.clearFocus();
                }
            }
        });

        final NpeRepository model = viewModel.getmodel();

        viewModel.entityJsonLiveData.observe(this, new Observer<NewsItemJokeItemViewModel>() {
            @Override
            public void onChanged(final NewsItemJokeItemViewModel jokeItemViewModel) {
                MaterialDialogUtils.showBasicDialog(getContext(), "确认删除此条新闻")
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ToastUtils.showLong("取消");
                            }
                        }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        viewModel.items.get(model.getAssortIndex()).observableList1.get(model.getJokeIndex()).deteleJoke();     //服务器端删除
                        viewModel.items.get(model.getAssortIndex()).deleteItem(jokeItemViewModel);                              //本地删除条目并刷新页面

                    }
                }).show();

            }
        });
        viewModel.addItemData.observe(this, new Observer<JokeEntity.DataBean>() {
            @Override
            public void onChanged(JokeEntity.DataBean dataBean) {
                NewsItemJokeItemViewModel jokeItemViewModel = new NewsItemJokeItemViewModel(viewModel, dataBean);
                viewModel.items.get(model.getAssortIndex()).addItem(jokeItemViewModel);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        NpeRepository model = viewModel.getmodel();
        if (model.getUserStatus() && model.getUserType()) {
            viewModel.uploadVisibility.set(View.VISIBLE);
        } else {
            viewModel.uploadVisibility.set(View.GONE);
        }
    }
}
