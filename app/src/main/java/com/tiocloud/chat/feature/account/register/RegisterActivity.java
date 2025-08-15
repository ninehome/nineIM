package com.tiocloud.chat.feature.account.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.lzy.okgo.model.Response;
import com.tiocloud.chat.R;
import com.tiocloud.chat.databinding.TioRegisterActivityBinding;
import com.tiocloud.chat.feature.account.login.LoginActivity;
import com.watayouxiang.androidutils.widget.dialog.confirm.TioConfirmDialog;
import com.watayouxiang.androidutils.feature.TioBrowserActivity;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.progress.SingletonProgressDialog;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.TioWebUrl;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.RegisterReq;
import com.watayouxiang.httpclient.model.response.RegisterResp;
import com.watayouxiang.httpclient.prefernces.HttpCache;

/**
 * author : TaoWang
 * date : 2020-01-08
 * desc : 注册页
 */
@Deprecated
public class RegisterActivity extends TioActivity {

    private TioRegisterActivityBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.tio_register_activity);
        hideStatusBar();
        setStatusBarLightMode(false);
        addMarginTopEqualStatusBarHeight(binding.tvPageDesc);
        initViews();
    }

    private void initViews() {
        binding.buttonRegisterButton.setOnClickListener(v -> register(v.getContext()));
        binding.buttonRegisterLogin.setOnClickListener(v -> {
            LoginActivity.start(RegisterActivity.this);
            RegisterActivity.this.finish();
        });
        binding.protocolXieyi.setOnClickListener(v -> TioBrowserActivity.start(RegisterActivity.this, "https://xxsj.wangliantong.com"));
        binding.protocolZhengce.setOnClickListener(v -> TioBrowserActivity.start(RegisterActivity.this, "https://yszc.wangliantong.com"));
        binding.protocolTvAgree.setOnClickListener(v -> {
            boolean checked = binding.protocolCheckBox.isChecked();
            binding.protocolCheckBox.setChecked(!checked);
        });
    }

    private void register(final Context context) {
        final RegisterReq registerReq = getRegisterReq();
        if (registerReq == null) return;

        SingletonProgressDialog.show_unCancel(this, getString(R.string.registering));

        TioHttpClient.post(this, registerReq, new TaoCallback<BaseResp<RegisterResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<RegisterResp>> response) {
                BaseResp<RegisterResp> body = response.body();
                String msg = body.getMsg();
                if (body.isOk()) {
                    onRegisterSuccess(context, registerReq, msg == null ? getString(R.string.register_success) : msg);
                } else {
                    TioToast.show(RegisterActivity.this, msg);
                }
            }

            @Override
            public void onError(Response<BaseResp<RegisterResp>> response) {
                super.onError(response);
                TioToast.showShort(getString(R.string.register_fail));
            }

            @Override
            public void onFinish() {
                super.onFinish();
                SingletonProgressDialog.dismiss();
            }
        });
    }

    private void onRegisterSuccess(final Context context, @NonNull RegisterReq registerReq, @NonNull String msg) {
        // 弹出确认弹窗，跳转登录页
        new TioConfirmDialog(msg, (view, dialog) -> {
            LoginActivity.start(context);
            RegisterActivity.this.finish();
            dialog.dismiss();
        }).show_unCancel(context);
    }

    private RegisterReq getRegisterReq() {
        String account = binding.editRegisterAccount.getSubmitText();
        if (account == null) {
            TioToast.showShort(getString(R.string.account_null_tip));
            return null;
        }
        String nick = binding.editRegisterNick.getSubmitText();
        if (nick == null) {
            TioToast.showShort(getString(R.string.nick_null_tip));
            return null;
        }
        String pwd = binding.editRegisterPassword.getSubmitText();
        if (pwd == null) {
            TioToast.showShort(getString(R.string.pwd_null_tip));
            return null;
        }
        boolean checked = binding.protocolCheckBox.isChecked();
        if (!checked) {
            TioToast.showShort("请同意服务协议和隐私政策");
            return null;
        }
        return new RegisterReq(account, nick, pwd);
    }

    //点击空白处收起键盘
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (this.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }


}
