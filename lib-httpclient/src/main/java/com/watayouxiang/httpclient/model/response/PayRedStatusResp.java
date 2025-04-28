package com.watayouxiang.httpclient.model.response;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/24
 *     desc   :
 * </pre>
 */
public class PayRedStatusResp {

    /**
     * openflag : 1
     * grabstatus : INIT
     * redstatus : SEND
     */

    /**
     * 用户是否开户：1：是；2：否
     */
    private int openflag;
    /**
     * 自己抢的状态：
     * <p>
     * INIT-未抢;
     * SUCCESS-已抢
     */
    private String grabstatus;
    /**
     * 红包状态：
     * <p>
     * SUCCESS-已抢完;
     * TIMEOUT-24小时超时;
     * SEND-抢红包中
     */
    private String redstatus;

    public int getOpenflag() {
        return openflag;
    }

    public void setOpenflag(int openflag) {
        this.openflag = openflag;
    }

    public String getGrabstatus() {
        return grabstatus;
    }

    public void setGrabstatus(String grabstatus) {
        this.grabstatus = grabstatus;
    }

    public String getRedstatus() {
        return redstatus;
    }

    public void setRedstatus(String redstatus) {
        this.redstatus = redstatus;
    }
}
