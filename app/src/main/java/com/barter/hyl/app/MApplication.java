package com.barter.hyl.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.barter.hyl.app.event.GoToMarketHylEvent;
import com.barter.hyl.app.event.InitEventBus;
import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.GetPhoneInfoListener;
import com.chuanglan.shanyan_sdk.listener.InitListener;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.header.waveswipe.DropBounceInterpolator;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
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
        EventBus.getDefault().register(this);


    }

    public static Context getContext() {
        return context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void init(InitEventBus initEventBus) {
        api = WXAPIFactory.createWXAPI(this, "wxf62d1bee757cd65a");
        api.registerApp("wxf62d1bee757cd65a");
//        sHmelvZ7正确
//        OneKeyLoginManager.getInstance().init(getApplicationContext(), "sHmelvZ7", new InitListener() {
//            @Override
//            public void getInitStatus(int code, String result) {
//                //初始化回调
//
//            }
//        });
//
//        //闪验SDK预取号（可缩短拉起授权页时间）
//        OneKeyLoginManager.getInstance().getPhoneInfo(new GetPhoneInfoListener() {
//            @Override
//            public void getPhoneInfoStatus(int code, String result) {
//                //预取号回调
//                Log.e("VVV", "预取号： code==" + code + "   result==" + result);
//
//            }
//        });
    }
}
