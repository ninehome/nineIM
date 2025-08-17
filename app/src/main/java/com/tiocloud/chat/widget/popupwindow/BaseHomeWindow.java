package com.tiocloud.chat.widget.popupwindow;

import android.view.View;

import com.watayouxiang.httpclient.TioHttpClient;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/22
 *     desc   :
 * </pre>
 */
abstract class BaseHomeWindow extends BasePopupWindow {
    public BaseHomeWindow(View anchor) {
        super(anchor);
    }

    @Override
    public void show() {
        showAnchorCenterDown();
        setAnchorBg();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        TioHttpClient.cancel(this);
        cancelAnchorBg();
    }
}
