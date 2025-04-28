package com.tiocloud.chat.feature.session.common.model;

public interface SessionExtras {
    /**
     * 返回到哪个Activity
     */
    String EXTRA_BACK_TO_CLASS = "backToClass";
    /**
     * 功能列表
     */
    String EXTRA_ACTIONS = "actions";
    /**
     * 会话id
     */
    String EXTRA_CHAT_LINK_ID = "chatLinkId";
    /**
     * 组id
     */
    String EXTRA_GROUP_ID = "groupId";
    /**
     * 用户id
     */
    String EXTRA_USER_ID = "userId";

    /**
     * 聊天类型
     */
    String CHATMODE = "chatMode";
}
