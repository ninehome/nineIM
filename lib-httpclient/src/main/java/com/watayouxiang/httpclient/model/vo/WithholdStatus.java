package com.watayouxiang.httpclient.model.vo;

import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/30
 *     desc   : 提现状态
 * </pre>
 */
public class WithholdStatus {
    /**
     * 正常
     */
    public static final String SUCCESS = "SUCCESS";
    /**
     * 失败
     */
    public static final String FAIL = "FAIL";
    /**
     * 处理中
     */
    public static final String PROCESS = "PROCESS";
    /**
     * 初始化
     */
    public static final String INIT = "INIT";
    /**
     * 取消
     */
    public static final String CANCEL = "CANCEL";

    public static boolean isSuccess(String status) {
        return SUCCESS.equals(status);
    }

    @Nullable
    public static String getDesc(String status) {
        String desc = null;
        if (SUCCESS.equals(status)) {
            desc = "正常";
        } else if (FAIL.equals(status)) {
            desc = "失败";
        } else if (PROCESS.equals(status)) {
            desc = "处理中";
        } else if (INIT.equals(status)) {
            desc = "初始化";
        } else if (CANCEL.equals(status)) {
            desc = "取消";
        }
        return desc;
    }
}
