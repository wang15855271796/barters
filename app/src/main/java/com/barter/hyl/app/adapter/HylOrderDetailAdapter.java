package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylOrderDetailModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/2
 */
public class HylOrderDetailAdapter extends BaseQuickAdapter<HylOrderDetailModel.DataBean.ProdsBean,BaseViewHolder> {

    private ImageView imageView;
    TextView coupon;
    RecyclerView rv_spec;
    TextView tv_old_price;
    TextView tv_price;
    TextView tv_spec;
    TextView textTitle;
    public HylOrderDetailAdapter(int layoutResId, @Nullable List<HylOrderDetailModel.DataBean.ProdsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylOrderDetailModel.DataBean.ProdsBean item) {
        textTitle = helper.getView(R.id.textTitle);
        tv_spec = helper.getView(R.id.tv_spec);
        tv_old_price = helper.getView(R.id.tv_old_price);
        tv_price = helper.getView(R.id.tv_price);
        coupon = helper.getView(R.id.coupon);
        imageView = helper.getView(R.id.imageView);
        rv_spec = helper.getView(R.id.rv_spec);
        textTitle.setText(item.getProductName());
        tv_price.setText("￥"+item.getTotalPrice());
        tv_spec.setText(item.getSpec());
        Glide.with(mContext).load(item.getDefaultPic()).into(imageView);
        HylOrderSpecAdapter orderPriceAdapter = new HylOrderSpecAdapter(R.layout.item_order_desc_hyl,item.getPrices());
        rv_spec.setLayoutManager(new LinearLayoutManager(mContext));
        rv_spec.setAdapter(orderPriceAdapter);

    }
}
