package com.watayouxiang.qrcode.feature.qrcode_decoder.mvp;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

import cn.bingoogolapple.qrcode.zxing.ZXingView;

public interface Contract {
    interface View extends BaseView {
        void resetUI();

        ZXingView getZXingView();

        TextView getTvFlashlight();
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void reqUserCurr(TioCallback<UserCurrResp> callback);

        public abstract void reqGroupInfo(String userflag, String groupid, TioCallback<GroupInfoResp> callback);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void init(Activity activity);

        public abstract void chooseQRCodeFromGallery(Activity activity);

        public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

        public abstract void onStart();

        public abstract void onStop();

        public abstract void onDestroy();
    }
}
