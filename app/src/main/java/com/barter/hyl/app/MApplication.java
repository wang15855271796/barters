package com.barter.hyl.app;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;

import com.barter.hyl.app.event.InitEventBus;
import com.barter.hyl.app.utils.HookUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ${王涛} on 2021/7/30
 */
public class MApplication extends Application {

    private static Context context;

    public IWXAPI api;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
//        HookUtils.hookMacAddress("Z-Application",getApplicationContext());
        EventBus.getDefault().register(this);
    }

    public static Context getContext() {
        return context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void init(InitEventBus initEventBus) {
        api = WXAPIFactory.createWXAPI(this, "wxf62d1bee757cd65a");
        api.registerApp("wxf62d1bee757cd65a");
    }
}
