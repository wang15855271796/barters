package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HomeBaseModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/17
 */
public class HylFullAdapter extends BaseQuickAdapter<HomeBaseModel.DataBean.FullActiveBean.ActiveListBeanXX,BaseViewHolder> {

    List<HomeBaseModel.DataBean.FullActiveBean.ActiveListBeanXX> data;
    public HylFullAdapter(int layoutResId, @Nullable List<HomeBaseModel.DataBean.FullActiveBean.ActiveListBeanXX> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBaseModel.DataBean.FullActiveBean.ActiveListBeanXX item) {
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        TextView tv_price = helper.getView(R.id.tv_price);
        Glide.with(mContext).load(item.getPic()).into(iv_pic);
        tv_price.setText(item.getPrice());


    }
}
