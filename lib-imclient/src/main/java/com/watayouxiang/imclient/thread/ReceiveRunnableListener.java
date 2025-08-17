package com.watayouxiang.imclient.thread;

import androidx.annotation.NonNull;

import com.watayouxiang.imclient.packet.Packet;

import java.nio.ByteBuffer;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/09
 *     desc   :
 * </pre>
 */
public interface ReceiveRunnableListener<P extends Packet> {
    public abstract void onReceiveThreadStart();

    public abstract void onReceiveThreadStop();

    public abstract void onReceiveThreadError(@NonNull Exception e);

    public abstract void onReceivePacketBegin(ByteBuffer byteBuffer);

    public abstract void onReceivePacketCancel();

    public abstract void onReceivePacketEnd(@NonNull P packet);
}
