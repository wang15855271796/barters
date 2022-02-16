package com.barter.hyl.app.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylOrderDetailActivity;
import com.barter.hyl.app.model.BillDetailModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ${王涛} on 2021/10/25
 */
public class BillConnectionAdapter extends BaseQuickAdapter<BillDetailModel.DataBean.OrdersBean,BaseViewHolder> {

    public BillConnectionAdapter(int layoutResId, @Nullable List<BillDetailModel.DataBean.OrdersBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final BillDetailModel.DataBean.OrdersBean item) {
        helper.setText(R.id.tv_connect_order,item.getOrderStatus()+"");
        helper.setText(R.id.tv_orderId,item.getOrderId());
        helper.setText(R.id.tv_time,item.getOrderTime());
        helper.setText(R.id.tv_amount,item.getOrderAmt());
        RelativeLayout rl_order = helper.getView(R.id.rl_order);

        rl_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mContext,HylOrderDetailActivity.class);
                intent.putExtra("orderId" ,item.getOrderId());
                intent.putExtra("status" ,item.getOrderStatus());
                mContext.startActivity(intent);
            }
        });
    }
}
