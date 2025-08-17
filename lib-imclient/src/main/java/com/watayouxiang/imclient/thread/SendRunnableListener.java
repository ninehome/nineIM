package com.watayouxiang.imclient.thread;

import androidx.annotation.NonNull;

import com.watayouxiang.imclient.packet.Packet;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/09
 *     desc   :
 * </pre>
 */
public interface SendRunnableListener<P extends Packet> {
    public abstract void onSendThreadStart();

    public abstract void onSendThreadStop();

    public abstract void onSendThreadError(@NonNull Exception e);

    public abstract void onSendPacketStart(P packet);

    public abstract void onSendPacketCancel(P packet);

    public abstract void onSendPacketEnd(P packet);
}
