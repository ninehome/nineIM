package com.tiocloud.account.mvp.bind_phone;

import android.content.Context;
import android.text.TextUtils;

import com.tiocloud.account.R;
import com.tiocloud.verification.widget.TioBlockPuzzleDialog;
import com.watayouxiang.androidutils.tool.WtTimer;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.BindPhoneResp;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

public class BindPhonePresenter extends BindPhoneContract.Presenter {

    private WtTimer wtTimer;

    public BindPhonePresenter(BindPhoneContract.View view) {
        super(new BindPhoneModel(), view, false);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (wtTimer != null) {
            wtTimer.stop();
        }
    }

    @Override
    public void bindPhone(Context context,String code, String phone, String pwd) {
        if (TextUtils.isEmpty(phone)) {
            TioToast.showShort(context.getString(R.string.shoujihaonotempty));
            return;
        }
        if (TextUtils.isEmpty(code)) {
            TioToast.showShort(context.getString(R.string.yanzhengmanotempty));
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            TioToast.showShort(context.getString(R.string.mimanotempty));
            return;
        }
        getModel().reqUserCurr(new TioCallback<UserCurrResp>() {
            @Override
            public void onTioSuccess(UserCurrResp resp) {
                getModel().reqBindPhone(code, phone, resp.email, pwd, new TioCallback<BindPhoneResp>() {
                    @Override
                    public void onTioSuccess(BindPhoneResp bindPhoneResp) {
                        getView().onBindPhoneSuccess();
                    }

                    @Override
                    public void onTioError(String msg) {
                        TioToast.showShort(msg);
                    }
                });
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    @Override
    public void reqSendSms(Context context, String phone) {
        if (TextUtils.isEmpty(phone)) {
            TioToast.showShort(context.getString(R.string.shoujihaonotempty));
            return;
        }
        smsBeforeCheck(phone, context);
    }

    // 验证手机号是否可用
    private void smsBeforeCheck(String phone, Context context) {
        getModel().reqSmsBeforeCheck("1", phone, new TioCallback<String>() {
            @Override
            public void onTioSuccess(String smsBeforeCheckResp) {
                showBlockPuzzleDialog(context, phone);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    // 显示滑块验证弹窗
    private void showBlockPuzzleDialog(Context context, String phone) {
        TioBlockPuzzleDialog blockPuzzleDialog = new TioBlockPuzzleDialog(context);
        blockPuzzleDialog.setOnResultsListener(result -> realSendSms(result, phone));
        blockPuzzleDialog.show();
    }

    // 发送短信验证码
    private void realSendSms(String captchaVerification, String phone) {
        getModel().reqSendSms("1", phone, captchaVerification, new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                getView().onSendSmsSuccess();
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    @Override
    public void startCodeTimer(int initTime) {
        if (wtTimer == null) {
            wtTimer = new WtTimer();
        }
        wtTimer.start((count, timer) -> {
            if (count < initTime) {
                getView().onCodeTimerRunning(initTime - count);
            } else {
                timer.stop();
                getView().onCodeTimerStop();
            }
        }, true, 0, 1000);
    }
}
