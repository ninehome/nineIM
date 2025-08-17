package com.watayouxiang.imclient.thread;

public abstract class BaseRunnable implements Runnable {
    private boolean mCloseFlag = false;
    private boolean mIsRunning = false;

    public boolean isIsRunning() {
        return mIsRunning;
    }

    void setIsRunning(boolean isRunning) {
        this.mIsRunning = isRunning;
    }

    /**
     * 是否关闭线程
     * <p>
     * true: 停止线程，如果有错误，不会报错
     * false: 杀死线程，如果有错误，会报错
     */
    boolean getCloseFlag() {
        return mCloseFlag;
    }

    /**
     * 标记关闭线程
     * <p>
     * true: 停止线程，如果有错误，不会报错
     * false: 杀死线程，如果有错误，会报错
     */
    public void setCloseFlag(boolean closeFlag) {
        mCloseFlag = closeFlag;
    }
}
