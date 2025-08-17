package com.watayouxiang.imclient.thread;

import com.watayouxiang.imclient.client.IMConfig;
import com.watayouxiang.imclient.utils.SSLUtils;

import java.net.InetSocketAddress;
import java.net.Socket;

public abstract class ConnectThread extends BaseThread {
    private IMConfig mConfig;
    private ConnectThreadStop mStopState;

    public ConnectThread(IMConfig config) {
        mConfig = config;
    }

    @Override
    public void run() {
        super.run();
        onConnectThreadStart();
        Socket socket = null;
        Exception exception = null;
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
            mStopState = ConnectThreadStop.SUCCESS;
        } catch (Exception e) {
            exception = e;
            if (getCloseFlag()) {
                mStopState = ConnectThreadStop.CLOSE;
            } else {
                mStopState = ConnectThreadStop.ERROR;
            }
            closeSocket(socket);
        }
        onConnectThreadStop(mStopState, socket, exception);
        release();
    }

    /**
     * 线程开始
     */
    public abstract void onConnectThreadStart();

    /**
     * 线程停止
     *
     * @param state  状态
     * @param socket 套接字
     * @param e      异常
     */
    public abstract void onConnectThreadStop(ConnectThreadStop state, Socket socket, Exception e);

    @Override
    public void stopThread() {
        interrupt();
    }

    @Override
    void release() {
        super.release();
        mConfig = null;
        mStopState = null;
    }
}
