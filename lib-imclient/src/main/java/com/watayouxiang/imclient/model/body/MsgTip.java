package com.watayouxiang.imclient.model.body;

public class MsgTip extends Base {
    /**
     * 提示码
     * <p>
     * 1: 未登录
     * 2: 没权限
     * 99: 其他错误
     */
    public int code;
    /**
     * 提示等级
     */
    public int level;
    /**
     * 提示内容
     */
    public String msg;
}
