package com.watayouxiang.audiorecord;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/06
 *     desc   : 语音消息专用播放器，其他地方不要使用
 * </pre>
 */
public class TioAudioPlayer implements WtMediaPlayer.OnPlayerListener {

    private static TioAudioPlayer mTioAudioPlayer = null;

    private WtMediaPlayer mPlayer;
    private String mMsgId;
    private OnPlayerListener mOnPlayerListener;

    private TioAudioPlayer() {
        mPlayer = new WtMediaPlayer();
        mPlayer.setOnPlayerListener(this);
        mPlayer.setBackgroundStopFlag(true);
        mPlayer.setAudioFocusFlag(true);
    }

    public static TioAudioPlayer getInstance() {
        if (mTioAudioPlayer == null) {
            synchronized (TioAudioPlayer.class) {
                if (mTioAudioPlayer == null) {
                    mTioAudioPlayer = new TioAudioPlayer();
                }
            }
        }
        return mTioAudioPlayer;
    }

    /**
     * 初始化
     *
     * @param listener
     * @param msgId
     */
    public void init(@NonNull OnPlayerListener listener, String msgId) {
        if (isPlaying(msgId)) {
            // 初始化通知 "开始播放"
            listener.onWtPlayerStart();
            // 如果正在播放，则设置监听
            mOnPlayerListener = listener;
        } else {
            // 初始化通知 "停止播放"
            listener.onWtPlayerStop();
        }
    }

    /**
     * 开始播放 / 停止播放
     *
     * @param listener
     * @param audioUrl
     * @param msgId
     */
    public void toggle(@NonNull OnPlayerListener listener, @NonNull String audioUrl, String msgId) {
        if (isPlaying(msgId)) {
            // 停止播放
            mPlayer.stop();
        } else {
            // 记录 "消息id"
            mMsgId = msgId;
            // 通知 "上一个" 停止播放
            if (mOnPlayerListener != null) {
                mOnPlayerListener.onWtPlayerStop();
            }
            // 设置监听
            mOnPlayerListener = listener;
            // 开始播放
            mPlayer.initUrl(audioUrl);
            mPlayer.start();
        }
    }

    public void release() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        mMsgId = null;
        mTioAudioPlayer = null;
        mOnPlayerListener = null;
    }

    private boolean isPlaying(String msgId) {
        return mPlayer.isPlaying() && StringUtils.equals(msgId, mMsgId);
    }

    @Override
    public void onWtPlayerStart() {
        if (mOnPlayerListener != null) {
            mOnPlayerListener.onWtPlayerStart();
        }
    }

    @Override
    public void onWtPlayerPause() {

    }

    @Override
    public void onWtPlayerStop() {
        if (mOnPlayerListener != null) {
            mOnPlayerListener.onWtPlayerStop();
        }
    }

    @Override
    public void onWtPlayerError(WtMediaPlayer.ErrorType type) {

    }

    @Override
    public void onWtPlayerCompletion() {
        if (mOnPlayerListener != null) {
            mOnPlayerListener.onWtPlayerStop();
        }
    }

    public interface OnPlayerListener {
        void onWtPlayerStart();

        void onWtPlayerStop();
    }
}
