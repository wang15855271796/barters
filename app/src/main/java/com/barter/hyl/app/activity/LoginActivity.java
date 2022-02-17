package com.barter.hyl.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.api.LoginAPI;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.event.ChangeAccountHylEvent;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.HylOneRegisterModel;
import com.barter.hyl.app.model.HylPolicyModel;
import com.barter.hyl.app.utils.SharedPreferencesUtil;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.ConfigUtils;
import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.OneKeyLoginListener;
import com.chuanglan.shanyan_sdk.listener.OpenLoginAuthListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/7/29
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.tv_forget)
    TextView tv_forget;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_display)
    ImageView iv_display;
    @BindView(R.id.ll_choose)
    LinearLayout ll_choose;
    @BindView(R.id.cb_check)
    CheckBox cb_check;
    @BindView(R.id.tv_agreement)
    TextView tv_agreement;
    @BindView(R.id.tv_secret)
    TextView tv_secret;
    @BindView(R.id.tv_apply)
    TextView tv_apply;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.login_activity);
    }

    @Override
    public void setViewData() {
        if(SharedPreferencesUtil.getString(mContext,"userPhone")!=null) {
            String userPhone = SharedPreferencesUtil.getString(mContext, "userPhone");
            et_account.setText(userPhone);
        }
        iv_back.setVisibility(View.GONE);

        SpannableString smp = new SpannableString(tv_secret.getText().toString());
        smp.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#3483FF"));       //设置文件颜色
                   //设置下划线
                ds.setUnderlineText(false);
            }

            @Override
            public void onClick(View widget) {
            }
        }, 0, tv_secret.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_secret.setText(smp);

        getPolicy();
    }

    @Override
    public void setClickListener() {
        iv_display.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        ll_choose.setOnClickListener(this);
        cb_check.setOnClickListener(this);
        tv_agreement.setOnClickListener(this);
        tv_secret.setOnClickListener(this);
        tv_apply.setOnClickListener(this);
    }

    String etAccount;
    String etPassword;
    boolean showPassword = true;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_apply:
                Intent intent = new Intent(mContext,ApplyTryActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_secret:
                startActivity(HylCommonH5Activity.getIntent(mContext, HylCommonH5Activity.class, privacy));
                break;

            case R.id.tv_agreement:
                startActivity(HylCommonH5Activity.getIntent(mContext, HylCommonH5Activity.class, register));
                break;

            case R.id.ll_choose:
                if(cb_check.isChecked()) {
                    cb_check.setChecked(false);
                }else {
                    cb_check.setChecked(true);
                }

                break;

            case R.id.iv_display:
                if (showPassword) {// 显示密码
                    iv_display.setImageResource(R.mipmap.ic_login_display);
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_password.setSelection(et_password.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码

                    iv_display.setImageResource(R.mipmap.ic_login_hide);
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_password.setSelection(et_password.getText().toString().length());
                    showPassword = !showPassword;
                }
                break;

            case R.id.tv_register:
//                Intent intent = new Intent(LoginActivity.this,HylRegisterActivity.class);
//                startActivity(intent);
                OneKeyLoginManager.getInstance().setAuthThemeConfig(ConfigUtils.getCJSConfig(getApplicationContext()));
                openLoginActivity();
                break;

            case R.id.tv_forget:
                Intent forgetIntent = new Intent(mActivity,HylForgetPassWordActivity.class);
                forgetIntent.putExtra("title","忘记密码");
                startActivity(forgetIntent);
                break;

            case R.id.tv_login:
                etAccount = et_account.getText().toString();
                etPassword = et_password.getText().toString();
                hintKbTwo();
                if(cb_check.isChecked()) {
                    login();
                }else {
                    ToastUtil.showSuccessMsg(mContext,"请阅读并勾选相关协议与政策");
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
    String token1;
    private void openLoginActivity() {
        //开始拉取授权页
        OneKeyLoginManager.getInstance().openLoginAuth(false, new OpenLoginAuthListener() {
            @Override
            public void getOpenLoginAuthStatus(int code, String result) {

                if (1000 == code) {
                    Log.e("VVV", "拉起授权页成功： code==" + code + "   result==" + result);
                } else {
                    Log.e("VVV", "拉起授权页失败： code==" + code + "   result==" + result);
                    Intent intent = new Intent(LoginActivity.this,HylRegisterActivity.class);
                    startActivity(intent);
                }
            }
        }, new OneKeyLoginListener() {
            @Override
            public void getOneKeyLoginStatus(int code, String result) {

                if (1011 == code) {
                    Log.e("VVV", "用户点击授权页返回： code==" + code + "   result==" + result);
                    return;
                } else if (1000 == code) {
                    Log.e("VVV", "用户点击登录获取token成功： code==" + code + "   result==" + result);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        token1 = jsonObject.getString("token");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    checks(token1);
                } else {
                    Log.e("VVV", "用户点击登录获取token失败： code==" + code + "   result==" + result);
                }
            }
        });
    }

    private void checks(final String result) {
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

    private void login() {
        LoginAPI.login(mContext,etAccount,etPassword)
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
                    public void onNext(HylLoginModel hylLoginModel) {
                        if (hylLoginModel.code==1) {
                            ToastUtil.showSuccessMsg(mContext, hylLoginModel.message);
                            Intent intent = new Intent(mActivity,MainActivity.class);
                            UserInfoHelper.saveUserId(mContext, hylLoginModel.data);
                            EventBus.getDefault().post(new ChangeAccountHylEvent());
                            SharedPreferencesUtil.saveString(mActivity,"userPhone",et_account.getText().toString());
                            startActivity(intent);
                            finish();
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylLoginModel.message);
                        }
                    }
                });
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

    //禁用返回键
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
            //do something.
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

}
