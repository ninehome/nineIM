package com.watayouxiang.imclient.thread;

import android.util.Log;

import androidx.annotation.Nullable;

//import com.blankj.utilcode.util.LogUtils;
import com.watayouxiang.imclient.packet.Packet;
import com.watayouxiang.imclient.packet.PacketDecoder;
import com.watayouxiang.imclient.packet.PacketReceiver;
import com.watayouxiang.imclient.utils.CloseUtils;

import java.net.Socket;
import java.nio.ByteBuffer;

public class ReceiveRunnable<P extends Packet> extends BaseRunnable {
    private Socket mSocket = null;
    private PacketDecoder<P> mPacketDecoder = null;
    private PacketReceiver mPacketReceiver = null;
    @Nullable
    private ReceiveRunnableListener<P> mListener = null;

    public ReceiveRunnable(@Nullable Socket socket, @Nullable PacketDecoder<P> packetDecoder, @Nullable PacketReceiver packetReceiver) {
        mSocket = socket;
        mPacketDecoder = packetDecoder;
        mPacketReceiver = packetReceiver;
    }

    @Override
    public void run() {
        setIsRunning(true);
        if (mListener != null) mListener.onReceiveThreadStart();
        try {
            while (true) {
                //接收一个完整消息（阻塞式）
                ByteBuffer buffer = mPacketReceiver.getPacketBuffer(mSocket.getInputStream());
//                LogUtils.e("zlb", "socket:"+mSocket.getInetAddress());
                if (mListener != null) mListener.onReceivePacketBegin(buffer);

                //消息解码
                P packet = mPacketDecoder.decode(buffer);

                if (packet == null) {
                    if (mListener != null) mListener.onReceivePacketCancel();
                } else {
                    if (mListener != null) mListener.onReceivePacketEnd(packet);
                }
            }
        } catch (Exception e) {
            setIsRunning(false);
            CloseUtils.closeSocket(mSocket);
            release();

            if (getCloseFlag()) {
                if (mListener != null) mListener.onReceiveThreadStop();
            } else {
                if (mListener != null) mListener.onReceiveThreadError(e);
            }
        }
    }

    private void release() {
        mSocket = null;
        mPacketDecoder = null;
        mPacketReceiver = null;
    }

    public void setListener(@Nullable ReceiveRunnableListener<P> listener) {
        mListener = listener;
    }
}
