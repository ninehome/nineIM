package com.watayouxiang.imclient.thread;

import com.watayouxiang.imclient.packet.Packet;
import com.watayouxiang.imclient.packet.PacketDecoder;
import com.watayouxiang.imclient.packet.PacketReceiver;

import java.net.Socket;
import java.nio.ByteBuffer;

public abstract class ReceiveThread<P extends Packet> extends BaseThread {
    private Socket mSocket;
    private PacketDecoder<P> mPacketDecoder;
    private PacketReceiver mPacketReceiver;
    private ReceiveThreadStop mStopState;

    public ReceiveThread(Socket socket, PacketDecoder<P> packetDecoder, PacketReceiver packetReceiver) {
        mSocket = socket;
        mPacketDecoder = packetDecoder;
        mPacketReceiver = packetReceiver;
    }

    @Override
    public void run() {
        super.run();
        onReceiveThreadStart();
        Exception exception;
        try {
            while (true) {
                //接收一个完整消息（阻塞式）
                ByteBuffer packetBuffer = mPacketReceiver.getPacketBuffer(mSocket.getInputStream());

                onReceivePacketBegin(packetBuffer);

                //消息解码
                P packet = mPacketDecoder.decode(packetBuffer);

                if (packet == null) {
                    onReceivePacketCancel();
                } else {
                    onReceivePacketEnd(packet);
                }
            }
        } catch (Exception e) {
            exception = e;
            if (getCloseFlag()) {
                mStopState = ReceiveThreadStop.CLOSE;
            } else {
                mStopState = ReceiveThreadStop.ERROR;
            }
        }
        closeSocket(mSocket);
        onReceiveThreadStop(mStopState, exception);
        release();
    }

    /**
     * 线程开始
     */
    public abstract void onReceiveThreadStart();

    /**
     * 接收一条消息开始
     *
     * @param byteBuffer 字节缓冲区
     */
    public abstract void onReceivePacketBegin(ByteBuffer byteBuffer);

    /**
     * 接收一条消息取消
     */
    public abstract void onReceivePacketCancel();

    /**
     * 接收一条消息完成
     *
     * @param packet 消息
     */
    public abstract void onReceivePacketEnd(P packet);

    /**
     * 线程停止
     *
     * @param state 状态
     * @param e     异常
     */
    public abstract void onReceiveThreadStop(ReceiveThreadStop state, Exception e);

    @Override
    public void stopThread() {
        interrupt();
        try {
            mSocket.shutdownInput();
        } catch (Exception ignored) {
        }
        try {
            mSocket.getInputStream().close();
        } catch (Exception ignored) {
        }
    }

    @Override
    void release() {
        super.release();
        mSocket = null;
        mPacketDecoder = null;
        mPacketReceiver = null;
        mStopState = null;
    }
}
