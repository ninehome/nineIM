package com.watayouxiang.wallet.feature.account.mvp;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.PayGetWalletInfoReq;
import com.watayouxiang.httpclient.model.response.PayGetWalletInfoResp;

public class Model extends Contract.Model {

    public Model() {
        super(false);
    }

    @Override
    public void getWalletInfo(DataProxy<PayGetWalletInfoResp> proxy) {
        PayGetWalletInfoReq payGetWalletInfoReq = new PayGetWalletInfoReq();
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
}
