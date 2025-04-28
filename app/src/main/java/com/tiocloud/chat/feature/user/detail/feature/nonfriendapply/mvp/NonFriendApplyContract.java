package com.tiocloud.chat.feature.user.detail.feature.nonfriendapply.mvp;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.tiocloud.chat.feature.user.detail.model.NonFriendApply;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public interface NonFriendApplyContract {
    interface View extends BaseView {
        Activity getActivity();

        String getUid();

        @NonNull
        NonFriendApply getNonFriendApply();

        void resetViews();

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

        /**
         * 同意添加好友
         *
         * @param applyId
         * @param remarkName
         * @param view
         */
        public abstract void doAgreeAddFriend(String applyId, String remarkName, android.view.View view);
    }
}
