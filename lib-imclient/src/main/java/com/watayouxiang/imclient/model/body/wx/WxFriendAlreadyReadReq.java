package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseReq;

/**
 * 我告诉服务器，张三发给我的私聊消息已读
 */
public class WxFriendAlreadyReadReq extends BaseReq {
	/**
	 * 聊天对方的userid
	 */
	public Integer uid;
}