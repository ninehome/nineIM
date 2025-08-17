package com.tiocloud.account.mvp.login;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

/**
 * author : TaoWang
 * date : 2020-02-12
 * desc :
 */
public interface LoginContract {

    abstract class Model extends BaseModel {
        public abstract void reqSendSms(String biztype, String phone, String captchaVerification, TioCallback<String> callback);

        public abstract void reqSmsBeforeCheck(String biztype, String phone, TioCallback<String> callback);

        public abstract void reqCodeLogin(String account, String authcode, BaseModel.DataProxy<UserCurrResp> proxy);

        public abstract void reqPwdLogin(String account, String pwd, BaseModel.DataProxy<UserCurrResp> proxy);
    }

    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(View view) {
            super(new LoginModel(), view);
        }

        public abstract void pwdLogin(TextView accountView, TextView pwdView, Activity activity);

        public abstract void pwdLogin(String account, String pwd, Activity activity);

        public abstract void codeLogin(String account, String code, Activity activity);

        public abstract void sendSms(Context context, String phone, OnSendSmsListener listener);

        public abstract void startCodeTimer(OnTimerListener listener);
    }

    interface OnSendSmsListener {
        void onSendSmsSuccess(boolean isRegiester);
    }

    interface OnTimerListener {
        void OnTimerRunning(int second);

        void OnTimerStop();
    }

}
