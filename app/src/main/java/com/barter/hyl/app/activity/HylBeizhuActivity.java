package com.barter.hyl.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.event.BeizhuHylEvent;

import org.greenrobot.eventbus.EventBus;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by ${王涛} on 2020/8/8
 */
public class HylBeizhuActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv_finish)
    TextView tv_finish;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    String beizhu;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        beizhu = getIntent().getStringExtra("beizhu");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_beizhu_hyl);

    }

    @Override
    public void setViewData() {
        et.setText(beizhu);
    }

    @Override
    public void setClickListener() {
        tv_finish.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                EventBus.getDefault().post(new BeizhuHylEvent(et.getText().toString()));
                finish();
                break;

            case R.id.tv_finish:
                EventBus.getDefault().post(new BeizhuHylEvent(et.getText().toString()));
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
