package com.watayouxiang.imclient.thread;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.imclient.client.IMConfig;
import com.watayouxiang.imclient.utils.CloseUtils;
import com.watayouxiang.imclient.utils.SSLUtils;

import java.net.InetSocketAddress;
import java.net.Socket;

public abstract class SocketRunnable implements Runnable {
    private IMConfig mConfig = null;

    public SocketRunnable(@Nullable IMConfig config) {
        mConfig = config;
    }

    @Override
    public void run() {
        onConnectSocketStart();
        Socket socket = null;
        try {

            // 创建 Socket
            if (mConfig.isOpenSsl()) {
                socket = SSLUtils.getSslSocket();
            } else {
                socket = new Socket();
            }
            // 开始连接
            socket.connect(new InetSocketAddress(
                    mConfig.getRemoteIP(),
                    mConfig.getRemotePort()
            ), mConfig.getTimeout());

            release();
            onConnectSocketSuccess(socket);

        } catch (Exception e) {
            CloseUtils.closeSocket(socket);
            release();
            onConnectSocketError(e);
        }
    }

    private void release() {
        mConfig = null;
    }

    /**
     * 连接开始
     */
    public abstract void onConnectSocketStart();

    /**
     * 连接错误
     *
     * @param e 错误
     */
    public abstract void onConnectSocketError(@NonNull Exception e);

    /**
     * 连接成功
     *
     * @param socket 套接字
     */
    public abstract void onConnectSocketSuccess(@NonNull Socket socket);
}
