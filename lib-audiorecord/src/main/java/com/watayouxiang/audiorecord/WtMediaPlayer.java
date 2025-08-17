package com.watayouxiang.audiorecord;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CloseUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.Utils;
import com.watayouxiang.audiorecord.utils.AudioFocusUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/03
 *     desc   : 音频播放器
 *
 *     参考：https://www.imooc.com/u/3778140/courses?sort=publish
 * </pre>
 */
public class WtMediaPlayer {
    private static final String TAG = "TaoWang";

    @NonNull
    private ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
    @Nullable
    private MediaPlayer mMediaPlayer;
    @Nullable
    private OnPlayerListener mOnPlayerListener;
    @Nullable
    private File mParamFile;
    @Nullable
    private String mParamString;
    @Nullable
    private InitType mParamInitType;
    private boolean mIsPlaying = false;
    private boolean mAudioFocusFlag = false;

    // ====================================================================================
    // public
    // ====================================================================================

    public void initFile(@NonNull File audioFile) {
        init(InitType.FILE, audioFile, null);
    }

    public void initUrl(@NonNull String urlString) {
        init(InitType.URL_STRING, null, urlString);
    }

    public void initAssert(@NonNull String assetsString) {
        init(InitType.ASSETS_STRING, null, assetsString);
    }

    public void setVolume(float volume) {
        setVolume(volume, volume);
    }

    public void setLooping(final boolean looping) {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                if (mMediaPlayer != null) {
                    mMediaPlayer.setLooping(looping);
                }
            }
        });
    }

    public void start() {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                startInternal();
            }
        });
    }

    public void pause() {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                pauseInternal();
            }
        });
    }

    public void stop() {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                stopInternal();
            }
        });
    }

    public void reset() {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                resetInternal();
            }
        });
    }

    /**
     * 释放资源，不再使用
     */
    public synchronized void release() {
        if (mExecutorService != null) {
            mExecutorService.shutdownNow();
            mExecutorService = null;
        }
        mOnPlayerListener = null;
        resetPlayer(true);
        abandonAudioFocus();
        unregisterAppStatusChangedListener();
    }

    public synchronized void setOnPlayerListener(OnPlayerListener listener) {
        this.mOnPlayerListener = listener;
    }

    public synchronized boolean isPlaying() {
        return mIsPlaying;
    }

    public synchronized void setAudioFocusFlag(boolean flag) {
        mAudioFocusFlag = flag;
    }

    public void setBackgroundStopFlag(boolean isBackgroundStop) {
        if (isBackgroundStop) {
            registerAppStatusChangedListener();
        } else {
            unregisterAppStatusChangedListener();
        }
    }

    // ====================================================================================
    // private
    // ====================================================================================

    private void init(@NonNull final InitType initType, final File file, final String string) {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                initInternal(initType, file, string);
            }
        });
    }

    private void setVolume(final float leftVolume, final float rightVolume) {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                if (mMediaPlayer != null) {
                    mMediaPlayer.setVolume(leftVolume, rightVolume);
                }
            }
        });
    }

    // ====================================================================================
    // internal
    // ====================================================================================

    /**
     * 初始化
     *
     * @param initType {@link InitType}
     *                 url string: 链接地址
     *                 assets string: assets目录下的文件
     * @param file     音频文件 模式初始化
     * @param string   string 模式初始化
     */
    private void initInternal(@NonNull InitType initType, File file, String string) {
        resetPlayer(true);

        mParamFile = file;
        mParamString = string;
        mParamInitType = initType;

        mMediaPlayer = new MediaPlayer();
        FileInputStream stream = null;
        AssetFileDescriptor descriptor = null;

        try {
            if (initType == InitType.FILE) {
                if (file == null) throw new NullPointerException("初始化参数 file");
                stream = new FileInputStream(file);
                mMediaPlayer.setDataSource(stream.getFD());
            } else if (initType == InitType.URL_STRING) {
                if (string == null) throw new NullPointerException("初始化参数 url string");
                // url string
                mMediaPlayer.setDataSource(string);
            } else if (initType == InitType.ASSETS_STRING) {
                if (string == null) throw new NullPointerException("初始化参数 assets string");
                // assets string
                descriptor = Utils.getApp().getResources().getAssets().openFd(string);
                mMediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            } else {
                throw new RuntimeException("MediaPlayer.setDataSource error");
            }

            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mOnPlayerListener != null) {
                                mOnPlayerListener.onWtPlayerCompletion();
                            }
                        }
                    });
                    mIsPlaying = false;
                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    WtMediaPlayer.this.notifyError(ErrorType.PLAYER);
                    return true;// 表示错误被处理了
                }
            });

            mMediaPlayer.setVolume(1.0f, 1.0f);
            mMediaPlayer.setLooping(false);

            mMediaPlayer.prepare();
        } catch (RuntimeException | IOException e) {
            notifyError(ErrorType.INIT);
        } finally {
            CloseUtils.closeIO(stream);
            CloseUtils.closeIO(descriptor);
        }
    }

    private void startInternal() {
        try {
            // 如果停止播放，尝试先先初始化
            if (mMediaPlayer == null && mParamInitType != null) {
                initInternal(mParamInitType, mParamFile, mParamString);
            }
            // 如果没有播放，则播放
            if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
                if (mAudioFocusFlag) {
                    requestAudioFocus();
                }
                mMediaPlayer.start();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mOnPlayerListener != null) {
                            mOnPlayerListener.onWtPlayerStart();
                        }
                    }
                });
                mIsPlaying = true;
            }
        } catch (RuntimeException e) {
            notifyError(ErrorType.START);
        }
    }

    private void pauseInternal() {
        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mOnPlayerListener != null) {
                            mOnPlayerListener.onWtPlayerPause();
                        }
                    }
                });
                mIsPlaying = false;
            }
        } catch (RuntimeException e) {
            notifyError(ErrorType.PAUSE);
        }
    }

    private void stopInternal() {
        if (mMediaPlayer != null) {
            resetPlayer(false);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mOnPlayerListener != null) {
                        mOnPlayerListener.onWtPlayerStop();
                    }
                }
            });
        }
    }

    private void resetInternal() {
        if (mMediaPlayer != null) {
            resetPlayer(true);
        }
    }

    private void resetPlayer(boolean clearParam) {
        if (clearParam) {
            mParamFile = null;
            mParamString = null;
            mParamInitType = null;
        }
        if (mMediaPlayer != null) {
            mMediaPlayer.setOnCompletionListener(null);
            mMediaPlayer.setOnErrorListener(null);
            try {
                mMediaPlayer.stop();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mIsPlaying = false;
    }

    private synchronized void notifyError(final ErrorType type) {
        resetPlayer(false);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mOnPlayerListener != null) {
                    mOnPlayerListener.onWtPlayerError(type);
                }
            }
        });
    }

    private void runOnUiThread(Runnable r) {
        ThreadUtils.runOnUiThread(r);
    }

    // ====================================================================================
    // 音频焦点监听
    // ====================================================================================

    @NonNull
    private final AudioFocusUtils.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioFocusUtils.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusGain() {
            Log.d(TAG, "onAudioFocusGain");
        }

        @Override
        public void onAudioFocusLoss() {
            Log.d(TAG, "onAudioFocusLoss");
            stop();
        }

        @Override
        public void onAudioFocusLossTransient() {
            Log.d(TAG, "onAudioFocusLossTransient");
            stop();
        }

        @Override
        public void onAudioFocusLossTransientCanDuck() {
            Log.d(TAG, "onAudioFocusLossTransientCanDuck");
            stop();
        }
    };

    private void requestAudioFocus() {
        AudioFocusUtils.getInstance().requestAudioFocus(mOnAudioFocusChangeListener);
    }

    private void abandonAudioFocus() {
        AudioFocusUtils.getInstance().abandonAudioFocus();
    }

    // ====================================================================================
    // 前后台切换监听
    // ====================================================================================

    @NonNull
    private final Utils.OnAppStatusChangedListener mOnAppStatusChangedListener = new Utils.OnAppStatusChangedListener() {
        @Override
        public void onForeground(Activity activity) {
            Log.d(TAG, "onForeground");
        }

        @Override
        public void onBackground(Activity activity) {
            Log.d(TAG, "onBackground");
            stop();
        }
    };

    private void registerAppStatusChangedListener() {
        AppUtils.registerAppStatusChangedListener(mOnAppStatusChangedListener);
    }

    private void unregisterAppStatusChangedListener() {
        AppUtils.unregisterAppStatusChangedListener(mOnAppStatusChangedListener);
    }

    // ====================================================================================
    // class
    // ====================================================================================

    public enum ErrorType {
        PLAYER, INIT, START, PAUSE
    }

    public enum InitType {
        FILE, URL_STRING, ASSETS_STRING
    }

    public interface OnPlayerListener {
        void onWtPlayerStart();

        void onWtPlayerPause();

        void onWtPlayerStop();

        void onWtPlayerError(ErrorType type);

        void onWtPlayerCompletion();
    }

    public static class SimpleOnPlayerListener implements OnPlayerListener {
        @Override
        public void onWtPlayerStart() {

        }

        @Override
        public void onWtPlayerPause() {

        }

        @Override
        public void onWtPlayerStop() {

        }

        @Override
        public void onWtPlayerError(ErrorType type) {

        }

        @Override
        public void onWtPlayerCompletion() {

        }
    }
}
