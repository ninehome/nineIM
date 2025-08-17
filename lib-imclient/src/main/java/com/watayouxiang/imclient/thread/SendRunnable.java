package com.watayouxiang.imclient.thread;

import androidx.annotation.Nullable;

import com.watayouxiang.imclient.packet.Packet;
import com.watayouxiang.imclient.packet.PacketEncoder;
import com.watayouxiang.imclient.utils.CloseUtils;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;

public class SendRunnable<P extends Packet> extends BaseRunnable {
    private Socket mSocket = null;
    private PacketEncoder<P> mPacketEncoder = null;
    private LinkedBlockingQueue<P> mPacketQueue = null;
    @Nullable
    private SendRunnableListener<P> mListener = null;

    public SendRunnable(@Nullable Socket socket, @Nullable PacketEncoder<P> packetEncoder) {
        mSocket = socket;
        mPacketEncoder = packetEncoder;
        mPacketQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void run() {
        setIsRunning(true);
        if (mListener != null) mListener.onSendThreadStart();
        try {
            while (true) {
                //取出消息（阻塞式方法）
                P packet = mPacketQueue.take();

                if (mListener != null) mListener.onSendPacketStart(packet);

                //消息编码
                ByteBuffer packetBuffer = mPacketEncoder.encode(packet);

                //为空处理、判断是否可以安全地调用buffer.array()
                if (packetBuffer == null || !packetBuffer.hasArray()) {
                    if (mListener != null) mListener.onSendPacketCancel(packet);
                    continue;
                }

                //发送消息
                mSocket.getOutputStream().write(packetBuffer.array());
                mSocket.getOutputStream().flush();
                if (mListener != null) mListener.onSendPacketEnd(packet);
            }
        } catch (Exception e) {
            setIsRunning(false);
            CloseUtils.closeSocket(mSocket);
            release();

            if (getCloseFlag()) {
                if (mListener != null) mListener.onSendThreadStop();
            } else {
                if (mListener != null) mListener.onSendThreadError(e);
            }
        }
    }

    private void release() {
        mSocket = null;
        mPacketEncoder = null;
        if (mPacketQueue != null) {
            mPacketQueue.clear();
            mPacketQueue = null;
        }
    }

    public void putPacket(P packet) {
        try {
            if (mPacketQueue == null) return;
            if (packet == null) return;
            if (getCloseFlag()) return;
            mPacketQueue.put(packet);
        } catch (Exception ignored) {
        }
    }

    public void setListener(@Nullable SendRunnableListener<P> listener) {
        mListener = listener;
    }
}
