package com.tiocloud.account.mvp.retrieve_pwd;

import android.content.Context;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.ResetPwdBeforeResp;
import com.watayouxiang.httpclient.model.response.ResetPwdResp;

public interface RetrievePwdContract {
    interface View extends BaseView {
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void reqSmsBeforeCheck(String biztype, String phone, TioCallback<String> callback);

        public abstract void reqSendSms(String biztype, String phone, String captchaVerification, TioCallback<String> callback);

        public abstract void reqResetPwdBefore(String code, String phone, TioCallback<ResetPwdBeforeResp> callback);

        public abstract void reqResetPwd(String code, String pwd, String phone, String email, TioCallback<ResetPwdResp> callback);

        public abstract void reqSmsCheck(String biztype, String mobile, String code, TioCallback<String> callback);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void reqSendSms(Context context, String phone, OnSendSmsListener listener);

        public abstract void reqSmsBeforeCheck(Context context,String phone, OnSmsBeforeCheckListener listener);

        public abstract void startCodeTimer(int initSecond, OnCodeTimerListener listener);

        public abstract void reqResetPwd(String smsCode, String phone, String phonePwd, OnResetPwdListener listener);

        public abstract void reqSmsCheck(String smsCode, String phone, OnSmsCheckListener listener);
    }

    interface OnSmsCheckListener {
        void onSmsCheckSuccess();
    }

    interface OnResetPwdListener {
        void onResetPwdSuccess();
    }

    interface OnSmsBeforeCheckListener {
        void onSmsBeforeCheckSuccess();
    }

    interface OnSendSmsListener {
        void onSendSmsSuccess();
    }

    interface OnCodeTimerListener {
        void onCodeTimerRunning(int second);

        void onCodeTimerStop();
    }
}
