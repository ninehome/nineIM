package com.watayouxiang.wallet.feature.bill.fragment.mvp;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.PayGetWalletItemsReq;
import com.watayouxiang.httpclient.model.response.PayGetWalletItemsResp;

public class Model extends Contract.Model {
    public Model() {
        super(false);
    }

    @Override
    public void getWalletItems(String mode, String pageNumber, TioCallback<PayGetWalletItemsResp> callback) {
        PayGetWalletItemsReq walletItemsReq = new PayGetWalletItemsReq(mode, pageNumber);
        walletItemsReq.setCancelTag(this);
        walletItemsReq.get(callback);
    }
}
