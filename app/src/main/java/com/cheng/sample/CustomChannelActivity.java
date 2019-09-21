package com.cheng.sample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheng.channel.Channel;
import com.cheng.channel.ChannelView;
import com.cheng.channel.ViewHolder;
import com.cheng.channel.adapter.BaseStyleAdapter;
import com.cheng.channel.adapter.ChannelListenerAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CustomChannelActivity extends AppCompatActivity {
    private String TAG = "CustomChannelActivity:";
    private ChannelView channelView;
    private LinkedHashMap<String, List<Channel>> data = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_channel);

        channelView = findViewById(R.id.channelView);
        init();
    }

    private void init() {
        String[] myChannel = {"要闻", "视频", "新时代", "娱乐", "体育", "军事", "NBA", "国际", "科技", "财经", "汽车", "电影", "游戏", "独家", "房产",
                "图片", "时尚", "呼和浩特"};
        String[] recommendChannel1 = {"综艺", "美食", "育儿", "冰雪", "必读", "政法网事", "都市",
                "NFL", "韩流"};
        String[] recommendChannel2 = {"问答", "文化", "佛学", "股票", "动漫", "理财", "情感", "职场", "旅游"};
        String[] recommendChannel3 = {"家居", "电竞", "数码", "星座", "教育", "美容", "电视剧",
                "搏击", "健康"};

        List<Channel> myChannelList = new ArrayList<>();
        List<Channel> recommendChannelList1 = new ArrayList<>();
        List<Channel> recommendChannelList2 = new ArrayList<>();
        List<Channel> recommendChannelList3 = new ArrayList<>();

        for (int i = 0; i < myChannel.length; i++) {
            String aMyChannel = myChannel[i];
            Channel channel;
            if (i > 2 && i < 6) {
                //可设置频道归属板块（channelBelong），当前设置此频道归属于第二板块，当删除该频道时该频道将回到第二板块
                channel = new Channel(aMyChannel, 2, i);
            } else if (i > 7 && i < 10) {
                //可设置频道归属板块（channelBelong），当前设置此频道归属于第三板块，当删除该频道时该频道将回到第三板块中
                channel = new Channel(aMyChannel, 3, i);
            } else {
                channel = new Channel(aMyChannel, (Object) i);
            }
            myChannelList.add(channel);
        }

        for (String aMyChannel : recommendChannel1) {
            Channel channel = new Channel(aMyChannel);
            recommendChannelList1.add(channel);
        }

        for (String aMyChannel : recommendChannel2) {
            Channel channel = new Channel(aMyChannel);
            recommendChannelList2.add(channel);
        }

        for (String aMyChannel : recommendChannel3) {
            Channel channel = new Channel(aMyChannel);
            recommendChannelList3.add(channel);
        }

        data.put("我的频道", myChannelList);
        data.put("推荐频道", recommendChannelList1);
        data.put("国内", recommendChannelList2);
        data.put("国外", recommendChannelList3);

        channelView.setChannelFixedCount(3);
        channelView.setInsertRecommendPosition(6);
        channelView.setStyleAdapter(new MyAdapter());
        channelView.setOnChannelListener(new ChannelListenerAdapter() {
            @Override
            public void channelItemClick(int position, Channel channel) {
                Log.i(TAG, position + ".." + channel);
            }

            @Override
            public void channelEditStateItemClick(int position, Channel channel) {
                Log.i(TAG + "EditState:", position + ".." + channel);
            }

            @Override
            public void channelEditFinish(List<Channel> channelList) {
                Log.i(TAG, channelList.toString());
                Log.i(TAG, channelView.isChange() + "");
                Log.i(TAG, channelView.getOtherChannel().toString());
            }

            @Override
            public void channelEditStart() {
                Log.i(TAG, "channelEditStart");
            }
        });
    }

    class MyAdapter extends BaseStyleAdapter<MyAdapter.MyViewHolder> {

        @Override
        public MyViewHolder createStyleView(ViewGroup parent, String channelName) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_channel, null);
            MyViewHolder customViewHolder = new MyViewHolder(inflate);
            customViewHolder.tv.setText(channelName);
            return customViewHolder;
        }

        @Override
        public LinkedHashMap<String, List<Channel>> getChannelData() {
            return data;
        }

        @Override
        public void setNormalStyle(MyViewHolder viewHolder) {
            viewHolder.tv.setBackgroundResource(R.drawable.bg_channel_custom_normal);
            viewHolder.iv.setVisibility(View.INVISIBLE);
        }

        @Override
        public void setFixedStyle(MyViewHolder viewHolder) {
            viewHolder.tv.setTextColor(Color.parseColor("#1E87FF"));
            viewHolder.tv.setBackgroundResource(R.drawable.bg_channel_custom_fixed);
        }

        @Override
        public void setEditStyle(MyViewHolder viewHolder) {
            viewHolder.tv.setBackgroundResource(R.drawable.bg_channel_custom_edit);
            viewHolder.iv.setVisibility(View.VISIBLE);
        }

        @Override
        public void setFocusedStyle(MyViewHolder viewHolder) {
            viewHolder.tv.setBackgroundResource(R.drawable.bg_channel_custom_focused);
        }

        class MyViewHolder extends ViewHolder {
            TextView tv;
            ImageView iv;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv_channel);
                iv = itemView.findViewById(R.id.iv_delete);
            }
        }
    }
}
