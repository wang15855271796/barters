package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.BillConnectionAdapter;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.model.BillDetailModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/10/25
 */
public class BillDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_bill)
    TextView tv_bill;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.tv_jifen)
    TextView tv_jifen;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_order)
    TextView tv_order;
    @BindView(R.id.rl_order)
    RelativeLayout rl_order;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_flag)
    ImageView iv_flag;
    @BindView(R.id.ll_rent)
    LinearLayout ll_rent;
    @BindView(R.id.ll_pay)
    LinearLayout ll_pay;
    @BindView(R.id.ll_order)
    LinearLayout ll_order;
    @BindView(R.id.tv_style)
    TextView tv_style;
    @BindView(R.id.tv_style_desc)
    TextView tv_style_desc;
    @BindView(R.id.tv_time1)
    TextView tv_time1;
    @BindView(R.id.tv_time_desc)
    TextView tv_time_desc;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    int id;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        id = getIntent().getIntExtra("id", 0);
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.bill_detail_activity);
    }

    BillConnectionAdapter billConnectionAdapter;
    @Override
    public void setViewData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        billConnectionAdapter = new BillConnectionAdapter(R.layout.item_bill,orders);
        recyclerView.setAdapter(billConnectionAdapter);
        getBill(id);

    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
        rl_order.setOnClickListener(this);
    }



    /**
     * 订单详情
     * @param id
     */
    List<BillDetailModel.DataBean.OrdersBean> orders = new ArrayList<>();
    BillDetailModel.DataBean data;
    private void getBill(int id) {
        OrderApi.getBillDetail(mContext,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BillDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BillDetailModel billDetailModel) {
                        if (billDetailModel.getCode()==1) {
                            if(billDetailModel.getData()!=null) {
                                data = billDetailModel.getData();
                                tv_bill.setText(data.getRecordTypeName());
                                tv_amount.setText(data.getAmount());
                                tv_pay.setText(data.getPayChannel());
                                tv_jifen.setText(data.getPoint()+"");
                                tv_time.setText(data.getDateTime());

                                Glide.with(mContext).load(data.getIconUrl()).into(iv_flag);
                                if(billDetailModel.getData().getRecordType()==13) {
                                    tv_style_desc.setText("支付方式");
                                    tv_time_desc.setText("支付时间");
                                    ll_rent.setVisibility(View.VISIBLE);
                                    ll_pay.setVisibility(View.GONE);
                                    tv_style.setText(data.getPayChannel());
                                    tv_time1.setText(data.getDateTime());
                                    ll_order.setVisibility(View.GONE);
                                }else if(billDetailModel.getData().getRecordType()==14) {
                                    //转让招租 退款
                                    tv_style_desc.setText("退款方式");
                                    tv_time_desc.setText("退款时间");
                                    ll_order.setVisibility(View.GONE);
                                    ll_rent.setVisibility(View.VISIBLE);
                                    ll_pay.setVisibility(View.GONE);
                                    tv_style.setText(data.getPayChannel());
                                    tv_time1.setText(data.getDateTime());
                                }else {
                                    ll_order.setVisibility(View.VISIBLE);
                                    ll_rent.setVisibility(View.GONE);
                                    ll_pay.setVisibility(View.VISIBLE);
                                }
                                if(data.getReturnMainId()!=null&&data.getReturnMainId()!="") {
                                    tv_order.setText(data.getReturnMainId());
                                    rl_order.setVisibility(View.VISIBLE);
                                }else {
                                    rl_order.setVisibility(View.GONE);
                                }
                                orders.addAll(data.getOrders());
                                billConnectionAdapter.notifyDataSetChanged();
                            }

                        } else {
                            AppHelper.showMsg(mContext, billDetailModel.getMessage());
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

            case R.id.rl_order:
                Intent intent = new Intent(mContext,HylReturnGoodDetailActivity.class);
                intent.putExtra("returnMainId",data.getReturnMainId());
                startActivity(intent);
                break;
        }
    }
}
