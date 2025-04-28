package com.tiocloud.chat.feature.home.group.mvp;

import androidx.annotation.Nullable;

import com.lzy.okgo.cache.CacheMode;
import com.tiocloud.chat.feature.main.fragment.MainGroupFragment;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.MailListReq;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.watayouxiang.androidutils.widget.TioToast;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-18
 * desc :
 */
public class GroupModel extends GroupContract.Model {
    @Override
    public void requestMailList(final DataProxy<List<MailListResp.Group>> proxy) {
        MailListReq mailListReq = new MailListReq("2", null);
        mailListReq.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
        mailListReq.setCancelTag(this);
        TioHttpClient.get(mailListReq, new TioCallback<MailListResp>() {
            @Override
            public void onTioSuccess(MailListResp mailListResp) {
                proxy.onSuccess(mailListResp.group);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }
        });
    }

    @Override
    public void setGroupNum(@Nullable MainGroupFragment fragment, int groupNum) {
        if (fragment != null) {
            fragment.setAppendTitle(groupNum);
        } else {
            TioToast.showShort("获取不到父容器");
        }
    }
}