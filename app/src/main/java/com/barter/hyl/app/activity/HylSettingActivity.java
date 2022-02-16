package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.barter.hyl.app.model.HylSettingModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.DialogHelper;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/8/30
 */
public class HylSettingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_company)
    TextView tv_company;
    @BindView(R.id.tv_code)
    TextView tv_code;
    @BindView(R.id.tv_logout)
    TextView tv_logout;
    @BindView(R.id.rl_business)
    RelativeLayout rl_business;
    @BindView(R.id.rl_agree)
    RelativeLayout rl_agree;
    @BindView(R.id.rl_privacy)
    RelativeLayout rl_privacy;
    @BindView(R.id.rl_account_safe)
    RelativeLayout rl_account_safe;
    @BindView(R.id.rl_share)
    RelativeLayout rl_share;
    @BindView(R.id.rl_collection)
    RelativeLayout rl_collection;
    @BindView(R.id.rl_privacy_setting)
    RelativeLayout rl_privacy_setting;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_account_hyl);
    }

    @Override
    public void setViewData() {
        tv_title.setText("设置");
        getInfo();
    }



    @Override
    public void setClickListener() {
        rl_account_safe.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
        rl_business.setOnClickListener(this);
        rl_agree.setOnClickListener(this);
        rl_privacy.setOnClickListener(this);
        rl_share.setOnClickListener(this);
        rl_collection.setOnClickListener(this);
        rl_privacy_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_share:
                startActivity(HylCommonH5Activity.getIntent(mContext, HylCommonH5Activity.class, "https://shaokao.qoger.com/apph5/html/hyl_third.html"));
                break;

            case R.id.rl_collection:
                startActivity(HylCommonH5Activity.getIntent(mContext, HylCommonH5Activity.class, "https://shaokao.qoger.com/apph5/html/hyl_collect.html"));
                break;
            case R.id.rl_account_safe:
                Intent accountSafeIntent = new Intent(mActivity,HylAccountSafeActivity.class);
                accountSafeIntent.putExtra("phone",phone);
                startActivity(accountSafeIntent);
                break;

            case R.id.rl_privacy:
                Intent privacyIntent = new Intent(mActivity,HylPrivacyActivity.class);
                privacyIntent.putExtra("privacy",privacy);
                startActivity(privacyIntent);
                break;

            case R.id.rl_agree:
                Intent agreeIntent = new Intent(mActivity,HylAgreeActivity.class);
                agreeIntent.putExtra("register",register);
                startActivity(agreeIntent);
                break;

            case R.id.rl_business:
                Intent businessIntent = new Intent(mActivity,HylBusinessActivity.class);
                businessIntent.putExtra("businessPic",businessPic);
                businessIntent.putExtra("licensePic",licensePic);
                startActivity(businessIntent);
                break;

            case R.id.tv_logout:
                DialogHelper.showLogoutDialog(mActivity, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logout();
                    }
                });

                break;

            case R.id.rl_privacy_setting:
                Intent rlPrivacyIntent = new Intent(mActivity, HylPrivacySettingActivity.class);
                startActivity(rlPrivacyIntent);

                break;


            case R.id.iv_back:
                finish();
                break;
        }
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

    String licensePic;
    String businessPic;
    String register;
    String privacy;
    String phone;
    private void getInfo() {
        SettingApi.setting(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylSettingModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylSettingModel hylSettingModel) {
                        if (hylSettingModel.getCode()==1) {
                            if(hylSettingModel.getData()!=null) {
                                HylSettingModel.DataBean data = hylSettingModel.getData();
                                tv_company.setText(data.getCompanyName());
                                tv_code.setText(data.getAuthCode());
                                register = data.getRegister();
                                privacy = data.getPrivacy();
                                phone = data.getPhone();
                                licensePic = data.getLicensePic();
                                businessPic = data.getBusinessPic();
                            }
                        }else if(hylSettingModel.getCode() == -10001) {
                            Intent intent = new Intent(mContext,LoginActivity.class);
                            startActivity(intent);
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylSettingModel.getMessage());
                        }
                    }
                });
    }

}
