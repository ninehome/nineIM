package com.watayouxiang.webrtclib.tool;

import android.os.Handler;
import android.os.Looper;

/**
 * author : TaoWang
 * date : 2020/5/6
 * desc :
 */
public class UiHandler extends Handler {
    public UiHandler() {
        super(Looper.getMainLooper());
    }

    public void runOnUiThread(Runnable action) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            post(action);
        } else {
            action.run();
        }
    }
}
