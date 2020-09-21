package com.experiment.npe.ui.jokedetails;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.entity.JokeDetailsEntity;
import com.experiment.npe.utils.RetrofitClient;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;


/**
 * 评论列表ViewModel
 */
public class JokeDetailsItemViewModel extends ItemViewModel<JokeDetailsViewModel> {
    NpeRepository model;
    public ObservableField<JokeDetailsEntity.DataBean.RemarksBean> entity = new ObservableField<>();
    public ObservableField<String> img = new ObservableField<>("");
    public ObservableField<String> text = new ObservableField<>("");

    public JokeDetailsItemViewModel(@NonNull JokeDetailsViewModel viewModel, JokeDetailsEntity.DataBean.RemarksBean entity) {
        super(viewModel);
        this.entity.set(entity);
        model = viewModel.getmodel();
        img.set(RetrofitClient.baseUrl + entity.getUser().getIcon());
        text.set(entity.getUser().getName());
    }

    /**
     * 获取下标
     *
     * @return 评论下标
     */
    public int getPosition() {
        return viewModel.getItemPosition(this);
    }

    /**
     * 条目按钮长按事件(删除)
     */
    public BindingCommand itemLongClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getPosition();
            if (!model.getUserStatus()) {       //判断用户是否已经登录
                ToastUtils.showShort("您当前未登录");
                return;
            }
            if (!model.getUserId().equals(entity.get().getUserId()) && !model.getUserType()) {          //判断用户是否拥有权限
                ToastUtils.showShort("您无删除此条评论的权限");
                return;
            }
            if (model.getUserId().equals(entity.get().getUserId()) || model.getUserType()) {
                viewModel.deleteItemLiveData.setValue(JokeDetailsItemViewModel.this);       //启动删除监听
            }

        }
    });

}
