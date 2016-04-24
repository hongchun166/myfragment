//package com.example.hongchun.myapplication.ui.view;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.os.Handler;
//import android.os.Message;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewConfiguration;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.view.animation.LinearInterpolator;
//import android.view.animation.RotateAnimation;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.example.hongchun.myapplication.R;
//import com.example.hongchun.myapplication.interfacem.Pullable;
//
//import org.xutils.common.util.LogUtil;
//
///**
// * Created by TianHongChun on 2016/4/17.
// */
//public class PullRefreshLayout extends RelativeLayout {
//    Context context;
//    // 初始状态
//    public static final int STATUS_INIT = 0;
//    // 释放刷新
//    public static final int STATUS_RELEASE_TO_REFRESH = 1;
//    // 正在刷新
//    public static final int STATUS_REFRESHING = 2;
//    // 释放加载
//    public static final int STATUS_RELEASE_TO_LOAD = 3;
//    // 正在加载
//    public static final int STATUS_LOADING = 4;
//
//    public int currentStatus= STATUS_INIT;//当前状态
//    public int lastStatus= STATUS_INIT;//最后一次状态
//
//    // 释放刷新的距离
//    private float refreshDist = 200;
//    private float loadDist = 200;
//    private  boolean isLayout=false;// 是否初始化view
//
//    float radio=2;        // 距离计算比例
//    float yDown=0;
//    float lastY=0;
//    float pullDownY=0;      //下拉距离
//    float pullUpY=0;      //下拉距离
//    boolean isAutoScroll=false;// 判断是否正在滚动
//    /**下拉头部回滚的速度*/
//    private  final   int SCROLL_SPEED = -20;
//    private  int measuredHeightLayout=0;   // pullrefreshLayout高度
//
//    View headView;      // 刷新头
//    View loadView;      // 加载头
//    View pullableView;// 内容View
//
//    TextView loadTextView;//加载头子view
//    TextView headTextView;//刷新头子view
//    ImageView loadArrow;
//    ImageView headArrow;
//    ProgressBar loadProgressBar;
//    ProgressBar headProgressBar;
//
//    Animation animationRotate;
//    Animation animationRotateLeft;
//    Animation animationRotateRight;
//
//    OnPullToRefreshListener onPullToRefreshListener;
//
//    public PullRefreshLayout(Context context) {
//        super(context);
//        init(context);
//    }
//    public PullRefreshLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context);
//    }
//    public PullRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context);
//    }
//    public void init(Context context){
//        this.context=context;
//        initView();
//    }
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
//        if(!isLayout){
//            pullableView=getChildAt(2);//内容控件
//            refreshDist=headView.getMeasuredHeight();
//            loadDist=loadView.getMeasuredHeight();
//            measuredHeightLayout=getMeasuredHeight();
//            pullDownY=0;
//            pullUpY=0;
//            isLayout=true;
//        }
//        headView.layout(0,
//                (int) (pullDownY + pullUpY) - headView.getMeasuredHeight(),
//                headView.getMeasuredWidth(), (int) (pullDownY + pullUpY));
//        pullableView.layout(0, (int) (pullDownY + pullUpY),
//                pullableView.getMeasuredWidth(), (int) (pullDownY + pullUpY)
//                        + pullableView.getMeasuredHeight());
//        loadView.layout(0,
//                (int) (pullDownY + pullUpY) + pullableView.getMeasuredHeight(),
//                loadView.getMeasuredWidth(),
//                (int) (pullDownY + pullUpY) + pullableView.getMeasuredHeight()
//                        + loadView.getMeasuredHeight());
//    }
//
//
//    private void initView(){
//        headView= LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_layout,null);
//        LayoutParams layoutParams= new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        headView.setLayoutParams(layoutParams);
//        addView(headView);
//
//        loadView= LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_layout,null);
//        LayoutParams layoutParams22= new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        loadView.setLayoutParams(layoutParams22);
//        addView(loadView);
//
//        headTextView=(TextView)headView.findViewById(R.id.description);
//        headArrow=(ImageView)headView.findViewById(R.id.arrow);
//        headProgressBar=(ProgressBar)headView.findViewById(R.id.progress_bar);
//
//        loadTextView=(TextView)loadView.findViewById(R.id.description);
//        loadArrow=(ImageView)loadView.findViewById(R.id.arrow);
//        loadProgressBar=(ProgressBar)loadView.findViewById(R.id.progress_bar);
//
//        animationRotate=AnimationUtils.loadAnimation(context,R.anim.rotate);
//        animationRotateLeft=AnimationUtils.loadAnimation(context,R.anim.rotate_left_anim);
//        animationRotateRight=AnimationUtils.loadAnimation(context,R.anim.rotate_right_anim);
//        animationRotateLeft.setFillAfter(true);
//        animationRotateRight.setFillAfter(true);
//        // 添加匀速转动动画
//        LinearInterpolator lir = new LinearInterpolator();
//        animationRotate.setInterpolator(lir);
//        animationRotateLeft.setInterpolator(lir);
//        animationRotateRight.setInterpolator(lir);
//
//        changeState(STATUS_INIT);
//    }
//    private void changeState(int to){
//        currentStatus=to;
//        if(lastStatus!=currentStatus){
//            switch (to){
//                case STATUS_INIT:
//                    headTextView.setText("下拉刷新");
//                    loadTextView.setText("上拉加载");
//                    headProgressBar.setVisibility(GONE);
//                    loadProgressBar.setVisibility(GONE);
//                    headArrow.setVisibility(VISIBLE);
//                    loadArrow.setVisibility(VISIBLE);
//                    headArrow.startAnimation(animationRotateRight);
//                    loadArrow.startAnimation(animationRotateLeft);
//                    break;
//                case STATUS_REFRESHING:
//                    headArrow.clearAnimation();
//                    headTextView.setText("正在刷新");
//                    headArrow.setVisibility(GONE);
//                    headProgressBar.setVisibility(VISIBLE);
//                    break;
//                case STATUS_RELEASE_TO_REFRESH:
//                    headTextView.setText("释放立即刷新");
//                    headArrow.startAnimation(animationRotateLeft);
//                    break;
//                case STATUS_LOADING:
//                    loadArrow.clearAnimation();
//                    loadTextView.setText("正在加载");
//                    loadArrow.setVisibility(GONE);
//                    loadProgressBar.setVisibility(VISIBLE);
//                    break;
//                case STATUS_RELEASE_TO_LOAD:
//                    loadTextView.setText("松开加载更多");
//                    loadArrow.startAnimation(animationRotateRight);
//                    break;
//            }
//        }
//        lastStatus=currentStatus;
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                yDown=ev.getY();
//                lastY=yDown;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                // 只有在当没有自动滚动,并且也没有再刷新和加载的时候我们才去做滑动处理
//                if(!isAutoScroll() && currentStatus!=STATUS_LOADING && currentStatus!=STATUS_REFRESHING){
//                    if(ev.getY()-yDown>8){
//                        mTouchEventDown(ev);
//                    }else if(ev.getY()-yDown<-8){
//                        mTouchEventUp(ev);
//                    }
//                    lastY=ev.getY();
//                    // 根据下拉距离改变比例
//                    radio = (float) (2 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight() * (pullDownY + Math.abs(pullUpY))));
//                    if(pullDownY>0 || pullUpY<0){
//                        requestLayout();
//                    }
//                    if(pullDownY>0){
//                        if(currentStatus!=STATUS_REFRESHING){
//                            if(pullDownY<=refreshDist){
//                                changeState(STATUS_INIT);
//                            }else  {
//                                changeState(STATUS_RELEASE_TO_REFRESH);;
//                            }
//                        }
//                    }else if(pullUpY<0){
//                        if(currentStatus!=STATUS_LOADING){
//                            if(-pullUpY<=loadDist){
//                                // 上啦加载更多
//                                changeState(STATUS_INIT);
//                            }else {
//                                // 松开立即加载
//                                changeState(STATUS_RELEASE_TO_LOAD);
//                            }
//                        }
//                    }
//                }
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:
//            case MotionEvent.ACTION_POINTER_UP:
//                // 过滤多点触碰
//                break;
//            case MotionEvent.ACTION_UP:
//                // 只有在当没有自动滚动,并且也没有再刷新和加载的时候我们才去做滑动处理
//                if(!isAutoScroll() && currentStatus!=STATUS_LOADING && currentStatus!=STATUS_REFRESHING){
//                        if(currentStatus==STATUS_INIT){
//                            // 下拉状态,那么隐藏下拉头
//                            setRefresh(false);
//                        }else if(currentStatus==STATUS_RELEASE_TO_REFRESH){
//                            // 释放立即刷新状态,
//                            setRefresh(true);
//                        }else if (currentStatus==STATUS_RELEASE_TO_LOAD){
//                            setRefreshLoad(true);
//                        }
//                }
//                break;
//            default:
//        }
//        super.dispatchTouchEvent(ev);
//        return true;
//    }
//
//
//    private void mTouchEventDown(MotionEvent ev){
//        if(pullDownY>0 || (((Pullable)pullableView).canPullDown())){
//            pullDownY = pullDownY + (ev.getY()- lastY) / radio;
//            if(pullDownY<0){
//                pullDownY=0;
//            }else if(pullDownY>measuredHeightLayout){
//                pullDownY=measuredHeightLayout;
//            }
//        }
//    }
//    private void mTouchEventUp(MotionEvent ev){
//        if(pullUpY<0 || (((Pullable)pullableView).canPullUp())){
//            pullUpY = pullUpY + (ev.getY() - lastY) / radio;
//            if(pullUpY>0){
//                pullUpY=0;
//            }else if(-pullUpY>measuredHeightLayout){
//                pullUpY=-measuredHeightLayout;
//            }
//        }
//    }
//
//    public void setRefresh(boolean isRefresh){
//            if(isRefresh){
//                new RefreshingTask(STATUS_REFRESHING).execute();
//                // 回调刷新方法
//                if(onPullToRefreshListener!=null){
//                    onPullToRefreshListener.onRefreshDown(this);
//                }
//            }else {
//                new RefreshingTask(STATUS_INIT).execute();
//            }
//    }
//    public void setRefreshLoad(boolean isRefreshLoad){
//        if(isRefreshLoad){
//            new RefreshingTask(STATUS_LOADING).execute();
//            // 回调刷新方法
//            if(onPullToRefreshListener!=null){
//                onPullToRefreshListener.onRefreshUp(this);
//            }
//        }else {
//            new RefreshingTask(STATUS_INIT).execute();
//        }
//    }
//    public void setOnPullToRefreshListener(OnPullToRefreshListener onPullToRefreshListener){
//            this.onPullToRefreshListener=onPullToRefreshListener;
//    }
//
//    public boolean isAutoScroll() {
//        return isAutoScroll;
//    }
//
//    public void setIsAutoScroll(boolean isAutoScroll) {
//        this.isAutoScroll = isAutoScroll;
//    }
//
//    /**
//     * 执行滚动任务
//     */
//    class RefreshingTask extends AsyncTask<Void, Integer, Void>{
//        int state=-1;
//        public  RefreshingTask(int state){
//            this.state=state;
//        }
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            setIsAutoScroll(true);
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            boolean isBreak=false;
//            while (true) {
//                switch (state) {
//                    case STATUS_REFRESHING:
//                        pullDownY = pullDownY + SCROLL_SPEED;
//                        if (pullDownY <=refreshDist) {
//                            pullDownY = refreshDist;
//                            isBreak=true;
//                            break;
//                        }
//                        break;
//                    case STATUS_LOADING:
//                        pullUpY = pullUpY -SCROLL_SPEED;
//                        if (-pullUpY <=loadDist) {
//                            pullUpY =-loadDist;
//                            isBreak=true;
//                            break;
//                        }
//                        break;
//                    case STATUS_INIT:
//                        if(pullDownY>0){
//                            pullDownY +=SCROLL_SPEED;
//                        }else if(pullUpY<0){
//                            pullUpY-=SCROLL_SPEED;
//                        }
//                        if(pullDownY<=0){
//                            pullDownY = 0;
//                        }
//                        if(pullUpY>=0){
//                            pullUpY=0;
//
//                        }
//                        if(pullUpY==0 && pullDownY==0){
//                            isBreak=true;
//                            break;
//                        }
//                        break;
//                }
//                publishProgress(0);
//                if(isBreak){
//                    break;
//                }
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            publishProgress(0);
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            requestLayout();
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            setIsAutoScroll(false);
//            changeState(state);
//        }
//    }
//
//    /**
//     * 回掉接口
//     */
//    public interface OnPullToRefreshListener{
//        void onRefreshDown(PullRefreshLayout pullRefreshLayout);
//        void onRefreshUp(PullRefreshLayout pullRefreshLayout);
//    }
//}
