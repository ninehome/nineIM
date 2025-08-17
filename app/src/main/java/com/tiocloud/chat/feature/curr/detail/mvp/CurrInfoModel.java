package com.tiocloud.chat.feature.curr.detail.mvp;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.UserCurrReq;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

/**
 * author : TaoWang
 * date : 2020/3/13
 * desc :
 */
public class CurrInfoModel extends CurrInfoContract.Model {
    public void reqCurrUser(final TioCallback<UserCurrResp> proxy) {
        UserCurrReq req = new UserCurrReq();
        req.setCancelTag(this);
        req.get(proxy);
    }
}
