package com.barter.hyl.app;

import android.os.Bundle;

import com.barter.hyl.app.base.BaseActivity;

public class TestActivity extends BaseActivity {
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_apply);
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickListener() {

    }
}
