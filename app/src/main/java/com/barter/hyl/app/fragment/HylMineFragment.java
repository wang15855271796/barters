package com.barter.hyl.app.fragment;

import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylActivityCollection;
import com.barter.hyl.app.activity.HylAddressListsActivity;
import com.barter.hyl.app.activity.HylBillActivity;
import com.barter.hyl.app.activity.HylMessageCenterActivity;
import com.barter.hyl.app.activity.HylMyOrderActivity;
import com.barter.hyl.app.activity.JiFenActivity;
import com.barter.hyl.app.activity.LoginActivity;
import com.barter.hyl.app.activity.HylMyCouponActivity;
import com.barter.hyl.app.activity.HylSettingActivity;
import com.barter.hyl.app.api.HomeApi;
import com.barter.hyl.app.api.MyInfoApi;
import com.barter.hyl.app.base.BaseFragment;
import com.barter.hyl.app.event.ChangeAccountHylEvent;
import com.barter.hyl.app.event.MessageNumHylEvent;
import com.barter.hyl.app.model.HylMessageNumModel;
import com.barter.hyl.app.model.HylMyModel;
import com.barter.hyl.app.utils.APKVersionCodeUtils;
import com.barter.hyl.app.view.CustomDialog;
import com.barter.hyl.app.utils.ToastUtil;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/8/4
 */
public class HylMineFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_jifen)
    TextView tv_jifen;
    @BindView(R.id.tv_coupon)
    TextView tv_coupon;
    @BindView(R.id.rl_collection)
    RelativeLayout rl_collection;
    @BindView(R.id.rl_jifen)
    RelativeLayout rl_jifen;
    @BindView(R.id.rl_coupon)
    RelativeLayout rl_coupon;
    @BindView(R.id.rl_kefu)
    RelativeLayout rl_kefu;
    @BindView(R.id.rl_address)
    RelativeLayout rl_address;
    @BindView(R.id.rl_account)
    RelativeLayout rl_account;
    @BindView(R.id.iv_setting)
    ImageView iv_setting;
    @BindView(R.id.iv_message)
    ImageView iv_message;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.ll_common_order)
    LinearLayout ll_common_order;
    @BindView(R.id.rl_version)
    RelativeLayout rl_version;
    @BindView(R.id.tv_version_tip)
    TextView tv_version_tip;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.ll_self_sufficiency)
    LinearLayout ll_self_sufficiency;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    @BindView(R.id.tv_order_num)
    TextView tv_order_num;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.iv_update)
    ImageView iv_update;
    @Override
    public int setLayoutId() {
        return R.layout.mine_fragment_hyl;
    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMessageNum();
                myInfo();
                smart.finishRefresh();
            }
        });
        myInfo();
    }


    @Override
    public void onResume() {
        super.onResume();
        getMessageNum();
        myInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setClickListener() {
        rl_version.setOnClickListener(this);
        ll_self_sufficiency.setOnClickListener(this);
        ll_common_order.setOnClickListener(this);
        rl_collection.setOnClickListener(this);
        rl_jifen.setOnClickListener(this);
        rl_kefu.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        rl_account.setOnClickListener(this);
        iv_setting.setOnClickListener(this);
        iv_message.setOnClickListener(this);
        rl_coupon.setOnClickListener(this);
        rl_version.setOnClickListener(this);
        tv_order_num.setOnClickListener(this);
    }

    /**
     * 用户信息
     */
    HylMyModel.DataBean data;
    String updateMsg;
    String downLoadUrl;
    int waitToPayNum;
    private void myInfo() {
        MyInfoApi.getMyInfo(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylMyModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylMyModel hylMyModel) {
                        if (hylMyModel.getCode()==1) {
                            data = hylMyModel.getData();
                            Glide.with(mActivity).load(data.getHeadImg()).into(iv_avatar);
                            tv_phone.setText(data.getPhone());
                            tv_jifen.setText(data.getPoint()+"");
                            tv_coupon.setText(data.getGiftNum()+"张可用");
                            tv_name.setText(data.getShortName());
                            downLoadUrl = data.getDownLoadUrl();
                            updateMsg = data.getUpdateMsg();
                            waitToPayNum = data.getWaitToPayNum();
                            tv_version.setText(data.getAndVersion());

                            String verName = APKVersionCodeUtils.getVerName(mActivity);

                            if(data.isAndAppUpdate()) {
                                iv_update.setVisibility(View.VISIBLE);
                                tv_version.setText(verName);

                            }else {
                                iv_update.setVisibility(View.GONE);
                                tv_version.setText(verName+"(已是最新版本"+")");
                            }


                            if(waitToPayNum>0) {
                                tv_order_num.setText("您当前有"+waitToPayNum+"笔信用订单待支付");
                                tv_order_num.setVisibility(View.VISIBLE);
                            }else {
                                tv_order_num.setVisibility(View.GONE);
                            }

                        }else if(hylMyModel.getCode()==-10001) {
                            Intent intent = new Intent(mActivity,LoginActivity.class);
                            startActivity(intent);
                        }else {
                            ToastUtil.showSuccessMsg(mActivity, hylMyModel.getMessage());
                        }

                    }
                });
    }

    CustomDialog dialog;
    TextView tv_customer_phone;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_order_num:
                Intent unPayIntent = new Intent(mActivity,HylMyOrderActivity.class);
                unPayIntent.putExtra("orderDeliveryType",1);
                startActivity(unPayIntent);
                break;

            case R.id.ll_self_sufficiency:
                Intent orderSelfIntent = new Intent(mActivity,HylMyOrderActivity.class);
                orderSelfIntent.putExtra("orderDeliveryType",1);
                startActivity(orderSelfIntent);
                break;

            case R.id.ll_common_order:
                Intent orderIntent = new Intent(mActivity,HylMyOrderActivity.class);
                orderIntent.putExtra("orderDeliveryType",0);
                startActivity(orderIntent);
                break;
            case R.id.iv_message:
                Intent messageIntent = new Intent(mActivity,HylMessageCenterActivity.class);
                startActivity(messageIntent);
                break;

            case R.id.iv_setting:
                Intent settingIntent = new Intent(mActivity,HylSettingActivity.class);
                startActivity(settingIntent);
                break;

            case R.id.rl_account:
                Intent billIntent = new Intent(mActivity,HylBillActivity.class);
                startActivity(billIntent);
                break;

            case R.id.rl_address:
                Intent addressIntent = new Intent(mActivity,HylAddressListsActivity.class);
                startActivity(addressIntent);
                break;

            case R.id.rl_kefu:
                CustomDialog.Builder builder = new CustomDialog.Builder(mActivity);
                dialog = builder.view(R.layout.dialog_kefu_hyl)
                        .style(R.style.DialogStyle)
                        .heightdp(128)
                        .widthdp(270)
                        .build();
                dialog.show();
                tv_customer_phone = dialog.findViewById(R.id.tv_customer_phone);
                if(data!=null) {
                    if(data.getCustomerPhone()!=null) {
                        tv_customer_phone.setText("客服热线"+"("+data.getCustomerPhone()+")");
                    }
                }
                tv_customer_phone.setOnClickListener(this);
                break;
            case R.id.rl_collection:
                Intent intent1 = new Intent(mActivity,HylActivityCollection.class);
                startActivity(intent1);
                break;

            case R.id.rl_jifen:
                Intent intent2 = new Intent(mActivity,JiFenActivity.class);
                startActivity(intent2);
                break;

            case R.id.rl_coupon:
                Intent intent3 = new Intent(mActivity,HylMyCouponActivity.class);
                startActivity(intent3);
                break;

            case R.id.rl_version:
                if(data!=null) {
                    if(data.getDownLoadUrl()!=null) {
                        try {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(data.getDownLoadUrl());
                            intent.setData(content_url);
                            startActivity(intent);
                        } catch (Exception e) {

                        }
                    }
                }

                break;

            case R.id.tv_customer_phone:
                if(data!=null) {
                    if(data.getCustomerPhone()!=null) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.getCustomerPhone()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                }

                break;
        }
    }

    /**
     * 获取未读消息数量
     */
    private void getMessageNum() {
        HomeApi.getMessageNum(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylMessageNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylMessageNumModel messageModel) {
                        if (messageModel.code==1) {
                            if(messageModel.getData()!=null&&!messageModel.getData().equals("0")) {
                                tv_num.setText(messageModel.getData());
                                tv_num.setVisibility(View.VISIBLE);
                            }else {
                                tv_num.setVisibility(View.GONE);
                            }

                        }else if(messageModel.code== -10001) {

                        } else {
                            ToastUtil.showSuccessMsg(mActivity, messageModel.message);
                        }
                    }
                });
    }

    /**
     * 获取消息数量
     * @param messageNumHylEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessageNum(MessageNumHylEvent messageNumHylEvent) {
        getMessageNum();
    }

    /**
     * 账号切换时信息更新
     * @param changeAccountHylEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUserInfo(ChangeAccountHylEvent changeAccountHylEvent) {
        getMessageNum();
        myInfo();
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
