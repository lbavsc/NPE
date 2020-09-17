package com.experiment.npe.ui.setting.edit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.experiment.npe.R;
import com.experiment.npe.app.AppViewModelFactory;
import com.experiment.npe.databinding.ActivityEditInformationBinding;
import com.experiment.npe.databinding.ActivitySettingBinding;
import com.experiment.npe.ui.setting.SettingViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.tatarka.bindingcollectionadapter2.BR;

public class EditInformationActivity extends BaseActivity<ActivityEditInformationBinding, EditInformationViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_edit_information;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public EditInformationViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(EditInformationViewModel.class);
    }
}