package com.watayouxiang.qrcode;

import android.content.Context;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/29
 *     desc   :
 * </pre>
 */
public interface QRCodeBridge {
    void openP2PCard(Context context, String uid);

    void openGroupCard(Context context, String groupId, String shareFromUid, OpenGroupCardListener listener);

    interface OpenGroupCardListener {
        void onOpenGroupCardSuccess();

        void onOpenGroupCardError();
    }
}
