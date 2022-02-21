package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylOrderDetailModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/13
 */
public class HylOrderDetailsAdapter extends BaseQuickAdapter<HylOrderDetailModel.DataBean.ProdsBean,BaseViewHolder> {

    public HylOrderDetailsAdapter(int layoutResId, @Nullable List<HylOrderDetailModel.DataBean.ProdsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylOrderDetailModel.DataBean.ProdsBean item) {
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_num = helper.getView(R.id.tv_num);
        TextView tv_title = helper.getView(R.id.tv_title);

        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        tv_price.setText(item.getTotalPrice());
        tv_num.setText("退货数量："+item.getReturnNum());
        tv_title.setText(item.getProductName());
    }
}
