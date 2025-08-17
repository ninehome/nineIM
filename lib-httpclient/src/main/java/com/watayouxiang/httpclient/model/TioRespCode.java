package com.watayouxiang.httpclient.model;

/**
 * author : TaoWang
 * date : 2020-01-10
 * desc : 本类定义的是全局Code，用户在各自业务中不要定义与本类相同的Code
 */
public interface TioRespCode {
    /**
     * 禁止用户操作的错误码
     */
    interface ForbidOper {

        int START = 1000;
        /**
         * 没有登录
         */
        int NOTLOGIN = START + 1;
        /**
         * 登录超时
         */
        int TIMEOUT = START + 2;
        /**
         * 帐号在其它地方登录
         */
        int KICKTED = START + 3;
        /**
         * 登录了，但是没有权限操作
         */
        int NOTPERMISSION = START + 4;
        /**
         * 拒绝访问
         */
        int REFUSE = START + 5;
        /**
         * 需要提供正确的access_token
         */
        int NEED_ACCESS_TOKEN = START + 6;
        /**
         * 图形验证异常
         */
        int CAPTCHA_ERROR = START + 7;
    }

    /**
     * 数据库操作的错误码
     */
    interface Db {

        int START = 10000;
        /**
         * 记录重复
         */
        int RECORD_REPEAT = 1 + START;
    }
}
