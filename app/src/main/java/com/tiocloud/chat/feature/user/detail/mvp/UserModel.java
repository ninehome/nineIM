package com.tiocloud.chat.feature.user.detail.mvp;

import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.request.IsFriendReq;
import com.watayouxiang.httpclient.model.BaseResp;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public class UserModel extends UserContract.Model {

    @Override
    public void isFriend(final int uid, final DataProxy<Integer> proxy) {
        IsFriendReq isFriendReq = new IsFriendReq(String.valueOf(uid));
        TioHttpClient.get(this, isFriendReq, new TaoCallback<BaseResp<Integer>>() {
            @Override
            public void onSuccess(Response<BaseResp<Integer>> response) {
                BaseResp<Integer> body = response.body();
                Integer data = body.getData();
                if (data != null) {
                    proxy.onSuccess(data);
                } else {
                    proxy.onFailure(body.getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResp<Integer>> response) {
                super.onError(response);
                proxy.onFailure(response.getException().getMessage());
            }
        });
    }
}
