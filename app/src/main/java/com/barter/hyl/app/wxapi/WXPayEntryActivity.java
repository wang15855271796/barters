package com.barter.hyl.app.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.event.WeChatPayEvent;
import com.barter.hyl.app.event.WeChatUnPayEvent;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = WXPayEntryActivity.class.getSimpleName();
    IWXAPI api;
//              wxf62d1bee757cd65a
//            wxbc18d7b8fee86977
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wxf62d1bee757cd65a");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int code = resp.errCode;
            switch (code) {
                case 0:
                    AppHelper.showMsg(this, "支付成功");
                    EventBus.getDefault().post(new WeChatPayEvent());

                    finish();
                    break;
                case -1:
                    AppHelper.showMsg(this, "支付失败");
                    EventBus.getDefault().post(new WeChatPayEvent());
                    finish();
                    break;
                case -2:
                    AppHelper.showMsg(this, "支付取消");
                    EventBus.getDefault().post(new WeChatUnPayEvent());
                    finish();
                    break;
                default:
                    AppHelper.showMsg(this, "支付失败");
                    setResult(RESULT_OK);
                    EventBus.getDefault().post(new WeChatPayEvent());
                    finish();
                    break;
            }
        }
    }
}