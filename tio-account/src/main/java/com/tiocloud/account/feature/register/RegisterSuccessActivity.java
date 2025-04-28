package com.tiocloud.account.feature.register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.jaeger.library.StatusBarUtil;
import com.tiocloud.account.R;
import com.tiocloud.account.TioAccount;
import com.tiocloud.account.mvp.login.LoginContract;
import com.tiocloud.account.mvp.login.LoginPresenter;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.util.ClickUtils;

public class RegisterSuccessActivity extends TioActivity implements LoginContract.View{
    private LoginPresenter presenter;
    String uid;

    public static void start(Context context, String uid, String pwd) {
        Intent starter = new Intent(context, RegisterSuccessActivity.class);
        starter.putExtra("uid", uid);
        starter.putExtra("pwd", pwd);
        context.startActivity(starter);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);

//        StatusBarUtil.setTranslucent(this);

        TextView tvUid = findViewById(R.id.tv_uid);
        tvUid.setText(String.format(getString(R.string.you_app_is), /*getString(R.string.my_name)*/TioAccount.sitename));

        TextView tvTip = findViewById(R.id.tv_tip);
        tvTip.setText(String.format(getString(R.string.please_save_your_account), /*getString(R.string.my_name)*/TioAccount.sitename));

        uid = getIntent().getStringExtra("uid");

        TextView tvMyUid = findViewById(R.id.tv_my_uid);
        tvMyUid.setText(uid);

        presenter = new LoginPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    public void login(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        String pwd = getIntent().getStringExtra("pwd");
        presenter.pwdLogin(uid, pwd, getActivity());
    }
}
