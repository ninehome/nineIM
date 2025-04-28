package com.tiocloud.social;

import com.watayouxiang.social.SocialHelper;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/15
 *     desc   :
 * </pre>
 */
public enum TioSocial {
    INSTANCE();

    public SocialHelper socialHelper;

    TioSocial() {
        socialHelper = new SocialHelper.Builder()
                .setQqAppId("{qq_app_id}")
                .setWxAppId("{wx_app_id}")
                .setWxAppSecret("{wx_app_secret}")
                .build();
    }
}
