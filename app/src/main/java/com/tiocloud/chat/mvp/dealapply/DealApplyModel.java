package com.tiocloud.chat.mvp.dealapply;

import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.request.DealApplyReq;
import com.watayouxiang.httpclient.model.response.DealApplyResp;
import com.watayouxiang.httpclient.model.BaseResp;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public class DealApplyModel extends DealApplyContract.Model {
    @Override
    public void dealApply(String applyId, String remarkName, final DataProxy<DealApplyResp> proxy) {
        DealApplyReq req = new DealApplyReq(applyId, remarkName);
        TioHttpClient.post(this, req, new TaoCallback<BaseResp<DealApplyResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<DealApplyResp>> response) {
                BaseResp<DealApplyResp> body = response.body();
                DealApplyResp data = body.getData();
                if (body.isOk()) {
                    proxy.onSuccess(data);
                } else {
                    proxy.onFailure(response.body().getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResp<DealApplyResp>> response) {
                super.onError(response);
                proxy.onFailure(response.getException().getMessage());
            }
        });
    }
}
