package com.tiocloud.chat.feature.session.p2p.mvp;

import com.watayouxiang.httpclient.model.response.ActChatResp;
import com.watayouxiang.imclient.model.body.wx.WxChatItemInfoResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.androidutils.page.TioActivity;

/**
 * author : TaoWang
 * date : 2020-02-10
 * desc :
 */
public interface P2PActivityContract {

    interface View extends BaseView {
        TioActivity getActivity();

        void setTitle(CharSequence title);

        void showFragment(String chatLinkId);

        String getUid();

        String getChatLinkId();

        void setUid(String uid);

        void initUI();

        void onChatInfoResp(WxChatItemInfoResp resp);
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void actChat(final String touid, final DataProxy<ActChatResp> proxy);

        public abstract void getChatInfo(String chatLinkId, DataProxy<WxChatItemInfoResp> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void init();

        public abstract void enter(String chatLinkId);

        public abstract void active(String uid);

        public abstract void getChatInfo();
    }

}
