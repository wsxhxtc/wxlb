package com.project.jsproject.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 自定义实现LinearLayout让其能跟着ScrollView滚动
 * @author Administrator
 *
 */
public class NoscrollLinearLayout extends LinearLayout {


    public NoscrollLinearLayout(Context context) {
        super(context);
    }

    public NoscrollLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoscrollLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}