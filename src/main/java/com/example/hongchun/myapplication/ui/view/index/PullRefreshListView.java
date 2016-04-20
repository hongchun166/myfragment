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

                if(getFirstVisiblePosition()==0){
                    return true;
                }else {
                    return false;
                }
    }


    @Override
    public boolean canPullUp() {
        if(getLastVisiblePosition()==getCount()-1){
            return true;
        }else {
            return false;
        }

    }
}
