package com.tiocloud.account.mvp.bind_phone;

import android.content.Context;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.BindPhoneResp;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

public interface BindPhoneContract {
    interface View extends BaseView {
        void onSendSmsSuccess();

        void onCodeTimerRunning(int second);

        void onCodeTimerStop();

        void onBindPhoneSuccess();
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void reqSmsBeforeCheck(String biztype, String phone, TioCallback<String> callback);

        public abstract void reqSendSms(String biztype, String phone, String captchaVerification, TioCallback<String> callback);

        public abstract void reqBindPhone(String code, String phone, String email, String pwd, TioCallback<BindPhoneResp> callback);

        public abstract void reqUserCurr(TioCallback<UserCurrResp> callback);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void bindPhone(Context context,String code, String phone, String pwd);

        public abstract void reqSendSms(Context context, String phone);

        public abstract void startCodeTimer(int second);
    }
}
