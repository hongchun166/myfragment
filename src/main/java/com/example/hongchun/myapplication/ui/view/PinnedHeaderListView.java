package com.example.hongchun.myapplication.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.interfacem.PinnedHeaderAdapter;
import com.example.hongchun.myapplication.ui.adapter.ContactPersonListViewAdapter;

/**
 * Created by TianHongChun on 2016/4/12.
 */
public class PinnedHeaderListView extends ListView implements AbsListView.OnScrollListener {
    Context context;


    public PinnedHeaderListView(Context context) {
        super(context);
        init(context);
    }

    public PinnedHeaderListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PinnedHeaderListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    View mHeaderView=null;
    int mHeaderViewWidth;
    int mHeaderViewHeight;
    boolean mHeaderViewVisible=false;
    ContactPersonListViewAdapter adapter;
    int MAX_ALPHA=1;
    OnScrollListener onScrollListener;


    private void init(Context context){
        this.context=context;
        mHeaderView=null;
        initEven();
    }
    public void initEven(){
        setOnScrollListener(this);
    }
    private View getHeaderView() {
        View view=LayoutInflater.from(context).inflate(R.layout.contact_person_group_layout,null,false);
        view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(onScrollListener!=null){
            onScrollListener.onScrollStateChanged(view,scrollState);
        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        configureHeaderView(firstVisibleItem);
        if(onScrollListener!=null){
            onScrollListener.onScroll(view,firstVisibleItem,visibleItemCount,totalItemCount);
        }
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter=(ContactPersonListViewAdapter) adapter;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHeaderView != null) {
            measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
            mHeaderViewWidth = mHeaderView.getMeasuredWidth();
            mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        }else {
            if(adapter!=null){
                mHeaderView=adapter.getPinnedHeaderView(context);
               if(mHeaderView!=null){
                   measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
                   mHeaderViewWidth = mHeaderView.getMeasuredWidth();
                   mHeaderViewHeight = mHeaderView.getMeasuredHeight();
               }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mHeaderView != null) {
            mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
            configureHeaderView(getFirstVisiblePosition());
        }
    }
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHeaderViewVisible) {
            drawChild(canvas, mHeaderView, getDrawingTime());
        }
    }

    public void configureHeaderView(int position) {
        if (mHeaderView == null || null == adapter) {
            return;
        }
        int state = adapter.getPinnedHeaderState(position);
        switch (state) {
            case PinnedHeaderAdapter.PINNED_HEADER_GONE: {
                mHeaderViewVisible = false;
                break;
            }

            case PinnedHeaderAdapter.PINNED_HEADER_VISIBLE: {
                adapter.configurePinnedHeader(mHeaderView, position, MAX_ALPHA);
                if (mHeaderView.getTop() != 0) {
                    mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
                }
                mHeaderViewVisible = true;
                break;
            }

            case PinnedHeaderAdapter.PINNED_HEADER_PUSHED_UP: {
                View firstView = getChildAt(0);
                int bottom = firstView.getBottom();
                int itemHeight = firstView.getHeight();
                int headerHeight = mHeaderView.getHeight();
                int y;
                int alpha;
                if (bottom < headerHeight) {
                    y = (bottom - headerHeight);
                    alpha = MAX_ALPHA * (headerHeight + y) / headerHeight;
                } else {
                    y = 0;
                    alpha = MAX_ALPHA;
                }
                adapter.configurePinnedHeader(mHeaderView, position, alpha);
                if (mHeaderView.getTop() != y) {
                    mHeaderView.layout(0, y, mHeaderViewWidth, mHeaderViewHeight + y);
                }
                mHeaderViewVisible = true;
                break;
            }
        }
    }
    @Override
    public void setOnScrollListener(OnScrollListener l) {
        if(l==this){
            super.setOnScrollListener(l);
        }else {
            this.onScrollListener=l;
        }
    }
}
