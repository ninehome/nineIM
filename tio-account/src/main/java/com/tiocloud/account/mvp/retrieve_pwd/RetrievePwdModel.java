package com.tiocloud.account.mvp.retrieve_pwd;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.ResetPwdBeforeReq;
import com.watayouxiang.httpclient.model.request.ResetPwdReq;
import com.watayouxiang.httpclient.model.request.SmsBeforeCheckReq;
import com.watayouxiang.httpclient.model.request.SmsCheckReq;
import com.watayouxiang.httpclient.model.request.SmsSendReq;
import com.watayouxiang.httpclient.model.response.ResetPwdBeforeResp;
import com.watayouxiang.httpclient.model.response.ResetPwdResp;

public class RetrievePwdModel extends RetrievePwdContract.Model {
    public RetrievePwdModel() {
        super(false);
    }

    @Override
    public void reqSmsBeforeCheck(String biztype, String phone, TioCallback<String> callback) {
        new SmsBeforeCheckReq(biztype, phone).setCancelTag(RetrievePwdModel.this).post(callback);
    }

    @Override
    public void reqSendSms(String biztype, String phone, String captchaVerification, TioCallback<String> callback) {
        new SmsSendReq(biztype, phone, captchaVerification).setCancelTag(RetrievePwdModel.this).post(callback);
    }

    @Override
    public void reqResetPwdBefore(String code, String phone, TioCallback<ResetPwdBeforeResp> callback) {
        new ResetPwdBeforeReq(code, phone).setCancelTag(this).post(callback);
    }

    @Override
    public void reqResetPwd(String code, String pwd, String phone, String email, TioCallback<ResetPwdResp> callback) {
        new ResetPwdReq(code, pwd, phone, email).setCancelTag(this).post(callback);
    }

    @Override
    public void reqSmsCheck(String biztype, String mobile, String code, TioCallback<String> callback) {
        new SmsCheckReq(biztype, mobile, code).setCancelTag(this).post(callback);
    }
}
