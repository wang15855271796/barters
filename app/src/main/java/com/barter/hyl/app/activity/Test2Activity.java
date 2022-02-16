package com.barter.hyl.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.barter.hyl.app.R;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.utils.ToastUtil;

import butterknife.BindView;

/**
 * Created by ${王涛} on 2021/9/29
 */
public class Test2Activity extends BaseActivity {
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.test2);
    }

    @Override
    public void setViewData() {
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showSuccessMsg(mContext,"111");
            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showSuccessMsg(mContext,"222");
            }
        });
    }

    @Override
    public void setClickListener() {

    }
}
