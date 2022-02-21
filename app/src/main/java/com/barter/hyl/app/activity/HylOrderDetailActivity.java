package com.barter.hyl.app.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylOrderDetailAdapter;
import com.barter.hyl.app.adapter.HylReturnAdapter;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.event.CartListHylEvent;
import com.barter.hyl.app.event.CartNumHylEvent;
import com.barter.hyl.app.fragment.HylMyPaymentDialogFragment;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.HylOrderDetailModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.Snap;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/9/2
 */
public class HylOrderDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv_return)
    RecyclerView rv_return;
    // 非退货订单layout 头部
    @BindView(R.id.orderLinearLayout)
    LinearLayout orderLinearLayout;
    //支付方式
    @BindView(R.id.ll_payWay)
    LinearLayout ll_payWay;
    //底部支付信息
    @BindView(R.id.ll_order_info)
    LinearLayout ll_order_info;
    @BindView(R.id.tv_beizhu)
    TextView tv_beizhu;
    @BindView(R.id.tvOrderTitle)
    TextView tvOrderTitle; //订单类型
    @BindView(R.id.tv_statReturn)
    TextView tv_statReturn;
    @BindView(R.id.tvTimeTipe)
    TextView tvTimeTipe;
    @BindView(R.id.orderTimerView)
    Snap orderTimerView; //倒计时
    @BindView(R.id.tvOrderContent)
    TextView tvOrderContent; //订单内容
    @BindView(R.id.ll_wait_receive)
    LinearLayout ll_wait_receive; //三个按钮（退货按钮 评价按钮 在次购买按钮）
    @BindView(R.id.buttonReturnGoods)
    TextView buttonReturnGoods; // 退货按钮
    @BindView(R.id.buttonEvaluate)
    TextView buttonEvaluate; //评价按钮
    @BindView(R.id.tv_copy2)
    TextView tv_copy2; // 在次购买按钮
    @BindView(R.id.twoButtonLayout)
    LinearLayout twoButtonLayout; //二个按钮（取消订单  去支付）
    @BindView(R.id.buttonCancelOrder)
    TextView buttonCancelOrder; // 取消订单
    @BindView(R.id.buttonGOPay)
    TextView buttonGOPay; // 去支付

    //退货订单 layout 头部
    @BindView(R.id.ReturnGoodsLinearLayout)
    LinearLayout ReturnGoodsLinearLayout;
    @BindView(R.id.ReturnGoodsTitle)
    TextView ReturnGoodsTitle; //退货类型
    @BindView(R.id.ReturnGoodsContent)
    TextView ReturnGoodsContent; //退货内容
    @BindView(R.id.tv_full_price)
    TextView tv_full_price;
    @BindView(R.id.ll_full_activies)
    LinearLayout ll_full_activies;
    @BindView(R.id.tvNewOrderCommodityAmount)
    TextView tvNewOrderCommodityAmount; //商品金额
    @BindView(R.id.tvNewOrderDistributionFeePrice)
    TextView tvNewOrderDistributionFeePrice; // 配送费
    @BindView(R.id.tvNewOrderCoupons)
    TextView tvNewOrderCoupons;//优惠卷
    @BindView(R.id.ll_beizhu_desc)
    LinearLayout ll_beizhu_desc;
    //以下是每个订单详情都有的订单信息
    @BindView(R.id.tvNewOrderAddresseeName)
    TextView tvNewOrderAddresseeName;
    @BindView(R.id.tvNewOrderAddress)
    TextView tvNewOrderAddress;
    //关联id
    //下单时间
    @BindView(R.id.tv_order_time)
    TextView tv_order_time;
    //发货时间
    @BindView(R.id.ll_send_time)
    LinearLayout ll_send_time;
    //备注
    @BindView(R.id.tv_memo)
    TextView tv_memo;
    //发货时间
    @BindView(R.id.tv_send_time)
    TextView tv_send_time;
    //收货时间
    @BindView(R.id.tv_receive_time)
    TextView tv_receive_time;
    //收货时间
    @BindView(R.id.ll_receive_time)
    LinearLayout ll_receive_time;
    //审核通过时间
    @BindView(R.id.tv_check_time)
    TextView tv_check_time;
    //最迟应付时间
    @BindView(R.id.tv_payable_time)
    TextView tv_payable_time;
    //支付方式
    @BindView(R.id.tv_payWay)
    TextView tv_payWay;
    //订单金额
    @BindView(R.id.tv_order_amount)
    TextView tv_order_amount;
    //应付金额
    @BindView(R.id.tv_payable_amount)
    TextView tv_payable_amount;
    //删除订单
    @BindView(R.id.ll_delete_order)
    LinearLayout ll_delete_order;
    //审核通过时间
    @BindView(R.id.ll_check_time)
    RelativeLayout ll_check_time;
    //最迟应付时间
    @BindView(R.id.rl_payable_time)
    RelativeLayout rl_payable_time;
    //下单时间
    @BindView(R.id.ll_order_time)
    LinearLayout ll_order_time;
    @BindView(R.id.rl_should_pay)
    RelativeLayout rl_should_pay;
    @BindView(R.id.tv_pay)
    TextView tv_pay;

    @BindView(R.id.tv_copy3)
    TextView tv_copy3;
    @BindView(R.id.tv_eval)
    TextView tv_eval;
    @BindView(R.id.tv_after_serve)
    TextView tv_after_serve;
    //下单时间
//    @BindView(R.id.ll_order_time)
//    RelativeLayout ll_order_time;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_copy6)
    TextView tv_copy6;
    @BindView(R.id.iv_operate_pic)
    ImageView iv_operate_pic;
    private HylOrderDetailAdapter adapter;
    private List<HylOrderDetailModel.DataBean.ProdsBean> list = new ArrayList<>();
    private String orderId;
    private int orderStatusRequest;
    private Dialog mDialog;
    HylOrderDetailModel.DataBean data;
    @BindView(R.id.tv_deduct_desc)
    TextView tvDeductDsc;
    @BindView(R.id.linearLayout_shipped)
    LinearLayout mLinearLayoutShipped;
    @BindView(R.id.linearLayout_evalute)
    LinearLayout mLinearLayoutEvalute;
    //待评价
    @BindView(R.id.ll_wait_eval)
    LinearLayout ll_wait_eval;
    @BindView(R.id.linearLayout_get_order)
    LinearLayout mLinearLayoutReciverOrder;
    //复制订单
    @BindView(R.id.tv_copy4)
    TextView tv_copy4;
    @BindView(R.id.tv_copy5)
    TextView tv_copy5;
    @BindView(R.id.tv_copy1)
    TextView tv_copy1;
    @BindView(R.id.tv_confirm_order)
    TextView mTvConfirmOrder;
    @BindView(R.id.tv_order_return)
    TextView mTvOrderReturn;
    @BindView(R.id.buttonReturnGood_two)
    TextView buttonReturnGood_two;
    @BindView(R.id.tv_evaluate)
    TextView tv_evaluate;
    @BindView(R.id.tv_order_num)
    TextView tv_order_num;
    private int returnCode;
    private boolean isShowed = false;
    private String account;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_copy)
    TextView tv_copy;
    @BindView(R.id.ll_cancel_order)
    LinearLayout ll_cancel_order;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_return_goods)
    LinearLayout ll_return_goods;
    @BindView(R.id.ll_pay_time)
    LinearLayout ll_pay_time;
    @BindView(R.id.tv_pay_time)
    TextView tv_pay_time;
    @BindView(R.id.tv_payed)
    TextView tv_payed;
    @BindView(R.id.tv_remain_pay)
    TextView tv_remain_pay;
    @BindView(R.id.rl_remain_pay)
    RelativeLayout rl_remain_pay;
    @BindView(R.id.rl_payed)
    RelativeLayout rl_payed;
    String returnMainId = "";
    int status = 0;
    HylReturnAdapter hylReturnAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra("orderId");
        returnMainId = getIntent().getStringExtra("returnMainId");
        status = getIntent().getIntExtra("status",0);
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_order_detail_hyl);
    }


    @Override
    public void setViewData() {
        tv_title.setText("订单详情");
        tv_order_num.setText("订单编号："+orderId);
        //未支付，15分钟后跳转到取消订单
//        orderTimerView.setTimeout(new Snap.Timeout() {
//            @Override
//            public void getStop() {
//                if (orderStatusRequest == 1) {
//                    cancelOrder(orderId);
//                }
//            }
//        });

        adapter = new HylOrderDetailAdapter(R.layout.order_detail,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        rv_return.setLayoutManager(new LinearLayoutManager(mContext));
        hylReturnAdapter = new HylReturnAdapter(R.layout.item_return_hyl,returnOrdersList);
        rv_return.setAdapter(hylReturnAdapter);

        hylReturnAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext,HylReturnGoodDetailActivity.class);
                intent.putExtra("orderId",orderId);
                intent.putExtra("returnMainId",returnOrdersList.get(position).getReturnMainId());
                startActivity(intent);
            }
        });

//        list.clear();

    }


    @Override
    public void setClickListener() {
        tv_eval.setOnClickListener(this);
        tv_after_serve.setOnClickListener(this);
        tv_copy3.setOnClickListener(this);
        tv_copy.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        buttonCancelOrder.setOnClickListener(this);
        buttonGOPay.setOnClickListener(this);
        buttonReturnGoods.setOnClickListener(this);
        buttonEvaluate.setOnClickListener(this);
        tv_copy2.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_copy6.setOnClickListener(this);
        tv_copy4.setOnClickListener(this);
        tv_copy5.setOnClickListener(this);
        tv_copy1.setOnClickListener(this);
        mTvConfirmOrder.setOnClickListener(this);
        mTvOrderReturn.setOnClickListener(this);
        buttonReturnGood_two.setOnClickListener(this);
        tv_evaluate.setOnClickListener(this);
        ll_cancel_order.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getOrderDetail(orderId,returnMainId,status);
    }

    HylOrderDetailModel hylOrderDetailModels;

    /**
     * 获取订单详情
     * @param orderId
     * @param returnMainId
     * @param status
     */
    List<HylOrderDetailModel.DataBean.ReturnOrdersBean> returnOrdersList = new ArrayList<>();
    private void getOrderDetail(String orderId,String returnMainId,int status) {
        OrderApi.orderDetail(mContext,orderId,returnMainId,status)
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
                                setText(hylOrderDetailModel);
                                list.clear();
                                list.addAll(hylOrderDetailModel.getData().getProds());

                                returnOrdersList.clear();
                                if(hylOrderDetailModel.getData().getReturnOrders()!=null&& hylOrderDetailModel.getData().getReturnOrders().size()>0) {
                                    returnOrdersList.addAll(hylOrderDetailModel.getData().getReturnOrders());
                                }

                                adapter.notifyDataSetChanged();
                                hylReturnAdapter.notifyDataSetChanged();

                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylOrderDetailModel.getMessage());
                        }
                    }
                });
    }

    //设置文字
    private void setText(HylOrderDetailModel info) {
        data = info.getData();
        ReturnGoodsTitle.setText(data.getOrderStatusStr());
        tvOrderTitle.setText(data.getOrderStatusStr());
        orderStatusRequest = data.getOrderStatus();
        setViewShow();
        tv_payWay.setText(data.getPayChannel());
        tv_order_amount.setText("￥"+data.getTotalAmt());
        tv_payable_amount.setText("￥"+data.getShouldPayAmt());
        tv_amount.setText(data.getProdNum()+"");
        tvNewOrderCommodityAmount.setText("￥" + data.getProdAmt());
        tvNewOrderDistributionFeePrice.setText("￥" + data.getDeliveryFee());

        if(info.getData().getTitle()!=null) {
            tvOrderContent.setVisibility(View.VISIBLE);
            tvOrderContent.setText(info.getData().getTitle());
        }else {
            tvOrderContent.setVisibility(View.GONE);
        }

        //优惠券金额
        tvNewOrderCoupons.setText("￥"+data.getGiftAmt());
        //优惠券描述
        tvDeductDsc.setText(data.getGiftName());
        tv_full_price.setText(data.getDeductAmt());
        tvNewOrderAddresseeName.setText(data.getAddressVO().getUserName());
        tvNewOrderAddress.setText(data.getAddressVO().getProvinceName() + data.getAddressVO().getCityName()
                + data.getAddressVO().getAreaName() + data.getAddressVO().getDetailAddress());

        if(data.getOrderType()==1) {
            //信用订单
            if(data.isPayFlag()) {
                rl_should_pay.setVisibility(View.VISIBLE);
                tv_pay.setVisibility(View.GONE);
            }else {
                rl_should_pay.setVisibility(View.GONE);
                tv_pay.setVisibility(View.VISIBLE);
            }
        }else {
            //一般订单
            tv_pay.setVisibility(View.GONE);
            rl_should_pay.setVisibility(View.GONE);
        }


        if(data.getExpectPayTime()!=null&&!data.getExpectPayTime().equals("")) {
            tv_payable_time.setText(data.getExpectPayTime());
            rl_payable_time.setVisibility(View.VISIBLE);
        }else {
            rl_payable_time.setVisibility(View.GONE);
        }

        if(data.getPayTime()!=null&&!TextUtils.isEmpty(data.getPayTime())) {
            ll_pay_time.setVisibility(View.VISIBLE);
            tv_pay_time.setText(data.getPayTime());
        }else {
            ll_pay_time.setVisibility(View.GONE);
        }
        if(data.getOrderTime()!=null&&data.getOrderTime()!="") {
            tv_order_time.setText(data.getOrderTime());
            ll_order_time.setVisibility(View.VISIBLE);
        }else {
            ll_order_time.setVisibility(View.GONE);
        }

        if(data.getOrderType()==0) {
            //普通
            ll_check_time.setVisibility(View.GONE);
            rl_payable_time.setVisibility(View.GONE);
            tv_pay.setVisibility(View.GONE);
            rl_should_pay.setVisibility(View.GONE);
        }else {
            //信用
            if(data.isPayFlag()) {
                rl_should_pay.setVisibility(View.GONE);
                tv_pay.setVisibility(View.GONE);
            }else {
                rl_should_pay.setVisibility(View.VISIBLE);
                tv_pay.setVisibility(View.VISIBLE);
            }
        }
        if(data.getCheckTime()!=null&&!data.getCheckTime().equals("")) {
            tv_check_time.setText(data.getCheckTime());
            ll_check_time.setVisibility(View.VISIBLE);
        }else {
            ll_check_time.setVisibility(View.GONE);
        }

        if(data.getMemo()!=""&&!TextUtils.isEmpty(data.getMemo())) {
            tv_memo.setText(data.getMemo());
        }

        if (!TextUtils.isEmpty(data.getSendTime())&&data.getSendTime()!="") {
            tv_send_time.setText(data.getSendTime());
            ll_send_time.setVisibility(View.VISIBLE);
        } else {
            ll_send_time.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(data.getReceiveTime())&&data.getReceiveTime()!="") {
            tv_receive_time.setText(data.getReceiveTime());
            ll_receive_time.setVisibility(View.VISIBLE);
        } else {
            ll_receive_time.setVisibility(View.GONE);
        }

        //订单状态
        if(data.getOrderStatus()==1||data.getOrderStatus()==7) {
            ll_payWay.setVisibility(View.GONE);
        }else {
            ll_payWay.setVisibility(View.VISIBLE);
        }

        if(data.getOfflinePay()) {
            tv_payed.setText(data.getOfflinePayAmt());
            tv_remain_pay.setText(data.getOfflineRemainAmt());
            rl_payed.setVisibility(View.VISIBLE);
            rl_remain_pay.setVisibility(View.VISIBLE);
        }else {
            rl_payed.setVisibility(View.GONE);
            rl_remain_pay.setVisibility(View.GONE);
        }

        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HylMyPaymentDialogFragment paymentDialogFragment = new HylMyPaymentDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("orderId", orderId);
                bundle.putString("id",data.getId()+"");
                bundle.putString("memo", "");
                bundle.putString("total",data.getShouldPayAmt());
                bundle.putString("giftNo","");
                bundle.putString("orderType",data.getOrderType()+"");
                bundle.putString("orderDeliveryType","0");
                paymentDialogFragment.setArguments(bundle);
                paymentDialogFragment.show(getSupportFragmentManager(),"paymentFragment");
                paymentDialogFragment.setCancelable(false);
            }
        });

        if(data.getReturnOrders()!=null) {
            ll_return_goods.setVisibility(View.VISIBLE);
        }else {
            ll_return_goods.setVisibility(View.GONE);
        }
    }

    // 不同的状态显示不同的layout

    /**
     * 订单类型：("待付款", 1), ("待发货-待接收", 2), ("待发货-已接收", 14), ("待收货", 3),
     * ("已完成", 4), ("待评价", 5), ("已评价", 6), ("已取消", 7), ("退货", 11),
     * （待审核15）
     * @param
     */
    private void setViewShow() {
        //待审核
        ll_cancel_order.setVisibility(orderStatusRequest == 15 ? View.VISIBLE : View.GONE);
        //已评价
        mLinearLayoutEvalute.setVisibility(orderStatusRequest == 6 ? View.VISIBLE : View.GONE);
        //待评价
        ll_wait_eval.setVisibility(orderStatusRequest == 5 ? View.VISIBLE : View.GONE);
        //已取消
        ll_delete_order.setVisibility(orderStatusRequest == 7 ? View.VISIBLE : View.GONE);
        //待收货
        ll_wait_receive.setVisibility(orderStatusRequest == 3 ? View.VISIBLE : View.GONE);
//      //待发货 已接收
        mLinearLayoutReciverOrder.setVisibility(orderStatusRequest == 14 ? View.VISIBLE : View.GONE);
        //待发货-待接受
        mLinearLayoutShipped.setVisibility(orderStatusRequest == 2 ? View.VISIBLE : View.GONE);
//        ReturnGoodsLinearLayout.setVisibility(orderStatusRequest == 11 ? View.VISIBLE : View.GONE);
//        ReturnGoodsTitle.setVisibility(orderStatusRequest == 11 ? View.VISIBLE : View.GONE);
//        orderLinearLayout.setVisibility(orderStatusRequest == 11 ? View.GONE : View.VISIBLE);


//
//        //状态是待付款 或者是待支付 显示二个个按钮(取消订单 去支付) 其他状态需要不显示
        twoButtonLayout.setVisibility(orderStatusRequest == 1 ? View.VISIBLE : View.GONE);
        //  状态是待付款 或者是待支付 显示倒计时 其他状态不需要显示
//        orderTimerView.setVisibility(orderStatusRequest == 1 ? View.VISIBLE : View.GONE);
    }

    //显示取消订单提示
    private void showCancleOrder() {
        mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.dialog_are_you_sure_hyl);
        TextView mTvTip = mDialog.getWindow().findViewById(R.id.tv_dialog_tip);
        mTvTip.setText("确认取消订单?");
        mDialog.getWindow().findViewById(R.id.tv_dialog_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getWindow().findViewById(R.id.tv_dialog_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();

                cancelOrder(orderId);
            }
        });
    }

    /**
     * 取消订单的接口
     * @param orderId
     */
    private void cancelOrder(final String orderId) {
        OrderApi.cancleOrder(mActivity,orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.code==1) {
                            getOrderDetail(orderId,returnMainId,status);
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }else {
                            ToastUtil.showSuccessMsg(mActivity, baseModel.message);
                        }
                    }
                });
    }


    // 确认收货
    private void requestConfirmGetGoods() {
        OrderApi.confirmOrder(mActivity, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.code==1) {
                            getOrderDetail(orderId,returnMainId,status);
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 39) {
            returnCode = resultCode;
            isShowed = false;
            orderId = data.getStringExtra("orderId");
            //   list.clear();
            //    getOrderDetail(orderId, orderState, returnProductMainId);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_after_serve:
                Intent intent2 = new Intent(mContext, HylReturnGoodActivity.class);
                intent2.putExtra("orderStatus", orderStatusRequest + "");
                intent2.putExtra("orderId", orderId);
                intent2.putExtra("orderDeliveryType", 0);
                startActivity(intent2);
                break;

            case R.id.tv_copy3:
                getBuyAgain();
                break;

            case R.id.tv_eval:
                Intent intent = new Intent(mContext,JudgeActivity.class);
                intent.putExtra("orderId",orderId);
                startActivity(intent);
                break;

            case R.id.tv_copy:
                copyOrder();

                break;

            case R.id.iv_back:
                finish();
                break;

            case R.id.ll_beizhu:
                Intent intents = new Intent(mActivity,HylBeizhuActivity.class);
                intents.putExtra("beizhu",tv_beizhu.getText().toString()+"");
                startActivity(intents);
                break;

            case R.id.buttonCancelOrder:// 取消订单
                showCancleOrder();
                break;
            case R.id.buttonGOPay://去支付
                HylMyPaymentDialogFragment paymentOrderFragment = new HylMyPaymentDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", data.getId()+"");
                bundle.putString("orderId", orderId);
                bundle.putString("memo", "");
                bundle.putString("total", hylOrderDetailModels.getData().getTotalAmt());
                bundle.putString("giftNo","");
                bundle.putString("orderType",data.getOrderType()+"");
                bundle.putString("orderDeliveryType","0");
                paymentOrderFragment.setArguments(bundle);
                paymentOrderFragment.show(getSupportFragmentManager(),"paymentFragment");
                paymentOrderFragment.setCancelable(false);
                break;

            case R.id.buttonReturnGood_two:
                Intent intent1 = new Intent(mContext, HylReturnGoodActivity.class);
                intent1.putExtra("orderStatus", orderStatusRequest + "");
                intent1.putExtra("orderId", orderId);
                intent1.putExtra("orderDeliveryType", 0);
                startActivity(intent1);

                break;
            case R.id.buttonReturnGoods: //退货

                Intent intent3 = new Intent(mContext, HylReturnGoodActivity.class);
                intent3.putExtra("orderStatus", orderStatusRequest + "");
                intent3.putExtra("orderId", orderId);
                intent3.putExtra("orderDeliveryType", 0);
                startActivity(intent3);

                break;

            case R.id.tv_order_return:
                Intent intent4 = new Intent(mContext, HylReturnGoodActivity.class);
                intent4.putExtra("orderStatus", orderStatusRequest + "");
                intent4.putExtra("orderId", orderId);
                intent4.putExtra("orderDeliveryType", 0);
                startActivity(intent4);

                break;

            case R.id.tv_confirm_order:
                requestConfirmGetGoods();
                break;
            case R.id.buttonEvaluate:
                Intent intent6 = new Intent(mContext,CheckJudgeActivity.class);
                intent6.putExtra("orderId",orderId);
                startActivity(intent6);
                break;
            case R.id.tv_copy2:
                getBuyAgain();
                break;
            case R.id.tv_copy6:
                getBuyAgain();
                break;
            case R.id.tv_delete:
                showDeleteDialog(orderId);
                break;

            case R.id.tv_copy4:
                getBuyAgain();
                break;
            case R.id.tv_copy5:
                getBuyAgain();

                break;
            case R.id.tv_copy1:
                getBuyAgain();
                break;

            case R.id.tv_evaluate:
                //用户评价
                Intent intent5 = new Intent(mContext, HylUserEvaluateActivity.class);
                intent5.putExtra("orderId", orderId);
                intent5.putExtra("orderDeliveryType", 0);
                startActivity(intent5);
                break;

            case R.id.ll_cancel_order:
                //取消订单
                showCancleOrder();
                break;

        }
    }

    //拷贝订单
    private void copyOrder() {
        //获取剪贴板管理器：
        ClipboardManager cm2 = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData2 = ClipData.newPlainText("Label",orderId);
        // 将ClipData内容放到系统剪贴板里。
        cm2.setPrimaryClip(mClipData2);
        AppHelper.showMsg(mContext, "复制成功");
    }

    /**
     * 删除订单
     */
    private void showDeleteDialog(final String orderId) {
        final AlertDialog mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.dialog_delete_order_hyl);
        TextView mBtnCancel = (TextView) mDialog.getWindow().findViewById(R.id.btnCancel);
        TextView mBtnOK = (TextView) mDialog.getWindow().findViewById(R.id.btnOK);

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                //删除订单
                deleteOrder(orderId);
            }
        });
    }

    /**
     * 删除订单的接口
     * @param orderId
     */
    private void deleteOrder(String orderId) {
        OrderApi.deleteOrder(mActivity,orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.code==1) {
                            finish();
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }else {
                            ToastUtil.showSuccessMsg(mActivity, baseModel.message);
                        }
                    }
                });
    }

    /**
     * 再次购买
     */
    private void getBuyAgain() {
        OrderApi.getAgainBuy(mActivity,orderId)
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
                    public void onNext(HylLoginModel hylLoginModel) {
                        if (hylLoginModel.code==1) {
                            if(hylLoginModel.data!=null) {
                                ToastUtil.showSuccessMsg(mActivity, hylLoginModel.data);
                                EventBus.getDefault().post(new CartNumHylEvent());
                                EventBus.getDefault().post(new CartListHylEvent());
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity, hylLoginModel.message);
                        }
                    }
                });
    }


}
