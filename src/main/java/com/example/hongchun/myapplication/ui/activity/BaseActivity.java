package com.example.hongchun.myapplication.ui.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.hongchun.myapplication.application.MyApplication;

import org.xutils.x;

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        MyApplication.getApplication().addActivity(this);
    }
    public void initToolBar(Toolbar mToolBar){
        setSupportActionBar(mToolBar);
        if (getSupportActionBar()!=null){
            //其他操作
//            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
    public void setToolBarTitle(TextView textView,String title){
        textView.setText(title);
    }
    public void initToolBar(Toolbar mToolBar,TextView textView,String title){
        setSupportActionBar(mToolBar);
        textView.setText(title);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
    public void initToolBarAndBackButton(Toolbar mToolBar){
        setSupportActionBar(mToolBar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //开启返回按钮
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
    /**
     * 初始化带有返回按钮的toolbar
     * @param mToolBar
     */
    public void initToolBarAndBackButton(Toolbar mToolBar,TextView textView,int title){
        setSupportActionBar(mToolBar);
        textView.setText(title);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //开启返回按钮
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }



}
