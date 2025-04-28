package com.watayouxiang.audiorecord;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ViewUtils;
import com.lzy.okgo.request.base.Request;
import com.watayouxiang.audiorecord.internal.DialogHelper;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.UploadAudioReq;

import java.io.File;
import java.util.List;
import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/04
 *     desc   : Tio 录音器
 * </pre>
 */
public class TioAudioRecorder implements WtMediaRecorder.OnRecorderListener {

    public static final int MAX_RECORD_SECOND = 60;// 单位秒
    public static final int MIN_RECORD_MILLISECOND = 1000;// 单位毫秒


    private String mChatLinkId = null;

    private List<String> mChatLinkIds = null;

    @NonNull
    private WtMediaRecorder mMediaRecorder;
    private OnRecorderListener mOnRecorderListener;
    private boolean cancelled = false;
    private boolean started = false;
    @Nullable
    private Context mContext;
    @Nullable
    private File mAudioFile;

    public TioAudioRecorder(String chatlinkid) {
        mChatLinkId = chatlinkid;
        mMediaRecorder = new WtMediaRecorder();
        mMediaRecorder.setOnRecorderListener(this);
    }

    public TioAudioRecorder(List<String> chatlinkids) {
        mChatLinkIds = chatlinkids;
        mMediaRecorder = new WtMediaRecorder();
        mMediaRecorder.setOnRecorderListener(this);
    }

    /**
     * 初始化录音视图
     */

    public void initRecordView(@NonNull final View view) {
        mContext = view.getContext();
        notifyChangedOnUiThread(1);

        if (mMediaRecorder.isGrantedAllPermission()) {
            // 设置点击监听
            setViewOnTouchListener(view);
        } else {
            // 申请权限
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    requestPermissions(view);
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    requestPermissions(view);
                    return true;
                }
            });
        }
    }

    private void requestPermissions(final View view) {
        mMediaRecorder.requestPermissions(new PermissionUtils.SingleCallback() {
            @Override
            public void callback(boolean isAllGranted, @NonNull List<String> granted, @NonNull List<String> deniedForever, @NonNull List<String> denied) {
                if (isAllGranted) {
                    // 设置点击监听
                    view.setOnClickListener(null);
                    view.setOnLongClickListener(null);
                    setViewOnTouchListener(view);
                } else if (!deniedForever.isEmpty()) {
                    // 提示权限被禁用
                    showToastOnUiThread("权限被禁用，无法使用");
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setViewOnTouchListener(@NonNull View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        notifyChangedOnUiThread(2);
                        mMediaRecorder.start();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        notifyChangedOnUiThread(1);
                        mMediaRecorder.stop(isCancelled(view, event));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        cancelAudioRecord(isCancelled(view, event));
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mMediaRecorder != null) {
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
        TioHttpClient.cancel(this);
        mContext = null;
    }

    /**
     * 设置监听
     */
    public void setOnRecorderListener(OnRecorderListener listener) {
        this.mOnRecorderListener = listener;
    }
    volatile int count;

    private synchronized void setCount(int count) {
        this.count = count;
    }

    private synchronized void countDown(){
        count--;
    }

    private synchronized int getCount() {
        return count;
    }

    private void uploadAudio(@NonNull File audioFile) {
        if (mChatLinkIds != null){
            setCount(mChatLinkIds.size());
            for(String chatlinkId : mChatLinkIds){
                UploadAudioReq uploadAudioReq = new UploadAudioReq(chatlinkId, audioFile.getAbsolutePath());
                uploadAudioReq.setCancelTag(this);
                uploadAudioReq.upload(new TioCallback<String>() {
                    @Override
                    public void onStart(Request<BaseResp<String>, ? extends Request> request) {
                        super.onStart(request);
                        if (getCount() == mChatLinkIds.size()){
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mOnRecorderListener != null) {
                                        mOnRecorderListener.onUploadAudioStart();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onTioSuccess(String s) {

                    }

                    @Override
                    public void onTioError(String msg) {
                        showToastOnUiThread(msg);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        countDown();
                        if (getCount() == 0){
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mOnRecorderListener != null) {
                                        mOnRecorderListener.onUploadAudioFinish();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }else {
            UploadAudioReq uploadAudioReq = new UploadAudioReq(mChatLinkId, audioFile.getAbsolutePath());
            uploadAudioReq.setCancelTag(this);
            uploadAudioReq.upload(new TioCallback<String>() {
                @Override
                public void onStart(Request<BaseResp<String>, ? extends Request> request) {
                    super.onStart(request);

                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mOnRecorderListener != null) {
                                mOnRecorderListener.onUploadAudioStart();
                            }
                        }
                    });
                }

                @Override
                public void onTioSuccess(String s) {

                }

                @Override
                public void onTioError(String msg) {
                    showToastOnUiThread(msg);
                }

                @Override
                public void onFinish() {
                    super.onFinish();

                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mOnRecorderListener != null) {
                                mOnRecorderListener.onUploadAudioFinish();
                            }
                        }
                    });
                }
            });
        }
    }

    private void showToastOnUiThread(@NonNull final String text) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mOnRecorderListener != null) {
                    mOnRecorderListener.onShowToast(text);
                }
            }
        });
    }

    /**
     * @param type 1 抬起
     *             2 按下
     *             3 取消
     */
    private void notifyChangedOnUiThread(final int type) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mOnRecorderListener != null) {
                    mOnRecorderListener.onChanged(type);
                }
            }
        });
    }

    private void cancelAudioRecord(boolean cancel) {
        // reject
        if (!started) {
            return;
        }
        // no change
        if (cancelled == cancel) {
            return;
        }

        cancelled = cancel;

        if (cancel) {
            notifyChangedOnUiThread(3);
        } else {
            notifyChangedOnUiThread(2);
        }
    }

    // 上滑取消录音判断
    private static boolean isCancelled(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        if (event.getRawX() < location[0] || event.getRawX() > location[0] + view.getWidth()
                || event.getRawY() < location[1] - 40) {
            return true;
        }

        return false;
    }

    private void showOkCancelDialog() {
        ViewUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mContext == null) return;
                String message = String.format(Locale.getDefault(), "录音达到最大时长%d秒，是否发送？", MAX_RECORD_SECOND);
                DialogHelper.showOkCancelDialog(mContext, message, new DialogHelper.OnDialogListener() {
                    @Override
                    public void doOkAction() {
                        // 上传录音
                        if (mMediaRecorder != null && mAudioFile != null) {
                            uploadAudio(mAudioFile);
                        }
                    }

                    @Override
                    public void doCancelAction() {
                        // 删除文件
                        if (mMediaRecorder != null) {
                            mMediaRecorder.deleteFile(mAudioFile);
                        }
                    }
                });
            }
        });
    }

    // ====================================================================================
    // onWtRecorder
    // ====================================================================================

    @Override
    public void onWtRecorderStart() {
        cancelled = false;
        started = true;

        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mOnRecorderListener != null) {
                    mOnRecorderListener.onRecorderStart();
                }
            }
        });
    }

    @Override
    public void onWtRecorderStop(final boolean cancel) {
        started = false;

        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mOnRecorderListener != null) {
                    mOnRecorderListener.onRecorderStop(cancel);
                }
            }
        });
    }

    @Override
    public void onWtRecorderSuccess(long millisecond, @NonNull File audioFile) {
        if (millisecond < MIN_RECORD_MILLISECOND) {
            showToastOnUiThread("说话时间太短");
            mMediaRecorder.deleteAudio();
        } else {
            if (millisecond > MAX_RECORD_SECOND * 1000) {
                // 超过最大时长
                mAudioFile = audioFile;
            } else {
                // 正常范围内
                uploadAudio(audioFile);
            }
        }
    }

    @Override
    public void onWtRecorderError(int type) {

    }

    @Override
    public void onWtRecorderTick(int second) {
        if (second == MAX_RECORD_SECOND) {
            // 停止录音
            mMediaRecorder.stop();
            // 抬起状态
            notifyChangedOnUiThread(1);
            // 显示弹窗
            showOkCancelDialog();
        }
    }

    // ====================================================================================
    // interface
    // ====================================================================================

    public interface OnRecorderListener {
        void onShowToast(@NonNull String text);

        /**
         * @param type 1 抬起
         *             2 按下
         *             3 取消
         */
        void onChanged(int type);

        void onRecorderStart();

        void onRecorderStop(boolean cancel);

        void onUploadAudioStart();

        void onUploadAudioFinish();
    }

    public static class OnSimpleRecorderListener implements OnRecorderListener {
        @Override
        public void onShowToast(@NonNull String text) {

        }

        @Override
        public void onChanged(int type) {

        }

        @Override
        public void onRecorderStart() {

        }

        @Override
        public void onRecorderStop(boolean cancel) {

        }

        @Override
        public void onUploadAudioStart() {

        }

        @Override
        public void onUploadAudioFinish() {

        }
    }
}
