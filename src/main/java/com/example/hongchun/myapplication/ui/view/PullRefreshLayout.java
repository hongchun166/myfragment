package com.example.hongchun.myapplication.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hongchun.myapplication.R;

/**
 * Created by TianHongChun on 2016/4/17.
 */
public class PullRefreshLayout extends RelativeLayout {
    Context context;

    public PullRefreshLayout(Context context) {
        super(context);
        init(context);
    }
    public PullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public PullRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**下拉状态*/
    public static final int STATUS_PULL_TO_REFRESH = 0;
    /** 释放立即刷新状态*/
    public static final int STATUS_RELEASE_TO_REFRESH = 1;
    /** 正在刷新状态 */
    public static final int STATUS_REFRESHING = 2;
    /**刷新完成或未刷新状态 */
    public static final int STATUS_REFRESH_FINISHED = 3;
    /**下拉头部回滚的速度*/
    public static final int SCROLL_SPEED = -20;

    // 释放刷新的距离
    private float refreshDist = 200;
    private  boolean isLayout=false;// 是否初始化view

    View headView;      // 刷新头
    TextView headTextView;//属性头子view

    View pullableView;// 内容View


    public void init(Context context){
        this.context=context;
        headView= LayoutInflater.from(context).inflate(R.layout.contact_person_group_layout,null);
        headView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        headTextView=(TextView)headView.findViewById(R.id.textView_group);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChild(headView, widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(!isLayout){
            pullableView=getChildAt(0);
            refreshDist=headView.getMeasuredHeight();
        }
        headView.layout(0,- headView.getMeasuredHeight(), headView.getMeasuredWidth(),0);
        pullableView.layout(0,0,pullableView.getMeasuredWidth(),pullableView.getMeasuredHeight());
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {



        super.dispatchTouchEvent(ev);
        return true;
    }

    public interface OnPullToRefreshListener{
        void onRefreshDown();
        void onRefreshUp();
    }

}
