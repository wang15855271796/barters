package com.barter.hyl.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.api.LoginAPI;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.constant.StringHelper;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.event.ChangeAccountHylEvent;
import com.barter.hyl.app.model.HylAuthCompanyModel;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.HylPolicyModel;
import com.barter.hyl.app.utils.SharedPreferencesUtil;
import com.barter.hyl.app.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/7/29
 */
public class HylRegisterResultActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_password_sure)
    EditText et_password_sure;
    @BindView(R.id.et_author)
    EditText et_author;
    @BindView(R.id.tv_company)
    TextView tv_company;
    @BindView(R.id.rl_company_name)
    RelativeLayout rl_company_name;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.cb_register)
    CheckBox cb_register;
    @BindView(R.id.ll_choose)
    LinearLayout ll_choose;
    @BindView(R.id.tv_secret)
    TextView tv_secret;
    @BindView(R.id.tv_agreement)
    TextView tv_agreement;
    @BindView(R.id.iv_one)
    ImageView iv_one;
    @BindView(R.id.iv_two)
    ImageView iv_two;
    String phone;
    String et_yzm;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        if(getIntent().getStringExtra("phone")!=null) {
            phone = getIntent().getStringExtra("phone");
        }
        if(getIntent().getStringExtra("smsCode")!=null) {
            et_yzm = getIntent().getStringExtra("smsCode");
        }

        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.register_result_activity);
    }

    @Override
    public void setViewData() {
        tv_title.setText("注册");
        tv_agreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_secret.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        SpannableString smp = new SpannableString(tv_secret.getText().toString());
//        ClickableSpan clickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//
//            }
//
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                ds.setUnderlineText(true);
//            }
//        };
//        tv_secret.setText(smp);
        getPolicy();
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
        iv_one.setOnClickListener(this);
        iv_two.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        ll_choose.setOnClickListener(this);
        tv_agreement.setOnClickListener(this);
        tv_secret.setOnClickListener(this);
        et_author.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()!=0) {
                    rl_company_name.setVisibility(View.VISIBLE);
                    if(s.toString().length()==6) {
                        String authCode = s.toString();
                        getAuthCompany(authCode);
                    }
                }else {
                    rl_company_name.setVisibility(View.GONE);
                }

            }
        });
    }

    boolean check = false;
    private String password;
    private String passwordSure;
    private String etAuthor;
    private boolean isStarFirst = true;
    private boolean isStarSecond = true;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_register:
                hintKbTwo();
                password = et_password.getText().toString();
                passwordSure = et_password_sure.getText().toString();
                etAuthor = et_author.getText().toString();

                checkData();
                break;

            case R.id.ll_choose:
                cb_register.setChecked(!check);
                break;

            case R.id.tv_secret:
                startActivity(HylCommonH5Activity.getIntent(mContext, HylCommonH5Activity.class, privacy));
                break;

            case R.id.tv_agreement:
                startActivity(HylCommonH5Activity.getIntent(mContext, HylCommonH5Activity.class, register));
                break;

            case R.id.iv_one:
                //显示为星号或者显示数字
                if (isStarFirst) {
                    //现在显示的星星,点击变成数字
                    isStarFirst = false;
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_password.setSelection(et_password.getText().length());
                    iv_one.setImageResource(R.mipmap.ic_eye_open);
                } else {
                    //现在不是星星,点击变成星星
                    isStarFirst = true;
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_password.setSelection(et_password.getText().length());
                    iv_one.setImageResource(R.mipmap.ic_login_hide);
                }
                break;

            case R.id.iv_two:
                //显示为星号或者显示数字
                if (isStarSecond) {
                    //现在显示的星星,点击变成数字
                    isStarSecond = false;
                    et_password_sure.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_password_sure.setSelection(et_password_sure.getText().length());
                    iv_two.setImageResource(R.mipmap.ic_eye_open);
                } else {
                    //现在不是星星,点击变成星星
                    isStarSecond = true;
                    et_password_sure.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_password_sure.setSelection(et_password_sure.getText().length());
                    iv_two.setImageResource(R.mipmap.ic_login_hide);
                }
                break;


        }
    }

    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
    /**
     * 验证数据
     */
    private void checkData() {
        if(cb_register.isChecked()) {
            if(password !=null && passwordSure !=null) {
                if(password.equals(passwordSure)) {
                    if(password.length()>6&& passwordSure.length()>6) {
                        if (StringHelper.isLetterDigit(et_password.getText().toString())) {
                            registers();
//                            if(!etAuthor.equals("")) {
//                                //registerType 0短信验证码
////                                register(phone,passwordSure,et_yzm,etAuthor,"0");
//                                registers();
//                            }else {
//                                AppHelper.showMsg(mContext, "授权码不能为空");
//                            }
                        } else {
                            AppHelper.showMsg(mContext, "密码由8-16位数字与字母组成");
                        }
                    } else {
                        AppHelper.showMsg(mContext, "密码位数不足!");
                    }
                }else {
                    AppHelper.showMsg(mContext, "两次密码不一致!");
                }
            }else {
                AppHelper.showMsg(mContext, "密码不能为空");
            }
        }else {
            AppHelper.showMsg(mContext, "请阅读并勾选相关协议与政策");
        }
    }

    /**
     * 注册
     */
    private void registers() {
        //这里请求注册成功之后直接登录成功,返回的token存储下来,就代表着用户已经登录了
        if(et_yzm==null) {
            LoginAPI.requestRegister(mContext, phone,passwordSure, "000000", et_author.getText().toString(),1+"")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<HylLoginModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(HylLoginModel baseModel) {
                            if(baseModel.code==1) {
                                ToastUtil.showSuccessMsg(mContext, baseModel.message);
                                Intent intent = new Intent(mActivity,MainActivity.class);
                                SharedPreferencesUtil.saveString(mActivity,"userPhone",phone);
                                UserInfoHelper.saveUserId(mContext,baseModel.data);
                                EventBus.getDefault().post(new ChangeAccountHylEvent());
                                startActivity(intent);
                                finish();
                            }else {
                                ToastUtil.showSuccessMsg(mContext,baseModel.message);
                            }
                        }
                    });
        }else {
            LoginAPI.requestRegister(mContext, phone,passwordSure, et_yzm, et_author.getText().toString(), 0+"")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<HylLoginModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(HylLoginModel baseModel) {
                            if(baseModel.code==1) {
                                ToastUtil.showSuccessMsg(mContext, baseModel.message);
                                Intent intent = new Intent(mActivity,MainActivity.class);
                                UserInfoHelper.saveUserId(mContext,baseModel.data);
                                SharedPreferencesUtil.saveString(mActivity,"userPhone",phone);
                                EventBus.getDefault().post(new ChangeAccountHylEvent());
                                startActivity(intent);
                                finish();
                            }else {
                                ToastUtil.showSuccessMsg(mContext,baseModel.message);
                            }
                        }
                    });
        }
    }

    /**
     * 获取协议
     */
    String register;
    String privacy;
    private void getPolicy() {
        LoginAPI.getPolicy(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylPolicyModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylPolicyModel hylPolicyModel) {
                        if (hylPolicyModel.getCode()==1) {
                            if(hylPolicyModel.getData()!=null) {
                                register = hylPolicyModel.getData().getRegister();
                                privacy = hylPolicyModel.getData().getPrivacy();
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylPolicyModel.getMessage());
                        }
                    }
                });
    }


    /**
     * 获取授权公司
     */
    private void getAuthCompany(String authCode) {
        LoginAPI.getAuthCompany(mContext,authCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylAuthCompanyModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylAuthCompanyModel hylAuthCompanyModel) {
                        if (hylAuthCompanyModel.code==1) {
                            tv_company.setText(hylAuthCompanyModel.data);
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylAuthCompanyModel.message);
                        }
                    }
                });
    }



}
