package com.tiocloud.chat.feature.user.applylist.mvp;

import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.request.ApplyListReq;
import com.watayouxiang.httpclient.model.response.ApplyListResp;
import com.watayouxiang.httpclient.model.BaseResp;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public class ApplyListModel extends ApplyListContract.Model {
    @Override
    public void getApplyList(final DataProxy<ApplyListResp> proxy) {
        final ApplyListReq req = new ApplyListReq();
        req.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
        TioHttpClient.get(this, req, new TaoCallback<BaseResp<ApplyListResp>>() {
            @Override
            public void onCacheSuccess(Response<BaseResp<ApplyListResp>> response) {
                super.onCacheSuccess(response);
                onSuccess(response);
            }

            @Override
            public void onSuccess(Response<BaseResp<ApplyListResp>> response) {
                BaseResp<ApplyListResp> body = response.body();
                ApplyListResp data = body.getData();
                if (data != null) {
                    proxy.onSuccess(data);
                } else {
                    proxy.onFailure(body.getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResp<ApplyListResp>> response) {
                super.onError(response);
                proxy.onFailure(response.getException().getMessage());
            }
        });
    }
}
