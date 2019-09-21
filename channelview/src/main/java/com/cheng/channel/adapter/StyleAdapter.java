package com.cheng.channel.adapter;

import android.view.ViewGroup;

import com.cheng.channel.Channel;
import com.cheng.channel.ViewHolder;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 频道适配器，用于自定义频道样式
 */
public interface StyleAdapter<VH extends ViewHolder> {
    /**
     * 创建频道View，不要在这里设置监听，并不会生效，也不要将setEnable设置为false，
     * 适配器的作用仅仅是用于自定义频道样式
     *
     * @param channelName 频道名称
     * @return
     */
    VH createStyleView(ViewGroup parent, String channelName);

    /**
     * 获取频道数据
     *
     * @return
     */
    LinkedHashMap<String, List<Channel>> getChannelData();

    /**
     * 设置正常状态下频道样式
     *
     * @param viewHolder
     */
    void setNormalStyle(VH viewHolder);

    /**
     * 设置固定状态下频道样式
     *
     * @param viewHolder
     */
    void setFixedStyle(VH viewHolder);

    /**
     * 设置编辑状态下频道样式
     *
     * @param viewHolder
     */
    void setEditStyle(VH viewHolder);

    /**
     * 设置获取焦点（拖拽）状态下频道样式
     *
     * @param viewHolder
     */
    void setFocusedStyle(VH viewHolder);
}
