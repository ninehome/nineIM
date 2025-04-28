package com.tiocloud.chat.feature.user.detail.feature.nonfriend.mvp;

import android.app.Activity;

import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public interface NonFriendContract {
    interface View extends BaseView {
        Activity getActivity();

        String getUid();

        void resetViews();

        boolean getForceAddorDel();

        void initViews();

        void onUserInfoResp(UserInfoResp resp);
    }

    abstract class Model extends BaseModel {
        public abstract String getUid();

        public abstract void getUserInfo(String uid, DataProxy<UserInfoResp> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void init();

        public abstract void doAddFriend(android.view.View v);
    }
}
