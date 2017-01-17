package com.stew.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

public class MainActivity extends AppCompatActivity {

    private int[] aroundCircleTitleEn = {R.string.topTitleEn, R.string.topRightTitleEn, R.string.topLeftTitleEn, R.string.rightTitleEn, R.string.leftTitleEn, R.string.bottomRightTitleEn, R.string.bottomLeftTitleEn};
    private int[] aroundCircleTitleCn = {R.string.topTitleCn, R.string.topRightTitleCn, R.string.topLeftTitleCn, R.string.rightTitleCn, R.string.leftTitleCn, R.string.bottomRightTitleCn, R.string.bottomLeftTitleCn};
    private int[] circleIcon = {R.drawable.android, R.drawable.android, R.drawable.android, R.drawable.android, R.drawable.android, R.drawable.android, R.drawable.android};
    private int[] circleCompleteStatusList = {AttrEntity.COMPLETE,
            AttrEntity.COMPLETE,
            AttrEntity.COMPLETE,
            AttrEntity.DOING,
            AttrEntity.DEF,
            AttrEntity.DEF,
            AttrEntity.DEF};

    private int aroundCircleCount = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final CircleLayout circleLayout = (CircleLayout) findViewById(R.id.circle_layout);
        circleLayout.setView(aroundCircleTitleEn, aroundCircleTitleCn, circleIcon, aroundCircleCount, circleCompleteStatusList);
        circleLayout.setProgressNum(3);
        circleLayout.initView();

        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.viewtoshowanim);
        LayoutAnimationController lac = new LayoutAnimationController(animation);
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        lac.setDelay(0.3f);
        circleLayout.setLayoutAnimation(lac);
        circleLayout.startAnim(360f * 3 / 7);

        circleLayout.setOnClickListener(new CircleLayout.circleClickListener() {
            @Override
            public void click(int tag) {
                startActivity(TagActivity.getInstance(MainActivity.this, tag));
            }
        });

    }

}
