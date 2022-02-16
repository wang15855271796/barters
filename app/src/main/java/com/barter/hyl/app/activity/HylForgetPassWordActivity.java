package com.barter.hyl.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.api.LoginAPI;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.utils.EnCodeUtil;
import com.barter.hyl.app.utils.PhoneUtils;
import com.barter.hyl.app.utils.ToastUtil;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/7/29(找回密码界面)
 */
public class HylForgetPassWordActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_yzm)
    EditText et_yzm;
    @BindView(R.id.rl_yzm)
    RelativeLayout rl_yzm;
    @BindView(R.id.tv_yzm)
    TextView tv_yzm;
    String title;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        title = getIntent().getStringExtra("title");

        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.forget_password_activity_hyl);
    }

    @Override
    public void setViewData() {
        tv_title.setText(title);
    }

    @Override
    public void setClickListener() {
        tv_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        rl_yzm.setOnClickListener(this);
    }

    String phone;
    String yzm;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                yzm = et_yzm.getText().toString();
                String phones = et_phone.getText().toString();
                checkYzm(yzm,phones);
                break;

            case R.id.iv_back:
                finish();
                break;

            case R.id.rl_yzm:
                handSms();
                break;
        }
    }

    private void handSms() {
        int num = PhoneUtils.checkPhoneNum(et_phone.getText().toString());
        if(num == 0) {
            try {
                phone = EnCodeUtil.encryptByPublicKey(et_phone.getText().toString());
                sendYzm(phone);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if(num == 1) {
            ToastUtil.showErroMsg(mActivity,"号码错误");
        }else {
            ToastUtil.showErroMsg(mActivity,"请填写手机号码");
        }
    }

    /**
     * 验证验证码
     */
    private void checkYzm(final String smsCode, final String phones) {
        LoginAPI.checkYzm(mContext,phones,"2",smsCode)
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
                            Intent verifyIntent = new Intent(mActivity,HylVerifyActivity.class);
                            verifyIntent.putExtra("phone",phones);
                            verifyIntent.putExtra("smsCode",smsCode);
                            startActivity(verifyIntent);
                        } else {
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
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

    CountDownTimer countDownTimer;
    boolean isSendingCode;
    private void handleCountDown() {
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isSendingCode = true;
                rl_yzm.setEnabled(false);
                tv_yzm.setEnabled(false);
                tv_yzm.setText(millisUntilFinished / 1000 + "秒后" + "重新发送");
                tv_yzm.setTextColor(Color.parseColor("#FF2925"));

            }

            @Override
            public void onFinish() {
                isSendingCode = false;
                rl_yzm.setEnabled(true);
                tv_yzm.setText("点击发送验证码");
                tv_yzm.setEnabled(true);
                tv_yzm.setTextColor(Color.parseColor("#FF2925"));
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
