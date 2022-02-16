package com.barter.hyl.app.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylActiviteModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/5
 */
public class HylHotAdapter extends BaseQuickAdapter<HylActiviteModel.DataBean.ListBean,BaseViewHolder> {

    OnAddClickListener onAddClickListener;
    public HylHotAdapter(int layoutResId, @Nullable List<HylActiviteModel.DataBean.ListBean> data, OnAddClickListener onAddClickListener) {
        super(layoutResId, data);
        this.onAddClickListener = onAddClickListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HylActiviteModel.DataBean.ListBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_price = helper.getView(R.id.tv_price);
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        ImageView iv_add = helper.getView(R.id.iv_add);
        ImageView iv_sale_done = helper.getView(R.id.iv_sale_done);

        tv_name.setText(item.getProductName());
        tv_price.setText(item.getMinMaxPrice());
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);

        if(item.getInventFlag()==1) {
            iv_sale_done.setVisibility(View.VISIBLE);
        }else {
            iv_sale_done.setVisibility(View.GONE);
        }
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClickListener.onAddClick(helper.getAdapterPosition());
            }
        });
    }

    public interface OnAddClickListener {
        void onAddClick(int position);
    }

}
