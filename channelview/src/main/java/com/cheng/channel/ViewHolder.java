package com.cheng.channel;

import android.view.View;

/**
 * 保存自定义样式布局中的控件
 */
public abstract class ViewHolder {
    View itemView;

    public ViewHolder(View itemView) {
        this.itemView = itemView;
    }
}
