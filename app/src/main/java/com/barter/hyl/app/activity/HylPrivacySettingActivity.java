package com.barter.hyl.app.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.barter.hyl.app.R;
import com.barter.hyl.app.base.BaseActivity;

import butterknife.BindView;

public class HylPrivacySettingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.ll_root1)
    LinearLayout ll_root1;
    @BindView(R.id.ll_root2)
    LinearLayout ll_root2;
    @BindView(R.id.ll_root3)
    LinearLayout ll_root3;
    @BindView(R.id.ll_root4)
    LinearLayout ll_root4;
    @BindView(R.id.ll_root5)
    LinearLayout ll_root5;
    @BindView(R.id.tv0)
    TextView tv0;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_privacy);
    }


    @Override
    public void setViewData() {
        tv_title.setText("隐私权限设置");
    }

    @Override
    public void setClickListener() {
        ll_root1.setOnClickListener(this);
        ll_root2.setOnClickListener(this);
        ll_root3.setOnClickListener(this);
        ll_root4.setOnClickListener(this);
        ll_root5.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_root1:
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                break;

            case R.id.ll_root2:
                Intent intent1 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri1 = Uri.fromParts("package", getPackageName(), null);
                intent1.setData(uri1);
                startActivity(intent1);
                break;

            case R.id.ll_root3:
                Intent intent2 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri2 = Uri.fromParts("package", getPackageName(), null);
                intent2.setData(uri2);
                startActivity(intent2);
                break;

            case R.id.ll_root4:
                Intent intent3 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri3 = Uri.fromParts("package", getPackageName(), null);
                intent3.setData(uri3);
                startActivity(intent3);
                break;

            case R.id.ll_root5:
                Intent intent4 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri4 = Uri.fromParts("package", getPackageName(), null);
                intent4.setData(uri4);
                startActivity(intent4);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        int check0 = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION);
        int check1 = ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO);
        int check2 = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);
        int check3 = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE);
        int check4 = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
//        int check5 = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(check0== 0) {
            tv0.setText("已开启");
        }else {
            tv0.setText("去设置");
        }

        if(check1== 0) {
            tv1.setText("已开启");
        }else {
            tv1.setText("去设置");
        }

        if(check2== 0) {
            tv2.setText("已开启");
        }else {
            tv2.setText("去设置");
        }

        if(check3== 0) {
            tv3.setText("已开启");
        }else {
            tv3.setText("去设置");
        }

        if(check4== 0) {
            tv4.setText("已开启");
        }else {
            tv4.setText("去设置");
        }
    }
}
