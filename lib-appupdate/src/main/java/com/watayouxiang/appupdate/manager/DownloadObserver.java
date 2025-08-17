package com.watayouxiang.appupdate.manager;

import android.app.DownloadManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Locale;

/**
 * @author hule
 * @date 2019/7/11 10:38
 * description:通过ContentObserver监听下载的进度
 */
public class DownloadObserver extends ContentObserver {
    private final String TAG = getClass().getCanonicalName();// com.open.hule.library.downloadmanager.DownloadObserver
    private final Handler handler;
    private final DownloadManager downloadManager;
    private final DownloadManager.Query query;
    /**
     * 记录成功或者失败的状态，主要用来只发送一次成功或者失败
     */
    private boolean isEnd = false;

    public DownloadObserver(Handler handler, DownloadManager downloadManager, long downloadId) {
        super(handler);
        this.handler = handler;
        this.downloadManager = downloadManager;
        this.query = new DownloadManager.Query().setFilterById(downloadId);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        queryDownloadStatus();
    }

    /**
     * 检查下载的状态
     */
    private void queryDownloadStatus() {
        Cursor cursor = null;
        try {
            cursor = downloadManager.query(query);
            if (cursor != null && cursor.moveToNext()) {

                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                long totalSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                long currentSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));

                // 当前进度
                int progress;
                if (totalSize != 0) {
                    progress = (int) ((currentSize * 100) / totalSize);
                } else {
                    progress = 0;
                }

                Log.d(TAG, String.format(Locale.getDefault(), "status = %d, progress = %d", status, progress));

                switch (status) {
                    case DownloadManager.STATUS_PAUSED:
                        // 下载暂停
                        handler.sendEmptyMessage(DownloadManager.STATUS_PAUSED);
                        break;
                    case DownloadManager.STATUS_PENDING:
                        // 开始下载
                        handler.sendEmptyMessage(DownloadManager.STATUS_PENDING);
                        break;
                    case DownloadManager.STATUS_RUNNING:
                        // 正在下载，不做任何事情
                        Message message = Message.obtain();
                        message.what = DownloadManager.STATUS_RUNNING;
                        message.arg1 = progress;
                        handler.sendMessage(message);
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:
                        // 成功
                        if (!isEnd) {
                            handler.sendEmptyMessage(DownloadManager.STATUS_SUCCESSFUL);
                            isEnd = true;
                        }
                        break;
                    case DownloadManager.STATUS_FAILED:
                        // 失败
                        if (!isEnd) {
                            handler.sendEmptyMessage(DownloadManager.STATUS_FAILED);
                            isEnd = true;
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
