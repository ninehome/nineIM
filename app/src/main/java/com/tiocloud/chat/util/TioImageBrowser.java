package com.tiocloud.chat.util;

import com.watayouxiang.androidutils.engine.MNImageBrowserUtils;
import com.watayouxiang.httpclient.prefernces.HttpCache;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/23
 *     desc   :
 * </pre>
 */
public class TioImageBrowser extends MNImageBrowserUtils {
    private static class InnerHolder {
        private static final TioImageBrowser browser = new TioImageBrowser();
    }

    public static TioImageBrowser getInstance() {
        return InnerHolder.browser;
    }

    private TioImageBrowser() {
    }

    @Override
    public String getResUrl(String imgUrl) {
        return HttpCache.getResUrl(imgUrl);
    }
}
