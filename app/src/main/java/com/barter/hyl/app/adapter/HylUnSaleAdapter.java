package com.barter.hyl.app.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylSettleModel;
import com.barter.hyl.app.view.RoundImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class HylUnSaleAdapter extends BaseQuickAdapter<HylSettleModel.DataBean.ProdsBean, BaseViewHolder> {

    public HylUnSaleAdapter(int layoutResId, @Nullable List<HylSettleModel.DataBean.ProdsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylSettleModel.DataBean.ProdsBean item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_spec = helper.getView(R.id.tv_spec);
        TextView tv_total_price = helper.getView(R.id.tv_total_price);
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        RecyclerView rv_price = helper.getView(R.id.rv_price);
        tv_title.setText(item.getProductName());
        tv_spec.setText("规格："+item.getSpec());
        tv_total_price.setText("￥"+item.getTotalPrice());
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);

        HylOrderPriceAdapter hylOrderPriceAdapter = new HylOrderPriceAdapter(R.layout.item_price_hyl,item.getPrices());
        rv_price.setLayoutManager(new LinearLayoutManager(mContext));
        rv_price.setAdapter(hylOrderPriceAdapter);

    }
}
