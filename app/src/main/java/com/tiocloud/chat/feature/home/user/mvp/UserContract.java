package com.tiocloud.chat.feature.home.user.mvp;

import android.app.Activity;

import com.watayouxiang.httpclient.model.request.UserCurrReq;
import com.watayouxiang.httpclient.model.response.UserCurrResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020-02-19
 * desc :
 */
public interface UserContract {
    interface View extends BaseView {
        Activity getActivity();

        void initUI();

        void updateUI(UserCurrResp userCurrResp);
    }

    abstract class Model extends BaseModel {
        public abstract UserCurrReq getUserCurrReq();
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void init();

        public abstract void updateUIData();
    }
}
