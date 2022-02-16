package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppConstant;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by ${王涛} on 2021/9/24
 */
public class DelayPayResultActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_order_look)
    TextView tv_order_look;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    String orderId;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        orderId = bundle.getString(AppConstant.ORDERID);
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_delay_pay_hyl);
    }

    @Override
    public void setViewData() {
        tv_title.setText("支付详情");
    }

    @Override
    public void setClickListener() {
        tv_order_look.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_order_look:
                Intent intent = new Intent(mContext,HylOrderDetailActivity.class);
                intent.putExtra("orderId",orderId);
                startActivity(intent);
                finish();
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
