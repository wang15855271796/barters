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

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/7/29
 */
public class HylRegisterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.rl_yzm)
    RelativeLayout rl_yzm;
    @BindView(R.id.tv_yzm)
    TextView tv_yzm;
    @BindView(R.id.et_yzm)
    TextView et_yzm;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.register_activity);
    }

    @Override
    public void setViewData() {
        tv_title.setText("注册");
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        rl_yzm.setOnClickListener(this);
    }

    String phone;
    String yzm;
    String registerPhone;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_next:
                yzm = et_yzm.getText().toString();
                registerPhone = et_phone.getText().toString();
                checkYzm(yzm);
                break;

            case R.id.rl_yzm:
                //发送验证码
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

                break;

        }
    }


    /**
     * 验证验证码
     */
    private void checkYzm(String smsCode) {
        LoginAPI.checkYzm(mContext,registerPhone,"1",smsCode)
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
                            Intent registerResultIntent = new Intent(mActivity,HylRegisterResultActivity.class);
                            registerResultIntent.putExtra("phone",registerPhone);
                            registerResultIntent.putExtra("smsCode",et_yzm.getText().toString());
                            startActivity(registerResultIntent);
                        } else {
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

    /**
     * 发送验证码
     */
    private void sendYzm(String phone) {
        LoginAPI.sendYzm(mContext,phone,"1")
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
                tv_yzm.setTextColor(Color.parseColor("#ff2925"));

            }

            @Override
            public void onFinish() {
                isSendingCode = false;
                rl_yzm.setEnabled(true);
                tv_yzm.setText("点击发送验证码");
                tv_yzm.setEnabled(true);
                tv_yzm.setTextColor(Color.parseColor("#ff2925"));
            }
        }.start();
    }
}
