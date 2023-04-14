package com.barter.hyl.app.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.barter.hyl.app.R;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.model.HylPayResultModel;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InfoPayResultActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_state)
    TextView tv_state;
    private int payChannal;
    private String outTradeNo;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_pay_info_result);
    }


    Timer timer;
    LoadingDailog dialog;
    @Override
    public void setViewData() {
        tv_title.setText("支付结果");
        payChannal = getIntent().getIntExtra("payChannel",0);
        outTradeNo = getIntent().getStringExtra("outTradeNo");

        timer = new Timer();
        timer.schedule(task,0,2000);
        getPayResult(outTradeNo);
        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(mContext)
                .setMessage("获取支付结果中")
                .setCancelable(false)
                .setCancelOutside(true);
        dialog = loadBuilder.create();
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == keyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void setClickListener() {
       iv_back.setOnClickListener(this);
       tv_back.setOnClickListener(this);
    }

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if(payChannal == 14) {
                getPayResult(outTradeNo);
            }
        }
    };




    private void getPayResult(String outTradeNo) {
        OrderApi.getPayInfoResult(mContext, outTradeNo)
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
                        if (hylPayResultModel.getCode()==1) {
                            if(hylPayResultModel.getData()!=null) {
                                HylPayResultModel.DataBean data = hylPayResultModel.getData();
                                if(data.getState().equals("success")) {
                                    tv_state.setText("成功");
                                }else if(data.getState().equals("fail")) {
                                    tv_state.setText("失败");
                                }else {
                                    tv_state.setText("支付中");
                                }
                            }
                        } else {
                            AppHelper.showMsg(mContext, hylPayResultModel.getMessage());
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

            case R.id.tv_back:
                finish();
                break;
        }
    }
}
