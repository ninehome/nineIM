package com.watayouxiang.audiorecord;

import android.Manifest;
import android.media.MediaRecorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.UtilsTransActivity;
import com.watayouxiang.audiorecord.internal.WtTimer;
import com.watayouxiang.audiorecord.utils.AudioFocusUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/31
 *     desc   : 录音器
 *
 *     参考：https://www.imooc.com/u/3778140/courses?sort=publish
 * </pre>
 */
public class WtMediaRecorder {

    private static final String AUDIO_FILE_EXT = ".m4a";

    private ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
    private WtTimer mTimer;
    private MediaRecorder mMediaRecorder;
    private File mAudioFile;
    private long mStartTime, mStopTime;
    private OnRecorderListener mOnRecorderListener;

    /**
     * 开始录音
     */
    public void start() {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                // 重置
                releaseInternal();
                // 开始录音
                if (startInternal()) {
                    // 通知
                    if (mOnRecorderListener != null) {
                        mOnRecorderListener.onWtRecorderStart();
                    }
                    // 开始计时
                    startTimerInternal();
                } else {
                    // 错误处理
                    error(1);
                }
            }
        });
    }

    /**
     * 停止录音
     */
    public void stop() {
        stop(false);
    }

    /**
     * 停止录音
     *
     * @param cancel 是否取消
     */
    public void stop(final boolean cancel) {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                // 通知
                if (mOnRecorderListener != null) {
                    mOnRecorderListener.onWtRecorderStop(cancel);
                }

                if (cancel) {
                    // 取消录音
                    // 停止录音
                    stopInternal();
                    // 重置
                    releaseInternal();
                    // 删除录音文件
                    deleteAudioInternal();
                } else {
                    // 停止录音
                    if (stopInternal()) {
                        // 成功回调
                        success();
                    } else {
                        // 错误处理
                        error(2);
                    }
                }
            }
        });
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mExecutorService != null) {
            mExecutorService.shutdownNow();
            mExecutorService = null;
        }
        releaseInternal();
        mOnRecorderListener = null;
    }

    /**
     * 设置监听器
     */
    public void setOnRecorderListener(OnRecorderListener listener) {
        this.mOnRecorderListener = listener;
    }

    /**
     * 删除录音
     */
    public void deleteAudio() {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                deleteAudioInternal();
            }
        });
    }

    /**
     * 删除文件
     *
     * @param file 文件
     */
    public void deleteFile(@Nullable final File file) {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                deleteFileInternal(file);
            }
        });
    }

    /**
     * 申请权限
     */
    public void requestPermissions(PermissionUtils.SingleCallback callback) {
        PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.MICROPHONE)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(UtilsTransActivity activity, ShouldRequest shouldRequest) {
                        shouldRequest.again(true);
                    }
                })
                .callback(callback)
                .request();
    }

    /**
     * 判断权限是否满足
     */
    public boolean isGrantedAllPermission() {
        return PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO);
    }

    private boolean startInternal() {
        if (!isGrantedAllPermission()) {
            return false;
        }

        // 获取音频焦点，停止手机内其他音源的播放
        AudioFocusUtils.getInstance().requestAudioFocus(null);

        File audioDir = new File(PathUtils.getExternalAppFilesPath() + File.separator + "record");
        mAudioFile = new File(audioDir, TimeUtils.getNowMills() + AUDIO_FILE_EXT);// ".m4a" 是 ".mp4" 的声音文件后缀
        boolean existsOrCreatesFile = FileUtils.createOrExistsFile(mAudioFile);
        if (!existsOrCreatesFile) {
            return false;
        }

        try {
            mMediaRecorder = new MediaRecorder();

            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 声音来源
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);// 声音输出格式
            mMediaRecorder.setAudioSamplingRate(44100); //声音采集（所以安卓平台都支持 44.1kHz）
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);// 声音编码格式（通用的 AAC 编码格式）
            mMediaRecorder.setAudioEncodingBitRate(96000); //声音编码比特率（音质表较好的频率 96kbps）
            mMediaRecorder.setOutputFile(mAudioFile.getAbsolutePath());// 创建录音文件位置

            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (IOException | RuntimeException e) {
            return false;
        }

        mStartTime = System.currentTimeMillis();

        return true;
    }

    private boolean stopInternal() {
        try {
            if (mMediaRecorder != null) {
                mMediaRecorder.stop();
            }
            mStopTime = System.currentTimeMillis();
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private void releaseInternal() {
        if (mMediaRecorder != null) {
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
        mAudioFile = null;
        mStartTime = 0;
        mStopTime = 0;
        stopTimerInternal();
    }

    private void deleteAudioInternal() {
        FileUtils.delete(mAudioFile);
    }

    private void deleteFileInternal(@Nullable File file) {
        FileUtils.delete(file);
    }

    private void startTimerInternal() {
        mTimer = new WtTimer();
        mTimer.start(new WtTimer.OnTickListener() {
            @Override
            public void onTick(int count) {
                // 计时通知
                if (mOnRecorderListener != null) {
                    mOnRecorderListener.onWtRecorderTick(count);
                }
            }
        });
    }

    private void stopTimerInternal() {
        if (mTimer != null) {
            mTimer.stop();
            mTimer = null;
        }
    }

    /**
     * @param type 1 开始出错
     *             2 结束出错
     */
    private void error(int type) {
        // 释放资源
        releaseInternal();
        // 删除录音文件
        deleteAudioInternal();
        // 通知错误
        if (mOnRecorderListener != null) {
            mOnRecorderListener.onWtRecorderError(type);
        }
    }

    private void success() {
        // 计算录音时长
        long duration = -1;
        if (mStartTime != 0 && mStopTime != 0) {
            duration = mStopTime - mStartTime;
        }
        // 成功回调
        if (mAudioFile != null && duration != -1) {
            if (mOnRecorderListener != null) {
                mOnRecorderListener.onWtRecorderSuccess(duration, mAudioFile);
            }
        }
        // 重置
        releaseInternal();
    }

    public interface OnRecorderListener {
        void onWtRecorderStart();

        void onWtRecorderStop(boolean cancel);

        void onWtRecorderSuccess(long millisecond, @NonNull File audioFile);

        /**
         * @param type 1 开始出错
         *             2 结束出错
         */
        void onWtRecorderError(int type);

        void onWtRecorderTick(int second);
    }
}
