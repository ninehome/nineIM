package com.watayouxiang.imclient.model.body;

public class GroupChatReq extends BaseReq {
    /**
     * 聊天内容
     */
    public String c;
    /**
     * 艾特哪些用户。此值可为null。
     * 举例：[434343, 9898989]
     */
    public Integer[] at;
    /**
     * groupId
     * 举例：45454
     */
    public String g;
}
