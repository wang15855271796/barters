package com.barter.hyl.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.CouponDetailAdapter;
import com.barter.hyl.app.api.MyInfoApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.dialog.CouponDetailDialog;
import com.barter.hyl.app.model.HylMyCouponDetailModel;
import com.barter.hyl.app.model.HylMyCouponModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CouponDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    int pageNum = 1;
    int pageSize = 10;
    String poolNo;
    String giftName;
    CouponDetailAdapter couponDetailAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        poolNo = getIntent().getStringExtra("poolNo");
        giftName = getIntent().getStringExtra("giftName");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_coupon_detail);
    }

    List<HylMyCouponDetailModel.DataBean.ListBean> list = new ArrayList<>();
    @Override
    public void setViewData() {
        tv_title.setText("优惠券详情");
        tv_desc.setText("以下商品可使用"+giftName+"优惠券");
        smart.autoRefresh();
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum=1;
                list.clear();
                getCouponDetail();
                smart.finishRefresh();
            }
        });

        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (data!=null) {
                    if (data.getHasNextPage()) {
                        pageNum++;
                        getCouponDetail();
                        refreshLayout.finishLoadMore();
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        couponDetailAdapter = new CouponDetailAdapter(R.layout.item_search_hyl1, list, new CouponDetailAdapter.OnAddClickListener() {
            @Override
            public void onAddClick(HylMyCouponDetailModel.DataBean.ListBean item) {
                CouponDetailDialog couponDetailDialog = new CouponDetailDialog(CouponDetailActivity.this,item);
                couponDetailDialog.show();
            }
        });

        couponDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity,HylCommonGoodsActivity.class);
                intent.putExtra("mainId",list.get(position).getMainId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(couponDetailAdapter);

    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
        tv_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_search:
                if(et_search.getText().toString().trim().equals("")) {
                    ToastUtil.showSuccessMsg(mContext,"请输入搜索商品");
                    return;
                }
                list.clear();
                pageNum = 1;
                searchKey = et_search.getText().toString();
                getCouponDetail();
                hintKbTwo();
                break;
        }
    }

    //隐藏软键盘
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    String searchKey = "";
    HylMyCouponDetailModel.DataBean data;
    private void getCouponDetail() {
        MyInfoApi.getMyCouponDetail(mActivity,searchKey,poolNo,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylMyCouponDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HylMyCouponDetailModel info) {
                        if (info.getCode()==1) {
                            if(info.getData()!=null) {
                                data = info.getData();
                                if(info.getData().getList()!=null && info.getData().getList().size()> 0) {
                                    list.addAll(info.getData().getList());
                                    couponDetailAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            AppHelper.showMsg(mActivity, info.getMessage());
                        }


                    }
                });
    }

}
