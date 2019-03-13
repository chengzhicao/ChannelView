# 介绍
>一款频道选择器，可以进行频道的拖动、排序、增删，动态的改变高度，精简而又流畅

![](https://upload-images.jianshu.io/upload_images/6753190-9ef8bb620590ffad.gif?imageMogr2/auto-orient/strip)

# 方法

|名称|描述
|---|---|
|setFixedChannel(int toPosition)| 设置前toPosition+1个channel不能拖动，toPosition是数组下标
|addPlate(String plateName, List<Channel> channelList)| 添加频道板块
|inflateData()| 添加完频道板块之后，进行填充数据，要在addPlate方法之后调用
|setChannelNormalBackground(@DrawableRes int channelNormalBackground)| 设置频道正常状态
|setChannelSelectedBackground(@DrawableRes int channelSelectedBackground)| 设置频道已选择状态器
|setChannelFocusedBackground(@DrawableRes int channelFocusedBackground)| 设置频道点击状态
|setChannelFixedColor(@ColorInt int channelFixedColorRes)| 设置固定频道的颜色
|getMyChannel()| 获取我的频道
|setOnChannelItemClickListener(OnChannelListener onChannelListener)| 设置频道监听

# OnChannelListener接口

|名称|描述
|---|---|
|channelItemClick(int position, Channel channel)| 频道点击回调
|channelFinish(List<Channel> channelList)| 频道编辑完成回调

# 属性

|名称|描述
|---|---|
|channelHeight| 频道高度
|channelColumn| 频道列数
|channelPadding| 频道内间距
|channelVerticalSpacing| 频道之间竖直方向的间隔距离
|channelHorizontalSpacing| 频道之间水平方向的间隔距离

# 示例

在布局文件中：
```
<com.cheng.channelview.ChannelView
        android:id="@+id/channelView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:channelColumn="4"
        app:channelHeight="40dp"
        app:channelHorizontalSpacing="10dp"
        app:channelPadding="10dp"
        app:channelVerticalSpacing="5dp" />
```
在代码中：
```
public class ChannelViewActivity extends AppCompatActivity implements ChannelView.OnChannelListener {
    private String TAG = getClass().getSimpleName();
    private ChannelView channelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_view);

        channelView = findViewById(R.id.channelView);
        init();
    }

    private void init() {
        String[] myChannel = {"要闻", "视频", "新时代", "娱乐", "体育", "军事", "NBA", "国际", "科技", "财经", "汽车", "电影", "游戏", "独家", "房产",
                "图片", "时尚", "呼和浩特", "三打白骨精"};
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

        channelView.setFixedChannel(2);
        channelView.addPlate("我的频道", myChannelList);
        channelView.addPlate("推荐频道", recommendChannelList1);
        channelView.addPlate("国内", recommendChannelList2);
        channelView.addPlate("国外", recommendChannelList3);
        channelView.inflateData();
        channelView.setChannelNormalBackground(R.drawable.bg_channel_normal);
        channelView.setChannelSelectedBackground(R.drawable.bg_channel_selected);
        channelView.setChannelFocusedBackground(R.drawable.bg_channel_focused);
        channelView.setOnChannelItemClickListener(this);
    }


    @Override
    public void channelItemClick(int position, Channel channel) {
        Log.i(TAG, position + ".." + channel);
    }

    @Override
    public void channelFinish(List<Channel> channelList) {
        Log.i(TAG, channelList.toString());
        Log.i(TAG, channelView.getMyChannel().toString());
    }
}
```


# v1.0.1更新说明
> 修复拖拽时出现的崩溃bug

# v1.0.2更新说明
> 修复删除我的频道后该频道仍显示删除icon的问题

# v1.0.3更新说明
> 1. 简化频道设置方法
> 2. 可通过两种方式编辑频道，长按编辑和点击按键主动编辑
> 3. 设置频道额外属性（可用于保存频道Id或者设置其它信息）
> 4. 修复setChannelFixedColor方法和setChannelNormalBackground方法无效bug

# 项目详细解读

https://www.jianshu.com/p/53af72e8a4ba
