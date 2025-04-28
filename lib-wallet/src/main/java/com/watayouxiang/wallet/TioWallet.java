package com.watayouxiang.wallet;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/03
 *     desc   :
 * </pre>
 */
public class TioWallet {
    /**
     * 模块开关
     */
    public static final boolean IS_OPEN = true;

    public static String KEY = "";
    /**
     * 调试开关
     */
    public static final boolean IS_DEBUG = BuildConfig.DEBUG;
    /**
     * 支付用户服务协议
     */
    public static final String PAY_EASE_SERVICE_AGREEMENT = "https://merchant.5upay.com/webox/agreement/serviceAgreement.html";
    /**
     * 支付隐私政策
     */
    public static final String PAY_EASE_PRIVACY_POLICY = "https://merchant.5upay.com/webox/agreement/privacyPolicy.html";
}
