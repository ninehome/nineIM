package com.watayouxiang.qrcode.feature.qrcode_group.mvp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

import java.io.File;

public interface Contract {
    interface View extends BaseView {
        void resetUI();

        void onGroupInfoResp(GroupInfoResp resp);

        void onGroupQRCodeGet(Bitmap result);
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

        public abstract File saveQRCode2Album(android.view.View root);

        public abstract void playBackgroundBlinkAnimation(android.view.View root);

        public abstract void init(String groupId,Activity activity);

        public abstract void showShareDialog(Activity activity, android.view.View root);

        public abstract void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
