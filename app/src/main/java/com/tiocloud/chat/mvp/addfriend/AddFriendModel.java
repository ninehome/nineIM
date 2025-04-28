package com.tiocloud.chat.mvp.addfriend;

import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.AddFriendReq;
import com.watayouxiang.httpclient.model.request.CheckAddFriendReq;
import com.watayouxiang.httpclient.model.request.FriendApplyReq;
import com.watayouxiang.httpclient.model.request.UserCurrReq;
import com.watayouxiang.httpclient.model.response.AddFriendResp;
import com.watayouxiang.httpclient.model.response.FriendApplyResp;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
class AddFriendModel extends AddFriendContract.Model {

    // ====================================================================================
    // init
    // ====================================================================================

    public void reqCurrUser(final DataProxy<UserCurrResp> proxy) {
        UserCurrReq req = new UserCurrReq();
        req.get(new TioCallback<UserCurrResp>() {
            @Override
            public void onTioSuccess(UserCurrResp userCurrResp) {
                proxy.onSuccess(userCurrResp);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                proxy.onFinish();
            }
        });
    }

    private UserCurrResp mUserCurr;

    @Override
    public void init(final DataProxy<UserCurrResp> proxy) {
        if (mUserCurr != null) {
            proxy.onSuccess(mUserCurr);
            return;
        }
        reqCurrUser(new DataProxy<UserCurrResp>() {
            @Override
            public void onSuccess(UserCurrResp resp) {
                mUserCurr = resp;
                proxy.onSuccess(mUserCurr);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                proxy.onFailure(msg);
            }
        });
    }

    @Override
    public String getCurrUserNick() {
        if (mUserCurr != null) {
            return mUserCurr.nick;
        }
        return null;
    }

    // ====================================================================================
    // 申请添加好友
    // ====================================================================================

    /**
     * 检测
     *
     * @param uid
     * @param proxy 验证标识：1:申请好友；2:添加好友
     */
    @Override
    public void checkAddFriend(final int uid, final DataProxy<Integer> proxy) {
        CheckAddFriendReq req = new CheckAddFriendReq(String.valueOf(uid));
        TioHttpClient.get(this, req, new TaoCallback<BaseResp<Integer>>() {
            @Override
            public void onSuccess(Response<BaseResp<Integer>> response) {
                BaseResp<Integer> body = response.body();
                Integer data = body.getData();
                if (data != null) {
                    proxy.onSuccess(data);
                } else {
                    proxy.onFailure(body.getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResp<Integer>> response) {
                super.onError(response);
                proxy.onFailure(response.getException().getMessage());
            }
        });
    }

    /**
     * 申请好友
     *
     * @param uid
     * @param greet
     * @param proxy
     */
    @Override
    public void postFriendApply(final int uid, final String greet, final DataProxy<FriendApplyResp> proxy) {
        FriendApplyReq req = new FriendApplyReq(String.valueOf(uid), greet);
        TioHttpClient.post(this, req, new TaoCallback<BaseResp<FriendApplyResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<FriendApplyResp>> response) {
                BaseResp<FriendApplyResp> body = response.body();
                FriendApplyResp data = body.getData();
                if (data != null) {
                    proxy.onSuccess(data);
                } else {
                    proxy.onFailure(body.getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResp<FriendApplyResp>> response) {
                super.onError(response);
                proxy.onFailure(response.getException().getMessage());
            }
        });
    }

    /**
     * 非验证加好友
     *
     * @param uid
     * @param proxy
     */
    @Override
    public void postAddFriend(int uid, final DataProxy<AddFriendResp> proxy) {
        AddFriendReq req = new AddFriendReq(String.valueOf(uid));
        TioHttpClient.post(this, req, new TaoCallback<BaseResp<AddFriendResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<AddFriendResp>> response) {
                BaseResp<AddFriendResp> body = response.body();
                AddFriendResp data = body.getData();
                if (body.isOk()) {
                    proxy.onSuccess(data);
                } else {
                    proxy.onFailure(body.getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResp<AddFriendResp>> response) {
                super.onError(response);
                proxy.onFailure(response.getException().getMessage());
            }
        });
    }
}
