package com.barter.hyl.app.adapter;

import android.util.Log;
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
public class HylRvIconAdapter extends BaseQuickAdapter<HomeBaseModel.DataBean.ClassifiesBean,BaseViewHolder> {

    public HylRvIconAdapter(int layout, List<HomeBaseModel.DataBean.ClassifiesBean> classifyList) {
        super(layout, classifyList);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBaseModel.DataBean.ClassifiesBean item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_desc = helper.getView(R.id.tv_desc);
        Glide.with(mContext).load(item.getPic()).into(iv_icon);
        tv_desc.setText(item.getName());
    }
}
