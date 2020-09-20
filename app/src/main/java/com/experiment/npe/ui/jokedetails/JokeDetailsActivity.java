package com.experiment.npe.ui.jokedetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.experiment.npe.R;
import com.experiment.npe.app.AppViewModelFactory;
import com.experiment.npe.data.NpeRepository;
import com.experiment.npe.databinding.ActivityJokeDetailsBinding;
import com.experiment.npe.entity.JokeDetailsEntity;
import com.experiment.npe.ui.jokedetails.dialog.CommentDialogFragment;
import com.experiment.npe.ui.jokedetails.dialog.DialogFragmentDataCallback;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BR;

public class JokeDetailsActivity extends BaseActivity<ActivityJokeDetailsBinding, JokeDetailsViewModel> implements DialogFragmentDataCallback {
    private String jokeId;
    public static final String TOKEN_JokeDetailsActivity_REFRESH = "token_jokedetailsactivity_refresh";
    public ObservableList<JokeDetailsEntity.DataBean> entity = new ObservableArrayList<>();
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_joke_details;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public JokeDetailsViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(JokeDetailsViewModel.class);
    }

    @Override
    public void initData() {
        viewModel.JokeDetails(jokeId);
        binding.tvCommentFakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentDialogFragment commentDialogFragment = new CommentDialogFragment();
                commentDialogFragment.show(getFragmentManager(), "CommentDialogFragment");
            }
        });

        //接收输入的字符串
        Messenger.getDefault().register(this, JokeDetailsActivity.TOKEN_JokeDetailsActivity_REFRESH, String.class, new BindingConsumer<String>() {
            @Override
            public Integer call(String s) {
                NpeRepository model = viewModel.getmodel();
                if (model.getUserStatus()) {
                    viewModel.remarkLoad(s);
                } else {
                    ToastUtils.showShort("您当前未登录");
                }

                return null;
            }
        });

    }

    @Override
    public void initViewObservable() {

        viewModel.deleteItemLiveData.observe(this, new Observer<JokeDetailsItemViewModel>() {

            @Override
            public void onChanged(@Nullable final JokeDetailsItemViewModel jokeDetailsItemViewModel) {
                final int index = viewModel.getItemPosition(jokeDetailsItemViewModel);
                //删除选择对话框
                MaterialDialogUtils.showBasicDialog(JokeDetailsActivity.this, "确认删除此条评论")
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ToastUtils.showLong("取消");
                            }
                        }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        viewModel.deleteItem(jokeDetailsItemViewModel);
                        viewModel.deleteRemark(index);

                    }
                }).show();
            }
        });
        viewModel.addItemLiveData.observe(this, new Observer<JokeDetailsItemViewModel>() {
            @Override
            public void onChanged(JokeDetailsItemViewModel jokeDetailsItemViewModel) {
                viewModel.addItem(jokeDetailsItemViewModel);
            }
        });

    }

    @Override
    public void initParam() {
        //获取列表传入的实体
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            jokeId = bundle.getString("jokeId");
        }
    }

    @Override
    public String getCommentText() {
        return binding.tvCommentFakeButton.getText().toString();
    }

    @Override
    public void setCommentText(String commentTextTemp) {
        binding.tvCommentFakeButton.setText(commentTextTemp);
    }

}