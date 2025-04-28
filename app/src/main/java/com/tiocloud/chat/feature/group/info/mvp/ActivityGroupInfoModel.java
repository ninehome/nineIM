package com.tiocloud.chat.feature.group.info.mvp;

import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.request.GroupInfoReq;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;
import com.watayouxiang.httpclient.model.BaseResp;

/**
 * author : TaoWang
 * date : 2020-02-26
 * desc :
 */
public class ActivityGroupInfoModel extends ActivityGroupInfoContract.Model {
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
}
