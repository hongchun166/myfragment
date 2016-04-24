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
    private List<Activity> mList = new LinkedList<>();

    public static MyApplication getApplication(){
        return application;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        x.Ext.init(this);
        x.Ext.setDebug(true);
        SDKInitializer.initialize(this);
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
