package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylOrderDetailsAdapter;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.HylOrderDetailModel;
import com.barter.hyl.app.model.HylReturnGoodModel;
import com.barter.hyl.app.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王文博} on 2019/8/27
 * 只显示退货商品详情
 */
public class HylReturnGoodDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_cancel_return)
    ImageView iv_cancel_return;//撤销退货
    @BindView(R.id.tv_return_reason)
    TextView tv_return_reason;//退货原因
    @BindView(R.id.tv_return_content)
    TextView tv_return_content;//备注
    @BindView(R.id.tv_return_order)
    TextView tv_return_order;//订单编号
    @BindView(R.id.tv_apply_name)
    TextView tv_apply_name;//订单申请人
    @BindView(R.id.tv_apply_time)
    TextView tv_apply_time;//订单申请时间
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rl_accept_time)
    RelativeLayout rl_accept_time;
    @BindView(R.id.tv_accept_time)
    TextView tv_accept_time;//审核时间
    @BindView(R.id.tv_return_money)
    TextView tv_return_money;
    @BindView(R.id.rl_memo)
    RelativeLayout rl_memo;
    @BindView(R.id.tv_return_title)
    TextView tv_return_title;
    //金额去向
    @BindView(R.id.rl_money_direction)
    RelativeLayout rl_money_direction;
    @BindView(R.id.tv_return_direction)
    TextView tv_return_direction;
    @BindView(R.id.tv_return_memo)
    TextView tv_return_memo; //备注
    @BindView(R.id.rl_order)
    RelativeLayout rl_order;
    HylOrderDetailsAdapter adapter;
    String returnMainId;
    String orderId = "";
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        returnMainId = getIntent().getStringExtra("returnMainId");
        orderId = getIntent().getStringExtra("orderId");

        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_return_good_detail_hyl);
    }

    @Override
    public void setViewData() {
        tv_title.setText("售后详情");
        adapter = new HylOrderDetailsAdapter(R.layout.item_order_detail_hyl,list);
        //自营
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getReturnDetail();
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
        iv_cancel_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //撤销申请
                cancelReturnOrder();
            }
        });

        rl_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity,HylOrderDetailActivity.class);
                intent.putExtra("orderId", hylOrderDetailModels.getData().getOrderId());
                intent.putExtra("returnMainId",returnMainId);
                startActivity(intent);
            }
        });
    }

    /**
     * 撤销申请
     */
    private void cancelReturnOrder() {
        OrderApi.cancelApply(mContext,returnMainId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylLoginModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylLoginModel baseModel) {
                        if (baseModel.success) {
                            iv_cancel_return.setVisibility(View.GONE);
                            AppHelper.showMsg(mContext, "撤销成功");
                            getReturnDetail();
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

    /**
     * 获取退货商品
     */
    HylReturnGoodModel.DataBean data;
    HylOrderDetailModel hylOrderDetailModels;
    private List<HylOrderDetailModel.DataBean.ProdsBean> list = new ArrayList<>();
    private void getReturnDetail() {
        OrderApi.orderDetail(mContext,orderId,returnMainId,11)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylOrderDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylOrderDetailModel hylOrderDetailModel) {
                        if (hylOrderDetailModel.getCode()==1) {
                            if (hylOrderDetailModel != null) {
                                hylOrderDetailModels = hylOrderDetailModel;
                                setText(hylOrderDetailModel.getData());
                                list.clear();
                                list.addAll(hylOrderDetailModel.getData().getProds());
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylOrderDetailModel.getMessage());
                        }
                    }
                });
    }

    private void setText(HylOrderDetailModel.DataBean data) {
        tv_return_money.setText(data.getTotalAmt());
        tv_return_title.setText(data.getOrderStatusStr());

        if(data.getCheckTime()!=null) {
            rl_accept_time.setVisibility(View.VISIBLE);
        }else {
            rl_accept_time.setVisibility(View.GONE);
        }
        if(data.getCheckStatus()==0) {
            //审核
            tv_return_title.setText(data.getOrderStatusStr());
            iv_cancel_return.setVisibility(View.VISIBLE);
        }else if(data.getCheckStatus()==1) {
            //成功
            iv_cancel_return.setVisibility(View.GONE);
            tv_return_title.setText(data.getOrderStatusStr());
        }else if(data.getCheckStatus()==4){
            //已撤销
            iv_cancel_return.setVisibility(View.GONE);
            tv_return_title.setText(data.getOrderStatusStr());
        }else {
            //失败
            tv_return_title.setText(data.getOrderStatusStr());
            iv_cancel_return.setVisibility(View.GONE);
        }


        if(data.getCheckStatus()==1) {
            //成功
            if(data.isPayFlag()) {
                //履约
                tv_return_direction.setText(data.getPayChannel());
                rl_money_direction.setVisibility(View.VISIBLE);

            }else {
                //逾期
                rl_money_direction.setVisibility(View.GONE);
            }


        }else if(data.getCheckStatus()==0){
            if(data.isPayFlag()) {
                //履约
                tv_return_direction.setText(data.getPayChannel());
                rl_money_direction.setVisibility(View.VISIBLE);
            }else {
                //逾期
                rl_money_direction.setVisibility(View.GONE);
            }
        }else {
            //失败
            if(data.isPayFlag()) {
                //履约
                tv_return_direction.setText(data.getPayChannel());
                rl_money_direction.setVisibility(View.VISIBLE);

            }else {
                //逾期
                rl_money_direction.setVisibility(View.GONE);
            }
        }

        if(!data.getTitle().equals("")||data.getTitle()!=null) {
            tv_return_content.setText(data.getTitle());
        }

        if(!data.getMemo().equals("")||data.getMemo()!=null) {
            tv_return_memo.setText(data.getMemo());
        }

        tv_return_reason.setText(data.getReturnType());
        tv_return_order.setText(data.getOrderId());
        tv_apply_name.setText(data.getApplier());
        tv_apply_time.setText(data.getApplyTime());
        tv_accept_time.setText(data.getCheckTime());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

        }
    }
}
