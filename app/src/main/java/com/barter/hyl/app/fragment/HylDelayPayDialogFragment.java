package com.barter.hyl.app.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.alipay.sdk.app.PayTask;
import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.DelayPayResultActivity;
import com.barter.hyl.app.activity.DeliverPayResult;
import com.barter.hyl.app.adapter.HylPayListAdapter;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.constant.AppConstant;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.event.WeChatPayEvent;
import com.barter.hyl.app.event.WeChatUnPayEvent;
import com.barter.hyl.app.model.HylPayInfoModel;
import com.barter.hyl.app.model.HylPayListModel;
import com.barter.hyl.app.utils.SharedPreferencesUtil;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/9/17
 */
public class HylDelayPayDialogFragment extends DialogFragment {

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
    TextView tv_desc;
    LinearLayout ll_special_desc;
    List<String>ids = new ArrayList<>();
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String total = bundle.getString("total");
        ids = (List<String>) bundle.getSerializable("ids");
//        ids = bundle.getString("ids");
//        orderDeliveryType = bundle.getString("orderDeliveryType");
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_pay_detail_hyl);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        getPayList();
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
//        String total = total.substring(0, 1);
        tv_amount.setText(total);
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
                    //调支付接口
                    getPayInfo();
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
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if(outTradeNo!=null&&SharedPreferencesUtil.getString(getContext(),"payKey").equals("4")) {
//            getPayResult(outTradeNo);
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
    }

    /**
     * 微信支付的回调,使用的eventBus.......((‵□′))
     **/
    @Subscribe
    public void onEventMainThread(WeChatPayEvent event) {
        Intent intent = new Intent(getContext(), DeliverPayResult.class);
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
    //支付方式
    int payChannel;
    private void getPayList() {
        OrderApi.getPayWay(getContext(),1)
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
                                hylPayListAdapter = new HylPayListAdapter(R.layout.item_pay_list_hyl,list);
                                recyclerView.setAdapter(hylPayListAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                payChannel = hylPayListModel.getData().get(0).getPayChannel();
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

                                hylPayListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        hylPayListAdapter.selectionPosition(position);
                                        tv_balance.setText(hylPayListModel.getData().get(position).getPayChannelName());
                                        payChannel = hylPayListModel.getData().get(position).getPayChannel();

                                        rePayDetail.startAnimation(slide_left_to_right);
                                        rePayDetail.setVisibility(View.VISIBLE);
                                        LinPayWay.startAnimation(slide_left_to_right);
                                        LinPayWay.setVisibility(View.GONE);
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
     *获取支付信息
     */
    private void getPayInfo() {
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
                                } else if (payChannel == 3) {
                                    //微信支付
//                                    ToastUtil.showSuccessMsg(getActivity(),"暂未开通请使用支付宝");
                                    SharedPreferencesUtil.saveString(getContext(),"payKey","3");
                                    weChatPay(hylPayInfoModel.getData().getPayToken());

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





    public static Date getNowDate() {
        Date currentTimes = new Date();
        SimpleDateFormat formatters = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStrings = formatters.format(currentTimes);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatters.parse(dateStrings, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTimes = new Date();
        SimpleDateFormat formatters = new SimpleDateFormat("yyyy");
        String dateStrings = formatters.format(currentTimes);
        return dateStrings;
    }

}
