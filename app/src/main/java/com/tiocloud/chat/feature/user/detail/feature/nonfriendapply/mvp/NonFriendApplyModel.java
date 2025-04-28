package com.tiocloud.chat.feature.user.detail.feature.nonfriendapply.mvp;

import androidx.annotation.Nullable;

import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.UserInfoReq;
import com.watayouxiang.httpclient.model.response.UserInfoResp;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public class NonFriendApplyModel extends NonFriendApplyContract.Model {

    private UserInfoResp mUserInfo;

    @Override
    @Nullable
    public String getUid() {
        if (mUserInfo != null) {
            return String.valueOf(mUserInfo.id);
        }
        return null;
    }

    @Override
    public void getUserInfo(String uid, final DataProxy<UserInfoResp> proxy) {
        UserInfoReq req = new UserInfoReq(uid);
        TioHttpClient.get(this, req, new TaoCallback<BaseResp<UserInfoResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<UserInfoResp>> response) {
                mUserInfo = response.body().getData();
                if (mUserInfo != null) {
                    proxy.onSuccess(mUserInfo);
                } else {
                    proxy.onFailure(response.body().getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResp<UserInfoResp>> response) {
                super.onError(response);
                proxy.onFailure(response.getException().getMessage());
            }
        });
    }
}
