package com.example.hongchun.myapplication.ui.view.index;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import com.example.hongchun.myapplication.interfacem.Pullable;

/**
 * Created by TianHongChun on 2016/4/20.
 */
public class PullRefreshListView extends ListView implements Pullable {

    public PullRefreshListView(Context context) {
        super(context);
    }

    public PullRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean canPullDown() {

        if (getCount() == 0)
        {
            // 没有item的时候也可以下拉刷新
            return true;
        } else if (getFirstVisiblePosition() == 0
                && getChildAt(0).getTop() >= 0)
        {
            // 滑到ListView的顶部了
            return true;
        } else
            return false;
    }


    @Override
    public boolean canPullUp() {
        if (getCount() == 0)
        {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1))
        {
            // 滑到底部了
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(
                    getLastVisiblePosition()
                            - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
                return true;
        }
        return false;

    }
}
