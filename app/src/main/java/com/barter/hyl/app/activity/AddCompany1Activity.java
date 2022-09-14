package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.api.LoginAPI;
import com.barter.hyl.app.api.MyInfoApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.dialog.LoginDialog;
import com.barter.hyl.app.event.ChangeAccountHylEvent;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.HylOneRegisterModel;
import com.barter.hyl.app.utils.SharedPreferencesUtil;
import com.barter.hyl.app.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddCompany1Activity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    @BindView(R.id.et_sms)
    EditText et_sms;
    @BindView(R.id.tv_company)
    TextView tv_company;
    String phone;
    String password;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        if(getIntent().getStringExtra("phone")!=null) {
            phone = getIntent().getStringExtra("phone");
        }

        if(getIntent().getStringExtra("password")!=null) {
            password = getIntent().getStringExtra("password");
        }


        Log.d("wfasfsda.....",phone+"----"+password);
        return false;
    }


    @Override
    public void setContentView() {
        setContentView(R.layout.add_company_activity);
    }

    @Override
    public void setViewData() {
        tv_title.setText("添加企业");
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        et_sms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()==6) {
                    getCompanyName(editable.toString());
                }
            }
        });
    }

    /**
     * 获取公司简称
     */
    private void getCompanyName(String smsCode) {
        MyInfoApi.getCompanyName(mActivity,smsCode)
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
                    public void onNext(HylOneRegisterModel hylOneRegisterModel) {
                        if(hylOneRegisterModel.code==1) {
                            tv_company.setText(hylOneRegisterModel.data);
//                            ToastUtil.showSuccessMsg(mContext,hylOneRegisterModel.message);
                        }else {
                            ToastUtil.showSuccessMsg(mContext,hylOneRegisterModel.message);
                        }
                    }
                });
    }

    /**
     * 登录-添加企业
     */
    private void loginAddress() {
        LoginAPI.loginAddress(mContext,phone,etSms)
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
                            login();
                        }else {
                            ToastUtil.showSuccessMsg(mContext, hylLoginModel.message);
                        }
                    }
                });
    }

    private void login() {
        LoginAPI.login(mContext,phone,password)
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
                            SharedPreferencesUtil.saveString(mActivity,"userPhone",phone);
                            startActivity(intent);
                            finish();
                        } else if(hylLoginModel.code==100004) {
                            LoginDialog loginDialog = new LoginDialog(mActivity,hylLoginModel.extData,phone,password);
                            loginDialog.show();

                        }else {
                            ToastUtil.showSuccessMsg(mContext, hylLoginModel.message);
                        }
                    }
                });
    }

    String etSms;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_sure:
                etSms = et_sms.getText().toString();
                loginAddress();
                break;
        }
    }
}
