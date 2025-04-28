package com.watayouxiang.httpclient.model.response;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/06
 *     desc   :
 * </pre>
 */
public class ForbiddenFlagResp {

    /**
     * flag : 2
     * grant : 1
     */

    /**
     * forbiddenflag（禁言标识：1：时长禁言；2：未禁言；3：长久禁用）
     */
    private int flag;
    /**
     * 权限字段：1：有权限禁言；2：无权限
     */
    private int grant;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getGrant() {
        return grant;
    }

    public void setGrant(int grant) {
        this.grant = grant;
    }

    // ====================================================================================
    // 拓展
    // ====================================================================================

    /**
     * 是否有禁言权限
     */
    public boolean haveForbiddenPermission() {
        return grant == 1;
    }

    /**
     * 是否被禁言
     */
    public boolean isForbidden() {
        return flag == 1 || flag == 3;
    }

    /**
     * 禁言类型：1：用户时长禁言；3：用户长久禁言 ； 4：群禁言--------必填
     */
    public String getForbiddenMode() {
        if (flag == 1 || flag == 3) {
            return flag + "";
        }
        return null;
    }
}
