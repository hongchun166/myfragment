package com.example.hongchun.myapplication.data.dao;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by TianHongChun on 2016/4/24.
 */
public class BaseDao {


    DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("myfragment_db.db")
                    // 不设置dbDir时, 默认存储在app的私有目录.
            .setDbVersion(1)
            .setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) {
                    // 开启WAL, 对写入加速提升巨大
                    db.getDatabase().enableWriteAheadLogging();
                }
            })
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    // TODO: ...
                    // db.addColumn(...);
                    // db.dropTable(...);
                    // ...
                    // or
                    // db.dropDb();
                }
            });


    public DbManager getDBManage(){
        DbManager dbManager=x.getDb(daoConfig);
        return dbManager;
    }
}
