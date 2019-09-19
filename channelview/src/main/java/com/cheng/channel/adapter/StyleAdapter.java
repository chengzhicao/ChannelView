package com.cheng.channel.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * 频道适配器，用于自定义频道样式
 */
public interface StyleAdapter {
    /**
     * 创建频道View，不要在这里设置监听，并不会生效，也不要将setEnable设置为false，
     * 适配器的作用仅仅是用于自定义频道样式
     *
     * @param channelName 频道名称
     * @return
     */
    View createStyleView(ViewGroup parent, String channelName);

    /**
     * 设置正常状态下频道样式
     *
     * @param channelView
     */
    void setNormalStyle(View channelView);

    /**
     * 设置固定状态下频道样式
     *
     * @param channelView
     */
    void setFixedStyle(View channelView);

    /**
     * 设置编辑状态下频道样式
     *
     * @param channelView
     */
    void setEditStyle(View channelView);

    /**
     * 设置获取焦点（拖拽）状态下频道样式
     *
     * @param channelView
     */
    void setFocusedStyle(View channelView);
}
