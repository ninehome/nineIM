package com.tiocloud.chat.feature.session.group.fragment.mvp;

import androidx.annotation.NonNull;

import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.proxy.MsgListProxy;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.imclient.model.body.wx.WxGroupMsgResp;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-09
 * desc :
 */
public interface GroupFragmentContract {
    interface View extends BaseView {
        @NonNull
        List<String> getChatLinkIds();

        @NonNull
        String getGroupId();

        void resetUI();

        MsgListProxy getMsgListProxy();

        void toRefreshNotice(boolean showDialog, String notice);
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void getGroupMsgList(String chatLinkId, String startMid, DataProxy<WxGroupMsgResp> proxy);

        public abstract void convertTioGroupMsgList(List<WxGroupMsgResp.DataBean> resp, String chatlinkid, DataProxy<List<TioMsg>> proxy);

        public abstract void getTioGroupMsgList(String chatLinkId, String startMid, DataProxy<TioGroupMsgList> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void init();

        public abstract void refresh();

        public abstract void loadMore();
    }
}
