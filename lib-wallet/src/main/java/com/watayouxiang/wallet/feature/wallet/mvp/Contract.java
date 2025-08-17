package com.watayouxiang.wallet.feature.wallet.mvp;

import android.app.Activity;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.model.request.PayGetClientTokenReq;
import com.watayouxiang.httpclient.model.response.PayGetClientTokenResp;
import com.watayouxiang.httpclient.model.response.PayGetWalletInfoResp;

public interface Contract {
    interface View extends BaseView {
        void resetUI();

        boolean isMoneyVisibility();

        void hideMoney();

        void showMoney(String money);

        Activity getActivity();
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void getWalletInfo(DataProxy<PayGetWalletInfoResp> proxy);

        public abstract void getGetClientToken(@PayGetClientTokenReq.BizType String bizType, DataProxy<PayGetClientTokenResp> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void init();

        public abstract void query$ShowMoney();

        public abstract void getWalletInfo();

        public abstract void getClientToken(@PayGetClientTokenReq.BizType String bizType);
    }
}
