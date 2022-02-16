package com.barter.hyl.app.adapter;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HomeBaseModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2020/4/17
 */
public class HylCouponListAdapter extends BaseQuickAdapter<HomeBaseModel.DataBean.PopUpsBean.GiftsBean,BaseViewHolder> {


    public HylCouponListAdapter(int layoutResId, List<HomeBaseModel.DataBean.PopUpsBean.GiftsBean> gifts) {
        super(layoutResId, gifts);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBaseModel.DataBean.PopUpsBean.GiftsBean item) {
        helper.setText(R.id.tv_money,item.getAmountStr());
        helper.setText(R.id.tv_user,item.getLimitAmtStr());
        helper.setText(R.id.tv_title,item.getGiftName());
        helper.setText(R.id.tv_factor,item.getRole().get(0));
        helper.setText(R.id.tv_date,item.getDateTime());
    }
}
