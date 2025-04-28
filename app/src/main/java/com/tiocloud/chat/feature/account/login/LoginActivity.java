package com.tiocloud.chat.feature.account.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tiocloud.account.data.AccountSP;
import com.tiocloud.account.mvp.login.LoginContract;
import com.tiocloud.account.mvp.login.LoginPresenter;
import com.tiocloud.chat.R;
import com.tiocloud.chat.databinding.TioLoginActivityBinding;
import com.tiocloud.chat.feature.account.pwd.RetrievePwdActivity;
import com.tiocloud.chat.feature.account.register.RegisterActivity;
import com.watayouxiang.androidutils.page.TioActivity;

/**
 * author : TaoWang
 * date : 2019-12-31
 * desc : 登录页面
 */
@Deprecated
public class LoginActivity extends TioActivity implements LoginContract.View {

    private LoginPresenter presenter;
    private TioLoginActivityBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.tio_login_activity);
        hideStatusBar();
        setStatusBarLightMode(false);
        addMarginTopEqualStatusBarHeight(binding.tvPageDesc);
        presenter = new LoginPresenter(this);
        initViews();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        binding.unbind();
    }

    private void initViews() {
        // 设置显示账号
        String account = AccountSP.getLoginName();
        if (account != null) {
            binding.editLoginAccount.setText(account);
        }

        // 登录按钮
        binding.buttonLoginButton.setOnClickListener(v -> presenter.pwdLogin(binding.editLoginAccount, binding.editLoginPassword, getActivity()));
        // 注册按钮
        binding.registerLoginTip.setOnClickListener(v -> {
            RegisterActivity.start(LoginActivity.this);
            LoginActivity.this.finish();
        });

        // 忘记密码按钮
        binding.buttonLoginForgetPwd.setOnClickListener(v -> RetrievePwdActivity.start(LoginActivity.this));
    }
}
