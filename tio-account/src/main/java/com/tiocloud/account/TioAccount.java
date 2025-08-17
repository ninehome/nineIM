package com.tiocloud.account;

import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.tiocloud.account.feature.bind_phone.BindPhoneActivity;
import com.tiocloud.account.feature.t_bind_phone.TBindPhoneActivity;
import com.umeng.commonsdk.UMConfigure;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/21
 *     desc   :
 * </pre>
 */
public class TioAccount {

    public static String sitename;
    /**
     * 是否开启短信登录
     */
    public static final boolean OPEN_SMS_LOGIN = true;

    public static boolean useOnkeyLogin = false;

    public static boolean isWxLoginEnable = false;

    private static AccountBridge BRIDGE = null;

    public static String UM_APPKEY;

    public static String UM_APPSECRY;

    public static boolean showRegiest = true;

    public static boolean isOA = false;

    public static boolean inviteEnable = false;

    public static void setUseOnkeyLogin(boolean use){
        useOnkeyLogin = use;
    }

    public static void init(AccountBridge bridge) {
        BRIDGE = bridge;
    }



    public static void initUmVerify(Context context, String appkey, String secry){
        UMConfigure.init(context,appkey,"渠道",UMConfigure.DEVICE_TYPE_PHONE,null);
        UM_APPKEY = appkey;
        UM_APPSECRY = secry;
    }

    public static AccountBridge getBridge() {
        return BRIDGE;
    }

    /**
     * 检测是否绑定手机
     */
    public static void checkIsBindPhone(UserCurrResp resp) {
        boolean bindPhone = resp.isBindPhone();
        boolean thirdbindflag = resp.isThirdbindflag();

        if (!bindPhone) {
            if (thirdbindflag) {
                TBindPhoneActivity.start(Utils.getApp());
            } else {
                BindPhoneActivity.start(Utils.getApp());
            }
        }
    }

}
