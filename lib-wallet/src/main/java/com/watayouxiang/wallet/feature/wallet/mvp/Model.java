package com.watayouxiang.wallet.feature.wallet.mvp;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.PayGetClientTokenReq;
import com.watayouxiang.httpclient.model.request.PayGetWalletInfoReq;
import com.watayouxiang.httpclient.model.response.PayGetClientTokenResp;
import com.watayouxiang.httpclient.model.response.PayGetWalletInfoResp;

public class Model extends Contract.Model {

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
    public void getGetClientToken(String bizType, DataProxy<PayGetClientTokenResp> proxy) {
        PayGetClientTokenReq payGetClientTokenReq = new PayGetClientTokenReq(bizType);
        payGetClientTokenReq.setCancelTag(this);
        payGetClientTokenReq.get(new TioCallback<PayGetClientTokenResp>() {
            @Override
            public void onTioSuccess(PayGetClientTokenResp payGetClientTokenResp) {
                proxy.onSuccess(payGetClientTokenResp);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }
        });
    }

}
