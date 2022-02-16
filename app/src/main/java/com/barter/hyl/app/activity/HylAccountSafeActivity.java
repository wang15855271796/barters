package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.base.BaseActivity;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by ${王涛} on 2021/8/30
 */
public class HylAccountSafeActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.rl_cancel)
    RelativeLayout rl_cancel;
    @BindView(R.id.rl_secret)
    RelativeLayout rl_secret;
    String phone;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        phone = getIntent().getStringExtra("phone");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_account_safe_hyl);
    }

    @Override
    public void setViewData() {
        tv_phone.setText(phone);
    }

    @Override
    public void setClickListener() {
        tv_title.setText("账户与安全");
        iv_back.setOnClickListener(this);
        rl_secret.setOnClickListener(this);
        rl_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.rl_secret:
                Intent intent = new Intent(mContext,HylForgetPassWordActivity.class);
                intent.putExtra("title","修改登录密码");
                startActivity(intent);
                break;

            case R.id.rl_cancel:
                Intent intent2 = new Intent(mContext,HylAnnulActivity.class);
                intent2.putExtra("phone",phone);
                startActivity(intent2);
                break;
        }
    }


    public static Date getNowDate() {
        Date currentTimes = new Date();
        SimpleDateFormat formatters = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStrings = formatters.format(currentTimes);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatters.parse(dateStrings, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTimes = new Date();
        SimpleDateFormat formatters = new SimpleDateFormat("yyyy");
        String dateStrings = formatters.format(currentTimes);
        return dateStrings;
    }

}
