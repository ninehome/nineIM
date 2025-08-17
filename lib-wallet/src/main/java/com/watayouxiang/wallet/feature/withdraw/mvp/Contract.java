package com.watayouxiang.wallet.feature.withdraw.mvp;

import android.app.Activity;
import android.content.Context;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.model.response.PayGetWalletInfoResp;
import com.watayouxiang.httpclient.model.response.PayWithholdResp;

public interface Contract {
    interface View extends BaseView {
        void resetUI();

        void onMoneyResp(String money);

        void onWithdrawAllResp(String money);

        Activity getActivity();

        void finish();
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void getWalletInfo(DataProxy<PayGetWalletInfoResp> proxy);

        public abstract void postWithhold(DataProxy<PayWithholdResp> proxy, String amount);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void init();

        public abstract void getWalletInfo();

        public abstract void getWalletInfo(boolean withdrawAll);

        public abstract void postWithdraw(String amount);
    }
}
