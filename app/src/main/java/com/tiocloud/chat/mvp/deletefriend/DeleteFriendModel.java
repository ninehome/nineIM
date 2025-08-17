package com.tiocloud.chat.mvp.deletefriend;

import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.request.DelFriendReq;
import com.watayouxiang.httpclient.model.BaseResp;

/**
 * author : TaoWang
 * date : 2020-02-25
 * desc :
 */
public class DeleteFriendModel extends DeleteFriendContract.Model {

    @Override
    public void delFriend(int uid, boolean forceDel, final DataProxy<String> proxy) {
        DelFriendReq req = new DelFriendReq(String.valueOf(uid));
        req.setForceDel(forceDel);
        TioHttpClient.post(this, req, new TaoCallback<BaseResp<String>>() {
            @Override
            public void onSuccess(Response<BaseResp<String>> response) {
                String data = response.body().getData();
                if (data != null) {
                    proxy.onSuccess(data);
                } else {
                    proxy.onFailure(response.body().getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResp<String>> response) {
                super.onError(response);
                proxy.onFailure(response.getException().getMessage());
            }
        });
    }
}
