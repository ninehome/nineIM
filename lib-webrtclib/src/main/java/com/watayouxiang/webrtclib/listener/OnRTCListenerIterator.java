package com.watayouxiang.webrtclib.listener;

/**
 * author : TaoWang
 * date : 2020/5/22
 * desc :
 */
public interface OnRTCListenerIterator {
    /**
     * 遍历所有 OnRTCListener
     *
     * @param listener
     */
    void onIterator(OnRTCListener listener);
}
