package com.experiment.npe.ui.setting.edit;

import android.app.Application;

import androidx.annotation.NonNull;

import com.experiment.npe.data.NpeRepository;

import java.security.PublicKey;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by lbavsc on 20-9-17
 */
public class EditInformationViewModel extends BaseViewModel<NpeRepository> {
    public EditInformationViewModel(@NonNull Application application, NpeRepository model) {
        super(application, model);
    }

    public BindingCommand backOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onBackPressed();
        }
    });
    public BindingCommand rightIconOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("等待开发");
        }
    });
}
