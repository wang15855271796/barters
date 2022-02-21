package com.barter.hyl.app.adapter;

import android.graphics.Color;
import androidx.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylGoodCateModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/25
 */
public class HylCateAdapter extends BaseQuickAdapter<HylGoodCateModel.DataBean,BaseViewHolder> {

    int selectPosition = 0;
    public HylCateAdapter(int layoutResId, @Nullable List<HylGoodCateModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylGoodCateModel.DataBean item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_desc = helper.getView(R.id.tv_desc);
        tv_desc.setText(item.getName());
        Glide.with(mContext).load(item.getPic()).into(iv_icon);

        if(selectPosition == helper.getLayoutPosition()) {
            tv_desc.setBackgroundResource(R.drawable.shape_red_orders);
            tv_desc.setTextColor(Color.parseColor("#ffffff"));
        }else {
            tv_desc.setBackground(null);
            tv_desc.setTextColor(Color.parseColor("#333333"));
        }
    }

    public void selectPosition(int position) {
        this.selectPosition = position;
        notifyDataSetChanged();
    }
}
