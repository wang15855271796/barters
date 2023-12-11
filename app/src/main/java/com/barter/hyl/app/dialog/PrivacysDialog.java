package com.barter.hyl.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import androidx.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barter.hyl.app.MApplication;
import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylCommonH5Activity;
import com.barter.hyl.app.activity.LoginActivity;
import com.barter.hyl.app.activity.MainActivity;
import com.barter.hyl.app.constant.StringHelper;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.utils.SharedPreferencesUtil;
import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * Created by ${王涛} on 2021/9/14
 */
public class PrivacysDialog extends Dialog {
    Activity mContext;
    LinearLayout ll_sure;
    LinearLayout ll_cancel;
    public IWXAPI api;
//    TextView tv_content;
    String content = "https://shaokao.qoger.com/apph5/html/hyl_yszc.html";
    String register = "https://shaokao.qoger.com/apph5/html/hyl_enter.html";
    public PrivacysDialog(@NonNull Activity context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_privacy_hyl);
        mContext = context;
        initView();
    }

    private void initView() {
        ll_cancel = findViewById(R.id.ll_cancel);
        ll_sure = findViewById(R.id.ll_sure);
        TextView tv_content1 = findViewById(R.id.tv_content1);
        TextView tv_content2 = findViewById(R.id.tv_content2);
        ll_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.saveString(mContext,"once","0");
//                SDKInitializer.initialize(MApplication.getContext());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(mContext,MainActivity.class);
                        mContext.startActivity(intent);
                        mContext.finish();
                    }
                },1000);
                dismiss();
            }
        });

        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Privacy2Dialog privacy2Dialog = new Privacy2Dialog(mContext);
                privacy2Dialog.show();
                dismiss();
            }
        });

        tv_content1.setMovementMethod(LinkMovementMethod.getInstance());
        tv_content2.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString spStr = new SpannableString(tv_content1.getText().toString());
        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#3483FF"));       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                mContext.startActivity(HylCommonH5Activity.getIntent(mContext, HylCommonH5Activity.class, content));
            }
        }, 18, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#3483FF"));       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                mContext.startActivity(HylCommonH5Activity.getIntent(mContext, HylCommonH5Activity.class, content));
            }
        }, 73, 83, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_content1.setText(spStr);

        SpannableString spStr2 = new SpannableString(tv_content2.getText().toString());
        spStr2.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#3483FF"));       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                mContext.startActivity(HylCommonH5Activity.getIntent(mContext, HylCommonH5Activity.class, content));
            }
        }, 9, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spStr2.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#3483FF"));       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                mContext.startActivity(HylCommonH5Activity.getIntent(mContext, HylCommonH5Activity.class, "https://shaokao.qoger.com/apph5/html/hyl_third.html"));
            }
        }, 19, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_content2.setText(spStr2);
    }


}
