package com.example.hongchun.myapplication.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.hongchun.myapplication.ui.adapter.TestContactPersonRecyclerAdapter;

/**
 * Created by TianHongChun on 2016/4/12.
 */
public class PinnedHeaderRecyclerView extends RelativeLayout {
    Context context;
    View viewHead;
    ListView listView;
    final int headViewHeight=40;
    AbsListView.OnScrollListener onScrollListener;

    public PinnedHeaderRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public PinnedHeaderRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PinnedHeaderRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){
        this.context=context;
        {
            listView=new ListView(context);
            LayoutParams layoutParams=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            listView.setLayoutParams(layoutParams);
            addView(listView);
        }
        {
            TextView textViewHead=new TextView(context);
            textViewHead.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headViewHeight));
            textViewHead.setTextSize(26);
            textViewHead.setTextColor(Color.BLACK);
            textViewHead.setBackgroundColor(Color.GRAY);
            textViewHead.setPadding(20, 0, 0, 0);
            LayoutParams layoutParams=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin=0;
            layoutParams.topMargin=0;
            viewHead=textViewHead;
            addView(viewHead, layoutParams);
        }

        initEven();
    }

    int  tempTopMargin=0;
    public void initEven(){
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                TestContactPersonRecyclerAdapter adapter =getAdapter();
                int headerState = adapter.getPinnedHeaderState(firstVisibleItem);
                if (headerState == TestContactPersonRecyclerAdapter.PINNED_HEADER_VISIBLE) {
                    tempTopMargin = 0;
                } else if (headerState == TestContactPersonRecyclerAdapter.PINNED_HEADER_GONE) {
                    tempTopMargin = 0;
                }

                LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHead.getLayoutParams();
                layoutParams.topMargin = tempTopMargin;
                viewHead.setLayoutParams(layoutParams);
                adapter.configurePinnedHeader(viewHead, firstVisibleItem);

                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }
        });
    }
    public ListView getListView(){
        return listView;
    }
   public void setAdapter(TestContactPersonRecyclerAdapter adapter){
       listView.setAdapter(adapter);
   }

    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener){
        this.onScrollListener=onScrollListener;
    }

    public TestContactPersonRecyclerAdapter getAdapter(){
        TestContactPersonRecyclerAdapter adapter=(TestContactPersonRecyclerAdapter)listView.getAdapter();
        return adapter;
    }

}
