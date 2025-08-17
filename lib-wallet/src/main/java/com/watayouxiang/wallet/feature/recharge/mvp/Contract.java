package com.watayouxiang.wallet.feature.recharge.mvp;

import android.app.Activity;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.model.response.PayRechargeResp;

public interface Contract {
    interface View extends BaseView {
        void resetUI();

        Activity getActivity();

        void finish();
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void postRecharge(String amount, DataProxy<PayRechargeResp> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void init();

        public abstract void walletRecharge(String amount);
    }
}
