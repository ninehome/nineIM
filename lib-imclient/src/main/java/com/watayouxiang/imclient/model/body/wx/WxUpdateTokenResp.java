package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseResp;
import com.watayouxiang.imclient.packet.TioCommand;

/**
 * author : TaoWang
 * date : 2020/6/3
 * desc :
 *
 * @see TioCommand#WX_UPDATE_TOKEN_RESP
 */
public class WxUpdateTokenResp extends BaseResp {

    /**
     * code : 1
     */

    public int code;

    @Override
    public String toString() {
        return "WxUpdateTokenResp{" +
                "code=" + code +
                '}';
    }
}
