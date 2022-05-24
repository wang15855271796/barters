package com.barter.hyl.app.activity;

import android.os.Bundle;

import com.barter.hyl.app.R;
import com.barter.hyl.app.base.BaseActivity;

public class TestActivity extends BaseActivity {
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_test);
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickListener() {

    }
}
