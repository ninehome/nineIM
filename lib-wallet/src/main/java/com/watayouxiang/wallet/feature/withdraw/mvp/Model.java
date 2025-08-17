package com.watayouxiang.wallet.feature.withdraw.mvp;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.PayGetWalletInfoReq;
import com.watayouxiang.httpclient.model.request.PayWithholdReq;
import com.watayouxiang.httpclient.model.response.PayGetWalletInfoResp;
import com.watayouxiang.httpclient.model.response.PayOpenFlagResp;
import com.watayouxiang.httpclient.model.response.PayWithholdResp;

public class Model extends Contract.Model {

    private PayOpenFlagResp payOpenFlagResp;

    public Model() {
        super(false);
    }

    @Override
    public void getWalletInfo(DataProxy<PayGetWalletInfoResp> proxy) {
        PayGetWalletInfoReq payGetWalletInfoReq = new PayGetWalletInfoReq();
        payGetWalletInfoReq.setCancelTag(this);
        payGetWalletInfoReq.get(new TioCallback<PayGetWalletInfoResp>() {
            @Override
            public void onTioSuccess(PayGetWalletInfoResp payGetWalletInfoResp) {
                proxy.onSuccess(payGetWalletInfoResp);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }
        });
    }

    @Override
    public void postWithhold(DataProxy<PayWithholdResp> proxy, String amount) {
        PayWithholdReq payWithholdReq = new PayWithholdReq(amount);
        payWithholdReq.setCancelTag(this);
        payWithholdReq.post(new TioCallback<PayWithholdResp>() {
            @Override
            public void onTioSuccess(PayWithholdResp payWithholdResp) {
                proxy.onSuccess(payWithholdResp);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }
        });
    }
}
