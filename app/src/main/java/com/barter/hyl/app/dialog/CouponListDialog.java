package com.barter.hyl.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylCouponListAdapter;
import com.barter.hyl.app.api.HomeApi;
import com.barter.hyl.app.event.GoToMarketHylEvent;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.HomeBaseModel;
import com.barter.hyl.app.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/4/17
 */
public class CouponListDialog extends Dialog {
    Context mContext;
    public RecyclerView recyclerView;
    ImageView iv_close;
    TextView tv_use;
    HomeBaseModel.DataBean.PopUpsBean couponListModel;
    private HylCouponListAdapter hylCouponListAdapter;
    public CouponListDialog(@NonNull Context context, HomeBaseModel.DataBean.PopUpsBean couponListModel) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_coupon_list_hyl);
        mContext = context;
        this.couponListModel = couponListModel;
        initView();
        initAction();

    }

    private void initView() {
        tv_use = (TextView) findViewById(R.id.tv_use);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        hylCouponListAdapter = new HylCouponListAdapter(R.layout.item_home_coupon_list,couponListModel.getGifts());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(hylCouponListAdapter);
        tv_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrivacyRead(couponListModel.getId(),couponListModel.getType());
                EventBus.getDefault().post(new GoToMarketHylEvent());
                dismiss();
            }
        });
    }

    private void initAction() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrivacyRead(couponListModel.getId(),couponListModel.getType());
            }
        });
    }

    /**
     * 是否已读
     * @param id
     * @param type
     */
    private void getPrivacyRead(int id,int type) {
        HomeApi.getRead(getContext(),id,type)
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
                            dismiss();
                        } else {
                            ToastUtil.showSuccessMsg(getContext(), baseModel.message);
                        }
                    }
                });
    }
}
