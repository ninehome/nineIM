package com.tiocloud.chat.feature.session.p2p.fragment.mvp;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.proxy.MsgListProxy;
import com.tiocloud.chat.feature.session.p2p.P2PSessionActivity;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.imclient.model.body.wx.WxFriendMsgResp;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-11
 * desc :
 */
public interface P2PFragmentContract {
    interface View extends BaseView {
        Activity getActivity();

        P2PSessionActivity getP2PSessionActivity();

        @NonNull
        List<String> getChatLinkIds();

        void initList();

        MsgListProxy getMsgListProxy();
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void init();

        public abstract void refresh();

        public abstract void loadMore();
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void getP2PMsgList(String chatLinkId, String startMid, DataProxy<WxFriendMsgResp> proxy);

        public abstract void convertTioP2PMsgList(List<WxFriendMsgResp.DataBean> resp, String chatlinkid, DataProxy<List<TioMsg>> proxy);

        public abstract void getTioP2PMsgList(String chatLinkId, String startMid, DataProxy<TioP2PMsgList> proxy);
    }
}
