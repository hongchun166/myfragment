package com.example.hongchun.myapplication.application;

import android.app.Activity;
import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

import org.xutils.DbManager;
import org.xutils.x;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by HongChun on 2016/4/4.
 */
public class MyApplication extends Application {

    private static MyApplication application;
    private DbManager.DaoConfig daoConfig;
    private List<Activity> mList = new LinkedList<>();

    public static MyApplication getApplication(){
        return application;
    }

    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        x.Ext.init(this);
        x.Ext.setDebug(true);
        SDKInitializer.initialize(this);
        //初始化数据库
        daoConfig=new DbManager.DaoConfig()
                .setDbName("myfragment_db")
                .setDbVersion(1)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                    }
                });
    }


    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    /**
     * 关闭除当前activity外的所有activity
     * @param activity
     */
    public void finshAllOther(Activity activity) {
        try {
            for (Activity acti : mList) {
                if (acti != activity) {
                    acti.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
        }
    }
}
