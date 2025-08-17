package com.tiocloud.chat.feature.session.group.mvp;

import com.watayouxiang.httpclient.model.response.ActChatResp;
import com.watayouxiang.imclient.model.body.wx.WxChatItemInfoResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.androidutils.page.TioActivity;

/**
 * author : TaoWang
 * date : 2020-02-06
 * desc : 群聊合同
 */
public interface GroupActivityContract {
    interface View extends BaseView {
        TioActivity getActivity();

        void initFragment(String chatLinkId, String groupId);

        String getGroupId();

        void setGroupId(String groupId);

        String getChatLinkId();

        void initUI();

        void onChatInfoResp(WxChatItemInfoResp resp);
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void actChat(final String groupid, final DataProxy<ActChatResp> proxy);

        public abstract void getChatInfo(String chatLinkId, DataProxy<WxChatItemInfoResp> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void init();

        public abstract void enterGroup(String chatLinkId);

        public abstract void activeGroup(String groupId);

        public abstract void getChatInfo();
    }
}
