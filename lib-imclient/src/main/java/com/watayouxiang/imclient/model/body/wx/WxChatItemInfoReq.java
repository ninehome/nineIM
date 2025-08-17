package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseReq;

/**
 * author : TaoWang
 * date : 2020/3/12
 * desc : 会话详情信息请求
 */
public class WxChatItemInfoReq extends BaseReq {
    private String chatlinkid;

    public WxChatItemInfoReq(String chatlinkid) {
        this.chatlinkid = chatlinkid;
    }
}
