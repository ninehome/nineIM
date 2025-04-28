package com.watayouxiang.qrcode.feature.qrcode_my.mvp;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.UserCurrReq;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

public class Model extends Contract.Model {
    public Model() {
        super(false);
    }

    @Override
    public void reqUserCurr(TioCallback<UserCurrResp> callback) {
        new UserCurrReq().setCancelTag(this).get(callback);
    }
}
