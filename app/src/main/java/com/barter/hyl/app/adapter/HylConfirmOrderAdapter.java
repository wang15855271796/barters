package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylSettleModel;
import com.barter.hyl.app.view.RoundImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2021/8/24
 */
public class HylConfirmOrderAdapter extends BaseQuickAdapter<HylSettleModel.DataBean.ProdsBean,BaseViewHolder> {
    List<HylSettleModel.DataBean.ProdsBean.AdditionsBean>additionVOList1 = new ArrayList<>();
    List<HylSettleModel.DataBean.ProdsBean.AdditionsBean>additionVOList2 = new ArrayList<>();
    public HylConfirmOrderAdapter(int layoutResId, @Nullable List<HylSettleModel.DataBean.ProdsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylSettleModel.DataBean.ProdsBean item) {
        RecyclerView rv_given = helper.getView(R.id.rv_given);
        RecyclerView rv_coupon = helper.getView(R.id.rv_coupon);
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


        additionVOList1 = new ArrayList<>();
        additionVOList2 = new ArrayList<>();

        if(item.getAdditions()!=null) {
            for (int i = 0; i < item.getAdditions().size(); i++) {
                if(item.getAdditions().get(i).getType()==0) {
                    //优惠券
                    additionVOList1.add(item.getAdditions().get(i));
                }else {
                    //赠品
                    additionVOList2.add(item.getAdditions().get(i));
                }
            }
        }

        //赠品
        rv_given.setLayoutManager(new LinearLayoutManager(mContext));
        HylGivenOrderGoodsAdapter givenGoodsAdapter = new HylGivenOrderGoodsAdapter(R.layout.item_given_order_goods,additionVOList1);
        rv_given.setAdapter(givenGoodsAdapter);

        //赠优惠券
        rv_coupon.setLayoutManager(new LinearLayoutManager(mContext));
        HylOrderCouponAdapter hylOrderCouponAdapter = new HylOrderCouponAdapter(R.layout.item_order_coupon_hyl,additionVOList2);
        rv_coupon.setAdapter(hylOrderCouponAdapter);
    }
}
