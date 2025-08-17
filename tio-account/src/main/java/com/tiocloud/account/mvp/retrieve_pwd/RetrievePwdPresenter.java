package com.tiocloud.account.mvp.retrieve_pwd;

import android.content.Context;
import android.text.TextUtils;

import com.tiocloud.account.R;
import com.tiocloud.account.data.AccountSP;
import com.tiocloud.verification.widget.TioBlockPuzzleDialog;
import com.watayouxiang.androidutils.tool.WtTimer;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.yanxun.ConstantUtils;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.ResetPwdBeforeResp;
import com.watayouxiang.httpclient.model.response.ResetPwdResp;

public class RetrievePwdPresenter extends RetrievePwdContract.Presenter {

    private WtTimer wtTimer;
    private RetrievePwdContract.OnSendSmsListener onSendSmsListener;

    public RetrievePwdPresenter(RetrievePwdContract.View view) {
        super(new RetrievePwdModel(), view, false);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (wtTimer != null) {
            wtTimer.stop();
        }
    }

    @Override
    public void reqSmsBeforeCheck(Context context,String phone, RetrievePwdContract.OnSmsBeforeCheckListener listener) {
        if (TextUtils.isEmpty(phone)) {
            TioToast.showShort(ConstantUtils.checkCode?
                    context.getString(R.string.shoujihaonotempty):context.getString(R.string.zhanghaonotempty));
            return;
        }
        if (ConstantUtils.checkEmail){
            listener.onSmsBeforeCheckSuccess();
            return;
        }
        getModel().reqSmsBeforeCheck("6", phone, new TioCallback<String>() {
            @Override
            public void onTioSuccess(String smsBeforeCheckResp) {
                listener.onSmsBeforeCheckSuccess();
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    @Override
    public void reqSendSms(Context context, String phone, RetrievePwdContract.OnSendSmsListener listener) {
        onSendSmsListener = listener;
        if (TextUtils.isEmpty(phone)) {
            TioToast.showShort(context.getString(R.string.shoujihaonotempty));
            return;
        }
        smsBeforeCheck(phone, context);
    }

    // 验证手机号是否可用
    private void smsBeforeCheck(String phone, Context context) {
        if (ConstantUtils.checkEmail){
            showBlockPuzzleDialog(context, phone);
            return;
        }
        getModel().reqSmsBeforeCheck("6", phone, new TioCallback<String>() {
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
        getModel().reqSendSms("6", phone, captchaVerification, new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                if (onSendSmsListener != null) {
                    onSendSmsListener.onSendSmsSuccess();
                }
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    @Override
    public void startCodeTimer(int initTime, RetrievePwdContract.OnCodeTimerListener listener) {
        if (wtTimer == null) {
            wtTimer = new WtTimer();
        }
        wtTimer.start((count, timer) -> {
            if (count < initTime) {
                if (listener != null)
                    listener.onCodeTimerRunning(initTime - count);
            } else {
                timer.stop();
                if (listener != null)
                    listener.onCodeTimerStop();
            }
        }, true, 0, 1000);
    }

    @Override
    public void reqResetPwd(String smsCode, String phone, String phonePwd, RetrievePwdContract.OnResetPwdListener listener) {
        if (ConstantUtils.checkEmail){
            getModel().reqResetPwd(smsCode, phonePwd, phone, null, new TioCallback<ResetPwdResp>() {
                @Override
                public void onTioSuccess(ResetPwdResp resetPwdResp) {
                    // 存储登录名
                    AccountSP.putLoginName(phone);
                    // 回调
                    if (listener != null)
                        listener.onResetPwdSuccess();
                }

                @Override
                public void onTioError(String msg) {
                    TioToast.showShort(msg);
                }
            });
            return;
        }
        getModel().reqResetPwdBefore(smsCode, phone, new TioCallback<ResetPwdBeforeResp>() {
            @Override
            public void onTioSuccess(ResetPwdBeforeResp resetPwdBeforeResp) {

                String email = resetPwdBeforeResp.getEmail();
                if (TextUtils.isEmpty(email)) email = null;

                getModel().reqResetPwd(smsCode, phonePwd, phone, email, new TioCallback<ResetPwdResp>() {
                    @Override
                    public void onTioSuccess(ResetPwdResp resetPwdResp) {
                        // 存储登录名
                        AccountSP.putLoginName(phone);
                        // 回调
                        if (listener != null)
                            listener.onResetPwdSuccess();
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
    public void reqSmsCheck(String smsCode, String phone, RetrievePwdContract.OnSmsCheckListener listener) {
        getModel().reqSmsCheck("6", phone, smsCode, new TioCallback<String>() {
            @Override
            public void onTioSuccess(String resp) {
                if (listener != null) listener.onSmsCheckSuccess();
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }
}
