package com.barter.hyl.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.barter.hyl.app.R;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.constant.UserInfoHelper;

import butterknife.ButterKnife;

/**
 * Created by ${王涛} on 2021/10/26
 */
public abstract class Base2Activity extends AppCompatActivity {

    private long mExitTime = 0;
    private boolean mIsExit = false;
    protected Context mContext;
    protected Activity mActivity;
    protected Resources mResources;
    protected Bundle mBundle;

    public static Intent getIntent(Context context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        handleExtra(savedInstanceState);

        mContext = this;
        mActivity = this;
//        setStatusBar();
        setContentView();
        ButterKnife.bind(this);
        mResources = this.getResources();
        mBundle = savedInstanceState;

        setViewData();
        setClickListener();
    }


    /**
     * 设置为白色状态栏
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));//设置状态栏颜色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        }
    }

    /**
     * //必须在super.OnCreate()之前调用，因为titlebar
     * 处理Extra数据，意味着需要同时实现onSaveInstanceState和onRestoreInstanceState
     * 技巧：紧接着就在它后面重写
     * onSaveInstanceState-存储：解决突发状况，比如电话进来
     * onRestoreInstanceState-获取
     *
     * @param savedInstanceState
     * @return
     */
    public abstract boolean handleExtra(Bundle savedInstanceState);

    /**
     * 设置ContentView
     */
    public abstract void setContentView();

    /**
     * 填充View数据
     */
    public abstract void setViewData();

    /**
     * 设置View点击事件监听器
     */
    public abstract void setClickListener();

    /**
     * 是否允许退出App，默认false
     *
     * @param isExit
     */
    protected void keyBackExitApp(boolean isExit) {
        mIsExit = isExit;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mIsExit) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    AppHelper.showMsg(this, "再按一次退出程序！");
                    mExitTime = System.currentTimeMillis();
                    return true;
                } else {
                    finish();
                }
            }
        }
        if (AppHelper.isShow) {
            AppHelper.hidePhotoDetailDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void logoutAndToHome(Context context, int mStateCode) {
        //清空UserId
        if (mStateCode == -10000 || mStateCode == -10001) {
            UserInfoHelper.saveUserId(context, "");
            UserInfoHelper.saveUserType(context, "");
            UserInfoHelper.saveUserHomeRefresh(context, "");
            UserInfoHelper.saveUserMarketRefresh(context, "");
            UserInfoHelper.saveChangeFlag(mActivity,"0");
//            startActivity(new Intent(context, HomeActivity.class));
//            EventBus.getDefault().post(new LogoutsEvent());
            finish();
        }
    }
}
