package com.tiocloud.chat.feature.share.msg.mvp;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.MsgForwardReq;
import com.watayouxiang.httpclient.model.request.MultiMsgForwardReq;
import com.watayouxiang.httpclient.model.request.UserInfoReq;
import com.watayouxiang.httpclient.model.response.UserInfoResp;

public class ShareMsgModel extends ShareMsgContract.Model {
    private UserInfoResp userInfoResp;

    public ShareMsgModel() {
        super(false);
    }

    @Override
    public void getFriendInfo(String friendUid, DataProxy<UserInfoResp> proxy) {
        if (userInfoResp != null) {
            proxy.onSuccess(userInfoResp);
            return;
        }
        UserInfoReq userInfoReq = new UserInfoReq(friendUid);
        userInfoReq.setCancelTag(this);
        userInfoReq.get(new TioCallback<UserInfoResp>() {
            @Override
            public void onTioSuccess(UserInfoResp userInfoResp) {
                ShareMsgModel.this.userInfoResp = userInfoResp;
                proxy.onSuccess(userInfoResp);
            }

            @Override
            public void onTioError(String msg) {

            }
        });
    }

    @Override
    public MsgForwardReq getShareCardReq(String chatlinkid, String uids, String groupids, String mids) {
        MsgForwardReq msgForwardReq = new MsgForwardReq(chatlinkid, uids, groupids, mids);
        msgForwardReq.setCancelTag(this);
        return msgForwardReq;
    }

    @Override
    public MultiMsgForwardReq getMultiShareCardReq(String chatlinkid, String bizid, String chotMode, String msgIds) {
        MultiMsgForwardReq multiMsgForwardReq = new MultiMsgForwardReq(chatlinkid,bizid,chotMode,msgIds);
        return multiMsgForwardReq;
    }
}
