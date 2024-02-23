package com.barter.hyl.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.barter.hyl.app.R;
import com.barter.hyl.app.api.MyInfoApi;
import com.barter.hyl.app.event.AuthEvent;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.HylOneRegisterModel;
import com.barter.hyl.app.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AuthDialog extends Dialog implements View.OnClickListener {
    Context mContext;
    ImageView iv_close;
    TextView tv_sure;
    EditText et_content;
    ImageView iv_tip;
    TextView tv_desc;
    public AuthDialog(Context mContext) {
        super(mContext, R.style.promptDialog);
        this.mContext = mContext;
        setContentView(R.layout.dialog_auth);
        initAction();
    }

    private void initAction() {
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        et_content = (EditText) findViewById(R.id.et_content);
        iv_tip= (ImageView) findViewById(R.id.iv_tip);
        iv_close.setOnClickListener(this);
        tv_sure.setOnClickListener(this);

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length() == 6) {
                    getCompanyName(editable.toString());
                    tv_desc.setVisibility(View.VISIBLE);
                    iv_tip.setVisibility(View.VISIBLE);
                }else {
                    tv_desc.setVisibility(View.GONE);
                    iv_tip.setVisibility(View.GONE);
                }
            }
        });
//        getCompanyName(authCode);
    }

//    public abstract void Confirm(String content);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                String authCode = et_content.getText().toString();
                getAuth(authCode);
                break;

            case R.id.iv_close:
                dismiss();
                break;

        }
    }

    /**
     * 获取公司简称
     */
    private void getCompanyName(String smsCode) {
        MyInfoApi.getCompanyName(mContext,smsCode)
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
                            tv_desc.setText(hylOneRegisterModel.data);
                            tv_desc.setVisibility(View.VISIBLE);
                            iv_tip.setImageResource(R.mipmap.ic_green_tip);
                            iv_tip.setVisibility(View.VISIBLE);
                        } else {
                            iv_tip.setVisibility(View.VISIBLE);
                            tv_desc.setVisibility(View.VISIBLE);
                            tv_desc.setText(hylOneRegisterModel.message);
                            iv_tip.setImageResource(R.mipmap.ic_hot_tip);
                        }
                    }
                });
    }

    private void getAuth(String smsCode) {
        MyInfoApi.getAuth(mContext,smsCode)
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
                        if(baseModel.code==1) {
                            dismiss();
                            EventBus.getDefault().postSticky(new AuthEvent());
                        }
                        ToastUtil.showSuccessMsg(mContext,baseModel.message);
                    }
                });
    }

}
