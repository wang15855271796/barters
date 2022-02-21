package com.barter.hyl.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;

import androidx.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylCommonH5Activity;
import com.barter.hyl.app.api.HomeApi;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/9/14
 */
public class Privacy4Dialog extends Dialog {

//    Activity mContext;
//    LinearLayout ll_sure;
//    LinearLayout ll_cancel;
//    int id;
//    int type;
//    String content = "https://shaokao.qoger.com/apph5/html/hyl_yszc.html";
//    String register = "https://shaokao.qoger.com/apph5/html/hyl_enter.html";
//    public Privacy4Dialog(@NonNull Activity context, int id, int type) {
//        super(context, R.style.promptDialog);
//        setContentView(R.layout.dialog_privacy_hyl);
//        mContext = context;
//        this.id = id;
//        this.type = type;
//        initView();
//    }
//
//    private void initView() {
//        ll_cancel = findViewById(R.id.ll_cancel);
//        ll_sure = findViewById(R.id.ll_sure);
//        TextView tv_content1 = findViewById(R.id.tv_content1);
//        TextView tv_content2 = findViewById(R.id.tv_content2);
//        ll_sure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getPrivacyRead();
//            }
//        });
//        ll_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Privacy5Dialog privacy5Dialog = new Privacy5Dialog(mContext,id,type);
//                privacy5Dialog.show();
//                dismiss();
//            }
//        });
//        String s = tv_content1.getText().toString();
//        SpannableStringBuilder spannableStringBuilder = StringSpecialHelper.buildSpanColorStyle(s, 0,
//                10, Color.parseColor("#ff5000"));
//        tv_content1.setText(spannableStringBuilder);
//
//
//        tv_content1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.startActivity(HylCommonH5Activity.getIntent(mContext, HylCommonH5Activity.class, content));
//            }
//        });
//
//        String s1 = tv_content2.getText().toString();
//        SpannableStringBuilder spannableStringBuilder1 = StringSpecialHelper.buildSpanColorStyle(s1, 0,
//                6, Color.parseColor("#ff5000"));
//        tv_content2.setText(spannableStringBuilder1);
//
//
//        tv_content2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.startActivity(HylCommonH5Activity.getIntent(mContext, HylCommonH5Activity.class, register));
//            }
//        });
//
//
//
//    }

    /**
     * 隐私权限是否已读
     */
    private void getPrivacyRead() {
        HomeApi.getRead(mContext,id,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.code==1) {
                            dismiss();
                        } else {
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

    Activity mContext;
    LinearLayout ll_sure;
    LinearLayout ll_cancel;
    public IWXAPI api;
    int id;
    int type;
    String content = "https://shaokao.qoger.com/apph5/html/hyl_yszc.html";
    String register = "https://shaokao.qoger.com/apph5/html/hyl_enter.html";
    public Privacy4Dialog(@NonNull Activity context,int id, int type) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_privacy_hyl);
        mContext = context;
        this.id = id;
        this.type = type;
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
                getPrivacyRead();
            }
        });

        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Privacy5Dialog privacy5Dialog = new Privacy5Dialog(mContext,id,type);
                privacy5Dialog.show();
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
