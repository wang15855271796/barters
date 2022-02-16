package com.barter.hyl.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.barter.hyl.app.MApplication;
import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylCommonH5Activity;
import com.barter.hyl.app.activity.LoginActivity;
import com.barter.hyl.app.activity.MainActivity;
import com.barter.hyl.app.constant.StringHelper;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.utils.SharedPreferencesUtil;

/**
 * Created by ${王涛} on 2021/9/8
 */
public class Privacy2Dialog extends Dialog {

    Activity mContext;
    TextView tv_content;
    TextView tv_sure;
    TextView tv_unAgree;
    LinearLayout ll_sure;
    String contents = "https://shaokao.qoger.com/apph5/html/hyl_yszc.html";
    public Privacy2Dialog(@NonNull Activity context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_privacy2_hyl);
        mContext = context;
        initView();
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);
        tv_unAgree = findViewById(R.id.tv_unAgree);
        ll_sure = findViewById(R.id.ll_sure);
        tv_sure = findViewById(R.id.tv_sure);
        TextView tv_content = findViewById(R.id.tv_content);
        tv_unAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mContext.finish();
            }
        });


        SpannableString spStr = new SpannableString(tv_content.getText().toString());
        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#3483FF"));       //设置文件颜色
                ds.setUnderlineText(false);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                mContext.startActivity(HylCommonH5Activity.getIntent(mContext, HylCommonH5Activity.class, contents));
            }
        }, 9, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#3483FF"));       //设置文件颜色
                ds.setUnderlineText(false);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                mContext.startActivity(HylCommonH5Activity.getIntent(mContext, HylCommonH5Activity.class, "https://shaokao.qoger.com/apph5/html/hyl_third.html"));
            }
        }, 19, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_content.setText(spStr);
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());


        ll_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.saveString(mContext,"once","0");
                SDKInitializer.initialize(MApplication.getContext());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String userId = UserInfoHelper.getUserId(mContext);
                        if(StringHelper.notEmptyAndNull(userId)) {
                            Intent intent = new Intent(mContext,MainActivity.class);
                            mContext.startActivity(intent);
                            mContext.finish();
                        }else {
                            Intent intent = new Intent(mContext,LoginActivity.class);
                            mContext.startActivity(intent);
                            mContext.finish();
                        }
                    }
                },1000);
                dismiss();
            }
        });
    }


//    Activity mContext;
//    TextView tv_content;
//    TextView tv_look;
//    TextView tv_unAgree;
//    String content = "https://shaokao.qoger.com/apph5/html/hyl_yszc.html";
//    String register = "https://shaokao.qoger.com/apph5/html/hyl_enter.html";
//    public Privacy2Dialog(@NonNull Activity context) {
//        super(context, R.style.promptDialog);
//        setContentView(R.layout.dialog_privacy2_hyl);
//        mContext = context;
//        initView();
//    }
//
//    private void initView() {
//        tv_content = findViewById(R.id.tv_content);
//        tv_unAgree = findViewById(R.id.tv_unAgree);
//        tv_look = findViewById(R.id.tv_look);
//
//        tv_unAgree.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Privacy3Dialog privacy3Dialog = new Privacy3Dialog(mContext);
//                privacy3Dialog.show();
//                dismiss();
//            }
//        });
//
//        tv_look.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PrivacysDialog privacyDialog = new PrivacysDialog(mContext);
//                privacyDialog.show();
//                dismiss();
//            }
//        });
//    }



}
