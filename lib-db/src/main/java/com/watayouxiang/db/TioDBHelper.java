package com.watayouxiang.db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.watayouxiang.db.callback.IMCallbackInternal;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.db.table.DaoMaster;
import com.watayouxiang.db.table.DaoSession;
import com.watayouxiang.db.upgrade.TioDBOpenHelper;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.engine.EventEngine;
import com.watayouxiang.imclient.tool.UIHandler;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/21
 *     desc   : 数据库
 * </pre>
 */
public class TioDBHelper {
    private static DaoSession mDaoSession;
    private static IMCallbackInternal mImCallback;
    private static UIHandler mUIHandler;
    private static EventEngine mEventEngine;

    public static void init(@NonNull Application app) {
        init(app, "tio.db");
    }

    private static void init(@NonNull Application app, @NonNull String dbName) {
        release();

        initDatabase(app, dbName);
        mUIHandler = new UIHandler();
        mEventEngine = TioIMClient.getInstance().getEventEngine();
        TioIMClient.getInstance().registerCallback(mImCallback = new IMCallbackInternal());
        TioDBPreferences.init(app);
    }

    private static void initDatabase(@NonNull Application app, @NonNull String dbName) {
        // 数据库操作日志
        // TAG = "greenDAO"
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        // 数据库升级日志
        // TAG = "MigrationHelper"
        MigrationHelper.DEBUG = true;

        // DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(app, DB_NAME);
        TioDBOpenHelper helper = new TioDBOpenHelper(app, dbName);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    /**
     * 数据库DAO
     */
    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    /**
     * 运行在线程池中
     * <p>
     * 线程池为：Executors.newCachedThreadPool()
     */
    public static void runInTx(@NonNull Runnable runnable) {
        mDaoSession.startAsyncSession().runInTx(runnable);
    }

    /**
     * UIHandler
     */
    public static UIHandler getUIHandler() {
        return mUIHandler;
    }

    /**
     * 事件发送器
     */
    public static EventEngine getEventEngine() {
        return mEventEngine;
    }

    public static void release() {
        if (mImCallback != null) {
            TioIMClient.getInstance().unregisterCallback(mImCallback);
        }
        if (mUIHandler != null) {
            mUIHandler.removeCallbacksAndMessages(null);
            mUIHandler = null;
        }
        if (mEventEngine != null) {
            mEventEngine = null;
        }
        // 数据库操作日志
        // TAG = "greenDAO"
        QueryBuilder.LOG_SQL = false;
        QueryBuilder.LOG_VALUES = false;
        // 数据库升级日志
        // TAG = "MigrationHelper"
        MigrationHelper.DEBUG = false;
        // DAO清除
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }
}
