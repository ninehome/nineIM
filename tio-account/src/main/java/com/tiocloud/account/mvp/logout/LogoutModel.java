package com.tiocloud.account.mvp.logout;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.LogoutReq;

/**
 * author : TaoWang
 * date : 2020-02-12
 * desc :
 */
public class LogoutModel extends LogoutContract.Model {
    @Override
    public void requestLogout(TioCallback<Void> callback) {
        new LogoutReq().setCancelTag(this).get(callback);
    }
}
