package com.watayouxiang.audiorecord.utils;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import com.blankj.utilcode.util.Utils;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/06/18
 *     desc   : 音频焦点
 *
 *     安卓是多应用的系统，后台可能有多个程序在同时运行。
 *     假如你在听音乐，这个时候突然打进来电话。如果音乐播放没有停止的话，
 *     那么你的和别人打电话的时候就会自带BGM，这种体验多数时候并不是让人那么愉快的。
 *
 *     为了解决这个问题，自android 2.2起，引入了Audio Focus用于让每个应用协调谁来使用扬声器播放。
 *     当某个应用获得了audio focus，那么它可以自由的使用扬声器。
 *     同时需要实现focus change接口，当其它应用获取audio focus时回调，及时的停止音乐的播放。
 *
 *     当然了，这并不是一种强制的机制，哪怕你失去了audio focus，依旧可以播放音乐。
 *     没有什么可以阻止你，但多半用户会关掉你。
 * </pre>
 */
public class AudioFocusUtils {
    private static final String TAG = "AudioFocusTag";

    private static class InnerHolder {
        private static final AudioFocusUtils mHolder = new AudioFocusUtils();
    }

    public static AudioFocusUtils getInstance() {
        return InnerHolder.mHolder;
    }

    private AudioFocusUtils() {
    }

    private OnAudioFocusChangeListener mListener = null;
    private final AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    // 可以继续播放
                    Log.d(TAG, "onAudioFocusGain");
                    if (mListener != null) mListener.onAudioFocusGain();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    // 失去Audio Focus，应该停止播放并释放资源。
                    Log.d(TAG, "onAudioFocusLoss");
                    if (mListener != null) mListener.onAudioFocusLoss();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    // 暂时失去，可能一会还会获得，可以停止播放但是不需要释放资源
                    Log.d(TAG, "onAudioFocusLossTransient");
                    if (mListener != null) mListener.onAudioFocusLossTransient();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    // 失去Focus，但是可以以比较小的声音继续播放。
                    Log.d(TAG, "onAudioFocusLossTransientCanDuck");
                    if (mListener != null) mListener.onAudioFocusLossTransientCanDuck();
                    break;
            }
        }
    };

    public void requestAudioFocus(OnAudioFocusChangeListener listener) {
        mListener = listener;

        AudioManager audioManager = (AudioManager) Utils.getApp().getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                Log.d(TAG, "requestAudioFocus failed");
                int i = audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
            } else {
                Log.d(TAG, "requestAudioFocus success");
            }
        }
    }

    public void abandonAudioFocus() {
        mListener = null;

        AudioManager audioManager = (AudioManager) Utils.getApp().getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            int i = audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    public interface OnAudioFocusChangeListener {
        void onAudioFocusGain();

        void onAudioFocusLoss();

        void onAudioFocusLossTransient();

        void onAudioFocusLossTransientCanDuck();
    }

    public static class SimpleOnAudioFocusChangeListener implements OnAudioFocusChangeListener{
        @Override
        public void onAudioFocusGain() {

        }

        @Override
        public void onAudioFocusLoss() {

        }

        @Override
        public void onAudioFocusLossTransient() {

        }

        @Override
        public void onAudioFocusLossTransientCanDuck() {

        }
    }
}
