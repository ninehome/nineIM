package com.watayouxiang.imclient.client;

import com.watayouxiang.imclient.packet.Packet;

interface IMCallbackIterator<P extends Packet> {
    /**
     * 遍历所有的回调
     *
     * @param callback 回调
     */
    void onIterator(IMCallback<P> callback);
}
