package com.watayouxiang.appupdate.manager;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.watayouxiang.appupdate.UpdateManager;
import com.watayouxiang.appupdate.entity.InstallApkResult;
import com.watayouxiang.appupdate.utils.InternalUtils;

import java.io.File;

/**
 * @author hule
 * @date 2019/7/11 10:34
 * description:DownloadManager下载广播接收器，需要在xml中注册,
 * 主要实现通知栏显示下载完成自动安装，或者通知栏点击跳转到系统的下载管理器界面
 */
public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!UpdateManager.isAutoInstall) return;
        if (context == null || intent == null) return;

        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            // 下载完成
            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            // 自动安装app
            InternalUtils instance = InternalUtils.getInstance();
            File downloadFile = instance.getDownloadFile(context, downloadId);
            if (downloadFile != null) {
                InstallApkResult installApkResult = instance.installApk(context, downloadFile);
                if (installApkResult == InstallApkResult.SUCCESS) {
                    // 安装成功
                }
            }
        } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {
            // 未下载完成，点击跳转系统的下载管理界面
            Intent viewDownloadIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
            viewDownloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(viewDownloadIntent);
        }
    }
}
