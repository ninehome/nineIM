package com.watayouxiang.db.upgrade;

import android.content.Context;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.watayouxiang.db.table.ChatListTableDao;
import com.watayouxiang.db.table.CurrUserTableDao;
import com.watayouxiang.db.table.DaoMaster;
import com.watayouxiang.db.table.FocusTableDao;
import com.watayouxiang.db.table.IpInfoTableDao;
import com.watayouxiang.db.table.RedPacketStatusTableDao;

import org.greenrobot.greendao.database.Database;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/03
 *     desc   : 实现了数据库升级
 * </pre>
 */
public class TioDBOpenHelper extends DaoMaster.OpenHelper {

    public TioDBOpenHelper(Context context, String name) {
        super(context, name);
    }

    /**
     * 数据库升级
     * <p>
     * 需要将所有表的Dao添加于此
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
                    @Override
                    public void onCreateAllTables(Database db, boolean ifNotExists) {
                        DaoMaster.createAllTables(db, ifNotExists);
                    }

                    @Override
                    public void onDropAllTables(Database db, boolean ifExists) {
                        DaoMaster.dropAllTables(db, ifExists);
                    }
                },
                ChatListTableDao.class,
                FocusTableDao.class,
                CurrUserTableDao.class,
                IpInfoTableDao.class,
                RedPacketStatusTableDao.class
        );
    }
}