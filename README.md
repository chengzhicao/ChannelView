# 介绍
>一款频道选择器，可以进行频道的拖动、排序、增删，动态的改变高度，精简而又流畅

![](https://upload-images.jianshu.io/upload_images/6753190-9ef8bb620590ffad.gif?imageMogr2/auto-orient/strip)

# 使用[![](https://jitpack.io/v/chengzhicao/ChannelView.svg)](https://jitpack.io/#chengzhicao/ChannelView)

Add it in your root build.gradle at the end of repositories:

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency

```
dependencies {
        implementation 'com.github.chengzhicao:ChannelView:v1.0.6'
}
```

# v1.0.6说明
> 1. 可添加多个频道板块
> 2. 简化频道设置方法
> 3. 可通过两种方式编辑频道，长按编辑和点击按键主动编辑
> 4. 设置频道额外属性（可用于保存频道Id或者设置其它信息）
> 5. 简化设置固定频道方法
> 6. 自定义频道样式
> 7. 判断是否编辑过频道

# 主要方法

|名称|描述
|---|---|
|void setChannelFixedCount(int channelFixedCount)| 设置固定频道数量
|void addPlate(String plateName, List<Channel> channelList)| 添加频道板块
|void inflateData()| 添加完频道板块之后，进行填充数据，要在addPlate方法之后调用
|boolean isChange()| 频道序列是否发生变化

# 接口

|名称|描述
|---|---|
|void channelItemClick(int position, Channel channel)| 频道点击回调
|void channelEditFinish(List<Channel> channelList)| 频道编辑完成回调
|void channelEditStart()| 开始编辑频道

# 属性

|名称|描述
|---|---|
|channelHeight| 频道高度
|channelColumn| 频道列数
|channelPadding| 频道内间距
|channelVerticalSpacing| 频道之间竖直方向的间隔距离
|channelHorizontalSpacing| 频道之间水平方向的间隔距离
|channelNormalBackground| 设置频道正常状态下背景
|channelEditBackground| 设置频道编辑状态下背景
|channelFocusedBackground| 设置频道编辑且点击状态下背景
|channelNormalTextColor| 设置频道字体颜色
|channelFixedCount| 设置固定频道个数
|channelFixedTextColor| 设置固定频道的颜色
|channelFixedBackground| 设置固定频道的背景
|channelFocusedTextColor| 设置编辑且点击状态下频道字体颜色
|channelTextSize| 设置频道字体大小
|tipEditBackground| 设置编辑按键背景
|platesTitleBackground| 设置频道板块标题背景
|tipEditTextColor| 设置编辑按键颜色
|platesTitleColor| 设置频道板块标题颜色
|platesTitleBold| 设置频道板块标题是否加粗
|tipEditTextSize| 设置编辑按键字体大小
|platesTitleSize| 设置频道板块标题大小
|platesTitleHeight| 频道板块title高度
|platesTitleLeftRightPadding| 频道板块title左右padding
|otherSubTitleBackground| 设置其它频道板块的副标题背景
|otherSubTitleTextColor| 设置其它频道板块的副标题颜色
|otherSubTitleTextSize| 设置其它频道板块的副标题字体大小
|subTitleBackground| 设置已选频道板块的副标题背景
|subTitleTextColor| 设置已选频道板块的副标题颜色
|subTitleTextSize| 设置已选频道板块的副标题字体大小
|subTitleName| 设置已选频道的副标题
|otherSubTitleName| 设置其它未选频道的副标题

# 示例

在布局文件中：
```
<com.cheng.channelview.ChannelView
    android:id="@+id/channelView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
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

        channelView.setChannelFixedCount(3);
        channelView.addPlate("我的频道", myChannelList);
        channelView.addPlate("推荐频道", recommendChannelList1);
        channelView.addPlate("国内", recommendChannelList2);
        channelView.addPlate("国外", recommendChannelList3);
        channelView.inflateData();
        channelView.setOnChannelItemClickListener(this);
    }


    @Override
    public void channelItemClick(int position, Channel channel) {
        Log.i(TAG, position + ".." + channel);
    }

    @Override
    public void channelEditFinish(List<Channel> channelList) {
        Log.i(TAG, channelList.toString());
        Log.i(TAG, channelView.getMyChannel().toString());
    }
    
    @Override
    public void channelEditStart() {

    }
}
```

# 项目详细解读

https://www.jianshu.com/p/53af72e8a4ba
