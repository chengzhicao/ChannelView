package com.cheng.channel;

import java.util.List;

public abstract class ChannelListenerAdapter implements ChannelView.OnChannelListener {
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
