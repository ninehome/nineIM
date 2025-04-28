package com.tiocloud.chat.feature.group.member.mvp;

import com.lzy.okgo.model.Response;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.ChangeOwnerReq;
import com.watayouxiang.httpclient.model.request.GroupUserListReq;
import com.watayouxiang.httpclient.model.request.KickGroupReq;
import com.watayouxiang.httpclient.model.response.ChangeOwnerResp;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-27
 * desc :
 */
public class GroupMemberModel extends GroupMemberContract.Model {
    @Override
    public void getGroupUserList(String pageNumber, String groupid, String searchkey, TioCallback<GroupUserListResp> callback) {
        GroupUserListReq req = new GroupUserListReq(groupid, pageNumber, searchkey);
        TioHttpClient.get(this, req, callback);
    }

    // 当前用户是否为群主
    @Override
    public boolean currUserIsGroupOwner(List<GroupUserListResp.GroupMember> userList) {
        if (userList == null) return false;
        String currUid = String.valueOf(TioDBPreferences.getCurrUid());
        for (GroupUserListResp.GroupMember user : userList) {
            if (user.isGroupOwner() || user.grouprole == 3) {
                if (String.valueOf(user.uid).equals(currUid))
                    return true;
            }
        }
        return false;
    }

    @Override
    public void changeGroupOwner(String groupId, int uid, final DataProxy<ChangeOwnerResp> proxy) {
        ChangeOwnerReq changeOwnerReq = new ChangeOwnerReq(String.valueOf(uid), groupId);
        TioHttpClient.post(this, changeOwnerReq, new TaoCallback<BaseResp<ChangeOwnerResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<ChangeOwnerResp>> response) {
                BaseResp<ChangeOwnerResp> body = response.body();
                ChangeOwnerResp data = body.getData();
                if (body.isOk()) {
                    proxy.onSuccess(data);
                } else {
                    proxy.onFailure(body.getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResp<ChangeOwnerResp>> response) {
                super.onError(response);
                proxy.onFailure(response.getException().getMessage());
            }
        });
    }

    @Override
    public void postKickGroup(String uids, String groupId, final DataProxy<String> proxy) {
        KickGroupReq kickGroupReq = new KickGroupReq(uids, groupId);
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
        });
    }
}
