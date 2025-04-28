package com.tiocloud.chat.feature.group.create.fragment.mvp;

import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.request.CreateGroupReq;
import com.watayouxiang.httpclient.model.request.MailListReq;
import com.watayouxiang.httpclient.model.response.CreateGroupResp;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class FragmentCreateGroupModel extends FragmentCreateGroupContract.Model {
    @Override
    public void getMailList(String searchKey, final DataProxy<List<MailListResp.Friend>> proxy) {
        final MailListReq req = new MailListReq("1", searchKey);
        TioHttpClient.get(this, req, new TaoCallback<BaseResp<MailListResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<MailListResp>> response) {
                BaseResp<MailListResp> body = response.body();
                MailListResp data = body.getData();
                if (data != null && data.fd != null) {
                    proxy.onSuccess(data.fd);
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

    private CreateGroupReq createGroupReq;

    @Override
    public void postCreateGroup(String name, String uidList, final DataProxy<CreateGroupResp> proxy) {
        // 防止重复提交
        if (createGroupReq != null) return;
        createGroupReq = new CreateGroupReq(name, uidList);
        TioHttpClient.post(this, createGroupReq, new TaoCallback<BaseResp<CreateGroupResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<CreateGroupResp>> response) {
                CreateGroupResp data = response.body().getData();
                if (data != null) {
                    proxy.onSuccess(data);
                } else {
                    proxy.onFailure(response.body().getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResp<CreateGroupResp>> response) {
                super.onError(response);
                proxy.onFailure(response.getException().getMessage());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                createGroupReq = null;
                proxy.onFinish();
            }
        });
    }
}
