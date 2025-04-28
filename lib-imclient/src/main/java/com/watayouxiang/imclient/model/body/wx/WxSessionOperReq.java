package com.watayouxiang.imclient.model.body.wx;

/**
 * author : TaoWang
 * date : 2020/3/12
 * desc :
 */
public class WxSessionOperReq {
    /**
     * 会话id，离开时可以为空
     */
    public String chatlinkid;
    /**
     * 操作码：1：进入会话；2：跳出所有会话
     */
    public String oper;

    private WxSessionOperReq(String chatlinkid, String oper) {
        this.chatlinkid = chatlinkid;
        this.oper = oper;
    }

    /**
     * 进入
     *
     * @param chatlinkid
     * @return
     */
    public static WxSessionOperReq in(String chatlinkid) {
        return new WxSessionOperReq(chatlinkid, "1");
    }

    /**
     * 跳出
     *
     * @return
     */
    public static WxSessionOperReq out() {
        return new WxSessionOperReq(null, "2");
    }
}
