package com.tiocloud.chat.feature.share.group.mvp;

import android.app.Activity;

import com.tiocloud.chat.feature.share.group.model.ShareCardEntity;
import com.tiocloud.chat.feature.share.group.model.ShareCardTo;
import com.watayouxiang.httpclient.model.request.CheckSendCardReq;
import com.watayouxiang.httpclient.model.request.ShareCardReq;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

public interface ShareGroupContract {
    interface View extends BaseView {
        void bindContentView();

        void initEditText();

        void initFragmentContainers();

        void showRecentPage();

        void showSearchResultPage(String s);

        void shareCard(ShareCardTo entity);

        String getFromGroupId();

        void showCardDialog(ShareCardEntity entity);

        Activity getActivity();
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void getFriendInfo(String friendUid, DataProxy<UserInfoResp> proxy);

        public abstract ShareCardReq getShareCardReq(String chatmode, String uids, String groupids, String cardid);

        public abstract CheckSendCardReq getCheckSendCardReq(String groupid);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void initUI();

        public abstract void onEtTextChanged(CharSequence s);

        public abstract void shareCard(ShareCardTo entity);

        public abstract void reqShareCard(String chatmode, String uids, String groupids, String cardid);
    }
}
