package com.example.hongchun.myapplication.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.interfacem.Pullable;

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

    // 初始状态
    public static final int STATUS_INIT = 0;
    // 释放刷新
    public static final int STATUS_RELEASE_TO_REFRESH = 1;
    // 正在刷新
    public static final int STATUS_REFRESHING = 2;
    // 释放加载
    public static final int STATUS_RELEASE_TO_LOAD = 3;
    // 正在加载
    public static final int STATUS_LOADING = 4;
    // 操作完毕
    public static final int STATUS_DONE = 5;

    /**下拉头部回滚的速度*/
    public static final int SCROLL_SPEED = -20;

    public int currentStatus= STATUS_INIT;

    // 释放刷新的距离
    private float refreshDist = 200;
    private float loadDist = 200;
    private  boolean isLayout=false;// 是否初始化view

    View headView;      // 刷新头
    TextView headTextView;//刷新头子view

    View loadView;      // 加载头
    TextView loadTextView;//加载头子view


    View pullableView;// 内容View

    OnPullToRefreshListener onPullToRefreshListener;

    public void init(Context context){
        this.context=context;
        headView= LayoutInflater.from(context).inflate(R.layout.contact_person_group_layout,null);
        LayoutParams layoutParams= new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(layoutParams);
        headTextView=(TextView)headView.findViewById(R.id.textView_group);
        addView(headView);

        loadView= LayoutInflater.from(context).inflate(R.layout.contact_person_group_layout,null);
        LayoutParams layoutParams22= new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadView.setLayoutParams(layoutParams22);
        loadTextView=(TextView)loadView.findViewById(R.id.textView_group);
        addView(loadView);


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
            pullableView=getChildAt(2);//内容控件
            refreshDist=headView.getMeasuredHeight();
            loadDist=loadView.getMeasuredHeight();
            pullDownY=0;
            pullUpY=0;
            isLayout=true;
        }

//        pullableView.layout(0,(int)pullDownY,pullableView.getMeasuredWidth(),(int)(pullDownY+pullableView.getMeasuredHeight()));
//        headView.layout(0, (int)(pullDownY-headView.getMeasuredHeight()), headView.getMeasuredWidth(), (int)pullDownY);

        headView.layout(0,
                (int) (pullDownY + pullUpY) - headView.getMeasuredHeight(),
                headView.getMeasuredWidth(), (int) (pullDownY + pullUpY));
        pullableView.layout(0, (int) (pullDownY + pullUpY),
                pullableView.getMeasuredWidth(), (int) (pullDownY + pullUpY)
                        + pullableView.getMeasuredHeight());
        loadView.layout(0,
                (int) (pullDownY + pullUpY) + pullableView.getMeasuredHeight(),
                loadView.getMeasuredWidth(),
                (int) (pullDownY + pullUpY) + pullableView.getMeasuredHeight()
                        + loadView.getMeasuredHeight());

    }

    float radio=2;        // 距离计算比例
    float lastY=0;
    float pullDownY=0;      //下拉距离
    float pullUpY=0;      //下拉距离
    boolean canPullDown=true;
    boolean canPullUp=true;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
               float yDown=ev.getY();
                lastY=yDown;
                releasePull();
                break;
            case MotionEvent.ACTION_MOVE:
                // 判断是上啦还是下拉,计算移动距离pullDownY 和pullUpY
                if(ev.getY()-lastY>10){
                    // 下拉
                    mTouchEventDown(ev);
                }else if(ev.getY()-lastY<10){
                    //上啦
                    mTouchEventUp(ev);
                }
                lastY=ev.getY();
                // 根据下拉距离改变比例
                radio = (float) (2 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight() * (pullDownY + Math.abs(pullUpY))));
                if(pullDownY>0 || pullUpY<0){
                    requestLayout();
                }
                // 更改状态
                if(pullDownY>0){
                    if(currentStatus!=STATUS_REFRESHING){
                        if(pullDownY<=refreshDist){
                            // 下拉刷新状态
                            changeState(STATUS_INIT);
                        }else  {
                            // 释放立即刷新
                            changeState(STATUS_RELEASE_TO_REFRESH);;
                        }
                    }
                }else if(pullUpY<0){
                        if(-pullUpY<=loadDist){
                            // 上啦加载更多
                            changeState(STATUS_INIT);
                        }else {
                            // 松开立即加载
                            changeState(STATUS_RELEASE_TO_LOAD);
                        }
                }

                // 因为刷新和加载操作不能同时进行，所以pullDownY和pullUpY不会同时不为0，因此这里用(pullDownY +
                // Math.abs(pullUpY))就可以不对当前状态作区分了
                if ((pullDownY + Math.abs(pullUpY)) > 8){
                    // 防止下拉过程中误触发长按事件和点击事件
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                }

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                // 过滤多点触碰
                break;
            case MotionEvent.ACTION_UP:
            default:
                if(currentStatus==STATUS_INIT){
                    // 下拉状态,那么隐藏下拉头
                    new HideHeaderTask().execute();
                    changeState(STATUS_INIT);
                }else if(currentStatus==STATUS_RELEASE_TO_REFRESH){
                    // 释放立即刷新状态,
                    new RefreshingTask().execute();
                    changeState(STATUS_REFRESHING);
                    // 回调刷新方法
                    if(onPullToRefreshListener!=null){
                        onPullToRefreshListener.onRefreshDown();
                    }
                }else if (currentStatus==STATUS_RELEASE_TO_LOAD){
                        new LoadingTask().execute();
                    changeState(STATUS_LOADING);
                    // 回调刷新方法
                    if(onPullToRefreshListener!=null){
                        onPullToRefreshListener.onRefreshUp();
                    }
                }
                break;
        }
        super.dispatchTouchEvent(ev);
        return true;
    }

    private void mTouchEventDown(MotionEvent ev){
        if(pullDownY>0 || (((Pullable)pullableView).canPullDown()&& canPullDown &&currentStatus!=STATUS_LOADING)){
            pullDownY = pullDownY + (ev.getY()- lastY) / radio;
            if(pullDownY<0){
                pullDownY=0;
                canPullDown=false;
                canPullUp=true;
            }else if(pullDownY>getMeasuredHeight()){
                pullDownY=getMeasuredHeight();
            }
        }
    }
    private void mTouchEventUp(MotionEvent ev){
        if(pullUpY<0 || (((Pullable)pullableView).canPullUp()&& canPullUp && currentStatus!=STATUS_REFRESHING)){
            // 可以上拉，正在刷新时不能上拉
            pullUpY = pullUpY + (ev.getY() - lastY) / radio;
            if(pullUpY>0){
                pullUpY=0;
                canPullDown=true;
                canPullUp=false;
            }else if(-pullUpY>getMeasuredHeight()){
                pullUpY=-getMeasuredHeight();
            }
        }
    }

    private void setChangeState(int to){
        currentStatus=to;
    }
    private void changeState(int to){
        currentStatus=to;
            switch (to){
                case STATUS_INIT:
                    headTextView.setText("下拉刷新");
                    loadTextView.setText("上啦记载");
                    break;
                case STATUS_REFRESHING:
                    headTextView.setText("正在刷新");
                    break;
                case STATUS_RELEASE_TO_REFRESH:
                    headTextView.setText("释放立即刷新");
                    break;
                case STATUS_LOADING:
                    loadTextView.setText("正在加载");
                    break;
                case STATUS_RELEASE_TO_LOAD:
                    loadTextView.setText("松开加载更多");
                    break;
                case STATUS_DONE:
                    break;
            }
    }

    public void setRefresh(boolean isRefresh){
            if(isRefresh){
                new RefreshingTask().execute();
                changeState(STATUS_REFRESHING);
                // 回调刷新方法
                if(onPullToRefreshListener!=null){
                    onPullToRefreshListener.onRefreshDown();
                }
            }else {
                new HideHeaderTask().execute();
                changeState(STATUS_INIT);

            }
    }
    public void setOnPullToRefreshListener(OnPullToRefreshListener onPullToRefreshListener){
            this.onPullToRefreshListener=onPullToRefreshListener;
    }
    /**
     * 不限制上拉或下拉
     */
    private void releasePull()
    {
        canPullDown = true;
        canPullUp = true;
    }
    /**
     * 正在刷新的任务，在此任务中会去回调注册进来的下拉刷新监听器。
     *
     * @author guolin
     */
    class RefreshingTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            while (true) {
                pullDownY = pullDownY + SCROLL_SPEED;
                if (pullDownY <=refreshDist) {
                    pullDownY = refreshDist;
                    break;
                }
                publishProgress(0);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            publishProgress(0);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            requestLayout();
        }
    }

    /**
     * 隐藏下拉头的任务，当未进行下拉刷新或下拉刷新完成后，此任务将会使下拉头重新隐藏。
     *
     * @author guolin
     */
    class HideHeaderTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {

            // 回弹速度随下拉距离moveDeltaY增大而增大
            while (true) {
                pullDownY = pullDownY + SCROLL_SPEED;
                if (pullDownY <=0 ) {
                    pullDownY =0;
                    break;
                }
                publishProgress(1);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            publishProgress(0);
            return 1;
        }

        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            requestLayout();
        }
    }

    /**
     * 正在加载的任务，在此任务中会去回调注册进来的上啦加载监听器。
     *
     * @author guolin
     */
    class LoadingTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            while (true) {
                pullUpY = pullUpY -SCROLL_SPEED;
                if (-pullUpY <=loadDist) {
                    pullUpY =-loadDist;
                    break;
                }
                publishProgress(0);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            publishProgress(0);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            requestLayout();
        }
    }
    public interface OnPullToRefreshListener{
        void onRefreshDown();
        void onRefreshUp();
    }

}
