package com.experiment.npe.ui.jokedetails;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.experiment.npe.R;
import com.experiment.npe.app.AppViewModelFactory;
import com.experiment.npe.databinding.ActivityJokeDetailsBinding;
import com.experiment.npe.ui.jokedetails.dialog.CommentDialogFragment;
import com.experiment.npe.ui.jokedetails.dialog.DialogFragmentDataCallback;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.tatarka.bindingcollectionadapter2.BR;

public class JokeDetailsActivity extends BaseActivity<ActivityJokeDetailsBinding, JokeDetailsViewModel> implements DialogFragmentDataCallback {
    private String jokeId;
    public static final String TOKEN_LOGINVIEWMODEL_REFRESH = "token_jokedetailsactivity_refresh";
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

    }

    @Override
    public void initViewObservable() {

        //接收输入的字符串
        Messenger.getDefault().register(this, JokeDetailsActivity.TOKEN_LOGINVIEWMODEL_REFRESH, String.class, new BindingConsumer<String>() {
            @Override
            public Integer call(String s) {
                viewModel.remarkLoad(s);
                return null;
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