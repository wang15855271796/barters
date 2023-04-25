package com.barter.hyl.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.ImageViewAdapter;
import com.barter.hyl.app.api.InfoListAPI;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.model.InfoDetailModel;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShopDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    String msgId;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_cate)
    TextView tv_cate;
    @BindView(R.id.tv_contact)
    TextView tv_contact;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rl_pay_time)
    RelativeLayout rl_pay_time;
    @BindView(R.id.tv_pay_time)
    TextView tv_pay_time;
    @BindView(R.id.rl_return_account)
    RelativeLayout rl_return_account;
    @BindView(R.id.tv_return_account)
    TextView tv_return_account;
    @BindView(R.id.rl_return_time)
    RelativeLayout rl_return_time;
    @BindView(R.id.tv_return_time)
    TextView tv_return_time;
    @BindView(R.id.ll_info)
    LinearLayout ll_info;
    @BindView(R.id.rl_pay)
    RelativeLayout rl_pay;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    InfoDetailModel.DataBean lists;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        msgId = getIntent().getStringExtra("msgId");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_shop_detail);
    }

    @Override
    public void setViewData() {
        iv_back.setOnClickListener(this);
        getCityList();
    }

    @Override
    public void setClickListener() {

    }


    private void getCityList() {
        InfoListAPI.getDetail(mActivity, msgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InfoDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(InfoDetailModel infoListModel) {
                        if (infoListModel.getCode()==1) {
                            if (infoListModel.getData() != null) {
                                lists = infoListModel.getData();
                                tv_phone.setText(lists.getUserPhone());
                                tv_content.setText(lists.getContent());
                                tv_cate.setText(lists.getMsgTypeName());
                                tv_contact.setText(lists.getContactPhone());
                                tv_address.setText(lists.getProvinceName()+lists.getCityName()+lists.getAreaName()+lists.getDetailAddress());

                                List<String> pictureList = lists.getPictureList();
                                if(lists.getVideoUrl()!=null) {
                                    pictureList.add(lists.getVideoUrl());
                                }

                                ImageViewAdapter imageViewAdapter = new ImageViewAdapter(R.layout.item_image,pictureList);
                                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                                recyclerView.setAdapter(imageViewAdapter);
                                if(lists.getPayFlag() ==0) {
                                    ll_info.setVisibility(View.GONE);
                                }else if(lists.getPayFlag() ==1) {
                                    ll_info.setVisibility(View.VISIBLE);
                                    if(lists.getPayAmt()!=null && !lists.getPayAmt().equals("")) {
                                        rl_pay.setVisibility(View.VISIBLE);
                                        rl_pay_time.setVisibility(View.VISIBLE);
                                    }else {
                                        rl_pay.setVisibility(View.GONE);
                                        rl_pay_time.setVisibility(View.GONE);
                                    }

                                    rl_return_account.setVisibility(View.GONE);
                                    rl_return_time.setVisibility(View.GONE);
                                    tv_pay.setText(lists.getPayAmt());
                                    tv_pay_time.setText(lists.getPayTime());
                                }else {
                                    ll_info.setVisibility(View.VISIBLE);
                                    rl_pay.setVisibility(View.GONE);
                                    rl_pay_time.setVisibility(View.GONE);
                                    if(lists.getReturnAmt()!=null && !lists.getReturnAmt().equals("")) {
                                        rl_return_account.setVisibility(View.VISIBLE);
                                        rl_return_time.setVisibility(View.VISIBLE);
                                    }else {
                                        rl_return_account.setVisibility(View.GONE);
                                        rl_return_time.setVisibility(View.GONE);
                                    }

                                    tv_return_time.setText(lists.getReturnTime());
                                    tv_return_account.setText(lists.getReturnAmt());
                                }

                                imageViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        if(pictureList.size()>0) {
                                            AppHelper.showIssueDetailDialog(mActivity, pictureList, position);
                                        }
                                    }
                                });
                            }
                        } else {
                            AppHelper.showMsg(mActivity, infoListModel.getMessage());
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

