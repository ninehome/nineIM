package com.watayouxiang.imclient.model.body.wx.msg;

/**
 * <pre>
 *     author : zlb
 *     desc   :
 * </pre>
 */
public class InnerMsgTransAmount {
    public Long id;
    /**
     * 订单号
     */
    public String serial;

    /**
     * 备注
     */
    public String remark;
    /**
     *  1-发包 2-收包
     */
    public Integer type;
    /**
     * 金额：分
     */
    public Integer amount;

    /**
     * 状态
     */
    public Integer status;

    public boolean isSendMsg;
}
