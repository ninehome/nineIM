package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.packet.TioCommand;

/**
 * author : TaoWang
 * date : 2020/6/3
 * desc :
 *
 * @see TioCommand#WX_UPDATE_TOKEN_REQ
 */
public class WxUpdateTokenReq {
    /**
     * 新的token
     */
    public String t;
    /**
     * 旧的token
     */
    public String o;

    public WxUpdateTokenReq(String newToken, String oldToken) {
        this.t = newToken;
        this.o = oldToken;
    }

    @Override
    public String toString() {
        return "WxUpdateTokenReq{" +
                "t='" + t + '\'' +
                ", o='" + o + '\'' +
                '}';
    }
}
