package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylConfirmOrderAdapter;
import com.barter.hyl.app.adapter.HylGivenOrderGoodsAdapter;
import com.barter.hyl.app.adapter.HylOrderCouponAdapter;
import com.barter.hyl.app.adapter.HylUnSaleAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.dialog.ChooseAddressDialog;
import com.barter.hyl.app.event.AddAddressHylEvent;
import com.barter.hyl.app.event.BeizhuHylEvent;
import com.barter.hyl.app.event.CartListHylEvent;
import com.barter.hyl.app.event.CartNumHylEvent;
import com.barter.hyl.app.event.ChooseCoupon1HylEvent;
import com.barter.hyl.app.event.ChooseCouponHylEvent;
import com.barter.hyl.app.event.SetDefaultHylEvent;
import com.barter.hyl.app.fragment.HylPaymentDialogFragment;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.HylSettleModel;
import com.barter.hyl.app.model.HylStatModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/8/11
 */
public class HylOrderConfirmActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_peisong)
    TextView tv_peisong;
    @BindView(R.id.tv_coupon)
    TextView tv_coupon;
    @BindView(R.id.tv_beizhu)
    TextView tv_beizhu;
    @BindView(R.id.rl_beizhu)
    RelativeLayout rl_beizhu;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.ll_choose_address)
    LinearLayout ll_choose_address;
    @BindView(R.id.rl_choose_address)
    RelativeLayout rl_choose_address;
    @BindView(R.id.ll_unDispose)
    LinearLayout ll_unDispose;
    @BindView(R.id.tv_user)
    TextView tv_user;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.iv_change_address)
    ImageView iv_change_address;
    @BindView(R.id.ll_coupon)
    LinearLayout ll_coupon;
    @BindView(R.id.btn_sure)
    Button btn_sure;
    @BindView(R.id.av_loading)
    AVLoadingIndicatorView av_loading;
    @BindView(R.id.rv_coupon)
    RecyclerView rv_coupon;
    @BindView(R.id.rv_given)
    RecyclerView rv_given;
    @BindView(R.id.rv_unSale)
    RecyclerView rv_unSale;
    @BindView(R.id.rv_unGiven)
    RecyclerView rv_unGiven;
    @BindView(R.id.rv_unCoupon)
    RecyclerView rv_unCoupon;
    HylStatModel hylStatModel;
    HylSettleModel.DataBean orderData;
    ArrayList<String> cartIds;
    String giftNo = "";
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        orderData = (HylSettleModel.DataBean) extras.getSerializable("data");
        totalAmount = extras.getString("totalAmount");
        cartIds = extras.getStringArrayList("cartIds");
        return false;
    }
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.order_red));//设置状态栏颜色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        }
    }
    @Override
    public void setContentView() {
        setContentView(R.layout.order_confirm_activity);
    }

    HylConfirmOrderAdapter hylConfirmOrderAdapter;
    HylUnSaleAdapter unSaleAdapter;
    HylGivenOrderGoodsAdapter givenGoodsAdapter;
    HylOrderCouponAdapter hylOrderCouponAdapter;
    @Override
    public void setViewData() {
        tv_title.setText("确认订单");
        settle(cartIds.toString(),"");
        EventBus.getDefault().register(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        hylConfirmOrderAdapter = new HylConfirmOrderAdapter(R.layout.item_confirm_order_new,prods1);
        recyclerView.setAdapter(hylConfirmOrderAdapter);

        rv_unSale.setLayoutManager(new LinearLayoutManager(mContext));
        unSaleAdapter = new HylUnSaleAdapter(R.layout.item_confirm_order_new,prods2);
        rv_unSale.setAdapter(unSaleAdapter);


        //赠品
        rv_given.setLayoutManager(new LinearLayoutManager(mContext));
        givenGoodsAdapter = new HylGivenOrderGoodsAdapter(R.layout.item_given_order_goods,additionVOList1);
        rv_given.setAdapter(givenGoodsAdapter);

        //赠优惠券
        rv_coupon.setLayoutManager(new LinearLayoutManager(mContext));
        hylOrderCouponAdapter = new HylOrderCouponAdapter(R.layout.item_order_coupon_hyl,additionVOList2);
        rv_coupon.setAdapter(hylOrderCouponAdapter);


        //赠品1
        rv_unGiven.setLayoutManager(new LinearLayoutManager(mContext));
        givenGoodsAdapter = new HylGivenOrderGoodsAdapter(R.layout.item_given_order_goods,additionVOList3);
        rv_unGiven.setAdapter(givenGoodsAdapter);

        //赠优惠券1
        rv_unCoupon.setLayoutManager(new LinearLayoutManager(mContext));
        hylOrderCouponAdapter = new HylOrderCouponAdapter(R.layout.item_order_coupon_hyl,additionVOList4);
        rv_unCoupon.setAdapter(hylOrderCouponAdapter);

        tv_num.setText(orderData.getProdNum()+"");
        tv_amount.setText("￥"+orderData.getProdAmount());
        tv_peisong.setText("￥"+orderData.getDeliverFee());
        tv_coupon.setText(orderData.getGiftInfo());
        tv_total_price.setText("￥"+orderData.getTotalAmount());


        if(orderData.getAddressVO()!=null) {
            rl_choose_address.setVisibility(View.VISIBLE);
            ll_unDispose.setVisibility(View.GONE);
            tv_user.setText(orderData.getAddressVO().getUserName());
            tv_phone.setText(orderData.getAddressVO().getContactPhone());
            tv_address.setText(orderData.getAddressVO().getDetailAddress());
        }else {
            ll_unDispose.setVisibility(View.VISIBLE);
            rl_choose_address.setVisibility(View.GONE);
        }


    }

    @Override
    public void setClickListener() {
        ll_back.setOnClickListener(this);
        rl_beizhu.setOnClickListener(this);
        ll_choose_address.setOnClickListener(this);
        ll_coupon.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
        rl_choose_address.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_choose_address:
                ChooseAddressDialog chooseAddressDialog = new ChooseAddressDialog(mActivity);
                chooseAddressDialog.show();
                break;

            case R.id.btn_sure:
                av_loading.show();
                orderPay();
                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_coupon:
                Intent couponIntent = new Intent(mContext,HylCouponActivity.class);
                couponIntent.putExtra("cartIds", (Serializable) cartIds);
                couponIntent.putExtra("giftDetailNo",orderData.getGiftNo());
                startActivity(couponIntent);
                break;
            case R.id.rl_beizhu:
                Intent intent = new Intent(mContext,HylBeizhuActivity.class);
                intent.putExtra("beizhu",tv_beizhu.getText().toString()+"");
                startActivity(intent);
                break;

            case R.id.ll_choose_address:
                Intent chooseAddressIntent = new Intent(mContext,HylAddressListsActivity.class);
                startActivity(chooseAddressIntent);
                break;
        }
    }

    /**
     * 提交订单
     */
    HylPaymentDialogFragment paymentOrderFragment;
    String orderId;
    private void orderPay() {
        OrderApi.cartOrder(mActivity,cartIds.toString(),giftNo,tv_beizhu.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylLoginModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        av_loading.hide();
                    }

                    @Override
                    public void onNext(HylLoginModel hylLoginModel) {
                        if(hylLoginModel.code==1) {
                            orderId = hylLoginModel.data;
                            paymentOrderFragment = new HylPaymentDialogFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("memo", tv_beizhu.getText().toString());
                            bundle.putString("total",totalAmount);
                            bundle.putString("giftNo",giftNo);
                            bundle.putString("orderId",orderId);
                            bundle.putSerializable("cartIds", (Serializable) cartIds);
                            bundle.putString("orderDeliveryType","0");
                            paymentOrderFragment.setArguments(bundle);
                            paymentOrderFragment.show(getSupportFragmentManager(),"paymentFragment");
                            paymentOrderFragment.setCancelable(false);
                            av_loading.hide();
                            EventBus.getDefault().post(new CartListHylEvent());
                            EventBus.getDefault().post(new CartNumHylEvent());
                        }else {
                            av_loading.hide();
                            ToastUtil.showSuccessMsg(mContext, hylLoginModel.message);
                        }
                    }
                });
    }
    /**
     * 结算
     * @param cartId
     */
    HylSettleModel.DataBean data;
    String totalAmount;
    List<HylSettleModel.DataBean.ProdsBean> prods1 = new ArrayList<>();
    List<HylSettleModel.DataBean.ProdsBean> prods2 = new ArrayList<>();
    List<HylSettleModel.DataBean.AdditionsBean>additionVOList1 = new ArrayList<>();
    List<HylSettleModel.DataBean.AdditionsBean>additionVOList2 = new ArrayList<>();
    List<HylSettleModel.DataBean.AdditionsBean>additionVOList3 = new ArrayList<>();
    List<HylSettleModel.DataBean.AdditionsBean>additionVOList4 = new ArrayList<>();
    private void settle(String cartId,String giftNo) {
        DetailApi.settle(mActivity,cartId,giftNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylSettleModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HylSettleModel hylSettleModel) {
                        if (hylSettleModel.getCode()==1) {
                            if(hylSettleModel.getData()!=null) {
                                data = hylSettleModel.getData();
                                totalAmount = data.getTotalAmount();
                                tv_user.setText(data.getAddressVO().getUserName());
                                tv_coupon.setText(data.getGiftInfo());
                                tv_total_price.setText(data.getTotalAmount());
                                tv_address.setText(data.getAddressVO().getDetailAddress());
                                tv_phone.setText(data.getAddressVO().getContactPhone());
                                prods1.clear();
                                prods2.clear();
                                additionVOList1.clear();
                                additionVOList2.clear();
                                additionVOList3.clear();
                                additionVOList4.clear();
                                for (int i = 0; i < data.getProds().size(); i++) {
                                    if(data.getProds().get(i).getSendFlag()==0) {
                                        //在配送区域
                                        prods1.add(data.getProds().get(i));
                                    }else {
                                        prods2.add(data.getProds().get(i));
                                    }
                                }
                                if(prods1.size()>0) {
                                    recyclerView.setVisibility(View.VISIBLE);
                                }else {
                                    recyclerView.setVisibility(View.GONE);
                                }

                                if(prods2.size()>0) {
                                    rv_unSale.setVisibility(View.VISIBLE);
                                }else {
                                    rv_unSale.setVisibility(View.GONE);
                                }

                                if(null!=data.getAdditions()) {
                                    List<HylSettleModel.DataBean.AdditionsBean> additions = data.getAdditions();
                                    for (int i = 0; i < additions.size(); i++) {
                                        if(additions.get(i).getSendFlag()==0) {
                                            //配送范围
                                            if(additions.get(i).getType()==0) {
                                                //优惠券
                                                additionVOList1.add(additions.get(i));
                                            }else {
                                                //赠品
                                                additionVOList2.add(additions.get(i));
                                            }
                                        }else {
                                            //不在配送范围
                                            if(additions.get(i).getType()==0) {
                                                //优惠券
                                                additionVOList3.add(additions.get(i));
                                            }else {
                                                //赠品
                                                additionVOList4.add(additions.get(i));
                                            }
                                        }
                                    }
                                }

                                if(additionVOList1.size()>0) {
                                    rv_given.setVisibility(View.VISIBLE);
                                }else {
                                    rv_given.setVisibility(View.GONE);
                                }
//
                                if(additionVOList2.size()>0) {
                                    rv_coupon.setVisibility(View.VISIBLE);
                                }else {
                                    rv_coupon.setVisibility(View.GONE);
                                }

                                hylConfirmOrderAdapter.notifyDataSetChanged();
                                unSaleAdapter.notifyDataSetChanged();

                                givenGoodsAdapter.notifyDataSetChanged();
                                hylOrderCouponAdapter.notifyDataSetChanged();
                                rv_coupon.setLayoutManager(new LinearLayoutManager(mContext));
                                rv_given.setLayoutManager(new LinearLayoutManager(mContext));
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylSettleModel.getMessage());
                        }
                    }
                });
    }


    /**
     * 获取备注内容
     * @param beizhuHylEvent
     */
    BeizhuHylEvent beizhuHylEvent;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getBeizhu(BeizhuHylEvent beizhuHylEvent) {
        this.beizhuHylEvent = beizhuHylEvent;
        tv_beizhu.setText(beizhuHylEvent.getS());
    }


    /**
     * 选中某一个优惠券
     * @param chooseCouponHylEvent
     */
    ChooseCouponHylEvent chooseCouponHylEvent;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCoupon(ChooseCouponHylEvent chooseCouponHylEvent) {
        giftNo = chooseCouponHylEvent.getGiftDetailNo();
        settle(cartIds.toString(), chooseCouponHylEvent.getGiftDetailNo());
        hylStatModel.setSelects(false);
    }

    /**
     * 未选优惠券
     * @param chooseCouponEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCoupons(ChooseCoupon1HylEvent chooseCouponEvent) {
        settle(cartIds.toString(),"");
        hylStatModel.setSelects(true);
    }

    /**
     * 编辑默认地址的回调
     * @param setDefaultHylEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setSetDefaultAddress(SetDefaultHylEvent setDefaultHylEvent) {
        settle(cartIds.toString(),giftNo);

    }

    /**
     * 新增地址的回调
     * @param addAddressHylEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addAddress(AddAddressHylEvent addAddressHylEvent) {
        settle(cartIds.toString(),giftNo);
        rl_choose_address.setVisibility(View.VISIBLE);
        ll_unDispose.setVisibility(View.GONE);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//        if(paymentOrderFragment!=null) {
//            paymentOrderFragment.dismiss();
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if(paymentOrderFragment!=null) {
//            paymentOrderFragment.dismiss();
//        }
//    }
}
