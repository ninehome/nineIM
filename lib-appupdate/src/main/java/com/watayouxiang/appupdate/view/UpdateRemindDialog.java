package com.watayouxiang.appupdate.view;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.watayouxiang.appupdate.R;
import com.watayouxiang.appupdate.entity.AppUpdate;
import com.watayouxiang.appupdate.listener.UpdateListener;

/**
 * @author hule
 * @date 2019/7/11 10:46
 * description:至于为什么使用DialogFragment而不是Dialog,
 * 相信你会明白这是google的推荐，在一个原因可高度定制你想要的任何更新界面
 */
public class UpdateRemindDialog extends BaseDialog {
    /**
     * 进度条
     */
    @Nullable
    private NumberProgressBar progressBar;
    /**
     * 更新数据
     */
    private AppUpdate appUpdate;
    /**
     * 监听接口
     */
    private UpdateListener updateListener;

    /**
     * 初始化弹框
     */
    public static UpdateRemindDialog newInstance(@NonNull AppUpdate appUpdate, @NonNull UpdateListener updateListener) {
        UpdateRemindDialog downloadDialog = new UpdateRemindDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("appUpdate", appUpdate);
        downloadDialog.setArguments(bundle);
        downloadDialog.updateListener = updateListener;
        return downloadDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 禁止屏幕旋转，重走生命周期方法，从而导致 updateDialogListener 为空
        FragmentActivity activity = getActivity();
        if (activity == null) {
            throw new NullPointerException("getActivity() = null");
        }
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            appUpdate = getArguments().getParcelable("appUpdate");
            if (appUpdate != null && appUpdate.getUpdateResourceId() != 0) {
                return inflater.inflate(appUpdate.getUpdateResourceId(), container, false);
            }
        }
        throw new NullPointerException("appUpdate == null || appUpdate.getUpdateResourceId() == 0");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 标题
        @Nullable
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        if (tvTitle != null) {
            tvTitle.setText(appUpdate.getUpdateTitle());
        }
        // 更新的内容
        @Nullable
        TextView tvContent = view.findViewById(R.id.tvContent);
        if (tvContent != null) {
            String updateInfo = appUpdate.getUpdateInfo();
            if (updateInfo == null) updateInfo = "";
            tvContent.setText(updateInfo);
            tvContent.setMovementMethod(new ScrollingMovementMethod());
        }
        // 进度条
        progressBar = view.findViewById(R.id.nbpProgress);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        // 稍后更新（必须）
        @Nullable
        TextView btnUpdateLater = view.findViewById(R.id.btnUpdateLater);
        if (btnUpdateLater != null) {
            if (!appUpdate.getForceUpdate()) {
                btnUpdateLater.setVisibility(View.VISIBLE);
            } else {
                btnUpdateLater.setVisibility(View.GONE);
            }
            btnUpdateLater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 取消下载
                    if (updateListener != null) {
                        updateListener.cancelDownload();
                    }
                    // 隐藏弹窗
                    dismiss();
                }
            });
        }
        // 立即更新（必须）
        @Nullable
        TextView btnUpdateNow = view.findViewById(R.id.btnUpdateNow);
        if (btnUpdateNow != null) {
            btnUpdateNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 开始下载
                    if (updateListener != null) {
                        updateListener.downloadApk();
                    }
                    // 非强制更新 && 静默模式 -> 隐藏弹窗
                    if (!appUpdate.getForceUpdate()) {
                        if (appUpdate.getIsSilentMode()) {
                            if (updateListener != null) {
                                updateListener.onUpdateToast("后台下载中...");
                            }
                            dismiss();
                        }
                    }
                }
            });
        }
    }

    /**
     * 更新下载的进度
     *
     * @param currentProgress 当前进度
     */
    public void setProgress(int currentProgress) {
        if (progressBar == null) return;
        if (currentProgress >= 0) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(currentProgress);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
