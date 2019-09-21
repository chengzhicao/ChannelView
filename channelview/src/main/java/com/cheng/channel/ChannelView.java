package com.cheng.channel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cheng.channel.adapter.StyleAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChannelView extends ScrollView {
    private Context mContext;
    private Map<String, List<Channel>> channelContents = new LinkedHashMap<>();
    private int channelFixedCount = 0;
    private ChannelLayout channelLayout;

    /**
     * 列数
     */
    private int channelColumn = 4;

    private int channelWidth;

    private int channelHeight;

    /**
     * 周围padding
     */
    private int channelPadding;

    /**
     * 频道板块title高度
     */
    private int platesTitleHeight;

    /**
     * 频道板块title左右padding
     */
    private int platesTitleLeftRightPadding;

    /**
     * 水平方向上的间隔线
     */
    private int channelHorizontalSpacing;

    /**
     * 竖直方向上的间隔线
     */
    private int channelVerticalSpacing;

    @DrawableRes
    @Deprecated
    private int channelNormalBackground;

    @DrawableRes
    @Deprecated
    private int channelEditBackground;

    @DrawableRes
    @Deprecated
    private int channelFocusedBackground;

    @DrawableRes
    @Deprecated
    private int channelFixedBackground;

    @ColorInt
    @Deprecated
    private int channelNormalTextColor;

    @ColorInt
    @Deprecated
    private int channelFixedTextColor;

    @ColorInt
    @Deprecated
    private int channelFocusedTextColor;

    @DrawableRes
    private int tipEditBackground;

    @DrawableRes
    private int tipFinishBackground;

    @DrawableRes
    private int platesTitleBackground;

    @ColorInt
    private int tipEditTextColor;

    @ColorInt
    private int tipFinishTextColor;

    @ColorInt
    private int platesTitleColor;

    @DrawableRes
    private int otherSubTitleBackground;

    @ColorInt
    private int otherSubTitleTextColor;

    private int otherSubTitleTextSize;

    @DrawableRes
    private int subTitleBackground;

    @ColorInt
    private int subTitleTextColor;

    private int subTitleTextSize;

    private boolean platesTitleBold;

    private int platesTitleSize;

    private int tipEditTextSize;

    private int tipFinishTextSize;

    @Deprecated
    private int channelTextSize;

    private List<View> fixedTextView = new ArrayList<>();

    private List<View> allTextView = new ArrayList<>();

    private List<TextView> platesTitle = new ArrayList<>();

    private List<TextView> otherSubTitles = new ArrayList<>();

    private float density;

    public ChannelView(Context context) {
        this(context, null);
    }

    public ChannelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChannelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChannelView);
        channelHeight = typedArray.getDimensionPixelSize(R.styleable.ChannelView_channelHeight, getResources().getDimensionPixelSize(R.dimen.channelHeight));
        channelColumn = typedArray.getInteger(R.styleable.ChannelView_channelColumn, channelColumn);
        channelPadding = typedArray.getDimensionPixelSize(R.styleable.ChannelView_channelPadding, getResources().getDimensionPixelSize(R.dimen.channelPadding));
        channelHorizontalSpacing = typedArray.getDimensionPixelSize(R.styleable.ChannelView_channelHorizontalSpacing, getResources().getDimensionPixelSize(R.dimen.channelHorizontalSpacing));
        channelVerticalSpacing = typedArray.getDimensionPixelSize(R.styleable.ChannelView_channelVerticalSpacing, getResources().getDimensionPixelSize(R.dimen.channelVerticalSpacing));
        channelNormalBackground = typedArray.getResourceId(R.styleable.ChannelView_channelNormalBackground, R.drawable.bg_channel_normal);
        channelEditBackground = typedArray.getResourceId(R.styleable.ChannelView_channelEditBackground, R.drawable.bg_channel_edit);
        channelFocusedBackground = typedArray.getResourceId(R.styleable.ChannelView_channelFocusedBackground, R.drawable.bg_channel_focused);
        channelFixedBackground = typedArray.getResourceId(R.styleable.ChannelView_channelFixedBackground, R.drawable.bg_channel_normal);
        channelNormalTextColor = typedArray.getColor(R.styleable.ChannelView_channelNormalTextColor, getResources().getColor(R.color.channelNormalTextColor));
        channelFixedTextColor = typedArray.getColor(R.styleable.ChannelView_channelFixedTextColor, getResources().getColor(R.color.channelFixedTextColor));
        channelFocusedTextColor = typedArray.getColor(R.styleable.ChannelView_channelFocusedTextColor, getResources().getColor(R.color.channelNormalTextColor));
        channelFixedCount = typedArray.getInteger(R.styleable.ChannelView_channelFixedCount, channelFixedCount);
        channelTextSize = typedArray.getDimensionPixelSize(R.styleable.ChannelView_channelTextSize, getResources().getDimensionPixelSize(R.dimen.channelTextSize));
        tipEditBackground = typedArray.getResourceId(R.styleable.ChannelView_tipEditBackground, R.drawable.bg_channel_transparent);
        platesTitleBackground = typedArray.getResourceId(R.styleable.ChannelView_platesTitleBackground, R.drawable.bg_channel_transparent);
        tipEditTextColor = typedArray.getColor(R.styleable.ChannelView_tipEditTextColor, getResources().getColor(R.color.channelNormalTextColor));
        platesTitleColor = typedArray.getColor(R.styleable.ChannelView_platesTitleColor, getResources().getColor(R.color.channelNormalTextColor));
        platesTitleBold = typedArray.getBoolean(R.styleable.ChannelView_platesTitleBold, false);
        platesTitleSize = typedArray.getDimensionPixelSize(R.styleable.ChannelView_platesTitleSize, getResources().getDimensionPixelSize(R.dimen.channelTextSize));
        tipEditTextSize = typedArray.getDimensionPixelSize(R.styleable.ChannelView_tipEditTextSize, getResources().getDimensionPixelSize(R.dimen.channelTextSize));
        platesTitleHeight = typedArray.getDimensionPixelSize(R.styleable.ChannelView_platesTitleHeight, getResources().getDimensionPixelSize(R.dimen.platesTitleHeight));
        platesTitleLeftRightPadding = typedArray.getDimensionPixelSize(R.styleable.ChannelView_platesTitleLeftRightPadding, getResources().getDimensionPixelSize(R.dimen.platesTitleLeftRightPadding));
        otherSubTitleBackground = typedArray.getResourceId(R.styleable.ChannelView_otherSubTitleBackground, R.drawable.bg_channel_transparent);
        otherSubTitleTextColor = typedArray.getColor(R.styleable.ChannelView_otherSubTitleTextColor, getResources().getColor(R.color.subTitleTextColor));
        otherSubTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.ChannelView_otherSubTitleTextSize, getResources().getDimensionPixelSize(R.dimen.subTitleTextSize));
        subTitleBackground = typedArray.getResourceId(R.styleable.ChannelView_subTitleBackground, R.drawable.bg_channel_transparent);
        subTitleTextColor = typedArray.getColor(R.styleable.ChannelView_subTitleTextColor, getResources().getColor(R.color.subTitleTextColor));
        subTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.ChannelView_subTitleTextSize, getResources().getDimensionPixelSize(R.dimen.subTitleTextSize));
        subTitleName = typedArray.getString(R.styleable.ChannelView_subTitleName);
        otherSubTitleName = typedArray.getString(R.styleable.ChannelView_otherSubTitleName);
        tipFinishBackground = typedArray.getResourceId(R.styleable.ChannelView_tipFinishBackground, R.drawable.bg_channel_transparent);
        tipFinishTextColor = typedArray.getColor(R.styleable.ChannelView_tipFinishTextColor, getResources().getColor(R.color.channelNormalTextColor));
        tipFinishTextSize = typedArray.getDimensionPixelSize(R.styleable.ChannelView_tipFinishTextSize, getResources().getDimensionPixelSize(R.dimen.channelTextSize));
        typedArray.recycle();
        if (subTitleName == null) {
            subTitleName = "";
        }
        if (otherSubTitleName == null) {
            otherSubTitleName = "";
        }
        if (channelColumn < 1) {
            channelColumn = 1;
        }
        if (channelFixedCount < 0) {
            channelFixedCount = 0;
        }
        density = context.getResources().getDisplayMetrics().density;
        maxAccessDrag = density * DRAG_THRESHOLD + 0.5f;
    }

    /**
     * 可允许拖拽的阈值(单位为dp)
     */
    private final int DRAG_THRESHOLD = 5;

    /**
     * 触摸频道进行move时，当达到该值时可允许频道拖拽
     */
    private float maxAccessDrag;

    private int recommendPosition = -1;

    /**
     * 设置固定频道个数
     *
     * @param channelFixedCount
     */
    public void setChannelFixedCount(int channelFixedCount) {
        if (channelFixedCount < 0) {
            throw new RuntimeException("固定频道数量必须大于0");
        }
        this.channelFixedCount = channelFixedCount;
        if (channelLayout == null) {
            return;
        }
        if (channelLayout.channelGroups.size() > 0 && channelLayout.channelGroups.get(0) != null) {
            if (channelFixedCount > channelLayout.channelGroups.get(0).size()) {
                throw new RuntimeException("固定频道数量不能大于已选频道数量");
            }
            for (int i = 0; i < channelFixedCount; i++) {
                View view = channelLayout.channelGroups.get(0).get(i);
                styleAdapter.setFixedStyle(getViewHolder(view));
            }
        }
    }

    /**
     * 添加频道时设置是否插入到推荐位置，如果不设置，默认插入到尾部
     *
     * @param recommendPosition 推荐位置
     */
    public void setInsertRecommendPosition(int recommendPosition) {
        this.recommendPosition = recommendPosition;
    }

    /**
     * 添加频道板块
     *
     * @see StyleAdapter#getChannelData()
     */
    @Deprecated
    public void addPlate(String plateName, List<Channel> channelList) {
        if (channelList != null && channelList.size() > 0) {
            if (channelContents.size() != 0) {
                for (Channel channel : channelList) {
                    channel.channelBelong = channelContents.size();
                }
            } else {
                myChannelCode = new int[channelList.size()];
                for (int i = 0; i < channelList.size(); i++) {
                    channelList.get(i).code = i;
                    myChannelCode[i] = i;
                }
            }
            channelContents.put(plateName, channelList);
        }
    }

    /**
     * 设置频道正常状态下背景
     *
     * @see StyleAdapter#setNormalStyle(ViewHolder)
     */
    @Deprecated
    public void setChannelNormalBackground(@DrawableRes int channelNormalBackground) {
        if (checkDefaultAdapter()) {
            for (View view : allTextView) {
                defaultStyleAdapter.setBackgroundResource(view, channelNormalBackground);
            }
        }
    }

    /**
     * 设置频道编辑状态下背景
     *
     * @see StyleAdapter#setEditStyle(ViewHolder)
     */
    @Deprecated
    public void setChannelEditBackground(@DrawableRes int channelEditBackground) {
        if (checkDefaultAdapter()) {
            defaultStyleAdapter.setChannelEditBackground(channelEditBackground);
        }
    }

    /**
     * 设置频道编辑且点击状态下背景
     *
     * @see StyleAdapter#setFocusedStyle(ViewHolder)
     */
    @Deprecated
    public void setChannelFocusedBackground(@DrawableRes int channelFocusedBackground) {
        if (checkDefaultAdapter()) {
            defaultStyleAdapter.setChannelFocusedBackground(channelFocusedBackground);
        }
    }

    /**
     * 设置固定频道的背景
     *
     * @param channelFixedBackground
     * @see StyleAdapter#setFixedStyle(ViewHolder)
     */
    @Deprecated
    public void setChannelFixedBackground(@DrawableRes int channelFixedBackground) {
        if (checkDefaultAdapter()) {
            for (View view : fixedTextView) {
                defaultStyleAdapter.setBackgroundResource(view, channelFixedBackground);
            }
        }
    }

    /**
     * 设置固定频道的颜色
     *
     * @param channelFixedTextColor
     * @see StyleAdapter#setFixedStyle(ViewHolder)
     */
    @Deprecated
    public void setChannelFixedTextColor(@ColorInt int channelFixedTextColor) {
        if (checkDefaultAdapter()) {
            for (View view : fixedTextView) {
                defaultStyleAdapter.setTextColor(view, channelFixedTextColor);
            }
        }
    }

    /**
     * 设置频道字体颜色
     *
     * @param channelNormalTextColor
     * @see StyleAdapter#setNormalStyle(ViewHolder)
     */
    @Deprecated
    public void setChannelNormalTextColor(@ColorInt int channelNormalTextColor) {
        if (checkDefaultAdapter()) {
            for (View view : allTextView) {
                defaultStyleAdapter.setTextColor(view, channelNormalTextColor);
            }
        }
    }

    /**
     * 设置编辑且点击状态下频道字体颜色
     *
     * @param channelFocusedTextColor
     * @see StyleAdapter#setFocusedStyle(ViewHolder)
     */
    @Deprecated
    public void setChannelFocusedTextColor(@ColorInt int channelFocusedTextColor) {
        if (checkDefaultAdapter()) {
            defaultStyleAdapter.setChannelFocusedTextColor(channelFocusedTextColor);
        }
    }

    /**
     * 设置频道字体大小
     *
     * @param channelTextSize
     * @see StyleAdapter#createStyleView(ViewGroup, String)
     */
    @Deprecated
    public void setChannelTextSizeRes(@DimenRes int channelTextSize) {
        this.channelTextSize = getResources().getDimensionPixelSize(channelTextSize);
        if (checkDefaultAdapter()) {
            for (View view : allTextView) {
                defaultStyleAdapter.setTextSize(view, this.channelTextSize);
            }
        }
        if (checkDefaultAdapter()) {
            for (View view : fixedTextView) {
                defaultStyleAdapter.setTextSize(view, this.channelTextSize);
            }
        }
    }

    /**
     * 设置频道字体大小
     *
     * @param channelTextSize
     * @see StyleAdapter#createStyleView(ViewGroup, String)
     */
    @Deprecated
    public void setChannelTextSize(int unit, int channelTextSize) {
        this.channelTextSize = (int) TypedValue.applyDimension(unit, channelTextSize, getResources().getDisplayMetrics());
        if (checkDefaultAdapter()) {
            for (View view : allTextView) {
                defaultStyleAdapter.setTextSize(view, this.channelTextSize);
            }
        }
        if (checkDefaultAdapter()) {
            for (View view : fixedTextView) {
                defaultStyleAdapter.setTextSize(view, this.channelTextSize);
            }
        }
    }

    /**
     * 设置频道板块标题背景
     *
     * @param platesTitleBackground
     */
    public void setPlatesTitleBackground(@DrawableRes int platesTitleBackground) {
        this.platesTitleBackground = platesTitleBackground;
        for (TextView title : platesTitle) {
            title.setBackgroundResource(platesTitleBackground);
        }
    }

    /**
     * 设置频道板块标题颜色
     *
     * @param platesTitleColor
     */
    public void setPlatesTitleColor(@ColorInt int platesTitleColor) {
        this.platesTitleColor = platesTitleColor;
        for (TextView title : platesTitle) {
            title.setTextColor(platesTitleColor);
        }
    }

    /**
     * 设置编辑按键背景
     *
     * @param tipEditBackground
     */
    public void setTipEditBackground(@DrawableRes int tipEditBackground) {
        this.tipEditBackground = tipEditBackground;
        if (channelLayout != null && channelLayout.tipEdit != null) {
            channelLayout.tipEdit.setBackgroundResource(tipEditBackground);
        }
    }

    /**
     * 设置完成按键背景
     *
     * @param tipFinishBackground
     */
    public void setTipFinishBackground(@DrawableRes int tipFinishBackground) {
        this.tipFinishBackground = tipFinishBackground;
        if (channelLayout != null && channelLayout.tipFinish != null) {
            channelLayout.tipFinish.setBackgroundResource(tipFinishBackground);
        }
    }

    /**
     * 设置编辑按键颜色
     *
     * @param tipEditTextColor
     */
    public void setTipEditTextColor(@ColorInt int tipEditTextColor) {
        this.tipEditTextColor = tipEditTextColor;
        if (channelLayout != null && channelLayout.tipEdit != null) {
            channelLayout.tipEdit.setTextColor(tipEditTextColor);
        }
    }

    /**
     * 设置完成按键颜色
     *
     * @param tipFinishTextColor
     */
    public void setTipFinishTextColor(@ColorInt int tipFinishTextColor) {
        this.tipFinishTextColor = tipFinishTextColor;
        if (channelLayout != null && channelLayout.tipFinish != null) {
            channelLayout.tipFinish.setTextColor(tipFinishTextColor);
        }
    }

    /**
     * 设置频道板块标题是否加粗
     *
     * @param platesTitleBold
     */
    public void setPlatesTitleBold(boolean platesTitleBold) {
        this.platesTitleBold = platesTitleBold;
        if (platesTitleBold) {
            for (TextView title : platesTitle) {
                title.setTypeface(Typeface.DEFAULT_BOLD);
            }
        }
    }

    /**
     * 设置频道板块标题大小
     *
     * @param unit
     * @param platesTitleSize
     */
    public void setPlatesTitleSize(int unit, int platesTitleSize) {
        this.platesTitleSize = (int) TypedValue.applyDimension(unit, platesTitleSize, getResources().getDisplayMetrics());
        for (TextView title : platesTitle) {
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.platesTitleSize);
        }
    }

    public void setPlatesTitleSizeRes(@DimenRes int platesTitleSize) {
        this.platesTitleSize = getResources().getDimensionPixelSize(platesTitleSize);
        for (TextView title : platesTitle) {
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.platesTitleSize);
        }
    }

    /**
     * 设置编辑按键字体大小
     *
     * @param unit
     * @param tipEditTextSize
     */
    public void setTipEditTextSize(int unit, int tipEditTextSize) {
        this.tipEditTextSize = (int) TypedValue.applyDimension(unit, tipEditTextSize, getResources().getDisplayMetrics());
        if (channelLayout != null && channelLayout.tipEdit != null) {
            channelLayout.tipEdit.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.tipEditTextSize);
        }
    }

    public void setTipEditTextSizeRes(@DimenRes int tipEditTextSize) {
        this.tipEditTextSize = getResources().getDimensionPixelSize(tipEditTextSize);
        if (channelLayout != null && channelLayout.tipEdit != null) {
            channelLayout.tipEdit.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.tipEditTextSize);
        }
    }

    /**
     * 设置完成按键字体大小
     *
     * @param unit
     * @param tipFinishTextSize
     */
    public void setTipFinishTextSize(int unit, int tipFinishTextSize) {
        this.tipFinishTextSize = (int) TypedValue.applyDimension(unit, tipFinishTextSize, getResources().getDisplayMetrics());
        if (channelLayout != null && channelLayout.tipFinish != null) {
            channelLayout.tipFinish.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.tipFinishTextSize);
        }
    }

    public void setTipFinishTextSizeRes(@DimenRes int tipFinishTextSize) {
        this.tipFinishTextSize = getResources().getDimensionPixelSize(tipFinishTextSize);
        if (channelLayout != null && channelLayout.tipFinish != null) {
            channelLayout.tipFinish.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.tipFinishTextSize);
        }
    }


    /**
     * 设置其它频道板块的副标题背景
     *
     * @param otherSubTitleBackground
     */
    public void setOtherSubTitleBackground(@DrawableRes int otherSubTitleBackground) {
        this.otherSubTitleBackground = otherSubTitleBackground;
        for (TextView otherSubTitle : otherSubTitles) {
            otherSubTitle.setBackgroundResource(otherSubTitleBackground);
        }
    }

    /**
     * 设置其它频道板块的副标题颜色
     *
     * @param otherSubTitleTextColor
     */
    public void setOtherSubTitleTextColor(@ColorInt int otherSubTitleTextColor) {
        this.otherSubTitleTextColor = otherSubTitleTextColor;
        for (TextView otherSubTitle : otherSubTitles) {
            otherSubTitle.setTextColor(otherSubTitleTextColor);
        }
    }

    /**
     * 设置其它频道板块的副标题字体大小
     *
     * @param unit
     * @param otherSubTitleTextSize
     */
    public void setOtherSubTitleTextSize(int unit, int otherSubTitleTextSize) {
        this.otherSubTitleTextSize = (int) TypedValue.applyDimension(unit, otherSubTitleTextSize, getResources().getDisplayMetrics());
        for (TextView otherSubTitle : otherSubTitles) {
            otherSubTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.otherSubTitleTextSize);
        }
    }

    public void setOtherSubTitleTextSizeRes(@DimenRes int otherSubTitleTextSize) {
        this.otherSubTitleTextSize = getResources().getDimensionPixelSize(otherSubTitleTextSize);
        for (TextView otherSubTitle : otherSubTitles) {
            otherSubTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.otherSubTitleTextSize);
        }
    }

    /**
     * 设置已选频道板块的副标题背景
     *
     * @param subTitleBackground
     */
    public void setSubTitleBackground(@DrawableRes int subTitleBackground) {
        this.subTitleBackground = subTitleBackground;
        if (channelLayout != null && channelLayout.subTitle != null) {
            channelLayout.subTitle.setBackgroundResource(subTitleBackground);
        }
    }

    /**
     * 设置已选频道板块的副标题颜色
     *
     * @param subTitleTextColor
     */
    public void setSubTitleTextColor(@ColorInt int subTitleTextColor) {
        this.subTitleTextColor = subTitleTextColor;
        if (channelLayout != null && channelLayout.subTitle != null) {
            channelLayout.subTitle.setTextColor(subTitleTextColor);
        }
    }

    /**
     * 设置已选频道板块的副标题字体大小
     *
     * @param unit
     * @param subTitleTextSize
     */
    public void setSubTitleTextSize(int unit, int subTitleTextSize) {
        this.subTitleTextSize = (int) TypedValue.applyDimension(unit, subTitleTextSize, getResources().getDisplayMetrics());
        if (channelLayout != null && channelLayout.subTitle != null) {
            channelLayout.subTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.subTitleTextSize);
        }
    }

    public void setSubTitleTextSizeRes(@DimenRes int subTitleTextSize) {
        this.subTitleTextSize = getResources().getDimensionPixelSize(subTitleTextSize);
        if (channelLayout != null && channelLayout.subTitle != null) {
            channelLayout.subTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.subTitleTextSize);
        }
    }

    /**
     * 设置已选频道的副标题
     *
     * @param subTitleName
     */
    public void setSubTitleName(String subTitleName) {
        this.subTitleName = subTitleName;
        if (channelLayout != null && channelLayout.subTitle != null) {
            channelLayout.subTitle.setText(subTitleName);
        }
    }

    private String subTitleName = "";

    private String otherSubTitleName = "";

    /**
     * 设置其它未选频道的副标题
     *
     * @param otherSubTitleName
     */
    public void setOtherSubTitleName(String otherSubTitleName) {
        this.otherSubTitleName = otherSubTitleName;
        for (TextView otherSubTitle : otherSubTitles) {
            otherSubTitle.setText(otherSubTitleName);
        }
    }

    private OnChannelListener onChannelListener;

    private DefaultStyleAdapter defaultStyleAdapter;

    /**
     * 检查adapter是否是defaultStyleAdapter
     *
     * @return
     */
    private boolean checkDefaultAdapter() {
        return defaultStyleAdapter != null || styleAdapter instanceof DefaultStyleAdapter;
    }

    /**
     * 是否已填充数据
     */
    private boolean isInflateData;

    /**
     * 添加完频道模块之后，进行填充数据
     *
     * @see StyleAdapter#getChannelData()
     */
    @Deprecated
    public void inflateData() {
        //只填充一次数据
        if (isInflateData) {
            return;
        }
        isInflateData = true;
        if (styleAdapter == null) {
            styleAdapter = defaultStyleAdapter = new DefaultStyleAdapter() {

                @Override
                public LinkedHashMap<String, List<Channel>> getChannelData() {
                    return null;
                }
            };
        }
        LinkedHashMap<String, List<Channel>> channelData = styleAdapter.getChannelData();
        if (channelData != null) {
            channelContents.clear();
            Set<String> keySet = channelData.keySet();
            for (String key : keySet) {
                addPlate(key, channelData.get(key));
            }
        }
        //如果只有一组频道，默认再加上一组
        if (channelContents.size() == 1) {
            channelContents.put("推荐频道", null);
        }
        if (checkDefaultAdapter()) {
            defaultStyleAdapter = (DefaultStyleAdapter) styleAdapter;
            defaultStyleAdapter.setChannelTextSize(channelTextSize);
            defaultStyleAdapter.setChannelNormalBackground(channelNormalBackground);
            defaultStyleAdapter.setChannelFocusedBackground(channelFocusedBackground);
            defaultStyleAdapter.setChannelEditBackground(channelEditBackground);
            defaultStyleAdapter.setChannelFixedBackground(channelFixedBackground);
            defaultStyleAdapter.setChannelNormalTextColor(channelNormalTextColor);
            defaultStyleAdapter.setChannelFixedTextColor(channelFixedTextColor);
            defaultStyleAdapter.setChannelFocusedTextColor(channelFocusedTextColor);
        }
        if (channelLayout == null) {
            channelLayout = new ChannelLayout(mContext);
            addView(channelLayout);
        }
    }

    /**
     * 获取我的频道
     *
     * @return
     */
    public List<Channel> getMyChannel() {
        List<Channel> channels = new ArrayList<>();
        if (channelLayout != null && channelLayout.channelGroups.size() > 0 && channelLayout.channelGroups.get(0) != null) {
            for (View view : channelLayout.channelGroups.get(0)) {
                channels.add(getChannelAttr(view).channel);
            }
        }
        return channels;
    }

    /**
     * 获取其他频道
     *
     * @return
     */
    public List<List<Channel>> getOtherChannel() {
        List<List<Channel>> otherChannels = new ArrayList<>();
        if (channelLayout != null && channelLayout.channelGroups.size() > 0) {
            int len = channelLayout.channelGroups.size();
            for (int i = 1; i < len; i++) {
                List<Channel> channels = new ArrayList<>();
                for (View view : channelLayout.channelGroups.get(i)) {
                    channels.add(getChannelAttr(view).channel);
                }
                otherChannels.add(channels);
            }
        }
        return otherChannels;
    }

    private int[] myChannelCode;

    /**
     * 频道序列是否发生变化
     *
     * @return
     */
    public boolean isChange() {
        if (channelLayout != null && channelLayout.channelGroups.size() > 0 && channelLayout.channelGroups.get(0) != null) {
            int[] nowMyChannelCode = new int[channelLayout.channelGroups.get(0).size()];
            for (int i = 0; i < channelLayout.channelGroups.get(0).size(); i++) {
                ChannelAttr channelAttr = getChannelAttr(channelLayout.channelGroups.get(0).get(i));
                nowMyChannelCode[i] = channelAttr.channel.code;
            }
            if (myChannelCode.length == nowMyChannelCode.length) {
                for (int i = 0; i < myChannelCode.length; i++) {
                    if (myChannelCode[i] != nowMyChannelCode[i]) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public interface OnChannelListener {
        /**
         * 正常状态下频道点击
         *
         * @param position
         * @param channel
         */
        void channelItemClick(int position, Channel channel);

        /**
         * 编辑频道完成
         *
         * @param channelList
         */
        void channelEditFinish(List<Channel> channelList);

        /**
         * 开始编辑频道
         */
        void channelEditStart();
    }

    public interface OnChannelListener2 extends OnChannelListener {
        /**
         * 编辑状态下频道点击
         *
         * @param position
         * @param channel
         */
        void channelEditStateItemClick(int position, Channel channel);
    }

    /**
     * @param onChannelListener
     * @see ChannelView#setOnChannelListener(OnChannelListener)
     */
    @Deprecated
    public void setOnChannelItemClickListener(OnChannelListener onChannelListener) {
        this.onChannelListener = onChannelListener;
    }

    /**
     * 状态或点击监听
     *
     * @param onChannelListener
     */
    public void setOnChannelListener(OnChannelListener onChannelListener) {
        this.onChannelListener = onChannelListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int actionMask = ev.getAction() & MotionEvent.ACTION_MASK;
        if (actionMask == MotionEvent.ACTION_POINTER_DOWN) {
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }

    private StyleAdapter styleAdapter;

    public void setStyleAdapter(StyleAdapter styleAdapter) {
        if (isInflateData) {
            return;
        }
        this.styleAdapter = styleAdapter;
        inflateData();
    }

    private class ChannelLayout extends GridLayout implements OnLongClickListener, OnClickListener, OnTouchListener {

        /**
         * 频道最小可拖动距离
         */
        private int RANGE = 30;

        private final int DURATION_TIME = 200;

        /**
         * 频道普通点击
         */
        private final int NORMAL = 0X00;

        /**
         * 点击删除频道
         */
        private final int DELETE = 0x01;

        private int channelClickType = NORMAL;

        /**
         * 是否重新布局
         */
        private boolean isAgainLayout = true;

        private AnimatorSet animatorSet = new AnimatorSet();

        /**
         * 所有频道标题组
         */
        private List<View> channelTitleGroups = new ArrayList<>();

        /**
         * 所有频道组
         */
        private List<ArrayList<View>> channelGroups = new ArrayList<>();

        /**
         * 每组channel的行数
         */
        private int[] groupChannelColumns;

        /**
         * 所有channel组的高度
         */
        private int allChannelGroupsHeight;

        private TextView tipEdit, tipFinish, subTitle;

        /**
         * 动态高度
         */
        private int animateHeight;

        /**
         * 是否通过动画改变高度
         */
        private boolean isAnimateChangeHeight;

        /**
         * 是否是编辑状态
         */
        private boolean isEditState;

        /**
         * 是否允许拖拽
         */
        private boolean isAccessDrag;

        /**
         * 可允许拖拽的最小间隔时间
         */
        private final int MIN_TIME_INTERVAL = 65;

        public ChannelLayout(Context context) {
            this(context, null);
        }

        public ChannelLayout(Context context, @Nullable AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public ChannelLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);//ChannelLayout的宽
            //不是通过动画改变ChannelLayout的高度
            if (!isAnimateChangeHeight) {
                int height = 0;
                int allChannelTitleHeight = 0;
                for (int i = 0; i < getChildCount(); i++) {
                    View childAt = getChildAt(i);
                    if (getChannelAttr(childAt).type == ChannelAttr.TITLE) {
                        //计算标题View的宽高
                        childAt.measure(MeasureSpec.makeMeasureSpec(width - channelPadding * 2, MeasureSpec.EXACTLY), heightMeasureSpec);
                        allChannelTitleHeight += childAt.getMeasuredHeight();
                    } else if (getChannelAttr(childAt).type == ChannelAttr.CHANNEL) {
                        //计算每个频道的宽高
                        channelWidth = (width - channelVerticalSpacing * (channelColumn * 2 - 2) - channelPadding * 2) / channelColumn;
                        childAt.measure(MeasureSpec.makeMeasureSpec(channelWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(channelHeight, MeasureSpec.EXACTLY));
                    }
                }
                for (int groupChannelColumn : groupChannelColumns) {
                    if (groupChannelColumn > 0) {
                        height += channelHeight * groupChannelColumn + (groupChannelColumn * 2 - 2) * channelHorizontalSpacing;
                    }
                }
                allChannelGroupsHeight = height;
                height += channelPadding * 2 + allChannelTitleHeight;//ChannelLayout的高
                setMeasuredDimension(width, height);
            } else {//通过动画改变ChannelLayout的高度
                setMeasuredDimension(width, animateHeight);
            }
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            if (isAgainLayout) {
                super.onLayout(changed, left, top, right, bottom);
                for (int i = 0; i < getChildCount(); i++) {
                    View childAt = getChildAt(i);
                    ChannelAttr tag = getChannelAttr(childAt);
                    tag.coordinate.x = childAt.getX();
                    tag.coordinate.y = childAt.getY();
                }
                isAgainLayout = false;
            }
        }

        private void init() {
            RANGE = (int) (density * RANGE + 0.5f);
            setColumnCount(channelColumn);
            addChannelView();
        }

        /**
         * 设置频道View
         */
        private void addChannelView() {
            if (channelContents != null) {
                groupChannelColumns = new int[channelContents.size()];
                int j = 0;
                int startRow = 0;
                for (String aKeySet : channelContents.keySet()) {//遍历key值，设置标题名称
                    List<Channel> channelContent = channelContents.get(aKeySet);
                    if (j == 0 && channelFixedCount > channelContent.size()) {
                        throw new RuntimeException("固定频道数量不能大于已选频道数量");
                    }
                    if (channelContent == null) {
                        channelContent = new ArrayList<>();
                    }
                    groupChannelColumns[j] = channelContent.size() % channelColumn == 0 ? channelContent.size() / channelColumn : channelContent.size() / channelColumn + 1;
                    if (j == 0) {
                        startRow = 0;
                    } else {
                        startRow += groupChannelColumns[j - 1] + 1;
                    }
                    Spec rowSpec = GridLayout.spec(startRow);
                    //标题要占channelColumn列
                    Spec columnSpec = GridLayout.spec(0, channelColumn);
                    ChannelLayoutParams layoutParams = new ChannelLayoutParams(rowSpec, columnSpec);
                    View view = LayoutInflater.from(mContext).inflate(R.layout.cgl_my_channel, null);
                    TextView otherSubTitle = view.findViewById(R.id.tv_sub_title);
                    if (j == 0) {
                        tipEdit = view.findViewById(R.id.tv_tip_edit);
                        tipEdit.setVisibility(VISIBLE);
                        tipEdit.setOnClickListener(this);
                        tipEdit.setBackgroundResource(tipEditBackground);
                        tipEdit.setTextColor(tipEditTextColor);
                        tipEdit.setTextSize(TypedValue.COMPLEX_UNIT_PX, tipEditTextSize);
                        tipFinish = view.findViewById(R.id.tv_tip_finish);
                        tipFinish.setVisibility(INVISIBLE);
                        tipFinish.setOnClickListener(this);
                        tipFinish.setBackgroundResource(tipFinishBackground);
                        tipFinish.setTextColor(tipFinishTextColor);
                        tipFinish.setTextSize(TypedValue.COMPLEX_UNIT_PX, tipFinishTextSize);
                        subTitle = otherSubTitle;
                        subTitle.setText(subTitleName);
                        subTitle.setTextColor(subTitleTextColor);
                        subTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, subTitleTextSize);
                        subTitle.setBackgroundResource(subTitleBackground);
                    } else {
                        otherSubTitle.setText(otherSubTitleName);
                        otherSubTitle.setTextColor(otherSubTitleTextColor);
                        otherSubTitle.setBackgroundResource(otherSubTitleBackground);
                        otherSubTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, otherSubTitleTextSize);
                        otherSubTitles.add(otherSubTitle);
                    }
                    ChannelAttr channelTitleAttr = new ChannelAttr();
                    channelTitleAttr.type = ChannelAttr.TITLE;
                    channelTitleAttr.coordinate = new PointF();
                    TextView tvTitle = view.findViewById(R.id.tv_title);
                    tvTitle.setText(aKeySet);
                    tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, platesTitleSize);
                    tvTitle.setBackgroundResource(platesTitleBackground);
                    tvTitle.setTextColor(platesTitleColor);
                    if (platesTitleBold) {
                        tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
                    }
                    platesTitle.add(tvTitle);
                    layoutParams.height = platesTitleHeight;
                    layoutParams.leftMargin = channelPadding;
                    //为标题View添加一个ChannelAttr属性
                    layoutParams.mChannelAttr = channelTitleAttr;
                    view.setPadding(platesTitleLeftRightPadding, 0, platesTitleLeftRightPadding, 0);
                    addView(view, layoutParams);
                    channelTitleGroups.add(view);
                    ArrayList<View> channelGroup = new ArrayList<>();
                    int remainder = channelContent.size() % channelColumn;
                    for (int i = 0; i < channelContent.size(); i++) {//遍历value中的频道
                        ViewHolder holder = styleAdapter.createStyleView(this, channelContent.get(i).channelName);
                        View channelView = holder.itemView;
                        if (channelView == null) {
                            throw new RuntimeException("You must set an adapter for the channel.");
                        }
                        ChannelAttr channelAttr = new ChannelAttr();
                        channelAttr.type = ChannelAttr.CHANNEL;
                        channelAttr.groupIndex = j;
                        channelAttr.coordinate = new PointF();
                        channelAttr.channel = channelContent.get(i);
                        if (j == 0) {
                            if (i < channelFixedCount) {
                                styleAdapter.setFixedStyle(holder);
                                fixedTextView.add(channelView);
                            } else {
                                channelView.setOnTouchListener(this);
                                channelView.setOnLongClickListener(this);
                                styleAdapter.setNormalStyle(holder);
                                allTextView.add(channelView);
                            }
                        } else {
                            styleAdapter.setNormalStyle(holder);
                            allTextView.add(channelView);
                        }
                        channelView.setOnClickListener(this);
                        //设置每个频道的间距
                        ChannelLayoutParams params = new ChannelLayoutParams();
                        int leftMargin = channelVerticalSpacing, topMargin = channelHorizontalSpacing, rightMargin = channelVerticalSpacing, bottomMargin = channelHorizontalSpacing;
                        if (i % channelColumn == 0) {
                            leftMargin = channelPadding;
                        }
                        if ((i + 1) % channelColumn == 0) {
                            rightMargin = channelPadding;
                        }
                        if (i < channelColumn) {
                            topMargin = 0;
                        }
                        if (remainder == 0) {
                            if (i >= channelContent.size() - channelColumn) {
                                bottomMargin = 0;
                            }
                        } else {
                            if (i >= channelContent.size() - remainder) {
                                bottomMargin = 0;
                            }
                        }
                        params.mChannelAttr = channelAttr;
                        params.mViewHolder = holder;
                        params.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
                        addView(channelView, params);
                        channelGroup.add(channelView);
                    }
                    channelGroups.add(channelGroup);
                    j++;
                }
            }
        }

        /**
         * 拖拽时距离点击时的最远距离
         */
        private double maxDistanceToDownPosition;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
//            //如果点击的是我的频道组中的频道
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                maxDistanceToDownPosition = 0;
                downX = dragX = event.getRawX();
                downY = dragY = event.getRawY();
                if (isEditState) {
                    setTime(v);
                }
            }
            if (isEditState) {
                if (event.getAction() == MotionEvent.ACTION_MOVE && isAccessDrag) {
                    //手移动时拖动频道
                    //请求ScrollView不要拦截MOVE事件，交给TextView处理
                    requestDisallowInterceptTouchEvent(true);
                    if (maxDistanceToDownPosition < maxAccessDrag) {
                        double sqrt = Math.sqrt(Math.pow(event.getRawX() - downX, 2) + Math.pow(event.getRawY() - downY, 2));
                        if (sqrt > maxDistanceToDownPosition) {
                            maxDistanceToDownPosition = sqrt;
                        }
                    }
                    channelDrag(v, event);
                }
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    if (thread != null && thread.isAlive() && !thread.isInterrupted()) {
                        thread.interrupt();
                    }
                    if (isAccessDrag) {
                        ChannelAttr vTag = getChannelAttr(v);
                        v.animate().x(vTag.coordinate.x).y(vTag.coordinate.y).setDuration(DURATION_TIME);
                        styleAdapter.setEditStyle(getViewHolder(v));
                        isAccessDrag = false;
                        return !(maxDistanceToDownPosition < maxAccessDrag);
                    }
                }
            }
            return false;
        }

        private void setTime(final View v) {
            thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(MIN_TIME_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                    Message message = new Message();
                    message.obj = v;
                    handler.sendMessage(message);
                }
            };
            thread.start();
        }

        private Thread thread;

        private Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                View v = (View) msg.obj;
                v.bringToFront();
                styleAdapter.setFocusedStyle(getViewHolder(v));
                isAccessDrag = true;
            }
        };

        @Override
        public void onClick(View v) {
            if (v == tipEdit) {
                edit();
                if (onChannelListener != null) {
                    onChannelListener.channelEditStart();
                }
            } else if (v == tipFinish) {//点击完成按钮时
                changeTip(false);
                if (onChannelListener != null) {
                    onChannelListener.channelEditFinish(getMyChannel());
                }
            } else {
                ChannelAttr tag = getChannelAttr(v);
                ArrayList<View> channels = channelGroups.get(tag.groupIndex);
                //如果点击的是我的频道组中的频道
                int indexOf = channels.indexOf(v);
                if (tag.groupIndex == 0) {
                    if (channelClickType == DELETE && indexOf >= channelFixedCount) {
                        forwardSort(v, channels);
                        //减少我的频道
                        deleteMyChannel(v);
                        if (onChannelListener != null && onChannelListener instanceof OnChannelListener2) {
                            ((OnChannelListener2) onChannelListener).channelEditStateItemClick(indexOf, getChannelAttr(v).channel);
                        }
                    } else if (channelClickType == NORMAL) {
                        //普通状态时进行点击事件回调
                        if (onChannelListener != null) {
                            onChannelListener.channelItemClick(indexOf, getChannelAttr(v).channel);
                        }
                    }
                } else {//点击的其他频道组中的频道
                    forwardSort(v, channels);
                    //增加我的频道
                    addMyChannel(v);
                    if (onChannelListener != null && onChannelListener instanceof OnChannelListener2) {
                        ((OnChannelListener2) onChannelListener).channelEditStateItemClick(channelGroups.get(0).indexOf(v), getChannelAttr(v).channel);
                    }
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (isEditState) {
                return true;
            }
            v.bringToFront();
            ArrayList<View> views = channelGroups.get(0);
            int indexOf = views.indexOf(v);
            if (indexOf >= channelFixedCount) {
                for (int i = channelFixedCount; i < views.size(); i++) {
                    if (i == indexOf) {
                        styleAdapter.setFocusedStyle(getViewHolder(views.get(i)));
                    } else {
                        styleAdapter.setEditStyle(getViewHolder(views.get(i)));
                    }
                }
                changeTip(true);
            }
            isAccessDrag = true;
            //要返回true，否则会触发onclick事件
            return true;
        }

        private void edit() {
            ArrayList<View> views = channelGroups.get(0);
            for (int i = channelFixedCount; i < views.size(); i++) {
                styleAdapter.setEditStyle(getViewHolder(views.get(i)));
            }
            changeTip(true);
        }

        /**
         * 后面的频道向前排序
         *
         * @param v
         * @param channels
         */
        private void forwardSort(View v, ArrayList<View> channels) {
            int size = channels.size();
            int indexOfValue = channels.indexOf(v);
            if (indexOfValue != size - 1) {
                for (int i = size - 1; i > indexOfValue; i--) {
                    View lastView = channels.get(i - 1);
                    ChannelAttr lastViewTag = getChannelAttr(lastView);
                    View currentView = channels.get(i);
                    ChannelAttr currentViewTag = getChannelAttr(currentView);
                    currentViewTag.coordinate = lastViewTag.coordinate;
                    currentView.animate().x(currentViewTag.coordinate.x).y(currentViewTag.coordinate.y).setDuration(DURATION_TIME);
                }
            }
        }

        /**
         * 获取插入位置
         *
         * @return
         */
        private int getInsertPosition() {
            int size = channelGroups.get(0).size();
            if (recommendPosition < 0) {
                return size - 1;
            }
            if (size - 1 < recommendPosition) {
                return size - 1;
            } else {
                if (channelFixedCount > recommendPosition) {
                    return channelFixedCount;
                } else {
                    return recommendPosition;
                }
            }
        }

        /**
         * 增加我的频道
         *
         * @param v
         */
        private void addMyChannel(final View v) {
            //让点击的view置于最前方，避免遮挡
            v.bringToFront();
            ChannelAttr tag = getChannelAttr(v);
            ArrayList<View> channels = channelGroups.get(tag.groupIndex);
            ArrayList<View> myChannels = channelGroups.get(0);
            View insertPositionChannel;
            if (myChannels.size() == 0) {
                insertPositionChannel = channelTitleGroups.get(0);
            } else {
                insertPositionChannel = myChannels.get(myChannels.size() - 1);
            }
            ChannelAttr insertPositionChannelTag = getChannelAttr(insertPositionChannel);
            myChannels.add(myChannels.size(), v);
            channels.remove(v);
            v.setOnLongClickListener(this);
            v.setOnTouchListener(this);
            animateChangeGridLayoutHeight();
            ViewPropertyAnimator animate = v.animate();
            if (myChannels.size() % channelColumn == 1 || channelColumn == 1) {
                if (myChannels.size() == 1) {
                    tag.coordinate = new PointF(insertPositionChannelTag.coordinate.x, insertPositionChannelTag.coordinate.y + insertPositionChannel.getMeasuredHeight());
                    //我的频道多一行，下面的view往下移
                    viewMove(1, channelHeight);
                } else {
                    ChannelAttr firstMyChannelTag = getChannelAttr(myChannels.get(0));
                    tag.coordinate = new PointF(firstMyChannelTag.coordinate.x, insertPositionChannelTag.coordinate.y + channelHeight + channelHorizontalSpacing * 2);
                    //我的频道多一行，下面的view往下移
                    viewMove(1, channelHeight + channelHorizontalSpacing * 2);
                }
            } else {
                tag.coordinate = new PointF(insertPositionChannelTag.coordinate.x + channelWidth + channelVerticalSpacing * 2, insertPositionChannelTag.coordinate.y);
            }
            //可自定义插入位置，暂时在尾部插入
            int insertPosition = getInsertPosition();
            if (insertPosition != myChannels.size() - 1) {
                backOrForward(v, insertPosition, myChannels.size() - 1, myChannels, tag, getChannelAttr(myChannels.get(insertPosition)));
            }
            animate.x(tag.coordinate.x).y(tag.coordinate.y).setDuration(DURATION_TIME);
            if (channelClickType == DELETE) {
                styleAdapter.setEditStyle(getViewHolder(v));
            }
            //该频道少一行，下面的view往上移
            if (channels.size() % channelColumn == 0) {
                if (channels.size() == 0) {
                    viewMove(tag.groupIndex + 1, -channelHeight);
                } else {
                    viewMove(tag.groupIndex + 1, -channelHeight - channelHorizontalSpacing * 2);
                }
            }
            tag.groupIndex = 0;
        }

        /**
         * 删除我的频道
         *
         * @param v
         */
        private void deleteMyChannel(View v) {
            //让点击的view置于最前方，避免遮挡
            v.bringToFront();
            if (channelClickType == DELETE) {
                styleAdapter.setNormalStyle(getViewHolder(v));
            }
            ChannelAttr tag = getChannelAttr(v);
            int belong = tag.channel.channelBelong;
            if (belong < 1 || belong > channelContents.size() - 1) {
                belong = 1;
            }
            ArrayList<View> beLongChannels = channelGroups.get(belong);
            if (beLongChannels.size() == 0) {
                tag.coordinate = new PointF(getChannelAttr(channelTitleGroups.get(belong)).coordinate.x, getChannelAttr(channelTitleGroups.get(belong)).coordinate.y + channelTitleGroups.get(belong).getMeasuredHeight());
            } else {
                ChannelAttr arriveTag = getChannelAttr(beLongChannels.get(0));
                tag.coordinate = arriveTag.coordinate;
            }
            v.animate().x(tag.coordinate.x).y(tag.coordinate.y).setDuration(DURATION_TIME);
            beLongChannels.add(0, v);
            channelGroups.get(0).remove(v);
            v.setOnLongClickListener(null);
            v.setOnTouchListener(null);
            animateChangeGridLayoutHeight();
            PointF newPointF;
            ChannelAttr finalChannelViewTag = getChannelAttr(beLongChannels.get(beLongChannels.size() - 1));
            //这个地方要注意顺序
            if (channelGroups.get(0).size() % channelColumn == 0) {
                //我的频道中少了一行，底下的所有view全都上移
                if (channelGroups.get(0).size() == 0) {
                    viewMove(1, -channelHeight);
                } else {
                    viewMove(1, -channelHeight - channelHorizontalSpacing * 2);
                }
            }
            if (beLongChannels.size() % channelColumn == 1) {
                //回收来频道中多了一行，底下的所有view全都下移
                if (beLongChannels.size() == 1) {
                    viewMove(belong + 1, channelHeight);
                } else {
                    viewMove(belong + 1, channelHeight + channelHorizontalSpacing * 2);
                }
                newPointF = new PointF(tag.coordinate.x, finalChannelViewTag.coordinate.y + channelHeight + channelHorizontalSpacing * 2);
            } else {
                newPointF = new PointF(finalChannelViewTag.coordinate.x + channelWidth + channelVerticalSpacing * 2, finalChannelViewTag.coordinate.y);
            }
            for (int i = 1; i < beLongChannels.size(); i++) {
                View currentView = beLongChannels.get(i);
                ChannelAttr currentViewTag = getChannelAttr(currentView);
                if (i < beLongChannels.size() - 1) {
                    View nextView = beLongChannels.get(i + 1);
                    ChannelAttr nextViewTag = getChannelAttr(nextView);
                    currentViewTag.coordinate = nextViewTag.coordinate;
                } else {
                    currentViewTag.coordinate = newPointF;
                }
                currentView.animate().x(currentViewTag.coordinate.x).y(currentViewTag.coordinate.y).setDuration(DURATION_TIME);
            }
            tag.groupIndex = belong;
        }

        /**
         * 行数变化后的GridLayout高度并用动画改变
         */
        private void animateChangeGridLayoutHeight() {
            int newAllChannelGroupsHeight = 0;
            for (int i = 0; i < channelGroups.size(); i++) {
                ArrayList<View> channels = channelGroups.get(i);
                groupChannelColumns[i] = channels.size() % channelColumn == 0 ? channels.size() / channelColumn : channels.size() / channelColumn + 1;
            }
            for (int groupChannelColumn : groupChannelColumns) {
                if (groupChannelColumn > 0) {
                    newAllChannelGroupsHeight += channelHeight * groupChannelColumn + (groupChannelColumn * 2 - 2) * channelHorizontalSpacing;
                }
            }
            int changeHeight = newAllChannelGroupsHeight - allChannelGroupsHeight;
            if (changeHeight != 0) {
                allChannelGroupsHeight = newAllChannelGroupsHeight;
                ValueAnimator valueAnimator = ValueAnimator.ofInt(getMeasuredHeight(), getMeasuredHeight() + changeHeight);
                valueAnimator.setDuration(DURATION_TIME);
                valueAnimator.start();
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        animateHeight = (int) animation.getAnimatedValue();
                        isAnimateChangeHeight = true;
                        requestLayout();
                    }
                });
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isAnimateChangeHeight = false;
                    }
                });
            }
        }

        /**
         * 受到行数所影响的view进行上移或下移操作
         */
        private void viewMove(int position, int offSetY) {
            for (int i = position; i < channelTitleGroups.size(); i++) {
                View view = channelTitleGroups.get(i);
                ChannelAttr tag = getChannelAttr(view);
                tag.coordinate = new PointF(tag.coordinate.x, tag.coordinate.y + offSetY);
                view.animate().x(tag.coordinate.x).y(tag.coordinate.y).setDuration(DURATION_TIME);
            }
            for (int i = position; i < channelGroups.size(); i++) {
                ArrayList<View> otherChannels = channelGroups.get(i);
                for (int j = 0; j < otherChannels.size(); j++) {
                    View view = otherChannels.get(j);
                    ChannelAttr tag = getChannelAttr(view);
                    tag.coordinate = new PointF(tag.coordinate.x, tag.coordinate.y + offSetY);
                    view.animate().x(tag.coordinate.x).y(tag.coordinate.y).setDuration(DURATION_TIME);
                }
            }
        }

        private float downX, downY;
        private float dragX, dragY;

        /**
         * 频道拖动
         */
        private void channelDrag(View v, MotionEvent event) {
            float moveX = event.getRawX();
            float moveY = event.getRawY();
            v.setX(v.getX() + (moveX - dragX));
            v.setY(v.getY() + (moveY - dragY));
            dragX = moveX;
            dragY = moveY;
            ArrayList<View> myChannels = channelGroups.get(0);
            ChannelAttr vTag = getChannelAttr(v);
            int vIndex = myChannels.indexOf(v);
            for (int i = 0; i < myChannels.size(); i++) {
                if (i >= channelFixedCount && i != vIndex) {
                    View iChannel = myChannels.get(i);
                    ChannelAttr iChannelTag = getChannelAttr(iChannel);
                    int x1 = (int) iChannelTag.coordinate.x;
                    int y1 = (int) iChannelTag.coordinate.y;
                    int sqrt = (int) Math.sqrt((v.getX() - x1) * (v.getX() - x1) + (v.getY() - y1) * (v.getY() - y1));
                    if (sqrt <= RANGE) {
                        backOrForward(v, i, vIndex, myChannels, vTag, iChannelTag);
                        break;
                    }
                }
            }
        }

        /**
         * 我的频道，循环往前、后移
         */
        private void backOrForward(View v, int i, int vIndex, ArrayList<View> myChannels, ChannelAttr vTag, ChannelAttr iChannelTag) {
            PointF tempPoint = iChannelTag.coordinate;
            if (i < vIndex) {
                for (int j = i; j < vIndex; j++) {
                    View view = myChannels.get(j);
                    ChannelAttr viewTag = getChannelAttr(view);
                    ChannelAttr nextGridViewAttr = getChannelAttr(myChannels.get(j + 1));
                    viewTag.coordinate = nextGridViewAttr.coordinate;
                    view.animate().x(viewTag.coordinate.x).setDuration(DURATION_TIME).start();
                    view.animate().y(viewTag.coordinate.y).setDuration(DURATION_TIME).start();
                }
            } else {
                for (int j = i; j > vIndex; j--) {
                    View view = myChannels.get(j);
                    ChannelAttr viewTag = getChannelAttr(view);
                    ChannelAttr preGridViewAttr = getChannelAttr(myChannels.get(j - 1));
                    viewTag.coordinate = preGridViewAttr.coordinate;
                    view.animate().x(viewTag.coordinate.x).setDuration(DURATION_TIME).start();
                    view.animate().y(viewTag.coordinate.y).setDuration(DURATION_TIME).start();
                }
            }
            vTag.coordinate = tempPoint;
            myChannels.remove(v);
            myChannels.add(i, v);
        }

        /**
         * 更改提示语
         *
         * @param state
         */
        private void changeTip(boolean state) {
            ArrayList<View> views = channelGroups.get(0);
            if (state) {
                tipFinish.setVisibility(VISIBLE);
                tipEdit.setVisibility(INVISIBLE);
                channelClickType = DELETE;
                isEditState = true;
            } else {
                tipFinish.setVisibility(INVISIBLE);
                tipEdit.setVisibility(VISIBLE);
                channelClickType = NORMAL;
                isEditState = false;
                for (int i = 0; i < views.size(); i++) {
                    if (i >= channelFixedCount) {
                        styleAdapter.setNormalStyle(getViewHolder(views.get(i)));
                    }
                }
            }
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            handler.removeCallbacksAndMessages(null);
        }
    }

    private ChannelAttr getChannelAttr(View view) {
        return ((ChannelLayoutParams) view.getLayoutParams()).mChannelAttr;
    }

    private ViewHolder getViewHolder(View view) {
        return ((ChannelLayoutParams) view.getLayoutParams()).mViewHolder;
    }

    public static class ChannelLayoutParams extends GridLayout.LayoutParams {
        ViewHolder mViewHolder;
        ChannelAttr mChannelAttr;

        public ChannelLayoutParams(GridLayout.Spec rowSpec, GridLayout.Spec columnSpec) {
            super(rowSpec, columnSpec);
        }

        public ChannelLayoutParams() {
        }

        public ChannelLayoutParams(ViewGroup.LayoutParams params) {
            super(params);
        }

        public ChannelLayoutParams(MarginLayoutParams params) {
            super(params);
        }

        public ChannelLayoutParams(GridLayout.LayoutParams source) {
            super(source);
        }

        public ChannelLayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }
}
