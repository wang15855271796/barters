package com.barter.hyl.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    @BindView(R.id.tv_cancel_return)
    TextView tv_cancel_return;//撤销退货
    @BindView(R.id.tv_return_reason)
    TextView tv_return_reason;//退货原因
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
    @BindView(R.id.tvOrderContent1)
    TextView tvOrderContent1;
    @BindView(R.id.tvOrderContent2)
    TextView tvOrderContent2;
    @BindView(R.id.tvOrderContent3)
    TextView tvOrderContent3;
    @BindView(R.id.ll_progress)
    LinearLayout ll_progress;
    @BindView(R.id.ll_progress2)
    LinearLayout ll_progress2;
    @BindView(R.id.ll_progress3)
    LinearLayout ll_progress3;
    @BindView(R.id.rl_return_success1)
    RelativeLayout rl_return_success1;
    @BindView(R.id.rl_return_success2)
    RelativeLayout rl_return_success2;
    @BindView(R.id.rl_return_success3)
    RelativeLayout rl_return_success3;
    @BindView(R.id.rl_return_time)
    RelativeLayout rl_return_time;
    @BindView(R.id.tv_return_time)
    TextView tv_return_time;
    @BindView(R.id.rl_check_time)
    RelativeLayout rl_check_time;
    @BindView(R.id.tv_check_time)
    TextView tv_check_time;
    @BindView(R.id.rl_apply_time)
    RelativeLayout rl_apply_time;
    @BindView(R.id.rl_check_time2)
    RelativeLayout rl_check_time2;
    @BindView(R.id.tv_check_time2)
    TextView tv_check_time2;
    @BindView(R.id.rl_apply_time2)
    RelativeLayout rl_apply_time2;
    @BindView(R.id.tv_apply_time2)
    TextView tv_apply_time2;
    @BindView(R.id.rl_check_time3)
    RelativeLayout rl_check_time3;
    @BindView(R.id.tv_check_time3)
    TextView tv_check_time3;
    @BindView(R.id.rl_apply_time3)
    RelativeLayout rl_apply_time3;
    @BindView(R.id.tv_apply_time3)
    TextView tv_apply_time3;
    @BindView(R.id.rl_apply_time4)
    RelativeLayout rl_apply_time4;
    @BindView(R.id.tv_apply_time4)
    TextView tv_apply_time4;
    @BindView(R.id.ll_progress4)
    LinearLayout ll_progress4;
    @BindView(R.id.rl_return_success4)
    RelativeLayout rl_return_success4;
    @BindView(R.id.tv_cancel_time)
    TextView tv_cancel_time;
    @BindView(R.id.tv_apply_time5)
    TextView tv_apply_time5;
    @BindView(R.id.tv_amount_spec)
    TextView tv_amount_spec;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.ll_root)
    LinearLayout ll_root;
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
        setTranslucentStatus();
        adapter = new HylOrderDetailsAdapter(R.layout.item_order_detail_hyl,list);
        //自营
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getReturnDetail();
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
        tv_cancel_return.setOnClickListener(new View.OnClickListener() {
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
                            tv_cancel_return.setVisibility(View.GONE);
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
        tv_return_money.setText("￥"+data.getTotalAmt());
        tv_return_title.setText(data.getOrderStatusStr());

//        if(data.getCheckTime()!=null) {
//            rl_accept_time.setVisibility(View.VISIBLE);
//        }else {
//            rl_accept_time.setVisibility(View.GONE);
//        }
//        if(data.getCheckStatus()==0) {
//            //审核
//            tv_return_title.setText(data.getOrderStatusStr());
//            tv_cancel_return.setVisibility(View.VISIBLE);
//        }else if(data.getCheckStatus()==1) {
//            //成功
//            tv_cancel_return.setVisibility(View.GONE);
//            tv_return_title.setText(data.getOrderStatusStr());
//        }else if(data.getCheckStatus()==4){
//            //已撤销
//            tv_cancel_return.setVisibility(View.GONE);
//            tv_return_title.setText(data.getOrderStatusStr());
//        }else {
//            //失败
//            tv_return_title.setText(data.getOrderStatusStr());
//            tv_cancel_return.setVisibility(View.GONE);
//        }

        if (data.getCheckStatus() == 0) {
            //待审核
            tv_cancel_return.setVisibility(View.VISIBLE);
            tvOrderContent1.setVisibility(View.GONE);
            tvOrderContent2.setVisibility(View.GONE);
            tvOrderContent3.setVisibility(View.GONE);
//            if(data.getTitle().equals("")) {
//                tvOrderContent3.setVisibility(View.GONE);
//            }else {
//                tvOrderContent3.setVisibility(View.VISIBLE);
//                tvOrderContent3.setText(data.getTitle());
//            }
            ll_root.setBackgroundResource(R.mipmap.bg_return4);
            tv_apply_time4.setText(data.getApplyTime());
            rl_return_success1.setVisibility(View.GONE);
            rl_return_success2.setVisibility(View.GONE);
            rl_return_success3.setVisibility(View.GONE);
            rl_apply_time4.setVisibility(View.VISIBLE);

            ll_progress.setVisibility(View.GONE);
            ll_progress2.setVisibility(View.GONE);
            ll_progress3.setVisibility(View.GONE);
            tv1.setText("预计售后金额");
            tv_amount_spec.setVisibility(View.VISIBLE);
        } else if (data.getCheckStatus() == 1) {
            //成功
            tvOrderContent2.setVisibility(View.GONE);
            tvOrderContent1.setVisibility(View.GONE);
            if(data.getTitle().equals("") || TextUtils.isEmpty(data.getTitle())) {
                tvOrderContent3.setVisibility(View.GONE);
            }else {
                tvOrderContent3.setVisibility(View.VISIBLE);
                tvOrderContent3.setText(data.getTitle());
            }
            tv_cancel_return.setVisibility(View.GONE);
            tv1.setText("售后金额");
            tv_amount_spec.setVisibility(View.GONE);
            if(data.isBankReturnFlag()) {
                tv_return_time.setText(data.getBankReturnDate());
                tv_apply_time.setText(data.getApplyTime());
                tv_check_time.setText(data.getCheckTime());
                rl_return_success1.setVisibility(View.VISIBLE);
                rl_return_success2.setVisibility(View.GONE);
                rl_return_success3.setVisibility(View.GONE);
                rl_apply_time4.setVisibility(View.GONE);

                ll_progress.setVisibility(View.VISIBLE);
                ll_progress2.setVisibility(View.GONE);
                ll_progress3.setVisibility(View.GONE);
                ll_root.setBackgroundResource(R.mipmap.bg_return1);

                if(data.getTitle().equals("") || TextUtils.isEmpty(data.getTitle())) {
                    tvOrderContent1.setVisibility(View.GONE);
                }else {
                    tvOrderContent1.setVisibility(View.VISIBLE);
                    tvOrderContent1.setText(data.getTitle());
                }
            }else {
                rl_return_success1.setVisibility(View.GONE);
                rl_return_success2.setVisibility(View.VISIBLE);
                rl_return_success3.setVisibility(View.GONE);
                rl_apply_time4.setVisibility(View.GONE);

                tv_apply_time2.setText(data.getApplyTime());
                tv_check_time2.setText(data.getCheckTime());

                ll_progress.setVisibility(View.GONE);
                ll_progress2.setVisibility(View.VISIBLE);
                ll_progress3.setVisibility(View.GONE);
                ll_root.setBackgroundResource(R.mipmap.bg_return2);

                if(data.getTitle().equals("") || TextUtils.isEmpty(data.getTitle())) {
                    tvOrderContent3.setVisibility(View.GONE);
                }else {
                    tvOrderContent3.setVisibility(View.VISIBLE);
                    tvOrderContent3.setText(data.getTitle());
                }
            }

        } else if (data.getCheckStatus() == 2) {
            //失败
            tv_cancel_return.setVisibility(View.GONE);
            tvOrderContent1.setVisibility(View.GONE);
            tvOrderContent3.setVisibility(View.GONE);
            if(data.getTitle().equals("") || TextUtils.isEmpty(data.getTitle())) {
                tvOrderContent2.setVisibility(View.GONE);
            }else {
                tvOrderContent2.setVisibility(View.VISIBLE);
                tvOrderContent2.setText(data.getTitle());
            }

            tv_check_time3.setText(data.getCheckTime());
            tv_apply_time3.setText(data.getApplyTime());
            rl_return_success1.setVisibility(View.GONE);
            rl_return_success2.setVisibility(View.GONE);
            rl_return_success3.setVisibility(View.VISIBLE);
            rl_apply_time4.setVisibility(View.GONE);

            ll_progress.setVisibility(View.GONE);
            ll_progress2.setVisibility(View.GONE);
            ll_progress3.setVisibility(View.VISIBLE);
            tv1.setText("预计售后金额");
            tv_amount_spec.setVisibility(View.VISIBLE);
            ll_root.setBackgroundResource(R.mipmap.bg_return3);
        } else if (data.getCheckStatus() == 4) {
            //已撤销
            ll_progress4.setVisibility(View.VISIBLE);
            rl_return_success4.setVisibility(View.VISIBLE);

            ll_progress.setVisibility(View.GONE);
            ll_progress2.setVisibility(View.GONE);
            ll_progress3.setVisibility(View.GONE);

            tvOrderContent1.setVisibility(View.GONE);
            tvOrderContent2.setVisibility(View.GONE);

            rl_return_success1.setVisibility(View.GONE);
            rl_return_success2.setVisibility(View.GONE);
            rl_return_success3.setVisibility(View.GONE);

            rl_apply_time4.setVisibility(View.GONE);
            tv_cancel_time.setText(data.getCancelTime());
            tv_apply_time5.setText(data.getApplyTime());
            tv1.setText("预计售后金额");
            tv_amount_spec.setVisibility(View.VISIBLE);
            if(data.getTitle().equals("") || TextUtils.isEmpty(data.getTitle())) {
                tvOrderContent3.setVisibility(View.GONE);
            }else {
                tvOrderContent3.setVisibility(View.VISIBLE);
                tvOrderContent3.setText(data.getTitle());
            }

            tv_cancel_return.setVisibility(View.GONE);
            ll_root.setBackgroundResource(R.mipmap.bg_return5);
        }

        tv_return_title.setText(data.getOrderStatusStr());

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

        if(!data.getMemo().equals("")) {
            tv_return_memo.setText(data.getMemo());
            rl_memo.setVisibility(View.VISIBLE);
        }else {
            rl_memo.setVisibility(View.GONE);
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

    protected void setTranslucentStatus() {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
