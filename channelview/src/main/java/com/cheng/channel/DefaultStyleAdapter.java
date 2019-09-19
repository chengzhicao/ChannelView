package com.cheng.channel;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheng.channel.adapter.BaseStyleAdapter;

/**
 * 默认的频道适配样式，如果没有设置setAdapter，默认使用这个
 */
public class DefaultStyleAdapter extends BaseStyleAdapter {
    @Override
    public View createStyleView(ViewGroup parent, String channelName) {
        TextView textView = new TextView(parent.getContext());
        textView.setText(channelName);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, channelTextSize);
        return textView;
    }

    @Override
    public void setNormalStyle(View channelView) {
        TextView textView = (TextView) channelView;
        textView.setTextColor(channelNormalTextColor);
        textView.setBackgroundResource(channelNormalBackground);
    }

    @Override
    public void setFixedStyle(View channelView) {
        TextView textView = (TextView) channelView;
        textView.setTextColor(channelFixedTextColor);
        textView.setBackgroundResource(channelFixedBackground);
    }

    @Override
    public void setEditStyle(View channelView) {
        TextView textView = (TextView) channelView;
        textView.setBackgroundResource(channelEditBackground);
        textView.setTextColor(channelNormalTextColor);
    }

    @Override
    public void setFocusedStyle(View channelView) {
        TextView textView = (TextView) channelView;
        textView.setBackgroundResource(channelFocusedBackground);
        textView.setTextColor(channelFocusedTextColor);
    }

    void setBackgroundResource(View view, @DrawableRes int background) {
        this.channelNormalBackground = background;
        view.setBackgroundResource(background);
    }

    void setTextColor(View view, @ColorInt int textColor) {
        this.channelFixedTextColor = textColor;
        TextView textView = (TextView) view;
        textView.setTextColor(textColor);
    }

    void setTextSize(View view, int channelTextSize) {
        TextView textView = (TextView) view;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, channelTextSize);
    }

    private int channelTextSize;

    @ColorInt
    private int channelNormalTextColor;

    @DrawableRes
    private int channelNormalBackground;

    @ColorInt
    private int channelFixedTextColor;

    @DrawableRes
    private int channelFixedBackground;

    @DrawableRes
    private int channelEditBackground;

    @DrawableRes
    private int channelFocusedBackground;

    @ColorInt
    private int channelFocusedTextColor;

    void setChannelTextSize(int channelTextSize) {
        this.channelTextSize = channelTextSize;
    }

    void setChannelNormalTextColor(int channelNormalTextColor) {
        this.channelNormalTextColor = channelNormalTextColor;
    }

    void setChannelNormalBackground(int channelNormalBackground) {
        this.channelNormalBackground = channelNormalBackground;
    }

    void setChannelFixedTextColor(int channelFixedTextColor) {
        this.channelFixedTextColor = channelFixedTextColor;
    }

    void setChannelFixedBackground(int channelFixedBackground) {
        this.channelFixedBackground = channelFixedBackground;
    }

    void setChannelEditBackground(int channelEditBackground) {
        this.channelEditBackground = channelEditBackground;
    }

    void setChannelFocusedBackground(int channelFocusedBackground) {
        this.channelFocusedBackground = channelFocusedBackground;
    }

    void setChannelFocusedTextColor(int channelFocusedTextColor) {
        this.channelFocusedTextColor = channelFocusedTextColor;
    }
}
