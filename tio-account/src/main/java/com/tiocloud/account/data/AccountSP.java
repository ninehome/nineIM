package com.tiocloud.account.data;

import com.blankj.utilcode.util.SPStaticUtils;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/22
 *     desc   :
 * </pre>
 */
public class AccountSP {

    private static final String KEY_LOGIN_NAME = "TioAccountKey_loginName";

    public static void putLoginName(String loginName) {
        SPStaticUtils.put(KEY_LOGIN_NAME, loginName, true);
    }

    public static String getLoginName() {
        return SPStaticUtils.getString(KEY_LOGIN_NAME, (String) null);
    }


}
