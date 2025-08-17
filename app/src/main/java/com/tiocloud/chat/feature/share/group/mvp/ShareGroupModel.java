package com.tiocloud.chat.feature.share.group.mvp;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.CheckSendCardReq;
import com.watayouxiang.httpclient.model.request.ShareCardReq;
import com.watayouxiang.httpclient.model.request.UserInfoReq;
import com.watayouxiang.httpclient.model.response.UserInfoResp;

public class ShareGroupModel extends ShareGroupContract.Model {
    private UserInfoResp userInfoResp;

    public ShareGroupModel() {
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
                ShareGroupModel.this.userInfoResp = userInfoResp;
                proxy.onSuccess(userInfoResp);
            }

            @Override
            public void onTioError(String msg) {

            }
        });
    }

    @Override
    public ShareCardReq getShareCardReq(String chatmode, String uids, String groupids, String cardid) {
        ShareCardReq shareCardReq = new ShareCardReq(chatmode, uids, groupids, cardid);
        shareCardReq.setCancelTag(this);
        return shareCardReq;
    }

    @Override
    public CheckSendCardReq getCheckSendCardReq(String groupid) {
        CheckSendCardReq req = new CheckSendCardReq(groupid);
        req.setCancelTag(this);
        return req;
    }
}
