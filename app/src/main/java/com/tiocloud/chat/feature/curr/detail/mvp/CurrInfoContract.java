package com.tiocloud.chat.feature.curr.detail.mvp;

import android.app.Activity;
import android.content.Context;

import com.tiocloud.chat.widget.dialog.base.AvatarDialog;
import com.watayouxiang.httpclient.model.response.UserCurrResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020/3/13
 * desc :
 */
public interface CurrInfoContract {
    interface View extends BaseView {
        Activity getActivity();

        void onUserCurrResp(UserCurrResp userCurr);
    }

    abstract class Model extends BaseModel {
    }

    abstract class Presenter extends BasePresenter<CurrInfoModel, View> {
        public Presenter(CurrInfoModel model, View view) {
            super(model, view);
        }

        public abstract void updateUIData();

        public abstract AvatarDialog getAvatarDialog();

        public abstract void showGenderDialog(Context context, int sex);
    }
}
