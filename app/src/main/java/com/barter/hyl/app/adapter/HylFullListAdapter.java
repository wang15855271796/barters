package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylFullListModel;
import com.barter.hyl.app.view.RoundImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/31
 */
public class HylFullListAdapter extends BaseQuickAdapter<HylFullListModel.DataBean,BaseViewHolder> {

    public HylFullListAdapter(int layoutResId, @Nullable List<HylFullListModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylFullListModel.DataBean item) {
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_spec = helper.getView(R.id.tv_spec);
        TextView tv_price = helper.getView(R.id.tv_price);

        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        tv_price.setText(item.getMinMaxPrice());
        tv_spec.setText(item.getSpec());
        tv_title.setText(item.getProductName());
    }
}
