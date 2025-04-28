package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseResp;

/**
 * 你们不是好友
 * 你发消息给对方时，你并不是对方的好友，这时候前端提示当前用户发送申请好友请求
 */
public class WxNotFriendNtf extends BaseResp {
    /**
     * 对端的userid
     */
    public Integer uid;
    /**
     * 非好友类型
     * 1、对方在当前用户的好友列表中，这时候前端需要走完整的申请好友流程（先申请加对方为好友，然后等对方同意）
     * 2、当前用户在对方的好友列表中，这时候前端需要提示当前用户发送"/wx/friend/addFriendDirectly"请求，直接把对方加为好友
     * 3、双方的好友列表都没有对方（你中无我，我中无你），前端处理逻辑同type=1
     */
    public byte type = 1;
}
