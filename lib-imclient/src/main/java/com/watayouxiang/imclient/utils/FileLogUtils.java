package com.watayouxiang.imclient.utils;

import android.app.Application;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/01
 *     desc   : "文件日志" 工具类
 * </pre>
 */
public class FileLogUtils {
    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final String NEW_LINE = "\n";
    private static final String LOG_THREAD_NAME = "FileLogUtilsThread";
    private static final String LOG_FILE_NAME_DEFAULT = "log2file.log";

    private static String FILE_LOG_NAME = LOG_FILE_NAME_DEFAULT;
    private final Handler handler;
    private Application mApplication;

    private static class Holder {
        private static final FileLogUtils holder = new FileLogUtils();
    }

    public static FileLogUtils getInstance() {
        FILE_LOG_NAME = LOG_FILE_NAME_DEFAULT;
        return Holder.holder;
    }

    private FileLogUtils() {
        HandlerThread handlerThread = new HandlerThread(LOG_THREAD_NAME);
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    /**
     * 初始化（必须先初始化才能使用）
     */
    public void initApp(@NonNull Application application) {
        mApplication = application;
    }

    /**
     * 设置 "日志文件" 名
     */
    public FileLogUtils setFileName(@NonNull String fileName) {
        FILE_LOG_NAME = fileName;
        return this;
    }

    /**
     * 写日志
     */
    public void write(@Nullable final String log) {
        if (handler == null) return;
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    writeInternal(log);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void writeInternal(@Nullable String log) {
        if (log == null) log = "";
        // /storage/emulated/0/Android/data/package/files/log/
        String dirPath = getExternalAppFilesPath() + FILE_SEP + "log" + FILE_SEP;
        // /storage/emulated/0/Android/data/package/files/log/{LOG_FILE_NAME}.txt
        final String logFile = dirPath + FILE_LOG_NAME;
        // 追加 "日志内容"
        final String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
                .format(new Date(System.currentTimeMillis()));
        final StringBuilder builder = new StringBuilder()
                .append(time)
                .append(": ")
                .append(log)
                .append(NEW_LINE);
        writeFileFromString(logFile, builder.toString(), true);
    }

    private boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private boolean writeFileFromString(final String filePath, final String content, final boolean append) {
        return writeFileFromString(getFileByPath(filePath), content, append);
    }

    private boolean writeFileFromString(final File file, final String content, final boolean append) {
        if (file == null || content == null) return false;
        if (!createOrExistsFile(file)) {
            Log.e("FileIOUtils", "create file <" + file + "> failed.");
            return false;
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private String getExternalAppFilesPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(mApplication.getExternalFilesDir(null));
    }

    private String getAbsolutePath(final File file) {
        if (file == null) return "";
        return file.getAbsolutePath();
    }

    private boolean isSDCardEnableByEnvironment() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
