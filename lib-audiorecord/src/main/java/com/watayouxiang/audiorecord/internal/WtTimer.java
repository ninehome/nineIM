package com.watayouxiang.audiorecord.internal;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/05
 *     desc   : 计时器实现
 * </pre>
 */
public class WtTimer {
    private Timer mTimer;
    private TimerTask mTimerTask;

    /**
     * 启动
     */
    public void start(final OnTickListener listener) {
        stop();

        if (mTimer == null) {
            mTimer = new Timer();
        }
        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                private int mCount;

                @Override
                public void run() {
                    mCount++;
                    if (listener != null) {
                        listener.onTick(mCount);
                    }
                }
            };
        }

        mTimer.schedule(mTimerTask, 1000, 1000);
    }

    /**
     * 停止
     */
    public void stop() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    public interface OnTickListener {
        /**
         * 任务开启后，一秒钟执行一次
         *
         * @param count 执行次数
         */
        void onTick(int count);
    }
}
