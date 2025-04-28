package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseResp;

public class WxHandshakeResp extends BaseResp {
    public String cid;

    @Override
    public String toString() {
        return "WxHandshakeResp{" +
                "cid='" + cid + '\'' +
                '}';
    }
}
