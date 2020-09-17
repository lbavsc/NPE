package com.experiment.npe.ui.main.fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.experiment.npe.ui.main.activity.MainActivity;
import com.google.android.material.tabs.TabLayout;

import android.os.IBinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.app.AppViewModelFactory;
import com.experiment.npe.databinding.FragmentTabBar1Binding;
import com.experiment.npe.ui.main.adapter.ViewPagerBindingAdapter;
import com.experiment.npe.ui.main.viewmodel.TabBar1ViewModel;
import com.google.android.material.textfield.TextInputEditText;


import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

/**
 * Created by lbavsc on 20-9-11
 */
public class TabBar1Fragment extends BaseFragment<FragmentTabBar1Binding, TabBar1ViewModel> {
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
        // 使用 TabLayout 和 ViewPager 相关联
        binding.tabs.setupWithViewPager(binding.viewPager);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
        //给ViewPager设置adapter
        binding.setAdapter(new ViewPagerBindingAdapter());

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
        viewModel.getAssort();
    }

    @Override
    public TabBar1ViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(TabBar1ViewModel.class);
    }

    @Override
    public void initViewObservable() {
//        viewModel.itemClickEvent.observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String text) {
//                binding.searchText.clearFocus();
//                ToastUtils.showShort("position：" + text);
//            }
//        });

        viewModel.onFocusChangeCommand.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean) {
                    binding.searchText.clearFocus();
                }
            }
        });
    }


}
