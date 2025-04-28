package com.watayouxiang.imclient.thread;

import com.watayouxiang.imclient.packet.Packet;
import com.watayouxiang.imclient.packet.PacketEncoder;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class SendThread<P extends Packet> extends BaseThread implements SendThreadOperation<P> {
    private Socket mSocket;
    private PacketEncoder<P> mPacketEncoder;
    private LinkedBlockingQueue<P> mPacketQueue;
    private SendThreadStop mStopState;

    public SendThread(Socket socket, PacketEncoder<P> packetEncoder) {
        mSocket = socket;
        mPacketEncoder = packetEncoder;
        mPacketQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void run() {
        super.run();
        onSendThreadStart();
        Exception exception;
        try {
            while (true) {
                //取出消息（阻塞式方法）
                P packet = mPacketQueue.take();

                onSendPacketStart(packet);

                //消息编码
                ByteBuffer packetBuffer = mPacketEncoder.encode(packet);

                //为空处理、判断是否可以安全地调用buffer.array()
                if (packetBuffer == null || !packetBuffer.hasArray()) {
                    onSendPacketCancel(packet);
                    continue;
                }

                //发送消息
                mSocket.getOutputStream().write(packetBuffer.array());
                mSocket.getOutputStream().flush();

                onSendPacketEnd(packet);
            }
        } catch (Exception e) {
            exception = e;
            if (getCloseFlag()) {
                mStopState = SendThreadStop.CLOSE;
            } else {
                mStopState = SendThreadStop.ERROR;
            }
        }
        closeSocket(mSocket);
        onSendThreadStop(mStopState, exception);
        release();
    }

    /**
     * 线程开始
     */
    public abstract void onSendThreadStart();

    /**
     * 发送一条消息开始
     *
     * @param packet 消息
     */
    public abstract void onSendPacketStart(P packet);

    /**
     * 发送一条消息取消
     *
     * @param packet 消息
     */
    public abstract void onSendPacketCancel(P packet);

    /**
     * 发送一条消息完成
     *
     * @param packet 消息
     */
    public abstract void onSendPacketEnd(P packet);

    /**
     * 线程停止
     *
     * @param state 状态
     * @param e     异常
     */
    public abstract void onSendThreadStop(SendThreadStop state, Exception e);

    @Override
    public void stopThread() {
        interrupt();
        try {
            mSocket.shutdownOutput();
        } catch (Exception ignored) {
        }
        try {
            mSocket.getOutputStream().close();
        } catch (Exception ignored) {
        }
    }

    @Override
    void release() {
        super.release();
        mSocket = null;
        mPacketEncoder = null;
        if (mPacketQueue != null) {
            mPacketQueue.clear();
            mPacketQueue = null;
        }
        mStopState = null;
    }

    @Override
    public void putPacket(P packet) {
        if (mPacketQueue != null) {
            try {
                mPacketQueue.put(packet);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
