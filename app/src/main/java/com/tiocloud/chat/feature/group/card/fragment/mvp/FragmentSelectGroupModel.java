package com.tiocloud.chat.feature.group.card.fragment.mvp;

import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.request.MailListReq;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class FragmentSelectGroupModel extends FragmentSelectGroupContract.Model {
    @Override
    public void getMailList(String searchKey, final DataProxy<List<MailListResp.Group>> proxy) {
        final MailListReq req = new MailListReq("2", searchKey);
        TioHttpClient.get(this, req, new TaoCallback<BaseResp<MailListResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<MailListResp>> response) {
                BaseResp<MailListResp> body = response.body();
                MailListResp data = body.getData();
                if (data != null && data.group != null) {
                    proxy.onSuccess(data.group);
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
}
