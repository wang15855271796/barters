package com.barter.hyl.app.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.barter.hyl.app.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;


public class MyControllView extends StandardGSYVideoPlayer {


    public MyControllView(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public MyControllView(Context context) {
        super(context);
    }

    public MyControllView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
//        Drawable drawable = getResources().getDrawable(R.drawable.white_bg);
//        mBottomProgressBar.setProgressDrawable(drawable);
//        mProgressBar.setProgressDrawable(drawable);
//        mProgressBar.setThumb(drawable);
    }

    @Override
    public int getLayoutId() {
        return R.layout.my_controll;
    }
}
