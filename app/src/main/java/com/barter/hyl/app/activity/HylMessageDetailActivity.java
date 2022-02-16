package com.barter.hyl.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.api.HomeApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.event.MessageNumHylEvent;
import com.barter.hyl.app.model.HylMessageDetailModel;
import com.barter.hyl.app.model.HylMessageNumModel;
import com.barter.hyl.app.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/7/30
 */
public class HylMessageDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_detail)
    TextView tv_detail;
    int id;
    int readFlag;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        id = getIntent().getIntExtra("id", 0);
        readFlag = getIntent().getIntExtra("readFlag", 0);
        setContentView(R.layout.activity_message_detail_hyl);
    }

    @Override
    public void setViewData() {
        tv_title.setText("消息详情");
        getMessageDetail(id,readFlag);
        getMessageNum();
    }

    /**
     * 获取未读消息数量
     */

    private void getMessageNum() {
        HomeApi.getMessageNum(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylMessageNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylMessageNumModel messageModel) {
                        if (messageModel.code==1) {
                            if(messageModel.getData()!=null) {
                                EventBus.getDefault().post(new MessageNumHylEvent());
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, messageModel.message);
                        }
                    }
                });
    }

    /**
     * 获取消息详情
     */
    private void getMessageDetail(int id,int readFlag) {
        HomeApi.getMessageDetail(mActivity,id,readFlag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylMessageDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylMessageDetailModel messageModel) {
                        if (messageModel.getCode()==1) {
                            tv_name.setText(messageModel.getData().getTitle());
                            tv_time.setText(messageModel.getData().getDateTime());
                            tv_detail.setText(messageModel.getData().getContent());
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, messageModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
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
