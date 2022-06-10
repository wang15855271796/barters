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
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
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
        companyAdapter = new CompanyAdapter(R.layout.item_company_info,dataInfo);
        recyclerView.setAdapter(companyAdapter);
        companyAdapter.notifyDataSetChanged();

        getCompanyInfo();
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
    }


    /**
     * 获取企业信息
     */
    List<CompanyInfoModel.DataBean> dataInfo = new ArrayList<>();
    private void getCompanyInfo() {
        MyInfoApi.getCompanyInfo(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CompanyInfoModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CompanyInfoModel companyInfoModel) {
                        if(companyInfoModel.code==1) {
                            dataInfo.clear();
                            if(companyInfoModel.getData()!=null&&companyInfoModel.getData().size()>0) {
                                dataInfo.addAll(companyInfoModel.getData());
                                companyAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,companyInfoModel.message);
                        }
                    }
                });
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
