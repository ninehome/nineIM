package com.tiocloud.account.mvp.register;

import android.content.Context;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.PhoneRegisterBindEmailResp;
import com.watayouxiang.httpclient.model.response.PhoneRegisterResp;

public interface RegisterContract {
    interface View extends BaseView {
        void onPhoneRegisterSuccess(String uid, String pwd);

        void onSendSmsSuccess();

        void onCodeTimerRunning(int second);

        void onCodeTimerStop();

        void onRegisterBindEmailSuccess();
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void postPhoneRegister(String phone, String code, String nick, String pwd, String inviteCode, String email, TioCallback<PhoneRegisterResp> callback);

        public abstract void reqSmsBeforeCheck(String biztype, String phone, TioCallback<String> callback);

        public abstract void reqSendSms(String biztype, String phone, String captchaVerification, TioCallback<String> callback);

        public abstract void reqRegisterBindEmail(String code, String phone, String email, String pwd, TioCallback<PhoneRegisterBindEmailResp> callback);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void phoneRegisterBindEmail(Context context,String code, String phone, String email, String pwd, boolean isAgreeProtocol);

        public abstract void phoneRegister(Context context,String phone, String code, String nick, String pwd, String inviteCode,String email,  boolean isAgreeProtocol);

        public abstract void reqSendSms(Context context, String phone);

        public abstract void startCodeTimer(int second);
    }
}
