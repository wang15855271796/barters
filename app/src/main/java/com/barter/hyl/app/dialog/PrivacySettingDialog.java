package com.barter.hyl.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.barter.hyl.app.R;

public abstract class PrivacySettingDialog extends Dialog implements View.OnClickListener {
    Context mContext;
    TextView tv_sure;
    TextView tv_cancel;
    public PrivacySettingDialog(@NonNull Context context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_privacy_dialog);
        mContext = context;
        initView();
        initAction();
    }

    private void initAction() {
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);

        tv_sure.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

    }

    private void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                close();
                break;

            case R.id.tv_sure:
                sure();
                break;

        }

    }

    public abstract void close();
    public abstract void sure();
}
