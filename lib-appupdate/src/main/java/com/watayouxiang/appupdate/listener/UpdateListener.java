package com.watayouxiang.appupdate.listener;

import androidx.annotation.NonNull;

import com.watayouxiang.appupdate.entity.UpdateError;

/**
 * @author hule
 * @date 2019/7/10 15:48
 * description:弹出的更新对话框的接口
 */
public interface UpdateListener {
    /**
     * 立即更新
     */
    void downloadApk();

    /**
     * 取消更新
     */
    void cancelDownload();

    /**
     * 强制退出应用
     */
    void exitApp();

    /**
     * 用浏览器下载apk
     */
    void downloadApkFromBrowser();

    /**
     * 安装apk
     */
    void installApk();

    /**
     * 更新出错
     */
    void onUpdateError(@NonNull UpdateError error);

    /**
     * 更新提示
     */
    void onUpdateToast(@NonNull String msg);
}
