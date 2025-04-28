package com.tiocloud.chat.feature.session.common.proxy;

import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.watayouxiang.androidutils.recyclerview.BaseFetchLoadAdapter;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/04
 *     desc   :
 * </pre>
 */
public interface MsgListProxy {
    void sendMsg(TioMsg message);

    void deleteMsg(long mid);

    void deleteUserMsg(long uid);

    void clearList();

    void setNewData(List<TioMsg> msgList);

    void fetchMoreEnd(List<TioMsg> msgList);

    void fetchMoreComplete(List<TioMsg> msgList);

    void readAllMsg();

    void scrollToBottom();

    void scrollToPosition(int p);

    void scrollToBottomDelayed();

    void setOnFetchMoreListener(BaseFetchLoadAdapter.RequestFetchMoreListener requestFetchMoreListener);
}
