package com.example.hongchun.myapplication.interfacem;

import android.content.Context;
import android.view.View;

/**
 * Created by TianHongChun on 2016/4/12.
 * 分组 顶部悬浮接口
 */
public interface PinnedHeaderAdapter {

    /**
     * Pinned header state: don't show the header.
     */
    public static final int PINNED_HEADER_GONE = 0;

    /**
     * Pinned header state: show the header at the top of the list.
     */
    public static final int PINNED_HEADER_VISIBLE = 1;

    /**
     * Pinned header state: show the header. If the header extends beyond
     * the bottom of the first shown element, push it up and clip.
     */
    public static final int PINNED_HEADER_PUSHED_UP = 2;

    /**
     * Computes the desired state of the pinned header for the given
     * position of the first visible list item. Allowed return values are
     * {@link #PINNED_HEADER_GONE}, {@link #PINNED_HEADER_VISIBLE} or
     * {@link #PINNED_HEADER_PUSHED_UP}.
     */
    int getPinnedHeaderState(int position);

    /**
     * Configures the pinned header view to match the first visible list item.
     *
     * @param headerView pinned header view.
     * @param firstPosition position of the first visible list item.
     */
    void configurePinnedHeader(View headerView, int firstPosition,int alpha);

    /**
     *
     * @param context
     * @return null
     */
    View getPinnedHeaderView(Context context);

}
