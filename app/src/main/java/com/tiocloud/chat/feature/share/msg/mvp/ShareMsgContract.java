package com.tiocloud.chat.feature.share.msg.mvp;

import android.app.Activity;

import com.tiocloud.chat.feature.share.msg.model.MsgForwardEntity;
import com.tiocloud.chat.feature.share.msg.model.MsgForwardTo;
import com.watayouxiang.httpclient.model.request.MsgForwardReq;
import com.watayouxiang.httpclient.model.request.MultiMsgForwardReq;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

public interface ShareMsgContract {
    interface View extends BaseView {
        void bindContentView();

        void initEditText();

        void initFragmentContainers();

        void showRecentPage();

        void showSearchResultPage(String s);

        void forwardMsg(MsgForwardTo entity);

        String getChatLinkId();

        String getMids();

        String getChatMode();

        String getBizid();

        void showMsgForwardDialog(MsgForwardEntity entity,MsgForwardTo msgForwardTo);

        Activity getActivity();

        void switchChoose(boolean isMultiChoose);
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void getFriendInfo(String friendUid, DataProxy<UserInfoResp> proxy);

        public abstract MsgForwardReq getShareCardReq(String chatlinkid, String uids, String groupids, String mids);

        public abstract MultiMsgForwardReq getMultiShareCardReq(String chatlinkid, String bizid, String chotMode, String msgIds);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void initUI();
        public abstract void switchCHoose(boolean isMultiChoose);

        public abstract void onEtTextChanged(CharSequence s);

        public abstract void forwardMsg(MsgForwardTo entity);

        public abstract void reqMsgForward(String chatlinkid, String uids, String groupids, String mids);
        public abstract void reqMultiMsgForward(String chatlinkid, String bizid, String chatMode, String msgIds);
    }
}
