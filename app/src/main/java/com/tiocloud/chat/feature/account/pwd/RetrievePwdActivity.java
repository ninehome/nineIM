package com.tiocloud.chat.feature.account.pwd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tiocloud.chat.R;
import com.tiocloud.chat.databinding.TioRetrievePwdActivityBinding;
import com.tiocloud.chat.feature.account.login.LoginActivity;
import com.watayouxiang.androidutils.widget.dialog.confirm.TioConfirmDialog;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.progress.SingletonProgressDialog;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.RetrievePwdReq;

/**
 * author : TaoWang
 * date : 2020-01-09
 * desc :
 */
@Deprecated
public class RetrievePwdActivity extends TioActivity {

    private TioRetrievePwdActivityBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, RetrievePwdActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.tio_retrieve_pwd_activity);
        hideStatusBar();
        setStatusBarLightMode(false);
        addMarginTopEqualStatusBarHeight(binding.ivBackBtn);
        initViews();
    }

    private void initViews() {
        binding.ivBackBtn.setOnClickListener(view -> getActivity().finish());
        binding.buttonFind.setOnClickListener(v -> findPwd());
    }

    private void findPwd() {
        final RetrievePwdReq req = getRetrievePwdReq();
        if (req == null) return;

        SingletonProgressDialog.show_unCancel(this, getString(R.string.loading));

        TioHttpClient.post(req, new TioCallback<Void>() {
            @Override
            public void onTioSuccess(Void aVoid) {
                String msg = getTioMsg();
                onRetrievePwdSuccess(msg, req);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                SingletonProgressDialog.dismiss();
            }
        });
    }

    private RetrievePwdReq getRetrievePwdReq() {
        String account = binding.editAccount.getSubmitText();
        if (account == null) {
            TioToast.showShort(getString(R.string.account_null_tip));
            return null;
        }
        RetrievePwdReq retrievePwdReq = new RetrievePwdReq(account);
        retrievePwdReq.setCancelTag(this);
        return retrievePwdReq;
    }

    private void onRetrievePwdSuccess(String msg, RetrievePwdReq req) {
        final Activity activity = RetrievePwdActivity.this;
        // 弹出确认弹窗
        // 跳转登录页面
        // 关闭本页面
        msg = msg != null ? msg : getString(R.string.retrieve_pwd_success);
        new TioConfirmDialog(msg, (view, dialog) -> {
            LoginActivity.start(activity);
            activity.finish();
            dialog.dismiss();
        }).show_unCancel(activity);
    }
}
