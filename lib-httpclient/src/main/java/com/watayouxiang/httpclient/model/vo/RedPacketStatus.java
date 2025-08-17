package com.watayouxiang.httpclient.model.vo;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/30
 *     desc   :
 * </pre>
 */
public interface RedPacketStatus {
    /**
     * 结束成功
     */
    String SUCCESS = "SUCCESS";
    /**
     * 失败
     */
    String FAIL = "FAIL";
    /**
     * 超时
     */
    String TIMEOUT = "TIMEOUT";
    /**
     * 抢红包中
     */
    String SEND = "SEND";
    /**
     * 取消
     */
    String CANCEL = "CANCEL";
}
