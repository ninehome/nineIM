package com.watayouxiang.imclient.model.body.wx.msg;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 11/24/20
 *     desc   :
 * </pre>
 */
public class InnerMsgGroupApply {
    /**
     * 群id
     */
    private Long groupid;

    /**
     * 申请人
     */
    private Integer operuid;

    /**
     * 申请文案
     */
    private String applymsg;

    /**
     * 申请id
     */
    private Integer id;

    /**
     * 申请状态
     * 申请状态：1：已处理；2：申请中；3：已驳回
     */
    private Byte status;

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public Integer getOperuid() {
        return operuid;
    }

    public void setOperuid(Integer operuid) {
        this.operuid = operuid;
    }

    public String getApplymsg() {
        return applymsg;
    }

    public void setApplymsg(String applymsg) {
        this.applymsg = applymsg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
