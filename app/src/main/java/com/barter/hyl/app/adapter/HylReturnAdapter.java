package com.barter.hyl.app.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylOrderDetailModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/22
 */
public class HylReturnAdapter extends BaseQuickAdapter<HylOrderDetailModel.DataBean.ReturnOrdersBean,BaseViewHolder> {

    public HylReturnAdapter(int layoutResId, @Nullable List<HylOrderDetailModel.DataBean.ReturnOrdersBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylOrderDetailModel.DataBean.ReturnOrdersBean item) {
        TextView tv_amount = helper.getView(R.id.tv_amount);
        TextView tv_return_desc = helper.getView(R.id.tv_return_desc);
        tv_amount.setText(item.getAmount());
        tv_return_desc.setText(item.getReturnDesc());
    }
}
