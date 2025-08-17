package com.watayouxiang.imclient.model.body.webrtc;

/**
 * b回复s：同意通话，或拒绝通话（1、同意通话、2、拒接）
 */
public class WxCall04ReplyNtf extends WxCallItem {
    /**
     * callduration : 0
     * calltime : 2020-04-29 16:35:58
     * fromcid : 1255415443498344448
     * fromdevice : 2
     * fromipid : 651384
     * fromuid : 23436
     * hanguptype : 99
     * id : 798
     * resptime : 2020-04-29 16:36:25
     * respwait : 27788
     * result : 1
     * status : 2
     * streamwait : 0
     * tocid : 1255414868081778688
     * todevice : 1
     * toipid : 651384
     * touid : 22627
     * type : 2
     */

    /**
     * 1 同意通话，2 拒接，3 没有权限
     */
    public int result;
    /**
     * 不能通话的原因，当result=2时，此字段才有值
     */
    public String reason;

}
