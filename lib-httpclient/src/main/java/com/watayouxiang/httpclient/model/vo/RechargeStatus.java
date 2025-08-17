package com.watayouxiang.httpclient.model.vo;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/30
 *     desc   :
 * </pre>
 */
public interface RechargeStatus {
    /**
     * 正常
     */
    String SUCCESS = "SUCCESS";
    /**
     * 失败
     */
    String FAIL = "FAIL";
    /**
     * 处理中
     */
    String PROCESS = "PROCESS";
    /**
     * 初始化
     */
    String INIT = "INIT";
    /**
     * 取消
     */
    String CANCEL = "CANCEL";
}
