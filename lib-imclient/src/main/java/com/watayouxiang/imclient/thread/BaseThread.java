package com.watayouxiang.imclient.thread;

import java.net.Socket;

abstract class BaseThread extends Thread {
    /**
     * 关闭Socket
     *
     * @param socket SSLSocket
     */
    void closeSocket(Socket socket) {
        try {
            socket.shutdownInput();
        } catch (Exception ignored) {
        }
        try {
            socket.shutdownOutput();
        } catch (Exception ignored) {
        }
        try {
            socket.getInputStream().close();
        } catch (Exception ignored) {
        }
        try {
            socket.getOutputStream().close();
        } catch (Exception ignored) {
        }
        try {
            socket.close();
        } catch (Exception ignored) {
        }
    }

    private boolean mCloseFlag = false;

    /**
     * 是否关闭线程
     *
     * @return true关闭线程，false杀死线程
     */
    boolean getCloseFlag() {
        return mCloseFlag;
    }

    /**
     * 标记关闭线程
     * true: 关闭线程，不会报错
     * false: 杀死线程，抛出异常
     *
     * @param closeFlag 关闭标记
     */
    public void setCloseFlag(boolean closeFlag) {
        mCloseFlag = closeFlag;
    }

    /**
     * 停止线程
     */
    public abstract void stopThread();

    /**
     * 释放资源
     */
    void release() {
        mCloseFlag = false;
    }
}
