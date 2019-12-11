# 介绍
>一款频道选择器，可以进行频道的拖动、排序、增删，动态的改变高度，精简流畅

![](https://upload-images.jianshu.io/upload_images/6753190-97bcb4fb8a1c1f2a.gif?imageMogr2/auto-orient/strip)
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
        implementation 'com.github.chengzhicao:ChannelView:v1.1.0'
}
```

**建议将compileSdkVersion改为28，低于28会出现编译问题**

# v1.1.0更新说明（Latest release）
> 1. 优化动画。
> 2. 增加编辑状态下频道点击事件监听。
> 3. 可修改添加频道时的插入位置，如果不设置，默认在尾部插入。
> 4. 修改自定义样式和填充数据方式

**&emsp;&emsp;注意1：1.1.0版本以后原有自定义样式和填充数据方法已过时，但不影响使用，所以存在两种填充数据和自定义样式的方法，一种是使用以前版本的已过时方法和属性，另一种是最新的适配器方式。其中已过时的inflateData()方法只有填充数据作用，而适配器方式同时有填充数据和自定义样式的作用，这两个方式谁先调用谁先生效，所以不建议同时存在，已过时的自定义样式方法和属性只有在使用DefaultStyleAdapter或者使用inflateData()填充数据时才会生效。**

**&emsp;&emsp;注意2：强烈建议使用新的适配器方式来自定义，使用方式如``示例1``。如果需要完全自定义样式，可使用StyleAdapter接口或BaseStyleAdapter类；如果只是简单的修改默认样式，可使用DefaultStyleAdapter类，重写其部分方法即可。**

# 重要类或接口

|名称|描述
|---|---|
|StyleAdapter| 接口，定义频道样式的实现和数据的填充，用于自定义样式
|BaseStyleAdapter| 抽象类，实现部分StyleAdapter的方法，自定义样式时使用它可以不完全实现StyleAdapter中的方法
|DefaultStyleAdapter| 继承BaseStyleAdapter，是频道样式的默认实现类，使用它也可简单自定义样式
|ViewHolder| 配合StyleAdapter的类，保存自定义样式布局中控件
|ChannelListenerAdapter| 抽象类，实现ChannelView.OnChannelListener2方法，新的监听器，可针对的对性事件进行监听

# 主要方法

|名称|描述
|---|---|
|void setStyleAdapter(StyleAdapter styleAdapter)| 设置适配器，最主要的方法，可实现数据填充，自定义样式
|void setInsertRecommendPosition(int recommendPosition)| 设置插入到我的频道的位置，如果不设置，默认从尾部插入
|void setChannelFixedCount(int channelFixedCount)| 设置固定频道数量
|boolean isChange()| 频道序列是否发生变化
|List<Channel> getMyChannel()| 获取我的频道内容
|List<List<Channel>> getOtherChannel()| 获取其他频道内容
|void setOnChannelListener(OnChannelListener onChannelListener)| 设置监听器

# 监听器接口方法

|名称|描述
|---|---|
|void channelItemClick(int position, Channel channel)| 频道点击回调
|void channelEditFinish(List<Channel> channelList)| 频道编辑完成回调
|void channelEditStart()| 开始编辑频道
|void channelEditStateItemClick(int position, Channel channel)| 编辑状态下频道点击

# 属性

|名称|描述
|---|---|
|channelHeight| 频道高度
|channelColumn| 频道列数
|channelPadding| 频道内间距
|channelVerticalSpacing| 频道之间竖直方向的间隔距离
|channelHorizontalSpacing| 频道之间水平方向的间隔距离
|channelFixedCount| 设置固定频道个数
|tipEditBackground| 设置编辑按键背景
|tipEditTextColor| 设置编辑按键颜色
|tipEditTextSize| 设置编辑按键字体大小
|tipFinishBackground| 设置完成按键背景
|tipFinishTextColor| 设置完成按键颜色
|tipFinishTextSize| 设置完成按键字体大小
|platesTitleBold| 设置频道板块标题是否加粗
|platesTitleBackground| 设置频道板块标题背景
|platesTitleColor| 设置频道板块标题颜色
|platesTitleSize| 设置频道板块标题大小
|platesTitleHeight| 频道板块title高度
|platesTitleLeftRightPadding| 频道板块title左右padding
|otherSubTitleBackground| 设置其它频道板块的副标题背景
|otherSubTitleTextColor| 设置其它频道板块的副标题颜色
|otherSubTitleTextSize| 设置其它频道板块的副标题字体大小
|otherSubTitleName| 设置其它未选频道的副标题
|subTitleBackground| 设置已选频道板块的副标题背景
|subTitleTextColor| 设置已选频道板块的副标题颜色
|subTitleTextSize| 设置已选频道板块的副标题字体大小
|subTitleName| 设置已选频道的副标题

# 示例1（推荐使用）
在布局文件中：
```
<com.cheng.channelview.ChannelView
    android:id="@+id/channelView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```
在代码中：
```
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
```

# 示例2（已不推荐使用）

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

# 以往更新

## v1.0.9
> 1. 禁止多点触摸
> 2. 修复调整频道位置时频道之间不停切换bug

## v1.0.8
> 1. 优化频道删除

## v1.0.7
> 1. 修复bug
> 2. 增加获取其他频道内容方法
> 3. 增加3种attr属性

## v1.0.6
> 1. 可添加多个频道板块
> 2. 简化频道设置方法
> 3. 可通过两种方式编辑频道，长按编辑和点击按键主动编辑
> 4. 设置频道额外属性（可用于保存频道Id或者设置其它信息）
> 5. 简化设置固定频道方法
> 6. 自定义频道样式
> 7. 判断是否编辑过频道

# 项目详细解读

<https://www.jianshu.com/p/53af72e8a4ba>
