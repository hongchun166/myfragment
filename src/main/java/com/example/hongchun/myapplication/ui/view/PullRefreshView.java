package com.example.hongchun.myapplication.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hongchun.myapplication.interfacem.Pullable;

/**
 * Created by TianHongChun on 2016/4/16.
 */
public class PullRefreshView extends TextView implements Pullable {
    public PullRefreshView(Context context) {
        super(context);
    }
    public PullRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        return true;
    }

    @Override
    public boolean canPullUp() {
        return true;
    }
}
