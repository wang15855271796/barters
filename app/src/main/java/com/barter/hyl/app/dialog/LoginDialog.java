package com.barter.hyl.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.AddCompany1Activity;
import com.barter.hyl.app.activity.AddCompanyActivity;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.view.StringSpecialHelper;

public class LoginDialog extends Dialog {
    Activity mContext;
    TextView tv_title;
    TextView tv_ok;
    TextView tv_phone;
    TextView tv_add;
    String userPhone;
    String password;
    HylLoginModel.ExtDataBean extData;
    public LoginDialog(@NonNull Activity context, HylLoginModel.ExtDataBean extData, String userPhone,String password) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_login);
        mContext = context;
        this.extData = extData;
        this.userPhone = userPhone;
        this.password = password;
        initView();
    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        tv_ok = findViewById(R.id.tv_ok);
        tv_phone = findViewById(R.id.tv_phone);
        tv_add = findViewById(R.id.tv_add);
        String content = "您当前绑定的"+extData.getCompanyName()+"账号已停用，请联系企业进行处理";
        String phonesDesc = "客服电话："+extData.contactPhone;
        SpannableStringBuilder account = StringSpecialHelper.buildSpanColorStyle(content, 6,
                extData.getCompanyName().length(), Color.parseColor("#3483FF"));


        SpannableStringBuilder phone = StringSpecialHelper.buildSpanColorStyle(phonesDesc, 5,
                extData.getContactPhone().length(), Color.parseColor("#3483FF"));
        tv_phone.setText(phone);
        tv_title.setText(account);

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddCompany1Activity.class);
                intent.putExtra("phone",userPhone);
                intent.putExtra("password",password);
                mContext.startActivity(intent);
            }
        });
    }

}
