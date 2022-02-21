package com.barter.hyl.app.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.barter.hyl.app.R;
import com.barter.hyl.app.api.LoginAPI;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.constant.StringHelper;
import com.barter.hyl.app.dialog.PromptDialog;
import com.barter.hyl.app.model.HylOneRegisterModel;
import com.barter.hyl.app.model.HylRegisterModel;
import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.AuthenticationExecuteListener;
import com.sdk.mobile.manager.login.NoDoubleClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/9/13
 */
public class SyRegisterActivity extends BaseActivity implements View.OnClickListener{
    private Toolbar mToolbar;
    private EditText mEditPhone;
    private CheckBox mCb;
    private EditText mEditCode;
    private TextView mTvAgreement;
    private TextView tv_message;
//    private RegisterAgreementModel mModelRegisterAgreement;
    private String mUrlAgreement;
    private boolean isSendingCode = false;
    private CountDownTimer countDownTimer;
    private LoadingDailog dialog;
    private EditText mEditPasswordOnce;
    private EditText mEditPasswordSecond;
    private EditText mEditAuthorization;
    private HylRegisterModel mModelRegister;
    private ImageView mIvPwdOneEye;
    private ImageView mIvPwdTwoEye;
    private boolean isStarFirst = true;
    private boolean isStarSecond = true;
    private TextView mTvCustomerPhone;
    private String mCustomerCell;
    private AlertDialog mDialog;
    ProgressBar loading;
//    private List<GetRegisterShopModel.DataBean> list = new ArrayList<>();
//    private RegisterShopAdapterTwo mRegisterAdapter;



    private boolean isAuthSuccess;

    private String phone;

    private ImageView iv_auth_success;

    private TextView tv_register_secret;

    private TextView tv_yz;
    private String token1;
    private String phone1;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_sy_register);
    }

    @Override
    public void setViewData() {

        loading = (ProgressBar) findViewById(R.id.loading);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_register);
        tv_yz = findViewById(R.id.tv_yz);
        tv_message = findViewById(R.id.tv_message);
        mEditPhone = (EditText) findViewById(R.id.edit_login_register_phone);
        mCb = (CheckBox) findViewById(R.id.cb_register);
        mTvAgreement = (TextView) findViewById(R.id.tv_register_agreement);
        mEditCode = (EditText) findViewById(R.id.edit_register_code);
        mEditPasswordOnce = (EditText) findViewById(R.id.edit_input_password_once);//输入密码
        mEditPasswordSecond = (EditText) findViewById(R.id.edit_input_password_second);//确认密码
        mEditAuthorization = (EditText) findViewById(R.id.edit_input_password_authorization);//授权码,可填
        mIvPwdOneEye = (ImageView) findViewById(R.id.iv_register_pwd_one_eye);
        mIvPwdTwoEye = (ImageView) findViewById(R.id.iv_register_pwd_two_eye);
        mTvCustomerPhone = (TextView) findViewById(R.id.tv_register_customer_phone);
        iv_auth_success = findViewById(R.id.iv_auth_success);
        iv_auth_success.setVisibility(View.GONE);
        tv_register_secret = findViewById(R.id.tv_register_secret);

        tv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity,HylRegisterActivity.class);
                startActivity(intent);
            }
        });

        mTvCustomerPhone.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mTvCustomerPhone.getPaint().setAntiAlias(true);//抗锯齿
        //用了荣耀8青春版就黑屏

        mEditPasswordOnce.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEditPasswordSecond.setTransformationMethod(PasswordTransformationMethod.getInstance());

        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(mContext)
                .setMessage("信息提交中")
                .setCancelable(false)
                .setCancelOutside(false);
        dialog = loadBuilder.create();

//        requestRegisterAgreement();//请求注册协议的接口

        tv_yz.setOnClickListener(this);
    }

    @Override
    public void setClickListener() {
        final String url = "https://shaokao.qoger.com/apph5/html/yszc.html";
        tv_register_secret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HylNewWebViewActivity.class);
                intent.putExtra("URL", url);
                intent.putExtra("TYPE", 1);
                intent.putExtra("name", "协议");
                startActivity(intent);


            }
        });

        mToolbar.setNavigationOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                finish();
            }
        });
//        mBtnNext.setOnClickListener(noDoubleClickListener);
        mTvAgreement.setOnClickListener(noDoubleClickListener);

        mIvPwdOneEye.setOnClickListener(noDoubleClickListener);
        mIvPwdTwoEye.setOnClickListener(noDoubleClickListener);
        mTvCustomerPhone.setOnClickListener(noDoubleClickListener);
        mEditPasswordOnce.addTextChangedListener(textWatcher);
        mEditPasswordSecond.addTextChangedListener(textWatcher);
        mEditCode.addTextChangedListener(textWatcher);
        mEditPhone.addTextChangedListener(textWatcher);
        mCb.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (StringHelper.notEmptyAndNull(mEditPhone.getText().toString())
                        && mCb.isChecked()
                        && isAuthSuccess
                        && StringHelper.notEmptyAndNull(mEditPasswordOnce.getText().toString())
                        && StringHelper.notEmptyAndNull(mEditPasswordSecond.getText().toString())
                        && phone.equals(mEditPhone.getText().toString())) {
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_yz:
                phone1 = mEditPhone.getText().toString();
                int result = checkPhoneNum(phone1);
                if (result == 2) {
                    Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (result == 1) {
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    loading.setVisibility(View.VISIBLE);
                    OneKeyLoginManager.getInstance().startAuthentication(new AuthenticationExecuteListener() {
                        @Override
                        public void authenticationRespond(int code, String result) {
                            if(code==2000) {
                                //获取token成功
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(result);
                                    token1 = jsonObject.getString("token");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //一键验证的token
                                authPhone(token1);
                                loading.setVisibility(View.GONE);

                            }else {
                                //获取token失败
                                PromptDialog promptDialog = new PromptDialog(SyRegisterActivity.this) {
                                    @Override
                                    public void Confirm() {
                                        dismiss();
                                    }
                                };
                                promptDialog.title.setText("请检查填写号码是否本机号码与数据网络正常");
                                promptDialog.hint.setText("建议使用短信验证码注册");
                                promptDialog.show();
                                loading.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                break;
        }
    }

    /**
     * 本机验证
     * @param result
     */
    private void authPhone(final String result) {
        LoginAPI.getData(mContext,result,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylOneRegisterModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HylOneRegisterModel baseModel) {
                        if (baseModel.success) {
                            Intent intent = new Intent(mActivity,HylRegisterResultActivity.class);
                            intent.putExtra("phone",baseModel.data);
                            intent.putExtra("token",result);
                            startActivity(intent);
                            OneKeyLoginManager.getInstance().finishAuthActivity();
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                            OneKeyLoginManager.getInstance().finishAuthActivity();
                        }
                    }
                });
    }


    /**
     * 验证手机号码
     * @param username
     * @return
     */
    private int checkPhoneNum(String username){
        if (TextUtils.isEmpty(username)){
            return 2;
        }else if (!username.matches("^[1][0-9]{10}$")){
            return 1;
        }else {
            return 0;
        }
    }


    public interface PermissionCallback {
        void onPermissionGranted(boolean isRequestUser);

        void onPermissionDenied(boolean isRequestUser);
    }



//    private void requestRegisterAgreement() {
//        if (!NetWorkHelper.isNetworkAvailable(mContext)) {
//            AppHelper.showMsg(mContext, "网络不给力!");
//        } else {
//            RegisterAgreementAPI.requestRegisterAgreement(mContext)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<RegisterAgreementModel>() {
//                        @Override
//                        public void onCompleted() {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onNext(RegisterAgreementModel registerAgreementModel) {
//                            mModelRegisterAgreement = registerAgreementModel;
//                            if (mModelRegisterAgreement.success) {
//                                updateRegisterAgreement();
//                            } else {
//                                AppHelper.showMsg(mContext, mModelRegisterAgreement.message);
//                            }
//                        }
//                    });
//        }
//    }

//    private void updateRegisterAgreement() {
//        mUrlAgreement = mModelRegisterAgreement.data;
//    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //禁止空格输入
            if (s.toString().contains(" ")) {
                String[] str = s.toString().split(" ");
                String str1 = "";
                for (int i = 0; i < str.length; i++) {
                    str1 += str[i];
                }
                mEditPasswordOnce.setText(str1);
                mEditPasswordOnce.setSelection(start);

                mEditPasswordSecond.setText(str1);
                mEditPasswordSecond.setSelection(start);

                mEditAuthorization.setText(str1);
                mEditAuthorization.setSelection(start);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (StringHelper.notEmptyAndNull(mEditPhone.getText().toString())
                    && mCb.isChecked()
                    && isAuthSuccess
                    && StringHelper.notEmptyAndNull(mEditPasswordOnce.getText().toString())
                    && StringHelper.notEmptyAndNull(mEditPasswordSecond.getText().toString())) {
            } else {

            }

            if (s.length() > 0) {
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    if (c >= 0x4e00 && c <= 0X9fff) { // 根据字节码判断
                        // 如果是中文，则清除输入的字符，否则保留
                        s.delete(i, i + 1);
                    }
                }

            }
        }
    };

    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
//            if (view == mTvAgreement) {
//                startActivity(HylCommonH5Activity.getIntent(mContext, HylCommonH5Activity.class, mUrlAgreement));
//            }
//            else if (view == mBtnNext) {
//                if (mCb.isChecked()) {
//                    //先校验验证码的正确性
//                    if (mEditPasswordOnce.getText().toString().equals(mEditPasswordSecond.getText().toString())) {
//                        //两次输入的密码一致才能请求注册
//                        //两次密码一致之后判断密码是不是6-16位字母与数字的组合,如果是纯数字或者纯字母,不允许往下走
//                        if (mEditPasswordOnce.getText().toString().length() >= 6
//                                && mEditPasswordSecond.getText().toString().length() >= 6) {
//                            if (StringHelper.isLetterDigit(mEditPasswordOnce.getText().toString())) {
//                                //必须选择店铺类型    \
//
//
//                                if (isAuthSuccess) {
//                                    updateCheckCode();
//                                } else {
//                                    AppHelper.showMsg(mContext, "请进行本机认证");
//                                }
//
//                                //requestRegisterCodeRight();
//                            } else {
//                                AppHelper.showMsg(mContext, "密码由6-16位数字与字母组成");
//                            }
//                        } else {
//                            AppHelper.showMsg(mContext, "密码位数不足!");
//                        }
//                    } else {
//                        AppHelper.showMsg(mContext, "两次密码不一致!");
//                    }
//                } else {
//                    AppHelper.showMsg(mContext, "请选择注册协议");
//                }
//            } else if (view == mIvPwdOneEye) {
//                //显示为星号或者显示数字
//                if (isStarFirst) {
//                    //现在显示的星星,点击变成数字
//                    isStarFirst = false;
//                    mEditPasswordOnce.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    mEditPasswordOnce.setSelection(mEditPasswordOnce.getText().length());
//                    mIvPwdOneEye.setImageResource(R.mipmap.ic_eye_open);
//                } else {
//                    //现在不是星星,点击变成星星
//                    isStarFirst = true;
//                    mEditPasswordOnce.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    mEditPasswordOnce.setSelection(mEditPasswordOnce.getText().length());
//                    mIvPwdOneEye.setImageResource(R.mipmap.ic_login_hide);
//                }
//            } else if (view == mIvPwdTwoEye) {
//                //显示为星号或者显示数字
//                if (isStarSecond) {
//                    //现在显示的星星,点击变成数字
//                    isStarSecond = false;
//                    mEditPasswordSecond.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    mEditPasswordSecond.setSelection(mEditPasswordSecond.getText().length());
//                    mIvPwdTwoEye.setImageResource(R.mipmap.ic_eye_open);
//                } else {
//                    //现在不是星星,点击变成星星
//                    isStarSecond = true;
//                    mEditPasswordSecond.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    mEditPasswordSecond.setSelection(mEditPasswordSecond.getText().length());
//                    mIvPwdTwoEye.setImageResource(R.mipmap.ic_login_hide);
//                }
//            } else if (view == mTvCustomerPhone) {
//                if (StringHelper.notEmptyAndNull(mCustomerCell)) {
//                    showPhoneDialog(mCustomerCell);
//                }
//            }
        }
    };





}
