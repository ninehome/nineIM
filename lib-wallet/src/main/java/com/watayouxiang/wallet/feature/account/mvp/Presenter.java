package com.watayouxiang.wallet.feature.account.mvp;

import com.blankj.utilcode.util.ToastUtils;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.httpclient.model.response.PayGetWalletInfoResp;

public class Presenter extends Contract.Presenter {
    public Presenter(Contract.View view) {
        super(new Model(), view, false);
    }

    @Override
    public void init() {
        getWalletInfo();
        getView().resetUI();
    }

    @Override
    public void getWalletInfo() {
        getModel().getWalletInfo(new BaseModel.DataProxy<PayGetWalletInfoResp>() {
            @Override
            public void onSuccess(PayGetWalletInfoResp resp) {
                super.onSuccess(resp);
                getView().onWalletInfoResp(resp);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                ToastUtils.showShort(msg);
            }
        });
    }
}
