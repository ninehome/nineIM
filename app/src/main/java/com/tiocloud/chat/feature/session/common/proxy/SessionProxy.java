package com.tiocloud.chat.feature.session.common.proxy;

import android.view.View;

import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc : 会话层代理
 */
public interface SessionProxy {
    /**
     * 返回按钮通知
     *
     * @return 是否消耗返回事件
     */
    boolean onBackPressed();

    /**
     * 发送消息
     *
     * @param msg 消息
     * @return 发送是否成功
     */
    boolean sendMessage(String msg);

    /**
     * 输入区展开通知
     */
    void onInputPanelExpand();

    /**
     * 收起输入区
     */
    void collapseInputPanel();

    /**
     * 长按头像
     */
    boolean onAvatarLongClick(View v, TioMsg msg);
}
