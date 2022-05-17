package com.barter.hyl.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.baidu.mapapi.SDKInitializer;
import com.barter.hyl.app.MApplication;
import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylCommonH5Activity;
import com.barter.hyl.app.activity.LoginActivity;
import com.barter.hyl.app.activity.MainActivity;
import com.barter.hyl.app.constant.StringHelper;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.utils.SharedPreferencesUtil;
import com.barter.hyl.app.view.StringSpecialHelper;

public class LoginDialog extends Dialog {
    Activity mContext;
    TextView tv_title;
    TextView tv_ok;
    TextView tv_phone;
    HylLoginModel.ExtDataBean extData;
    public LoginDialog(@NonNull Activity context, HylLoginModel.ExtDataBean extData) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_login);
        mContext = context;
        this.extData = extData;
        initView();
    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        tv_ok = findViewById(R.id.tv_ok);
        tv_phone = findViewById(R.id.tv_phone);
        String content = "您当前绑定的"+extData.getCompanyName()+"账号已停用，请联系企业进行处理";
        String phonesDesc = "客服电话："+extData.connectPhone;
        SpannableStringBuilder account = StringSpecialHelper.buildSpanColorStyle(content, 7,
                extData.getCompanyName().length(), Color.parseColor("#3483FF"));

        SpannableStringBuilder phone = StringSpecialHelper.buildSpanColorStyle(phonesDesc, 5,
                extData.getConnectPhone().length(), Color.parseColor("#3483FF"));
        tv_phone.setText(phone);
        tv_title.setText(account);

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

}
