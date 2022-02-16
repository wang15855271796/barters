package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.api.LoginAPI;
import com.barter.hyl.app.api.SettingApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.event.HotHylEvent;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/7/29
 */
public class HylVerifyActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_password_sure)
    EditText et_password_sure;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.rl_eye1)
    RelativeLayout rl_eye1;
    @BindView(R.id.rl_eye2)
    RelativeLayout rl_eye2;
    @BindView(R.id.iv_eye1)
    ImageView iv_eye1;
    @BindView(R.id.iv_eye2)
    ImageView iv_eye2;
    String smsCode;
    String phone;
    boolean showPassword = true;
    boolean showPassword2 = true;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        smsCode = getIntent().getStringExtra("smsCode");
        phone = getIntent().getStringExtra("phone");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.verify_activity_hyl);
    }

    @Override
    public void setViewData() {
        tv_title.setText("输入新密码");
    }

    @Override
    public void setClickListener() {
        tv_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        rl_eye1.setOnClickListener(this);
        rl_eye2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_eye1:
                if (showPassword) {// 显示密码
                    iv_eye1.setImageResource(R.mipmap.ic_login_display);
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_password.setSelection(et_password.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码
                    iv_eye1.setImageResource(R.mipmap.ic_login_hide);
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_password.setSelection(et_password.getText().toString().length());
                    showPassword = !showPassword;
                }
                break;

            case R.id.rl_eye2:
                if (showPassword2) {// 显示密码
                    iv_eye2.setImageResource(R.mipmap.ic_login_display);
                    et_password_sure.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_password_sure.setSelection(et_password_sure.getText().toString().length());
                    showPassword2 = !showPassword2;
                } else {// 隐藏密码
                    iv_eye2.setImageResource(R.mipmap.ic_login_hide);
                    et_password_sure.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_password_sure.setSelection(et_password_sure.getText().toString().length());
                    showPassword2 = !showPassword2;
                }
                break;

            case R.id.tv_next:
                String pwd = et_password.getText().toString();
                String pwd1 = et_password_sure.getText().toString();

                if(TextUtils.isEmpty(et_password.getText().toString())||TextUtils.isEmpty(et_password_sure.getText().toString())) {
                    ToastUtil.showSuccessMsg(mContext,"密码不能为空");
                    return;
                }

                if(!pwd.equals(pwd1)) {
                    ToastUtil.showSuccessMsg(mContext,"密码不一致");
                    return;
                }

                getNewSecret(pwd);
                break;

            case R.id.iv_back:
                finish();
                break;
        }
    }

    /**
     * 新密码
     */
    private void getNewSecret(String pwd) {
        SettingApi.forgetSecret(mActivity,phone,smsCode,pwd)
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
                            logout();
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, baseModel.message);
                        }
                    }
                });
    }

    /**
     * 退出登录
     */
    private void logout() {
        LoginAPI.logout(mContext)
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
                            EventBus.getDefault().post(new HotHylEvent());
                            UserInfoHelper.saveUserId(mContext,"");
                            Intent intent = new Intent(mActivity,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

}
