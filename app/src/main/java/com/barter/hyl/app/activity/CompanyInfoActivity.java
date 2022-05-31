package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.app.model.CompanyListModel;
import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.CompanyAdapter;
import com.barter.hyl.app.api.MyInfoApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.model.CompanyInfoModel;
import com.barter.hyl.app.model.HylMyModel;
import com.barter.hyl.app.utils.APKVersionCodeUtils;
import com.barter.hyl.app.utils.ToastUtil;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CompanyInfoActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    CompanyAdapter companyAdapter;
    List<CompanyInfoModel.DataBean> companyInfo;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        companyInfo = (List<CompanyInfoModel.DataBean>) getIntent().getSerializableExtra("companyInfo");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_info);
    }

    @Override
    public void setViewData() {
        tv_title.setText("企业信息");

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        companyAdapter = new CompanyAdapter(R.layout.item_company_info,companyInfo);
        recyclerView.setAdapter(companyAdapter);
        companyAdapter.notifyDataSetChanged();
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

}
