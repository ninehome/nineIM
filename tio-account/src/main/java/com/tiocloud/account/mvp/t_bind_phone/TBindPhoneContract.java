package com.tiocloud.account.mvp.t_bind_phone;

import android.app.Activity;
import android.content.Context;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.TBindPhoneResp;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

public interface TBindPhoneContract {
    interface View extends BaseView {
        void onSendSmsSuccess();

        void onCodeTimerRunning(int second);

        void onCodeTimerStop();

        void onTBindPhoneSuccess();
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void reqSmsBeforeCheck(String biztype, String phone, TioCallback<String> callback);

        public abstract void reqSendSms(String biztype, String phone, String captchaVerification, TioCallback<String> callback);

        public abstract void reqTBindPhone(String code, String phone, TioCallback<TBindPhoneResp> callback);

        public abstract void reqUserCurr(TioCallback<UserCurrResp> callback);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void bindPhone(Context context,String code, String phone);

        public abstract void reqSendSms(Activity context, String phone);

        public abstract void startCodeTimer(int second);

        public abstract void showBackConfirmDialog(Activity activity);
    }
}
