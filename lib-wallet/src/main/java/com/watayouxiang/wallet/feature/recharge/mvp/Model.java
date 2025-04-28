package com.watayouxiang.wallet.feature.recharge.mvp;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.PayRechargeReq;
import com.watayouxiang.httpclient.model.response.PayRechargeResp;

public class Model extends Contract.Model {

    public Model() {
        super(false);
    }

    @Override
    public void postRecharge(String amount, DataProxy<PayRechargeResp> proxy) {
        PayRechargeReq payRechargeReq = new PayRechargeReq(amount);
        payRechargeReq.setCancelTag(this);
        payRechargeReq.post(new TioCallback<PayRechargeResp>() {
            @Override
            public void onTioSuccess(PayRechargeResp payRechargeResp) {
                proxy.onSuccess(payRechargeResp);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }
        });
    }

}
