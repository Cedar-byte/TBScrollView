package com.gxy.gxyscrollview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MyScrollview.ScrollViewListener{

    private MyScrollview scrollview;
    private RelativeLayout bg, bar;
    private TextView tv;
    private int bgHeight;// 上半身的高度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollview = (MyScrollview) findViewById(R.id.scrollview);
        bg = (RelativeLayout) findViewById(R.id.rel_bg);
        bar = (RelativeLayout) findViewById(R.id.rel_bar);
        tv = (TextView) findViewById(R.id.tv);
        initListeners();
    }

    /**
     * 获取上半身的高度后，设置滚动监听
     */
    private void initListeners() {
        ViewTreeObserver vto = bg.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bg.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                bgHeight = bg.getHeight();
                scrollview.setScrollViewListener(MainActivity.this);
            }
        });
    }

    @Override
    public void onScrollChanged(MyScrollview scrollView, int l, int t, int oldl, int oldt) {
        if (t <= 0) {   //设置标题的背景颜色
            bar.setBackgroundColor(Color.argb(0, 0, 255, 0));
        } else if (t > 0 && t <= bgHeight) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) t / bgHeight;
            float alpha = (255 * scale);
            tv.setTextColor(Color.argb((int) alpha, 255, 255, 255));
            bar.setBackgroundColor(Color.argb((int) alpha, 0, 255, 0));
        } else {    //滑动到banner下面设置普通颜色
            bar.setBackgroundColor(Color.argb(255, 0, 255, 0));
        }
    }
}
