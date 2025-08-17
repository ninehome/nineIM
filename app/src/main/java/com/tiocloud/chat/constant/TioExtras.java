package com.tiocloud.chat.constant;

public interface TioExtras {
    /**
     * 用户id
     */
    String EXTRA_USER_ID = "userId";
    /**
     * 好友申请中
     * <p>
     * {@link com.tiocloud.chat.feature.user.detail.model.NonFriendApply}
     */
    String EXTRA_MODEL_NON_FRIEND_APPLY = "EXTRA_MODEL_NON_FRIEND_APPLY";
    /**
     * 附加消息
     */
    String EXTRA_APPEND_MSG = "EXTRA_APPEND_MSG";
    /**
     * 申请id
     */
    String EXTRA_APPLY_ID = "EXTRA_APPLY_ID";
    /**
     * 备注名
     */
    String EXTRA_REMARK_NAME = "EXTRA_REMARK_NAME";
    /**
     * 群id
     */
    String EXTRA_GROUP_ID = "groupId";
    /**
     * 视频地址
     */
    String EXTRA_VIDEO_URL = "videoUrl";
    /**
     * 修改类型
     */
    String EXTRA_MODIFY_TYPE = "ModifyType";
    /**
     * 回显
     */
    String EXTRA_ECHO = "echo";
    /**
     * 好友的uid
     */
    String FRIEND_UID = "friendUid";
    /**
     * 会话id
     */
    String CHAT_LINK_ID = "chatlinkid";

    /**
     * 会话id
     */
    String BIZID = "chatlinkid";
    /**
     * 消息id（多条 "," 分割）
     */
    String MIDS = "mids";
    /**
     * 页签索引
     */
    String EXTRA_TAB_INDEX = "EXTRA_TAB_INDEX";

    String EXTRA_FIRST_SHOW_EDIT = "first_show_edit";

    String EXTRA_FIRST_CAN_EDIT = "first_can_edit";


}
