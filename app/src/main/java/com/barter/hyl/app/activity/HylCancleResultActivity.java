package com.barter.hyl.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.api.LoginAPI;
import com.barter.hyl.app.api.SettingApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.constant.StringHelper;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.utils.EnCodeUtil;
import com.barter.hyl.app.utils.ToastUtil;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by ${王涛} on 2020/6/5
 */
public class HylCancleResultActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_yzm)
    TextView tv_yzm;
    @BindView(R.id.ll_yzm)
    RelativeLayout ll_yzm;
    @BindView(R.id.et_yzm)
    EditText et_yzm;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.tv_commit)
    TextView tv_commit;
    private String phone;
//    private BaseModel mModelSendCode;
    CountDownTimer countDownTimer;
    boolean isSendingCode;
    String reason;
    String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTykrDv1TEKVjDeE29kVLo5M7mctlE65WlHSMN8RVL1iA9jXsF9SMNH1AErs2lqxpv18fd3TOAw0pBaG+cXOxApKdvRDKgxyuHnONOBzxr6EyWOQlRZt94auL1ESVbLdvYa7+cISkVe+MphfQh7uI/64tGQ34aRNmvFKv9PEeBTQIDAQAB";
    private String phones;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        phone = getIntent().getStringExtra("phone");
        reason = getIntent().getStringExtra("reason");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_result_hyl);
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickListener() {
        tv_phone.setText(phone);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(phone)){
                    try {
                        phones = EnCodeUtil.encryptByPublicKey(tv_phone.getText().toString());
                        sendYzm(phones);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else {
                    AppHelper.showMsg(mContext,"请填写正确手机号码");
                }

            }
        });

        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringHelper.notEmptyAndNull(et_pwd.getText().toString()) && !TextUtils.isEmpty(phone) && StringHelper.notEmptyAndNull(et_yzm.getText().toString())&& StringHelper.notEmptyAndNull(reason)) {
                    cancel();
                }else {
                    AppHelper.showMsg(mContext,"请填写正确信息");
                }
            }
        });
    }

    /**
     * 发送验证码
     * @param phone
     */
    private void sendYzm(String phone) {
        LoginAPI.sendYzm(mContext,phone,"2")
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
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
                            handleCountDown();
                        } else {
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

    /**
     * 注销接口
     * @param
     */
    private void cancel() {
        SettingApi.cancel(mActivity,phone,et_yzm.getText().toString(),et_pwd.getText().toString(),reason)
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
                            Intent intent = new Intent(mContext,LoginActivity.class);
                            startActivity(intent);
                            finish();
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, baseModel.message);
                        }
                    }
                });
    }

    private void handleCountDown() {
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isSendingCode = true;
                ll_yzm.setEnabled(false);
                tv_yzm.setEnabled(false);
                tv_yzm.setText(millisUntilFinished / 1000 + "秒后" + "重新发送验证码");
                tv_yzm.setTextColor(Color.parseColor("#ff2925"));

            }

            @Override
            public void onFinish() {
                isSendingCode = false;
                ll_yzm.setEnabled(true);
                tv_yzm.setText("点击发送验证码");
                tv_yzm.setEnabled(true);
                tv_yzm.setTextColor(Color.parseColor("#ff2925"));
            }
        }.start();
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
