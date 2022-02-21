package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylGoodListModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/26
 */
public class HylGoodsAdapter extends BaseQuickAdapter<HylGoodListModel.DataBean.ListBean,BaseViewHolder> {

    OnAddClickListener onAddClickListener;
    public HylGoodsAdapter(int layoutResId, @Nullable List<HylGoodListModel.DataBean.ListBean> data, OnAddClickListener onAddClickListener) {
        super(layoutResId, data);
        this.onAddClickListener = onAddClickListener;
    }


    @Override
    protected void convert(BaseViewHolder helper, final HylGoodListModel.DataBean.ListBean item) {
//        helper.setIsRecyclable(false);
        FrameLayout fl_container = helper.getView(R.id.fl_container);
        TextView tv_sale = helper.getView(R.id.tv_sale);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_spec = helper.getView(R.id.tv_spec);
        ImageView iv_add = helper.getView(R.id.iv_add);
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        ImageView iv_sold = helper.getView(R.id.iv_sold);
        tv_title.setText(item.getProductName());
        tv_price.setText(item.getMinMaxPrice());
        tv_spec.setText("规格："+item.getSpec());
        tv_sale.setText("销量："+item.getSaleNum());
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);

        if(item.getInventFlag()==0) {
            iv_sold.setVisibility(View.GONE);
            iv_add.setImageResource(R.mipmap.icon_add);
            iv_add.setEnabled(true);
        }else {
            iv_sold.setVisibility(View.VISIBLE);
            iv_add.setImageResource(R.mipmap.icon_add_saled);
            iv_add.setEnabled(false);
        }

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAddClickListener!=null) {
                    onAddClickListener.onAddClick(item.getMainId());
                }
            }
        });
    }

    public interface OnAddClickListener {
        void onAddClick(int mainId);
    }

}
