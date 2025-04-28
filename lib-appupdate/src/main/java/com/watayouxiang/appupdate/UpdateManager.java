package com.watayouxiang.appupdate;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.watayouxiang.appupdate.entity.AppUpdate;
import com.watayouxiang.appupdate.entity.InstallApkResult;
import com.watayouxiang.appupdate.entity.UpdateError;
import com.watayouxiang.appupdate.listener.UpdateListener;
import com.watayouxiang.appupdate.manager.DownloadHandler;
import com.watayouxiang.appupdate.manager.DownloadObserver;
import com.watayouxiang.appupdate.utils.InternalUtils;
import com.watayouxiang.appupdate.utils.Md5Util;
import com.watayouxiang.appupdate.view.UpdateRemindDialog;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * @author hule
 * @date 2019/7/11 9:34
 * description: 下载更新工具类
 * <p>
 * 修改来自：https://github.com/NewHuLe/AppUpdate
 */
public class UpdateManager implements UpdateListener, DownloadHandler.DownloadCallback {

    public static final String TEST_APK_URL_160M = "https://imtt.dd.qq.com/16891/apk/301DF3B2F145D207168C7BD777255764.apk";
    public static final String TEST_APK_URL_24M = "https://imtt.dd.qq.com/16891/apk/5CACCB57E3F02E46404D27ABAA85474C.apk";

    /**
     * 是否自动安装
     */
    public static boolean isAutoInstall = false;
    /**
     * 工具类
     */
    @NonNull
    private InternalUtils utils = InternalUtils.getInstance();
    /**
     * context的弱引用
     */
    @NonNull
    private WeakReference<FragmentActivity> wrfContext;
    /**
     * 更新的实体参数
     */
    @NonNull
    private AppUpdate appUpdate;
    /**
     * 系统DownloadManager
     */
    @Nullable
    private DownloadManager downloadManager;
    /**
     * 上次下载的id
     */
    private long lastDownloadId = -1;
    /**
     * 下载监听
     */
    @Nullable
    private DownloadObserver downloadObserver;
    /**
     * 更新提示弹窗
     */
    @Nullable
    private UpdateRemindDialog updateRemindDialog;

    public UpdateManager(@NonNull FragmentActivity context, @NonNull AppUpdate appUpdate) {
        this.wrfContext = new WeakReference<>(context);
        this.appUpdate = appUpdate;
        isAutoInstall = appUpdate.getIsSilentMode();
        // 更新提醒对话框
        updateRemindDialog = UpdateRemindDialog.newInstance(appUpdate, this);
    }

    /**
     * 开始更新
     */
    public void startUpdate() {
        FragmentActivity activity = wrfContext.get();
        if (activity == null) {
            onUpdateError(UpdateError.NULL_CONTEXT);
            return;
        }
        if (updateRemindDialog != null) {
            updateRemindDialog.show(activity.getSupportFragmentManager(), "UpdateManager");
        }
    }

    @Override
    public void downloadApkFromBrowser() {
        // 从浏览器下载
        FragmentActivity activity = wrfContext.get();
        if (activity == null) {
            onUpdateError(UpdateError.NULL_CONTEXT);
            return;
        }
        String newVersionUrl = appUpdate.getNewVersionUrl();
        if (newVersionUrl == null) {
            onUpdateError(UpdateError.NULL_DOWNLOAD_URL);
            return;
        }
        Intent intent = new Intent();
        Uri uri = Uri.parse(newVersionUrl);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        activity.startActivity(intent);
    }

    @Override
    public void exitApp() {
        // 回到退出整个应用，比较好的方式，先退到桌面，再杀掉应用，不然会黑屏闪烁
        FragmentActivity activity = wrfContext.get();
        if (activity != null) {
            activity.startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
            activity.finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    @Override
    public void cancelDownload() {
        // 取消下载
        if (downloadManager != null && lastDownloadId != -1) {
            downloadManager.remove(lastDownloadId);
        }
        // 删除上次下载的文件
        Context context = wrfContext.get();
        if (context != null) {
            File downloadFile = utils.getDownloadFile(context, lastDownloadId);
            utils.deleteFile(downloadFile);
        }
    }

    @Override
    public void downloadApk() {
        // 取消下载任务
        cancelDownload();
        // 容错处理
        Context context = wrfContext.get();
        if (context == null) {
            onUpdateError(UpdateError.NULL_CONTEXT);
            return;
        }
        String downloadUrl = appUpdate.getNewVersionUrl();
        if (downloadUrl == null) {
            onUpdateError(UpdateError.NULL_DOWNLOAD_URL);
            return;
        }
        if (!utils.downloadMangerIsEnable(context)) {
            onUpdateError(UpdateError.DOWNLOAD_MANAGER_ERROR);
            return;
        }
        try {
            // 获取下载管理器
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
            // 下载中和下载完成显示通知栏
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            // 使用系统默认的下载路径：/android/data/packages/Download
            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, utils.getAppName(context) + ".apk");
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            // 部分机型（暂时发现Nexus 6P）无法下载，猜测原因为默认下载通过计量网络连接造成的，通过动态判断一下
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                boolean activeNetworkMetered = connectivityManager.isActiveNetworkMetered();
                request.setAllowedOverMetered(activeNetworkMetered);
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                request.allowScanningByMediaScanner();
            }
            // 设置通知栏的标题
            request.setTitle(utils.getAppName(context));
            // 设置通知栏的描述
            request.setDescription("正在下载中...");
            // 设置媒体类型为apk文件
            request.setMimeType("application/vnd.android.package-archive");
            // 开启下载，返回下载id
            lastDownloadId = downloadManager.enqueue(request);
            // 如需要进度及下载状态，增加下载监听
            DownloadHandler downloadHandler = new DownloadHandler(this);
            downloadObserver = new DownloadObserver(downloadHandler, downloadManager, lastDownloadId);
            context.getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, downloadObserver);
        } catch (Exception e) {
            // 防止有些厂商更改了系统的downloadManager
            onUpdateError(UpdateError.DOWNLOAD_MANAGER_ERROR);
        }
    }

    /**
     * 安装app
     */
    @Override
    public void installApk() {
        Context context = wrfContext.get();
        if (context == null) {
            onUpdateError(UpdateError.NULL_CONTEXT);
            return;
        }
        // 获取apk
        File apkFile = utils.getDownloadFile(context, lastDownloadId);
        if (apkFile == null) {
            onUpdateError(UpdateError.NULL_APK);
            return;
        }
        // 验证md5
        String appUpdateMd5 = appUpdate.getMd5();
        if (appUpdateMd5 != null && appUpdateMd5.length() != 0) {
            boolean md5IsRight = Md5Util.checkFileMd5(appUpdateMd5, apkFile);
            if (!md5IsRight) {
                onUpdateError(UpdateError.MD5_CHECK_ERROR);
                return;
            }
        }
        // 安装
        InstallApkResult installApkResult = utils.installApk(context, apkFile);
        switch (installApkResult) {
            case INSTALL_FAIL:
                onUpdateError(UpdateError.INSTALL_APK_FAIL);
                break;
            case SUCCESS:
                break;
        }
    }

    @Override
    public void onUpdateError(@NonNull UpdateError error) {
        switch (error) {
            case NULL_APK:
                onUpdateToast("apk为空");
                break;
            case MD5_CHECK_ERROR:
                onUpdateToast("apk验证失败");
                break;
            case INSTALL_APK_FAIL:
                onUpdateToast("apk安装失败");
                break;
            case NULL_CONTEXT:
                onUpdateToast("context为空");
                break;
            case NULL_DOWNLOAD_URL:
                onUpdateToast("下载地址为空");
                break;
            case DOWNLOAD_MANAGER_ERROR:
                onUpdateToast("downloadManager出错，跳转浏览器下载...");
                downloadApkFromBrowser();
                break;
        }
    }

    @Override
    public void onUpdateToast(@NonNull String msg) {
        FragmentActivity activity = wrfContext.get();
        if (activity != null) {
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDownloadRunning(int progress) {
        if (updateRemindDialog != null) {
            updateRemindDialog.setProgress(progress);
        }
    }

    @Override
    public void onDownloadSuccessful() {
        if (updateRemindDialog != null) {
            updateRemindDialog.setProgress(100);
        }
        unregisterContentObserver();
        if (!isAutoInstall) {
            installApk();
        }
    }

    @Override
    public void onDownloadFailed() {
        unregisterContentObserver();
        cancelDownload();
    }

    /**
     * 取消下载的监听
     */
    private void unregisterContentObserver() {
        FragmentActivity activity = wrfContext.get();
        if (activity != null && downloadObserver != null) {
            activity.getContentResolver().unregisterContentObserver(downloadObserver);
        }
    }
}
