package com.watayouxiang.social;

import com.watayouxiang.social.callback.SocialLoginCallback;
import com.watayouxiang.social.callback.SocialShareCallback;
import com.watayouxiang.social.entities.ShareEntity;
import com.watayouxiang.social.entities.ThirdInfoEntity;

/**
 * Created by arvinljw on 17/11/24 16:06
 * Function：
 * Desc：
 */
public interface ISocial {
    void login(SocialLoginCallback callback);

    ThirdInfoEntity createThirdInfo();

    void share(SocialShareCallback callback, ShareEntity shareInfo);

    void onDestroy();
}
