package com.example.hongchun.myapplication.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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



    public int currentStatus= STATUS_INIT;//当前状态
    public int lastStatus= STATUS_INIT;//最后一次状态

    // 释放刷新的距离
    private float refreshDist = 200;
    private float loadDist = 200;
    private  boolean isLayout=false;// 是否初始化view
    /**下拉头部回滚的速度*/
    private  final   int SCROLL_SPEED = -20;
   private  int measuredHeightLayout=0;   // pullrefreshLayout高度

    View headView;      // 刷新头
    View loadView;      // 加载头
    View pullableView;// 内容View

    TextView loadTextView;//加载头子view
    TextView headTextView;//刷新头子view
    ImageView loadArrow;
    ImageView headArrow;
    ProgressBar loadProgressBar;
    ProgressBar headProgressBar;

    OnPullToRefreshListener onPullToRefreshListener;

    public void init(Context context){
        this.context=context;
        headView= LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_layout,null);
        LayoutParams layoutParams= new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(layoutParams);

        addView(headView);

        loadView= LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_layout,null);
        LayoutParams layoutParams22= new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadView.setLayoutParams(layoutParams22);
        addView(loadView);

        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChild(headView, widthMeasureSpec, heightMeasureSpec);
        measuredHeightLayout=getMeasuredHeight();
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
    float yDown=0;
    float lastY=0;
    float pullDownY=0;      //下拉距离
    float pullUpY=0;      //下拉距离
    boolean canPullDown=true;
    boolean canPullUp=true;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                yDown=ev.getY();
                lastY=yDown;
                releasePull();
                break;
            case MotionEvent.ACTION_MOVE:
                // 判断是上啦还是下拉,计算移动距离pullDownY 和pullUpY
                if(ev.getY()-yDown>10){
                    // 下拉
                    mTouchEventDown(ev);
                }else if(ev.getY()-yDown<10){
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
                }else if(currentStatus==STATUS_RELEASE_TO_REFRESH){
                    // 释放立即刷新状态,
                    new RefreshingTask().execute();
                    // 回调刷新方法
                    if(onPullToRefreshListener!=null){
                        onPullToRefreshListener.onRefreshDown();
                    }
                }else if (currentStatus==STATUS_RELEASE_TO_LOAD){
                        new LoadingTask().execute();
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

    Animation animationRotate;
    Animation animationRotateLeft;
    Animation animationRotateRight;
    private void initView(){
            headTextView=(TextView)headView.findViewById(R.id.description);
        headArrow=(ImageView)headView.findViewById(R.id.arrow);
        headProgressBar=(ProgressBar)headView.findViewById(R.id.progress_bar);

        loadTextView=(TextView)loadView.findViewById(R.id.description);
        loadArrow=(ImageView)loadView.findViewById(R.id.arrow);
        loadProgressBar=(ProgressBar)loadView.findViewById(R.id.progress_bar);

        changeState(STATUS_INIT);

        animationRotate=AnimationUtils.loadAnimation(context,R.anim.rotate);
        animationRotateLeft=AnimationUtils.loadAnimation(context,R.anim.rotate_left_anim);
        animationRotateRight=AnimationUtils.loadAnimation(context,R.anim.rotate_right_anim);
        animationRotateLeft.setFillAfter(true);
        animationRotateRight.setFillAfter(true);
        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        animationRotate.setInterpolator(lir);
        animationRotateLeft.setInterpolator(lir);
        animationRotateRight.setInterpolator(lir);
    }
    private void changeState(int to){
        currentStatus=to;
        if(lastStatus!=currentStatus){
            switch (to){
                case STATUS_INIT:
                    headTextView.setText("下拉刷新");
                    loadTextView.setText("上拉加载");
                    headProgressBar.setVisibility(GONE);
                    loadProgressBar.setVisibility(GONE);
                    headArrow.setVisibility(VISIBLE);
                    loadArrow.setVisibility(VISIBLE);
                    headArrow.startAnimation(animationRotateRight);
                    loadArrow.startAnimation(animationRotateLeft);
                    break;
                case STATUS_REFRESHING:
                    headArrow.clearAnimation();
                    headTextView.setText("正在刷新");
                    headArrow.setVisibility(GONE);
                    headProgressBar.setVisibility(VISIBLE);
                    break;
                case STATUS_RELEASE_TO_REFRESH:
                    headTextView.setText("释放立即刷新");
                    headArrow.startAnimation(animationRotateLeft);
                    break;
                case STATUS_LOADING:
                    loadArrow.clearAnimation();
                    loadTextView.setText("正在加载");
                    loadArrow.setVisibility(GONE);
                    loadProgressBar.setVisibility(VISIBLE);
                    break;
                case STATUS_RELEASE_TO_LOAD:
                    loadTextView.setText("松开加载更多");
                    loadArrow.startAnimation(animationRotateRight);
                    break;
                case STATUS_DONE:
                    break;
            }
        }
        lastStatus=currentStatus;
    }
    public void setRefresh(boolean isRefresh){
            if(isRefresh){
                new RefreshingTask().execute();
                // 回调刷新方法
                if(onPullToRefreshListener!=null){
                    onPullToRefreshListener.onRefreshDown();
                }
            }else {
                new HideHeaderTask().execute();
            }
    }
    public void setRefreshLoad(boolean isRefreshLoad){
        if(isRefreshLoad){
            new RefreshingTask().execute();
            // 回调刷新方法
            if(onPullToRefreshListener!=null){
                onPullToRefreshListener.onRefreshUp();
            }
        }else {
            new HideHeaderTask().execute();
        }
    }
    public void setOnPullToRefreshListener(OnPullToRefreshListener onPullToRefreshListener){
            this.onPullToRefreshListener=onPullToRefreshListener;
    }
    /**
     * 不限制上拉或下拉
     */
    private void releasePull(){
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

        @Override
        protected void onPostExecute(Void aVoid) {
            changeState(STATUS_REFRESHING);
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
                if(pullDownY>0){
                    pullDownY +=SCROLL_SPEED;
                }else if(pullUpY<0){
                    pullUpY-=SCROLL_SPEED;
                }
                if(pullDownY<=0){
                    pullDownY = 0;
                }
                if(pullUpY>=0){
                    pullUpY=0;

                }
                if(pullUpY==0 && pullDownY==0){
                        break;
                }
                publishProgress(1);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            publishProgress(1);
            return 1;
        }

        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            requestLayout();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            changeState(STATUS_INIT);
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

        @Override
        protected void onPostExecute(Void aVoid) {
            changeState(STATUS_LOADING);
        }
    }
    public interface OnPullToRefreshListener{
        void onRefreshDown();
        void onRefreshUp();
    }

}
