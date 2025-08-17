package com.tiocloud.chat.feature.forbidden;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.ForbiddenFlagReq;
import com.watayouxiang.httpclient.model.request.ForbiddenReq;
import com.watayouxiang.httpclient.model.request.ForbiddenUserListReq;
import com.watayouxiang.httpclient.model.response.ForbiddenFlagResp;
import com.watayouxiang.httpclient.model.response.ForbiddenResp;
import com.watayouxiang.httpclient.model.response.ForbiddenUserListResp;

public class ForbiddenMvpModel extends ForbiddenMvpContract.Model {
    public ForbiddenMvpModel() {
        super(false);
    }

    @Override
    public void reqForbiddenFlag(String uid, String groupId, TioCallback<ForbiddenFlagResp> callback) {
        new ForbiddenFlagReq(uid, groupId).setCancelTag(this).get(callback);
    }

    @Override
    public void reqForbidden(String mode, String duration, String uid, String groupid, String oper, TioCallback<ForbiddenResp> callback) {
        new ForbiddenReq(mode, duration, uid, groupid, oper).setCancelTag(this).post(callback);
    }

    @Override
    public void reqForbiddenUserList(String pageNumber, String groupid, String searchkey, TioCallback<ForbiddenUserListResp> callback) {
        new ForbiddenUserListReq(pageNumber, groupid, searchkey).setCancelTag(this).get(callback);
    }
}
