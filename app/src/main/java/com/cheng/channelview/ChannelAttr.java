package com.cheng.channelview;

import android.graphics.PointF;

class ChannelAttr {
    static final int TITLE = 0x01;
    static final int CHANNEL = 0x02;

    /**
     * view类型
     */
    int type;

    /**
     * view坐标
     */
    PointF coordinate;

    /**
     * view所在的channelGroups位置
     */
    int groupIndex;

    /**
     * 频道归属，用于删除频道时该频道的归属位置（推荐、国内、国外）,默认都为1
     */
    int belong = 1;
}
