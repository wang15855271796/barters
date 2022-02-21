package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.base.BaseActivity;

import butterknife.BindView;

public class ApplyResultActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_apply_result);
    }

    @Override
    public void setViewData() {
        tv_title.setText("货易链");
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_sure:
                Intent intent = new Intent(mActivity,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
