package com.tiocloud.chat.feature.search.curr.main.model;

import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.request.MailListReq;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.androidutils.mvp.BaseModel;

/**
 * author : TaoWang
 * date : 2020-02-14
 * desc :
 */
public class SearchModel extends BaseModel {

    // ====================================================================================
    // mailList
    // ====================================================================================

    private MailListReq mailListReq;

    public void requestMailList(String mode, String searchKey, final DataProxy<MailListResp> proxy) {
        cancelRequestMailList();
        mailListReq = new MailListReq(mode, searchKey);
        TioHttpClient.get(mailListReq, mailListReq, new TaoCallback<BaseResp<MailListResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<MailListResp>> response) {
                BaseResp<MailListResp> body = response.body();
                MailListResp data = body.getData();
                if (data != null) {
                    proxy.onSuccess(data);
                } else {
                    proxy.onFailure(body.getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResp<MailListResp>> response) {
                super.onError(response);
                proxy.onFailure(response.getException().getMessage());
            }
        });
    }

    private void cancelRequestMailList() {
        if (mailListReq != null) {
            TioHttpClient.cancel(mailListReq);
        }
    }

    @Override
    public void detachModel() {
        super.detachModel();
        cancelRequestMailList();
    }
}
