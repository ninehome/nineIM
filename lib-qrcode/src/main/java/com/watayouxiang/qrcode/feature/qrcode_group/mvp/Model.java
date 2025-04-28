package com.watayouxiang.qrcode.feature.qrcode_group.mvp;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.GroupInfoReq;
import com.watayouxiang.httpclient.model.request.UserCurrReq;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

public class Model extends Contract.Model {
    public Model() {
        super(false);
    }

    @Override
    public void reqUserCurr(TioCallback<UserCurrResp> callback) {
        new UserCurrReq().setCancelTag(this).get(callback);
    }

    @Override
    public void reqGroupInfo(String userflag, String groupid, TioCallback<GroupInfoResp> callback) {
        new GroupInfoReq(userflag, groupid).setCancelTag(this).get(callback);
    }
}
