package com.tiocloud.chat.wxapi;

import com.tiocloud.social.TioSocial;
import com.watayouxiang.social.SocialHelper;
import com.watayouxiang.social.WXHelperActivity;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 12/16/20
 *     desc   :
 * </pre>
 */
public class WXEntryActivity extends WXHelperActivity {
    @Override
    protected SocialHelper getSocialHelper() {
        return TioSocial.INSTANCE.socialHelper;
    }
}
