package com.tiocloud.chat.feature.home.user.mvp;

import com.lzy.okgo.cache.CacheMode;
import com.watayouxiang.httpclient.model.request.UserCurrReq;

/**
 * author : TaoWang
 * date : 2020-02-19
 * desc :
 */
public class UserModel extends UserContract.Model {
    @Override
    public UserCurrReq getUserCurrReq() {
        UserCurrReq userCurrReq = new UserCurrReq();
        userCurrReq.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
        userCurrReq.setCancelTag(this);
        return userCurrReq;
    }
}
