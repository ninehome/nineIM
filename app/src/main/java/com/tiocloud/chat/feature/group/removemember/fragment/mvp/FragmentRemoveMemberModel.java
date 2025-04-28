package com.tiocloud.chat.feature.group.removemember.fragment.mvp;

import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.request.GroupUserListReq;
import com.watayouxiang.httpclient.model.request.KickGroupReq;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;
import com.watayouxiang.httpclient.model.BaseResp;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class FragmentRemoveMemberModel extends FragmentRemoveMemberContract.Model {
    @Override
    public void getGroupUserList(String pageNumber, String groupid, String searchKey, final DataProxy<GroupUserListResp> proxy) {
        GroupUserListReq req = new GroupUserListReq(groupid, pageNumber, searchKey);
        TioHttpClient.get(this, req, new TaoCallback<BaseResp<GroupUserListResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<GroupUserListResp>> response) {
                GroupUserListResp data = response.body().getData();
                if (data != null) {
                    proxy.onSuccess(data);
                }
            }
        });
    }

    private KickGroupReq kickGroupReq;

    @Override
    public void postKickGroup(String uids, String groupId, final DataProxy<String> proxy) {
        if (kickGroupReq != null) {
            return;
        }
        kickGroupReq = new KickGroupReq(uids, groupId);
        TioHttpClient.post(this, kickGroupReq, new TaoCallback<BaseResp<String>>() {
            @Override
            public void onSuccess(Response<BaseResp<String>> response) {
                BaseResp<String> body = response.body();
                String data = body.getData();
                if (body.isOk()) {
                    proxy.onSuccess(data);
                } else {
                    proxy.onFailure(body.getMsg());
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
                kickGroupReq = null;
            }
        });
    }
}
