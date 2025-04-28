package com.watayouxiang.imclient.tool;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/09
 *     desc   :
 * </pre>
 */
public class ThreadHandler {

    private Handler mWtHandler = null;
    private HandlerThread handlerThread = null;

    public ThreadHandler() {
        handlerThread = new HandlerThread("WtThread");
        handlerThread.start();
        mWtHandler = new Handler(handlerThread.getLooper());
    }

    public void post(@NonNull Runnable r) {
        if (Thread.currentThread() != handlerThread) {
            mWtHandler.post(r);
        } else {
            r.run();
        }
    }

    public void postDelayed(@NonNull Runnable r, long delayMillis) {
        mWtHandler.postDelayed(r, delayMillis);
    }

    public void removeCallbacks(@NonNull Runnable r) {
        mWtHandler.removeCallbacks(r);
    }

    public void release() {
        if (mWtHandler != null) {
            mWtHandler.removeCallbacksAndMessages(null);
            mWtHandler.getLooper().quit();
            mWtHandler = null;
        }
        handlerThread = null;
    }

}
