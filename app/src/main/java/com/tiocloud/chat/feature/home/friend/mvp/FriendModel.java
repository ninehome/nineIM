package com.tiocloud.chat.feature.home.friend.mvp;

import androidx.annotation.Nullable;

import com.lzy.okgo.cache.CacheMode;
import com.tiocloud.chat.feature.home.friend.adapter.model.IData;
import com.tiocloud.chat.feature.home.friend.task.maillist.MailListTask;
import com.tiocloud.chat.feature.home.friend.task.maillist.MailListTaskProxy;
import com.tiocloud.chat.feature.main.fragment.MainFriendFragment;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.ApplyDataReq;
import com.watayouxiang.androidutils.widget.TioToast;

/**
 * author : TaoWang
 * date : 2020-02-17
 * desc :
 */
public class FriendModel extends FriendContract.Model {

    // ====================================================================================
    // 新好友申请个数
    // ====================================================================================

    @Override
    public void requestApplyData(final TioCallback<Integer> proxy) {
        ApplyDataReq applyDataReq = new ApplyDataReq();
        applyDataReq.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
        applyDataReq.setCancelTag(this);
        applyDataReq.get(proxy);
    }

    // ====================================================================================
    // 联系人接口
    // ====================================================================================

    private MailListTask mailListTask;

    @Override
    public void requestMailList(IData data, MailListTaskProxy proxy) {
        cancelMailList();
        mailListTask = new MailListTask(proxy);
        mailListTask.execute(data);
    }

    private void cancelMailList() {
        if (mailListTask != null) {
            mailListTask.setCancel(true);
            mailListTask.cancel(false);
        }
    }

    @Override
    public void detachModel() {
        super.detachModel();
        cancelMailList();
    }

    // ====================================================================================
    // 设置好友数量
    // ====================================================================================

    @Override
    public void setFriendNum(@Nullable MainFriendFragment fragment, int friendNum) {
        if (fragment != null) {
            fragment.setAppendTitle(friendNum);
        } else {
            TioToast.showShort("获取不到父容器");
        }
    }
}
