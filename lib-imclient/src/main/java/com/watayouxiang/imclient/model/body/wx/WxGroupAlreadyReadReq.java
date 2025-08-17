package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseReq;

/**
 * 已读请求： 告诉服务器，某群的信息已经阅读了
 */
public class WxGroupAlreadyReadReq extends BaseReq {
    /**
     * groupid
     */
    public Long g;
    /**
     * 此值可有可无，如果有则是表示该值以前的消息都标识为已读
     */
    public Long mid;
}