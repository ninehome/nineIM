package com.tiocloud.account.mvp.register;

import android.content.Context;
import android.text.TextUtils;

import com.tiocloud.account.R;
import com.tiocloud.account.TioAccount;
import com.tiocloud.account.data.AccountSP;
import com.tiocloud.verification.widget.TioBlockPuzzleDialog;
import com.watayouxiang.androidutils.tool.WtTimer;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.yanxun.ConstantUtils;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.PhoneRegisterBindEmailResp;
import com.watayouxiang.httpclient.model.response.PhoneRegisterResp;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class RegisterPresenter extends RegisterContract.Presenter {

    private WtTimer wtTimer;

    public RegisterPresenter(RegisterContract.View view) {
        super(new RegisterModel(), view, false);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (wtTimer != null) {
            wtTimer.stop();
        }
    }

    @Override
    public void phoneRegisterBindEmail(Context context,String code, String phone, String email, String pwd, boolean isAgreeProtocol) {
        if (TextUtils.isEmpty(phone)) {
            TioToast.showShort(context.getString(R.string.shoujihaonotempty));
            return;
        }
        if (TextUtils.isEmpty(code)) {
            TioToast.showShort(context.getString(R.string.yanzhengmanotempty));
            return;
        }
        if (TextUtils.isEmpty(email)) {
            TioToast.showShort(context.getString(R.string.mailnotempty));
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            TioToast.showShort(context.getString(R.string.mailaccountnotempty));
            return;
        }
        if (!isAgreeProtocol) {
            TioToast.showShort(context.getString(R.string.qingxiangou));
            return;
        }
        getModel().reqRegisterBindEmail(code, phone, email, pwd, new TioCallback<PhoneRegisterBindEmailResp>() {
            @Override
            public void onTioSuccess(PhoneRegisterBindEmailResp phoneRegisterBindEmailResp) {
                // 存储登录名
                AccountSP.putLoginName(phone);
                // 回调
                getView().onRegisterBindEmailSuccess();
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    @Override
    public void phoneRegister(Context context,String phone, String code, String nick, String pwd, String inviteCode, String email, boolean isAgreeProtocol) {
        if (TextUtils.isEmpty(phone)) {
            TioToast.showShort(context.getString(R.string.shoujihaonotempty));
            return;
        }
        if ((ConstantUtils.checkCode||ConstantUtils.checkEmail)&&TextUtils.isEmpty(code)) {
            TioToast.showShort(context.getString(R.string.yanzhengmanotempty));
            return;
        }
        if (!ConstantUtils.checkCode && phone.length() < 5){
            TioToast.showShort(context.getString(R.string.zhanghaobudiyu));
            return;
        }
        if (TextUtils.isEmpty(nick)) {
            TioToast.showShort(context.getString(R.string.nicknotempty));
            return;
        }
        try {
            int limit = TioAccount.isOA ? 4 : 1;
            if (nick.trim().getBytes("utf-8").length < limit) {
                TioToast.showShort(String.format(Locale.getDefault(),
                        context.getString(R.string.nicknotdiyu),limit));
                return;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(pwd)) {
            TioToast.showShort(context.getString(R.string.mimanotempty));
            return;
        }
        if (!isAgreeProtocol) {
            TioToast.showShort(context.getString(R.string.qingxiangou));
            return;
        }
        if (TioAccount.inviteEnable && TextUtils.isEmpty(inviteCode)){
            TioToast.showShort(context.getString(R.string.yaoqingmanotempty));
            return;
        }

        getModel().postPhoneRegister(phone, code, nick, pwd, inviteCode, email, new TioCallback<PhoneRegisterResp>() {
            @Override
            public void onTioSuccess(PhoneRegisterResp phoneRegisterResp) {
                // 存储登录名
                AccountSP.putLoginName(phone);
                // 回调
                getView().onPhoneRegisterSuccess(String.valueOf(phoneRegisterResp.getId()), pwd);
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
        if (ConstantUtils.checkEmail){
            showBlockPuzzleDialog(context, phone);
            return;
        }
        getModel().reqSmsBeforeCheck("2", phone, new TioCallback<String>() {
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
        getModel().reqSendSms("2", phone, captchaVerification, new TioCallback<String>() {
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
