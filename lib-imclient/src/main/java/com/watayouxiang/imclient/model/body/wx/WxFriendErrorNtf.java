package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseResp;
import com.watayouxiang.imclient.packet.TioCommand;

/**
 * author : TaoWang
 * date : 2020/3/11
 * desc : 异常通知
 * <p>
 * {@link TioCommand#WX_FRIEND_ERROR_NTF}
 */
public class WxFriendErrorNtf extends BaseResp {
    /**
     * chatlinkid : 1208
     * code : 20001
     * mid : 380372
     * msg : 系统异常
     * t : 1583911965842
     * touid : 23436
     * uid : 23436
     */

    public String chatlinkid;
    public int code;
    public String mid;
    public String msg;
    public long t;
    public int touid;
    public int uid;

    // ====================================================================================
    // inner
    // ====================================================================================

    public Code getCode() {
        return Code.valueOf(code);
    }

    public enum Code {
        // 禁止用户操作的错误码
        NOT_LOGIN(1001, "没有登录"),
        TIMEOUT(1002, "登录超时"),
        KICK_OUT(1003, "帐号在其它地方登录"),
        NOT_PERMISSION(1004, "登录了，但是没有权限操作"),
        REFUSE(1005, "拒绝访问"),
        NEED_ACCESS_TOKEN(1006, "需要提供正确的access_token"),
        CAPTCHA_ERROR(1007, "图形验证异常"),
        // 数据库操作的错误码
        RECORD_REPEAT(100001, "记录重复"),
        // 好友相关错误码
        SYS_ERROR(20001, "系统异常"),
        BLACK(20002, "拉黑状态"),
        NO_LINK(20003, "未关联-不是好友"),

        NOT_GROUP_MEMBER(30002, "你不在群组成员里"),
        ;

        private int code;
        private String desc;

        Code(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static Code valueOf(int code) {
            Code[] values = values();
            for (Code v : values) {
                if (v.code == code)
                    return v;
            }
            return null;
        }
    }
}
