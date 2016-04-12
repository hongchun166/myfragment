package com.example.hongchun.myapplication.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by TianHongChun on 2016/4/12.
 */
public class PinnedHeaderRecyclerView extends RelativeLayout {

    TextView textView;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

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
        textView=new TextView(context);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));
        textView.setTextSize(26);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.GRAY);
        textView.setText("AA");
        addView(textView, 0);

        recyclerView=new RecyclerView(context);
        linearLayoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        addView(recyclerView);

    }

   public void setAdapter(RecyclerView.Adapter adapter){
       recyclerView.setAdapter(adapter);
   }
    public RecyclerView.LayoutManager getLayoutManager(){
            return linearLayoutManager;
    }
    public void addOnScrollListener(RecyclerView.OnScrollListener onScrollListener){
        recyclerView.addOnScrollListener(onScrollListener);
    }

}
