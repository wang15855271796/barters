package com.barter.hyl.app.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayResultActivity;
import com.alipay.sdk.app.PayTask;
import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.DelayPayResultActivity;
import com.barter.hyl.app.activity.DeliverPayResult;
import com.barter.hyl.app.activity.HylOrderDetailActivity;
import com.barter.hyl.app.adapter.HylPayListAdapter;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.constant.AppConstant;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.dialog.PayErrorDialog;
import com.barter.hyl.app.event.WeChatPayEvent;
import com.barter.hyl.app.event.WeChatUnPayEvent;
import com.barter.hyl.app.model.HylPayInfoModel;
import com.barter.hyl.app.model.HylPayListModel;
import com.barter.hyl.app.utils.DateUtil;
import com.barter.hyl.app.utils.SharedPreferencesUtil;
import com.barter.hyl.app.utils.ToastUtil;
import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HylMyOrderDetailPaymentDialogFragment extends DialogFragment {

    private RelativeLayout rePayWay, rePayDetail;
    private LinearLayout LinPayWay;
    private Button btnPay;
    TextView tv_balance;
    TextView tv_amount;
    RecyclerView recyclerView;
    ImageView iv_close;
    String orderId;
    String orderDeliveryType;
    ImageView iv_closes;
    String memo;
    String giftNo;
    String orderType;
    TextView tv_desc;
    String id;
    LinearLayout ll_special_desc;
    List<String> ids = new ArrayList<>();
    String total;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        total = bundle.getString("total");
        memo = bundle.getString("memo");
        id = bundle.getString("id");
        giftNo = bundle.getString("giftNo");
        orderId = bundle.getString("orderId");
        orderType = bundle.getString("orderType");
        orderDeliveryType = bundle.getString("orderDeliveryType");
        ids.add(id);
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_pay_detail_hyl1);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        if(orderType.equals("1")) {
            getPayList(ids.toString());
        }else {
            getPayList(orderId);
        }
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 3 / 5;
        window.setAttributes(lp);
        ll_special_desc =  (LinearLayout) dialog.findViewById(R.id.ll_special_desc);
        tv_desc = (TextView) dialog.findViewById(R.id.tv_desc);
        iv_closes = (ImageView) dialog.findViewById(R.id.iv_closes);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        iv_close = (ImageView) dialog.findViewById(R.id.iv_close);
        tv_amount = (TextView) dialog.findViewById(R.id.tv_amount);
        tv_balance = (TextView) dialog.findViewById(R.id.tv_balance);
        rePayWay = (RelativeLayout) dialog.findViewById(R.id.re_pay_way);
        rePayDetail = (RelativeLayout) dialog.findViewById(R.id.re_pay_detail);//付款详情
        LinPayWay = (LinearLayout) dialog.findViewById(R.id.lin_pay_way);//付款方式
        btnPay = (Button) dialog.findViewById(R.id.btn_confirm_pay);
        rePayWay.setOnClickListener(listener);
        btnPay.setOnClickListener(listener);
        tv_amount.setText("￥"+total);
        iv_close.setOnClickListener(listener);
        iv_closes.setOnClickListener(listener);
        iv_closes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return dialog;
    }

    Animation slide_left_to_right;
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Animation slide_left_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_to_left);
            Animation slide_right_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right_to_left);
            slide_left_to_right = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_to_right);
            switch (view.getId()) {
                case R.id.iv_close:
                    rePayDetail.startAnimation(slide_left_to_right);
                    rePayDetail.setVisibility(View.VISIBLE);
                    LinPayWay.startAnimation(slide_left_to_right);
                    LinPayWay.setVisibility(View.GONE);
                    break;
                case R.id.re_pay_way:
                    rePayDetail.startAnimation(slide_left_to_left);
                    rePayDetail.setVisibility(View.GONE);
                    LinPayWay.startAnimation(slide_right_to_left);
                    LinPayWay.setVisibility(View.VISIBLE);
                    break;

                case R.id.btn_confirm_pay:
                    //调支付接口  1信用订单  0 普通订单
                    if(orderType.equals("1")) {
                        getDelayPayInfo();
                    }else {
                        getPayInfo();
                    }

                    break;

                case R.id.iv_closes:
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        //订单详情
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
//        if(outTradeNo!=null && jumpWx==1) {
//            dismiss();
//        }
//
//        if(outTradeNo!=null&&jumpWx==0) {
//            Intent intent = new Intent(getContext(), DeliverPayResult.class);
//            intent.putExtra(AppConstant.PAYCHANNAL, payChannel);
//            intent.putExtra(AppConstant.OUTTRADENO, outTradeNo);
//            intent.putExtra(AppConstant.ORDERID, orderId);
//            startActivity(intent);
//        }

        if(outTradeNo!=null) {
            dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    // 支付
    String outTradeNo;

    /**
     * 支付宝
     * @param parms
     */

    private void payAliPay(String parms){
        UnifyPayRequest msg = new UnifyPayRequest();
        msg.payChannel = UnifyPayRequest.CHANNEL_ALIPAY;
        msg.payData = parms;
        UnifyPayPlugin.getInstance(getContext()).sendPayRequest(msg);
        dismiss();
    }


    /**
     * 支付宝支付（小程序）
     */
    private void zhiFuBaoPay(String json) {
        try {
            String uri = json;
            Intent intent = Intent.parseUri(uri, Intent.URI_INTENT_SCHEME);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信支付
     */

    private void weChatPay(String json) {
        SharedPreferencesUtil.saveString(getContext(),"pays","0");
        try {
            IWXAPI api = WXAPIFactory.createWXAPI(getContext(), "wxf62d1bee757cd65a");
            JSONObject obj = new JSONObject(json);
            PayReq request = new PayReq();
            request.appId = obj.optString("appId");
            request.partnerId = obj.optString("mchID");
            request.prepayId = obj.optString("prepayId");
            request.packageValue = obj.optString("pkg");
            request.nonceStr = obj.optString("nonceStr");
            request.timeStamp = obj.optString("timeStamp");
            request.sign = obj.optString("paySign");
            api.sendReq(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        dismiss();
    }

    /**
     * 微信支付的回调,使用的eventBus.......((‵□′))
     **/
    @Subscribe
    public void onEventMainThread(WeChatPayEvent event) {
        Intent intent = new Intent(getActivity(), DeliverPayResult.class);
        intent.putExtra(AppConstant.PAYCHANNAL, payChannel);
        intent.putExtra(AppConstant.OUTTRADENO, outTradeNo);
        intent.putExtra(AppConstant.ORDERID, orderId);
        intent.putExtra(AppConstant.ORDERDELIVERYTYPE, orderDeliveryType+"");
        startActivity(intent);
        dismiss();
    }

    /**
     * 微信支付用户已取消支付的回调,使用的eventBus.......((‵□′))
     **/
    @Subscribe
    public void onEventMainThread(WeChatUnPayEvent event) {
        Intent intent = new Intent(getContext(), DeliverPayResult.class);
        intent.putExtra(AppConstant.PAYCHANNAL, payChannel);
        intent.putExtra(AppConstant.OUTTRADENO, outTradeNo);
        intent.putExtra(AppConstant.ORDERID, orderId);
        intent.putExtra(AppConstant.ORDERDELIVERYTYPE, orderDeliveryType+"");
        startActivity(intent);
        dismiss();

    }

    /**
     * 支付宝支付
     */
    private static final int SDK_PAY_FLAG = 1;
    private void aliPay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
                //设置支付调用
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝支付结果
     */
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    Map<String, String> result = (Map<String, String>) msg.obj;
                    Log.e("TGA", result.get("resultStatus") + "");
                    if ("9000".equals(result.get("resultStatus"))) {
                        //okpay
                        //支付成功
                        Intent intent = new Intent(getContext(), DeliverPayResult.class);
                        intent.putExtra(AppConstant.PAYCHANNAL, payChannel);
                        intent.putExtra(AppConstant.OUTTRADENO, outTradeNo);
                        intent.putExtra(AppConstant.ORDERDELIVERYTYPE, orderDeliveryType + "");
                        intent.putExtra(AppConstant.ORDERID, orderId);
                        startActivity(intent);
                        getActivity().finish();
                    } else if ("6001".equals(result.get("resultStatus"))) {
                        //用户取消支付
                        AppHelper.showMsg(getContext(), "您已取消支付");
                    } else if ("6002".equals(result.get("resultStatus"))) {
                        //网络连接错误
                        AppHelper.showMsg(getContext(), "网络连接错误");
                    } else {
                        //okpay
                        //支付失败
                        Intent intent = new Intent(getContext(), DeliverPayResult.class);
                        intent.putExtra(AppConstant.PAYCHANNAL, payChannel);
                        intent.putExtra(AppConstant.OUTTRADENO, outTradeNo);
                        intent.putExtra(AppConstant.ORDERID, orderId);
                        intent.putExtra(AppConstant.ORDERDELIVERYTYPE, orderDeliveryType + "");
                        startActivity(intent);
                        getActivity().finish();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     *获取支付方式
     */
    List<HylPayListModel.DataBean> list = new ArrayList<>();
    HylPayListAdapter hylPayListAdapter;
    //支付方式 信用订单flag 1 普通订单0
    int payChannel;
    private void getPayList(String orderId) {
        OrderApi.getPayWay(getContext(),Integer.parseInt(orderType),orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylPayListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(final HylPayListModel hylPayListModel) {
                        if(hylPayListModel.getCode()==1) {
                            if(hylPayListModel.getData()!=null) {
                                list.clear();
                                list.addAll(hylPayListModel.getData());
                                tv_balance.setText(hylPayListModel.getData().get(0).getPayChannelName());
                                hylPayListAdapter = new HylPayListAdapter(R.layout.item_pay_list_hyl,list,total);
                                recyclerView.setAdapter(hylPayListAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                payChannel = hylPayListModel.getData().get(0).getPayChannel();
                                dataBean = hylPayListModel.getData().get(0);
                                jumpWx = hylPayListModel.getData().get(0).getJumpWx();
                                for (int i = 0; i < hylPayListModel.getData().size(); i++) {
                                    if(hylPayListModel.getData().get(i).getPayChannel()==17) {
                                        if(hylPayListModel.getData().get(i).getDesc()!=null|| hylPayListModel.getData().get(i).getDesc()!="") {
                                            tv_desc.setText(hylPayListModel.getData().get(i).getDesc());
                                            ll_special_desc.setVisibility(View.VISIBLE);
                                        }else {
                                            ll_special_desc.setVisibility(View.GONE);
                                        }
                                    }else {
                                        ll_special_desc.setVisibility(View.GONE);
                                    }
                                }

                                hylPayListAdapter.setOnItemClickListener(new HylPayListAdapter.OnEventClickListener() {
                                    @Override
                                    public void onEventClick(int position) {
                                        hylPayListAdapter.selectionPosition(position);
                                        tv_balance.setText(hylPayListModel.getData().get(position).getPayChannelName());
                                        payChannel = hylPayListModel.getData().get(position).getPayChannel();

                                        rePayDetail.startAnimation(slide_left_to_right);
                                        rePayDetail.setVisibility(View.VISIBLE);
                                        LinPayWay.startAnimation(slide_left_to_right);
                                        LinPayWay.setVisibility(View.GONE);



                                        if(hylPayListModel.getData().get(position).getPayChannel()==2) {
                                            //支付宝
                                            payChannel = 2;
                                            jumpWx = hylPayListModel.getData().get(position).getJumpWx();
                                            dataBean = hylPayListModel.getData().get(position);
                                        }else if(hylPayListModel.getData().get(position).getPayChannel()==3){
                                            //微信
                                            payChannel = 3;
                                            dataBean = hylPayListModel.getData().get(position);
                                            jumpWx = hylPayListModel.getData().get(position).getJumpWx();
                                        }else if(hylPayListModel.getData().get(position).getPayChannel()==17){
                                            payChannel = 17;
                                            dataBean = hylPayListModel.getData().get(position);
                                            jumpWx = hylPayListModel.getData().get(position).getJumpWx();
                                        }
                                    }
                                });
                            }
                        }else {
                            ToastUtil.showSuccessMsg(getActivity(), hylPayListModel.getMessage());
                        }
                    }
                });
    }

    /**
     *获取一般支付信息
     */
    int jumpWx = 0;
    HylPayListModel.DataBean dataBean;
    int errorFlag = 0;
    private void getPayInfo() {
        OrderApi.getPayInfo(getContext(),orderId,payChannel,errorFlag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylPayInfoModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HylPayInfoModel hylPayInfoModel) {
                        if(hylPayInfoModel.getCode()==1) {
                            if(hylPayInfoModel.getData()!=null) {
                                outTradeNo = hylPayInfoModel.getData().getOutTradeNo();
                                if (payChannel == 2&& hylPayInfoModel.getData().getPayType()==2) {
                                    //支付宝支付 已经改好了
                                    SharedPreferencesUtil.saveString(getContext(),"payKey","2");
                                    aliPay(hylPayInfoModel.getData().getPayToken());
                                } else if (payChannel == 3 && jumpWx==1) {
                                    //微信支付(小程序)1
                                    if(DateUtil.isWeixin(getActivity())) {
                                        SharedPreferencesUtil.saveString(getContext(),"payKey","3");
                                        Intent lan = getActivity().getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                                        Intent t2 = new Intent(Intent.ACTION_MAIN);
                                        t2.addCategory(Intent.CATEGORY_LAUNCHER);
                                        t2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        t2.setComponent(lan.getComponent());
                                        startActivity(t2);
                                        weChatPay2(dataBean);
                                    }
                                }else if(payChannel == 3 && jumpWx==0) {
                                    //微信支付
                                    if(DateUtil.isWeixin(getActivity())) {
                                        SharedPreferencesUtil.saveString(getContext(),"payKey","3");
                                        weChatPay(hylPayInfoModel.getData().getPayToken());
                                    }
                                }else if(hylPayInfoModel.getData().getPayType()==14&&payChannel == 2) {
                                    //银联
                                    SharedPreferencesUtil.saveString(getContext(),"payKey","4");
                                    payAliPay(hylPayInfoModel.getData().getPayToken());
                                }else if(hylPayInfoModel.getData().getPayType()==22&&payChannel == 2) {
                                    //支付宝跳转小程序
                                    SharedPreferencesUtil.saveString(getContext(),"payKey","5");
                                    zhiFuBaoPay(hylPayInfoModel.getData().getPayToken());
                                }
                                else {
                                    //货到付款
                                    SharedPreferencesUtil.saveString(getContext(),"payKey","17");
                                    payDeliverPay();
                                }
                            }
                        }if(hylPayInfoModel.getCode()==100006) {
                            PayErrorDialog payErrorDialog = new PayErrorDialog(getContext(), hylPayInfoModel.getMessage()) {
                                @Override
                                public void Confirm() {
                                    errorFlag = 1;
                                    getPayInfo();
                                }

                                @Override
                                public void Cancel() {
                                    dismiss();
                                }
                            };
                            payErrorDialog.show();
                        } else {
                            ToastUtil.showSuccessMsg(getActivity(), hylPayInfoModel.getMessage());
                        }
                    }
                });
    }

    private void weChatPay2(HylPayListModel.DataBean dataBean) {
        SharedPreferencesUtil.saveString(getContext(),"pays","0");
        String appId = "wxf62d1bee757cd65a"; // 填移动应用(App)的 AppId，非小程序的 AppID
        IWXAPI api = WXAPIFactory.createWXAPI(getContext(), appId);
        String userId = UserInfoHelper.getUserId(getContext());
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = dataBean.getUserName(); // 填小程序原始id
        req.path = dataBean.getPath();
        ////拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
        if(dataBean.getType().equals("dev")) {
            //体验
            req.miniprogramType =  WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW;// 可选打开 开发版，体验版和正式版
        }else {
            //正式
            req.miniprogramType =  WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
        }

        api.sendReq(req);
    }

    /**
     *获取信用支付信息
     */
    private void getDelayPayInfo() {
        OrderApi.getDelayPay(getContext(),ids.toString(),payChannel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylPayInfoModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HylPayInfoModel hylPayInfoModel) {
                        if(hylPayInfoModel.getCode()==1) {
                            if(hylPayInfoModel.getData()!=null) {
                                outTradeNo = hylPayInfoModel.getData().getOutTradeNo();
                                if (payChannel == 2&& hylPayInfoModel.getData().getPayType()==2) {
                                    //支付宝支付 已经改好了
                                    SharedPreferencesUtil.saveString(getContext(),"payKey","2");
                                    aliPay(hylPayInfoModel.getData().getPayToken());
                                } else if (payChannel == 3 && jumpWx==1) {
                                    //微信支付(小程序)1
                                    if(DateUtil.isWeixin(getActivity())) {
                                        SharedPreferencesUtil.saveString(getContext(),"payKey","3");
                                        Intent lan = getActivity().getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                                        Intent t2 = new Intent(Intent.ACTION_MAIN);
                                        t2.addCategory(Intent.CATEGORY_LAUNCHER);
                                        t2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        t2.setComponent(lan.getComponent());
                                        startActivity(t2);
                                        weChatPay2(dataBean);
                                    }
                                }else if(payChannel == 3 && jumpWx==0) {
                                    //微信支付
                                    if(DateUtil.isWeixin(getActivity())) {
                                        SharedPreferencesUtil.saveString(getContext(),"payKey","3");
                                        weChatPay(hylPayInfoModel.getData().getPayToken());
                                    }
                                }else if(hylPayInfoModel.getData().getPayType()==14&&payChannel == 2) {
                                    //银联
                                    SharedPreferencesUtil.saveString(getContext(),"payKey","4");
                                    payAliPay(hylPayInfoModel.getData().getPayToken());
                                }else {
                                    //货到付款
                                    SharedPreferencesUtil.saveString(getContext(),"payKey","17");
                                    payDeliverPay();
                                }
                            }
                        }else {
                            ToastUtil.showSuccessMsg(getActivity(), hylPayInfoModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 货到付款
     * @param
     */
    private void payDeliverPay() {
        Intent intent = new Intent(getContext(), DelayPayResultActivity.class);
        intent.putExtra(AppConstant.ORDERID, orderId);
        startActivity(intent);
        dismiss();
    }

}
