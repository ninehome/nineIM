package com.tiocloud.account.mvp.register;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.PhoneRegisterBindEmailReq;
import com.watayouxiang.httpclient.model.request.PhoneRegisterReq;
import com.watayouxiang.httpclient.model.request.SmsBeforeCheckReq;
import com.watayouxiang.httpclient.model.request.SmsSendReq;
import com.watayouxiang.httpclient.model.response.PhoneRegisterBindEmailResp;
import com.watayouxiang.httpclient.model.response.PhoneRegisterResp;

public class RegisterModel extends RegisterContract.Model {
    public RegisterModel() {
        super(false);
    }

    @Override
    public void postPhoneRegister(String phone, String code, String nick, String pwd, String inviteCode, String email, TioCallback<PhoneRegisterResp> callback) {
        new PhoneRegisterReq(phone, nick, pwd, code, inviteCode, email).setCancelTag(this).post(callback);
    }

    @Override
    public void reqSmsBeforeCheck(String biztype, String phone, TioCallback<String> callback) {
        new SmsBeforeCheckReq(biztype, phone).setCancelTag(RegisterModel.this).post(callback);
    }

    @Override
    public void reqSendSms(String biztype, String phone, String captchaVerification, TioCallback<String> callback) {
        new SmsSendReq(biztype, phone, captchaVerification).setCancelTag(RegisterModel.this).post(callback);
    }

    @Override
    public void reqRegisterBindEmail(String code, String phone, String email, String pwd, TioCallback<PhoneRegisterBindEmailResp> callback) {
        new PhoneRegisterBindEmailReq(code, phone, email, pwd).setCancelTag(RegisterModel.this).post(callback);
    }
}
