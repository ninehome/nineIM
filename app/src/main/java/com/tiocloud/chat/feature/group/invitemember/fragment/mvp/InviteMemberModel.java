package com.tiocloud.chat.feature.group.invitemember.fragment.mvp;

import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.request.ApplyGroupFdListReq;
import com.watayouxiang.httpclient.model.request.JoinGroupReq;
import com.watayouxiang.httpclient.model.response.ApplyGroupFdListResp;
import com.watayouxiang.httpclient.model.BaseResp;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class InviteMemberModel extends InviteMemberContract.Model {
    @Override
    public void getApplyGroupFdList(String groupId, String searchKey, final DataProxy<ApplyGroupFdListResp> proxy) {
        ApplyGroupFdListReq req = new ApplyGroupFdListReq(groupId, searchKey);
        TioHttpClient.get(this, req, new TaoCallback<BaseResp<ApplyGroupFdListResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<ApplyGroupFdListResp>> response) {
                BaseResp<ApplyGroupFdListResp> body = response.body();
                ApplyGroupFdListResp data = body.getData();
                if (data != null) {
                    proxy.onSuccess(data);
                } else {
                    proxy.onFailure(body.getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResp<ApplyGroupFdListResp>> response) {
                super.onError(response);
                proxy.onFailure(response.getException().getMessage());
            }
        });
    }

    private JoinGroupReq joinGroupReq;

    @Override
    public void postJoinGroup(String groupId, String uids, final DataProxy<String> proxy) {
        // 防止重复提交
        if (joinGroupReq != null) return;
        joinGroupReq = new JoinGroupReq(uids, groupId);
        TioHttpClient.post(this, joinGroupReq, new TaoCallback<BaseResp<String>>() {
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

            @Override
            public void onFinish() {
                super.onFinish();
                joinGroupReq = null;
                proxy.onFinish();
            }
        });
    }
}
