package com.cheng.channel.adapter;

import com.cheng.channel.Channel;
import com.cheng.channel.ChannelView;

import java.util.List;

/**
 * 抽象监听器，可针对性进行监听事件
 */
public abstract class ChannelListenerAdapter implements ChannelView.OnChannelListener2 {
    @Override
    public void channelItemClick(int position, Channel channel) {

    }

    @Override
    public void channelEditStateItemClick(int position, Channel channel) {

    }

    @Override
    public void channelEditFinish(List<Channel> channelList) {

    }

    @Override
    public void channelEditStart() {

    }
}
