package com.tiocloud.chat.feature.curr.modify.mvp;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.callback.TioCallbackImpl;
import com.watayouxiang.httpclient.model.request.ModifyGroupNickReq;
import com.watayouxiang.httpclient.model.request.ModifyIntroReq;
import com.watayouxiang.httpclient.model.request.ModifyNameReq;
import com.watayouxiang.httpclient.model.request.ModifyNoticeReq;
import com.watayouxiang.httpclient.model.request.ModifyRemarkNameReq;
import com.watayouxiang.httpclient.model.request.UpdateNickReq;
import com.watayouxiang.httpclient.model.request.UpdateSignReq;

/**
 * author : TaoWang
 * date : 2020/4/1
 * desc :
 */
public class ModifyModel extends ModifyContract.Model {
    @Override
    public void reqModifyGroupName(String etContent, String groupId, final DataProxy<String> proxy) {
        ModifyNameReq req = new ModifyNameReq(groupId, etContent);
        req.setCancelTag(this);
        req.post(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                proxy.onSuccess(s);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }
        });
    }

    @Override
    public void reqModifyGroupIntro(String etContent, String groupId, final DataProxy<String> proxy) {
        ModifyIntroReq req = new ModifyIntroReq(groupId, etContent);
        req.setCancelTag(this);
        req.post(new TioCallback<Void>() {
            @Override
            public void onTioSuccess(Void aVoid) {
                proxy.onSuccess(null);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }
        });
    }

    @Override
    public void reqModifyGroupNotice(String etContent, String groupId, final DataProxy<String> proxy) {
        ModifyNoticeReq req = new ModifyNoticeReq(groupId, etContent);
        req.setCancelTag(this);
        req.post(new TioCallback<Void>() {
            @Override
            public void onTioSuccess(Void aVoid) {
                proxy.onSuccess(null);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }
        });
    }

    @Override
    public void reqModifyGroupNick(String nick, String groupId, final DataProxy<String> proxy) {
        ModifyGroupNickReq req = new ModifyGroupNickReq(groupId, nick);
        req.setCancelTag(this);
        req.post(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                proxy.onSuccess(null);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }
        });
    }

    @Override
    public void updateNickReq(String nick, DataProxy<String> proxy) {
        UpdateNickReq updateNickReq = new UpdateNickReq(nick);
        updateNickReq.setCancelTag(this);
        updateNickReq.post(new TioCallback<Void>() {
            @Override
            public void onTioSuccess(Void aVoid) {
                proxy.onSuccess(null);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }
        });
    }

    @Override
    public void updateSignReq(String sign, DataProxy<String> proxy) {
        UpdateSignReq req = new UpdateSignReq(sign);
        req.setCancelTag(this);
        req.post(new TioCallback<Void>() {
            @Override
            public void onTioSuccess(Void aVoid) {
                proxy.onSuccess(null);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }
        });
    }

    @Override
    public void modifyRemarkNameReq(String remarkName, String uid, DataProxy<String> proxy) {
        ModifyRemarkNameReq req = new ModifyRemarkNameReq(remarkName, uid);
        req.setCancelTag(this);
        req.post(new TioCallbackImpl<String>() {
            @Override
            public void onTioSuccess(String s) {
                proxy.onSuccess(s);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }
        });
    }
}
