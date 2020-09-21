package com.experiment.npe.ui.main.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.KeyEvent;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.experiment.npe.BR;
import com.experiment.npe.R;
import com.experiment.npe.databinding.ActivityMainBinding;
import com.experiment.npe.ui.main.fragment.NewsFragment;
import com.experiment.npe.ui.main.fragment.PersonCalenterFragment;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * 主页
 * Created by lbavsc on 20-9-11
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> {
    private static List<Fragment> mFragments;       //Fragment列表

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        //初始化Fragment
        initFragment();
        //初始化底部Button
        initBottomTab(0);

    }


    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new NewsFragment());
        mFragments.add(new PersonCalenterFragment());
        //默认选中第一个
        commitAllowingStateLoss(0);
    }

    private void initBottomTab(final int sub) {
        final NavigationController navigationController = binding.pagerBottomTab.material()
                .addItem(R.mipmap.news, "新闻")
                .addItem(R.mipmap.wode_select, "我的")
                .setDefaultColor(ContextCompat.getColor(this, R.color.textColorVice))
                .build();
        //底部按钮的点击事件监听
        if (sub == 1) {
            navigationController.setSelect(1);
        }
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                mFragments.get(0).onResume();
                commitAllowingStateLoss(index);
            }


            @Override
            public void onRepeat(int index) {
            }

        });

    }

    private void commitAllowingStateLoss(int position) {
        hideAllFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(position + "");
        if (currentFragment != null) {
            transaction.show(currentFragment);
        } else {
            currentFragment = mFragments.get(position);
            transaction.add(R.id.frameLayout, currentFragment, position + "");
        }
        transaction.commitAllowingStateLoss();
    }

    //隐藏所有Fragment
    private void hideAllFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(i + "");
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 创建退出对话框
            MaterialDialogUtils.showBasicDialog(MainActivity.this, "确定要退出吗")
                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            ToastUtils.showLong("取消");
                        }
                    }).onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    AppManager.getAppManager().AppExit();
                }
            }).show();
        }

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            int num = bundle.getInt("num");
            if (num == 1) {
                commitAllowingStateLoss(1);
                initBottomTab(1);
            }

        }

    }
}
