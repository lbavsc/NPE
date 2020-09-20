package com.experiment.npe.ui.jokedetails;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeDetailsEntity;
import com.experiment.npe.entity.JokeEntity;
import com.experiment.npe.ui.main.viewmodel.JokeItemViewModel;
import com.experiment.npe.utils.RetrofitClient;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class JokeDetailsItemViewModel extends ItemViewModel<JokeDetailsViewModel> {
    NpeRepository model;
    public ObservableField<JokeDetailsEntity.DataBean.RemarksBean> entity = new ObservableField<>();
    public ObservableField<String> img = new ObservableField<>("");
    public ObservableField<String> text = new ObservableField<>("");
    public JokeDetailsItemViewModel(@NonNull JokeDetailsViewModel viewModel, JokeDetailsEntity.DataBean.RemarksBean entity) {
        super(viewModel);
        this.entity.set(entity);
        model = viewModel.getmodel();
        if (model.getUserIcon().startsWith("img")){
            img.set(RetrofitClient.baseUrl+model.getUserIcon());
        }else {
            img.set(model.getUserIcon());
        }

        text.set(model.getUserName());
    }

    public int getPosition() {
        return viewModel.getItemPosition(this);
    }

    public BindingCommand itemLongClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getPosition();
            if (!model.getUserStatus()) {
                ToastUtils.showShort("您当前未登录");
                return;
            }
            if (!model.getUserId().equals(entity.get().getUserId()) && !model.getUserType()) {
                ToastUtils.showShort("您无删除此条评论的权限");
                return;
            }
            if (model.getUserId().equals(entity.get().getUserId()) || model.getUserType()) {
                viewModel.deleteItemLiveData.setValue(JokeDetailsItemViewModel.this);
            }

        }
    });

}
