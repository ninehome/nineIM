package com.tiocloud.chat.feature.user.detail.mvp;

import android.app.Activity;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public interface UserContract {
    interface View extends BaseView {
        Activity getActivity();

        void onIsFriendResp(boolean isFriend);
    }

    abstract class Model extends BaseModel {
        public abstract void isFriend(final int uid, final DataProxy<Integer> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void updateUI(String uid);
    }
}
