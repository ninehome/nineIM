package com.tiocloud.chat.feature.group.info.fragment.mvp;

import com.lzy.okgo.model.Response;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.callback.TioCallbackImpl;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.DelGroupReq;
import com.watayouxiang.httpclient.model.request.GroupInfoReq;
import com.watayouxiang.httpclient.model.request.GroupUserListReq;
import com.watayouxiang.httpclient.model.request.LeaveGroupReq;
import com.watayouxiang.httpclient.model.request.ModifyApplyReq;
import com.watayouxiang.httpclient.model.request.OperReq;
import com.watayouxiang.httpclient.model.response.DelGroupResp;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;

/**
 * author : TaoWang
 * date : 2020-02-26
 * desc :
 */
public class FragmentGroupInfoModel extends FragmentGroupInfoContract.Model {
    @Override
    public void getGroupInfo(String userFlag, String groupId, final DataProxy<GroupInfoResp> proxy) {
        GroupInfoReq groupInfoReq = new GroupInfoReq(userFlag, groupId);
        TioHttpClient.get(this, groupInfoReq, new TaoCallback<BaseResp<GroupInfoResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<GroupInfoResp>> response) {
                GroupInfoResp data = response.body().getData();
                if (data != null) {
                    proxy.onSuccess(data);
                }
            }
        });
    }

    @Override
    public void getGroupUserList(String pageNumber, String groupid, final DataProxy<GroupUserListResp> proxy) {
        GroupUserListReq req = new GroupUserListReq(groupid, pageNumber, null);
        req.setCancelTag(this);
        TioHttpClient.get(req, new TioCallbackImpl<GroupUserListResp>() {
            @Override
            public void onTioSuccess(GroupUserListResp groupUserListResp) {
                proxy.onSuccess(groupUserListResp);
            }
        });
    }

    @Override
    public void requestLeaveGroup(String groupId, final DataProxy<String> proxy) {
        LeaveGroupReq leaveGroupReq = new LeaveGroupReq(String.valueOf(TioDBPreferences.getCurrUid()), groupId);
        TioHttpClient.get(this, leaveGroupReq, new TaoCallback<BaseResp<String>>() {
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

    @Override
    public void requestDelGroup(String groupId, final DataProxy<DelGroupResp> proxy) {
        DelGroupReq delGroupReq = new DelGroupReq(String.valueOf(TioDBPreferences.getCurrUid()), groupId);
        TioHttpClient.get(this, delGroupReq, new TaoCallback<BaseResp<DelGroupResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<DelGroupResp>> response) {
                DelGroupResp data = response.body().getData();
                if (data != null) {
                    proxy.onSuccess(data);
                } else {
                    proxy.onFailure(response.body().getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResp<DelGroupResp>> response) {
                super.onError(response);
                proxy.onFailure(response.getException().getMessage());
            }
        });
    }

    @Override
    public void reqModifyInviteSwitch(final boolean isChecked, String groupId, final DataProxy<String> proxy) {
        ModifyApplyReq req = new ModifyApplyReq(isChecked, groupId);
        TioHttpClient.post(this, req, new TaoCallback<BaseResp<String>>() {
            @Override
            public void onSuccess(Response<BaseResp<String>> response) {
                if (response.body().isOk()) {
                    proxy.onSuccess(response.body().getData());
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

    @Override
    public void reqClearChatRecord(final String chatLinkId, final DataProxy<String> proxy) {
        OperReq req = OperReq.deleteChatRecord(chatLinkId);
        TioHttpClient.post(this, req, new TaoCallback<BaseResp<String>>() {
            @Override
            public void onSuccess(Response<BaseResp<String>> resp) {
                BaseResp<String> body = resp.body();
                if (body.isOk()) {
                    proxy.onSuccess(body.getData());
                } else {
                    proxy.onSuccess(body.getMsg());
                }
            }
        });
    }
}
