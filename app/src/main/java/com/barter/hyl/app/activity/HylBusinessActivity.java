package com.barter.hyl.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.base.BaseActivity;
import com.bumptech.glide.Glide;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by ${王涛} on 2021/8/30
 */
public class HylBusinessActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_business)
    ImageView iv_business;
    @BindView(R.id.iv_licensePic)
    ImageView iv_licensePic;
    String businessPic;
    String licensePic;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        businessPic = getIntent().getStringExtra("businessPic");
        licensePic = getIntent().getStringExtra("licensePic");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_business_hyl);
    }

    @Override
    public void setViewData() {
        tv_title.setText("经营证照");
        Glide.with(mActivity).load(licensePic).into(iv_business);
        Glide.with(mActivity).load(businessPic).into(iv_licensePic);
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
