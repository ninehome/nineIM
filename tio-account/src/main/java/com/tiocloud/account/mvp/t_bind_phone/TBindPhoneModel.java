package com.tiocloud.account.mvp.t_bind_phone;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.SmsBeforeCheckReq;
import com.watayouxiang.httpclient.model.request.SmsSendReq;
import com.watayouxiang.httpclient.model.request.TBindPhoneReq;
import com.watayouxiang.httpclient.model.request.UserCurrReq;
import com.watayouxiang.httpclient.model.response.TBindPhoneResp;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

public class TBindPhoneModel extends TBindPhoneContract.Model {
    public TBindPhoneModel() {
        super(false);
    }

    @Override
    public void reqSmsBeforeCheck(String biztype, String phone, TioCallback<String> callback) {
        new SmsBeforeCheckReq(biztype, phone).setCancelTag(TBindPhoneModel.this).post(callback);
    }

    @Override
    public void reqSendSms(String biztype, String phone, String captchaVerification, TioCallback<String> callback) {
        new SmsSendReq(biztype, phone, captchaVerification).setCancelTag(TBindPhoneModel.this).post(callback);
    }

    @Override
    public void reqTBindPhone(String code, String phone, TioCallback<TBindPhoneResp> callback) {
        new TBindPhoneReq(code, phone).setCancelTag(this).post(callback);
    }

    @Override
    public void reqUserCurr(TioCallback<UserCurrResp> callback) {
        new UserCurrReq().setCancelTag(this).get(callback);
    }
}
