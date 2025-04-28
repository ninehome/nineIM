package com.watayouxiang.qrcode.feature.qrcode_my.mvp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

import java.io.File;

public interface Contract {
    interface View extends BaseView {
        void onMyQRCodeGet(Bitmap result);

        void resetUI();

        void onUserCurrResp(UserCurrResp resp);
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void reqUserCurr(TioCallback<UserCurrResp> callback);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract File saveQRCode2Album(android.view.View root);

        public abstract void playBackgroundBlinkAnimation(android.view.View root);

        public abstract void init(Activity activity);
    }
}
