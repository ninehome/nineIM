package com.tiocloud.account.mvp.bind_phone;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.BindPhoneReq;
import com.watayouxiang.httpclient.model.request.SmsBeforeCheckReq;
import com.watayouxiang.httpclient.model.request.SmsSendReq;
import com.watayouxiang.httpclient.model.request.UserCurrReq;
import com.watayouxiang.httpclient.model.response.BindPhoneResp;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

public class BindPhoneModel extends BindPhoneContract.Model {
    public BindPhoneModel() {
        super(false);
    }

    @Override
    public void reqSmsBeforeCheck(String biztype, String phone, TioCallback<String> callback) {
        new SmsBeforeCheckReq(biztype, phone).setCancelTag(BindPhoneModel.this).post(callback);
    }

    @Override
    public void reqSendSms(String biztype, String phone, String captchaVerification, TioCallback<String> callback) {
        new SmsSendReq(biztype, phone, captchaVerification).setCancelTag(BindPhoneModel.this).post(callback);
    }

    @Override
    public void reqBindPhone(String code, String phone, String email, String pwd, TioCallback<BindPhoneResp> callback) {
        new BindPhoneReq(code, phone, email, pwd).setCancelTag(this).post(callback);
    }

    @Override
    public void reqUserCurr(TioCallback<UserCurrResp> callback) {
        new UserCurrReq().setCancelTag(this).get(callback);
    }
}
