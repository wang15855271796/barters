package com.barter.hyl.app.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;
import com.youth.banner.util.LogUtils;

/**
 * Created by ${daff}
 * on 2018/10/24
 * 备注
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wxf62d1bee757cd65a", false);
//        try {
//            if (!api.handleIntent(getIntent(), this)) {
//                finish();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {

            Intent intent = getIntent();

            api.handleIntent(intent, this);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @Override

    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);

        setIntent(intent);

        api.handleIntent(intent, this);

    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) baseResp;
            String extraData = launchMiniProResp.extMsg; //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
            LogUtils.i("小程序==" + extraData);

        }
        finish();

    }
}
