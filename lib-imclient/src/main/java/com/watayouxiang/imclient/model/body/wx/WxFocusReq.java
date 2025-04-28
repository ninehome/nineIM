package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseReq;
import com.watayouxiang.imclient.packet.TioCommand;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/31
 *     desc   : 焦点请求
 * </pre>
 */
public class WxFocusReq extends BaseReq {
    public static final short COMMAND = TioCommand.WX_FOCUS_REQ;

}
