package com.barter.hyl.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
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

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/9/30
 */
public class Privacy5Dialog extends Dialog {
    Activity mContext;
    TextView tv_content;
    TextView tv_sure;
    TextView tv_unAgree;
    String contents = "https://shaokao.qoger.com/apph5/html/hyl_yszc.html";
    String register = "https://shaokao.qoger.com/apph5/html/hyl_enter.html";
    int id;
    int type;
    LinearLayout ll_sure;
    public Privacy5Dialog(@NonNull Activity context,int id,int type) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_privacy2_hyl);
        mContext = context;
        this.id = id;
        this.type = type;
        initView();
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);
        tv_unAgree = findViewById(R.id.tv_unAgree);
        tv_sure = findViewById(R.id.tv_sure);
        ll_sure = findViewById(R.id.ll_sure);
        tv_unAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mContext.finish();

            }
        });

        ll_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                getPrivacyRead();
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


    }

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
}
