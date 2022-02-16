package com.barter.hyl.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.barter.hyl.app.R;
import com.barter.hyl.app.api.LoginAPI;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.StringHelper;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.dialog.PrivacysDialog;
import com.barter.hyl.app.model.HylPolicyModel;
import com.barter.hyl.app.utils.SharedPreferencesUtil;
import com.barter.hyl.app.utils.ToastUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/7/29
 */
public class SplashActivity extends BaseActivity {

    /**
     * 获取协议
     */
    String register;
    PrivacysDialog privacyDialog;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.splash_activity);
    }


    @Override
    public void setViewData() {
        //是否点击了隐私权限
        SharedPreferencesUtil.getString(mContext,"once").equals("-1");
        privacyDialog = new PrivacysDialog(mActivity);

        if (!SharedPreferencesUtil.getString(mActivity, "once").equals("0")) {
            privacyDialog.show();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String userId = UserInfoHelper.getUserId(mContext);
                    if(StringHelper.notEmptyAndNull(userId)) {
                        Intent intent = new Intent(mActivity,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(mActivity,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            },1000);
            privacyDialog.dismiss();
        }
    }

    @Override
    public void setClickListener() {
        setTranslucentStatus();
    }

    protected void setTranslucentStatus() {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
