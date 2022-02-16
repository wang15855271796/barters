package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppConstant;
import com.barter.hyl.app.model.HylPayResultModel;
import com.barter.hyl.app.utils.ToastUtil;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/9/7
 */
public class DeliverPayResult extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_pay_way)
    TextView tv_pay_way;
    @BindView(R.id.tv_pay_time)
    TextView tv_pay_time;
    @BindView(R.id.tv_pay_num)
    TextView tv_pay_num;
    @BindView(R.id.tv_state)
    TextView tv_state;
    @BindView(R.id.iv_state)
    ImageView iv_state;
    @BindView(R.id.tv_order_look)
    TextView tv_order_look;
    int payChannel;
    String outTradeNo;
    String orderId;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        payChannel = bundle.getInt(AppConstant.PAYCHANNAL);
        outTradeNo = bundle.getString(AppConstant.OUTTRADENO);
        orderId = bundle.getString(AppConstant.ORDERID);
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_pay_result_hyl);
    }

    @Override
    public void setViewData() {
        tv_title.setText("支付详情");
        getPayResult();
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
        tv_order_look.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_order_look:
                Intent intent = new Intent(mContext,HylOrderDetailActivity.class);
                intent.putExtra("orderId",orderId);
                startActivity(intent);
                finish();
                break;
        }
    }

    /**
     *获取支付结果信息
     */
    private void getPayResult() {
        OrderApi.getPayResult(mActivity,outTradeNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylPayResultModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HylPayResultModel hylPayResultModel) {
                        if(hylPayResultModel.getCode()==1) {
                            if(hylPayResultModel.getData()!=null) {
                                HylPayResultModel.DataBean data = hylPayResultModel.getData();
                                tv_pay_way.setText(data.getPayChannel());
                                tv_pay_time.setText(data.getPayTime());
                                tv_pay_num.setText(data.getTradeNo());

                                if(data.getState().equals("success")) {
                                    tv_state.setText("成功");
                                    iv_state.setImageResource(R.mipmap.icon_pay_success);
                                }else if(data.getState().equals("fail")) {
                                    tv_state.setText("失败");
                                    iv_state.setImageResource(R.mipmap.icon_pay_fail);
                                }else {
                                    tv_state.setText("支付中");
                                    iv_state.setImageResource(R.mipmap.icon_pay_fail);
                                }
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mContext, hylPayResultModel.getMessage());
                        }
                    }
                });
    }

}
