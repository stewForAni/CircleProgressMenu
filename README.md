### CircleProgressMenu

##### Intro:

It's  a simple circle menu with progress effect.  It's used for some projects that need to be done step by step.The default color of the circles around is dark grey, The color of a certain circle will turn green when you are doing tasks involved in it and then blue when you complete them.Of course ,you can change these three status colors as you like at xml.Just copy my code if you need such a menu, which is quite easy to understand. Any suggestion, please send to:stewforani@gmail.com. Thank you!

##### show:

![image](https://github.com/stewForAni/CircleMenuLayout/blob/master/images/WechatIMG6.png)

![image](https://github.com/stewForAni/CircleMenuLayout/blob/master/images/WechatIMG7.png)

![image](https://github.com/stewForAni/CircleMenuLayout/blob/master/images/WechatIMG8.png)


##### how to use:

```java
//amount of the circles around
private int aroundCircleCount = 7;
//the currrent progress num(=<aroundCircleCount)
private int currentProgressNum = 3;
//Icons of around circle (set by yourself)
private int[] circleIcon = {R.drawable.xxx,"","","","","",""};
//the en and cn of the circles around
private int[] aroundCircleTitleEn = {R.string.xxx,"","","","","",""};
private int[] aroundCircleTitleCn = {R.string.xxx,"","","","","",""};
//status of every circle(default->grey,doing->green,complete->blue,three status and //color set by yourself)
private int[] circleCompleteStatusList = {"","","","","","",""};
```


```java
final CircleLayout circleLayout = (CircleLayout) findViewById(R.id.circle_layout);

circleLayout.setView(aroundCircleTitleEn, 
                     aroundCircleTitleCn,
                     circleIcon,
                     aroundCircleCount,
                     circleCompleteStatusList);

circleLayout.setProgressNum(currentProgressNum);
circleLayout.initView();
circleLayout.startAnim(360f * currentProgressNum / 7);
circleLayout.setOnClickListener(new CircleLayout.circleClickListener() {
    @Override
    public void click(int tag) {
    //the tag of circle which you click
    }
});
```


```xml
<com.stew.myapplication.CircleLayout
    android:id="@+id/circle_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"                                   
    app:aroundCircleCompleteColor="@color/around_circle_complete_color"
    app:aroundCircleDefColor="@color/around_circle_def_color"
    app:aroundCircleDoingColor="@color/around_circle_doing_color"
    app:centerArcColor="@color/arc_change"
    app:centerArcColorDef="@color/arc_default"
    app:centerCircleColor="@color/center_circle_color"
    app:centerCircleText="Start"
    app:centerCircleTextColor="@color/white"
    app:centerCircleTextSize="22sp"
    app:aroundSmallCircleColor="@color/white"
    app:titleSize="12sp"
    app:titleColor="@color/white"/>
```
