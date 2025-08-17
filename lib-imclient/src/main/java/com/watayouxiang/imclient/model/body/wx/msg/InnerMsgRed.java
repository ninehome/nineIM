package com.watayouxiang.imclient.model.body.wx.msg;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 11/24/20
 *     desc   :
 * </pre>
 */
public class InnerMsgRed {
    /**
     * 红包订单号
     */
    public String serialnumber;

    /**
     * 红包文案
     */
    public String text;

    /**
     * 类型：1：普通红包；2：手气红包
     */
    public int mode;

    /**
     * 红包状态：
     * <p>
     * SUCCESS-已抢完;
     * TIMEOUT-24小时超时;
     * SEND-抢红包中
     */
    public String status;
}
