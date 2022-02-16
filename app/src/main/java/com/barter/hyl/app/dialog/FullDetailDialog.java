package com.barter.hyl.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylCommonDetailModel;

/**
 * Created by ${王涛} on 2021/9/17
 */
public abstract class FullDetailDialog extends Dialog {

    Context mContext;
    public TextView tv_sure,hint;
    public TextView title;
    HylCommonDetailModel.DataBean data;
    TextView tv_buy;
    TextView tv_given;
    TextView tv_desc;
    public FullDetailDialog(@NonNull Activity mActivity, HylCommonDetailModel.DataBean data) {
        super(mActivity, R.style.promptDialog);
        setContentView(R.layout.dialog_full_hyl);
        mContext = mActivity;
        this.data = data;
        initView();
        initAction();
    }

    private void initView() {
        tv_sure= (TextView) findViewById(R.id.tv_sure);
        tv_buy = (TextView) findViewById(R.id.tv_buy);
        tv_given = (TextView) findViewById(R.id.tv_given);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        title = findViewById(R.id.title);

        tv_buy.setText(data.getFullRoles().get(0).getBuySpec());
        tv_given.setText(data.getFullRoles().get(0).getSendProd());
        tv_desc.setText(data.getFullRoles().get(0).getRole());
    }

    private void initAction() {
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();
            }
        });
    }

    public abstract void Confirm();


}
