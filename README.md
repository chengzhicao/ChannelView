# 介绍
>一款频道选择器，可以进行频道的拖动、排序、增删，动态的改变高度，精简而又流畅

![](https://upload-images.jianshu.io/upload_images/6753190-9ef8bb620590ffad.gif?imageMogr2/auto-orient/strip)

# 方法

|名称|描述
|---|---|
|setFixedChannel(int toPosition)| 设置前toPosition+1个channel不能拖动，toPosition是数组下标
|setChannels(Map<String, String[]> channels)| 设置频道集合，第0项为“我的频道”，可拖拽、删除，剩下的所有为其他频道
|setMyChannelBelong(int channelIndex, int belongId)| 设置频道归属，只能设置在我的频道中
|setChannelNormalBackground(@DrawableRes int channelNormalBackground)| 设置频道正常状态
|setChannelSelectedBackground(@DrawableRes int channelSelectedBackground)| 设置频道已选择状态器
|setChannelFocusedBackground(@DrawableRes int channelFocusedBackground)| 设置频道点击状态
|setChannelFixedColor(@ColorInt int channelFixedColorRes)| 设置固定频道的颜色
|getMyChannel()| 获取我的频道
|setOnChannelItemClickListener(OnChannelListener onChannelListener)| 设置频道监听

# OnChannelListener接口

|名称|描述
|---|---|
|channelItemClick(int itemId, String channel)| 频道点击回调
|channelFinish(List<String> channels)| 频道编辑完成回调

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
        String[] recommendChannel0 = {"综艺", "美食", "育儿", "冰雪", "必读", "政法网事", "都市",
                "NFL", "韩流"};
        String[] recommendChannel = {"问答", "文化", "佛学", "股票", "动漫", "理财", "情感", "职场", "旅游"};
        String[] recommendChannel2 = {"家居", "电竞", "数码", "星座", "教育", "美容", "电视剧",
                "搏击", "健康"};

        Map<String, String[]> channels = new LinkedHashMap<>();
        channels.put("我的频道", myChannel);
        channels.put("推荐频道", recommendChannel0);
        channels.put("国内", recommendChannel);
        channels.put("国外", recommendChannel2);

        channelView.setFixedChannel(2);
        channelView.setChannels(channels);
        channelView.setMyChannelBelong(1, 2);
        channelView.setMyChannelBelong(1, 3);
        channelView.setMyChannelBelong(5, 4);
        channelView.setMyChannelBelong(7, 3);
        channelView.setMyChannelBelong(0, 2);
        channelView.setChannelNormalBackground(R.drawable.bg_channel_normal);
        channelView.setChannelSelectedBackground(R.drawable.bg_channel_selected);
        channelView.setChannelFocusedBackground(R.drawable.bg_channel_focused);
        channelView.setOnChannelItemClickListener(this);

    }

    @Override
    public void channelItemClick(int itemId, String channel) {
        Log.i(TAG, itemId + ".." + channel);
    }

    @Override
    public void channelFinish(List<String> channels) {
        Log.i(TAG, channels.toString());
    }
}
```


# v1.0.1更新说明
>修复拖拽时出现的崩溃bug

# 项目详细解读

https://www.jianshu.com/p/53af72e8a4ba
