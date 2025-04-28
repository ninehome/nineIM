package com.tiocloud.account.mvp.login;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.callback.TioCallbackImpl;
import com.watayouxiang.httpclient.model.request.LoginReq;
import com.watayouxiang.httpclient.model.request.SmsBeforeCheckReq;
import com.watayouxiang.httpclient.model.request.SmsSendReq;
import com.watayouxiang.httpclient.model.request.UserCurrReq;
import com.watayouxiang.httpclient.model.response.LoginResp;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

/**
 * author : TaoWang
 * date : 2020-02-12
 * desc :
 */
public class LoginModel extends LoginContract.Model {

    @Override
    public void reqSendSms(String biztype, String phone, String captchaVerification, TioCallback<String> callback) {
        new SmsSendReq(biztype, phone, captchaVerification).setCancelTag(this).post(callback);
    }

    @Override
    public void reqSmsBeforeCheck(String biztype, String phone, TioCallback<String> callback) {
        new SmsBeforeCheckReq(biztype, phone).setCancelTag(this).post(callback);
    }

    // ====================================================================================
    // 登录
    // ====================================================================================

    @Override
    public void reqCodeLogin(String account, String authcode, DataProxy<UserCurrResp> proxy) {
        LoginReq.getCodeInstance(authcode, account).setCancelTag(this).post(new TioCallbackImpl<LoginResp>() {
            @Override
            public void onTioSuccess(LoginResp loginResp) {
                loginStep2(proxy);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
                proxy.onFinish();
            }
        });
    }

    @Override
    public void reqPwdLogin(String account, String pwd, final DataProxy<UserCurrResp> proxy) {
        LoginReq.getPwdInstance(pwd, account).setCancelTag(this).post(new TioCallbackImpl<LoginResp>() {
            @Override
            public void onTioSuccess(LoginResp loginResp) {
                loginStep2(proxy);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
                proxy.onFinish();
            }
        });
    }

    private void loginStep2(final DataProxy<UserCurrResp> proxy) {
        // 获取用户信息
        new UserCurrReq().setCancelTag(this).get(new TioCallbackImpl<UserCurrResp>() {
            @Override
            public void onTioSuccess(UserCurrResp userCurrResp) {
                proxy.onSuccess(userCurrResp);
                proxy.onFinish();
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
                proxy.onFinish();
            }
        });
    }
}
