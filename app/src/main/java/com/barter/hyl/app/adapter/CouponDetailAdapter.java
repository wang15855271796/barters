package com.barter.hyl.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.dialog.CouponDetailDialog;
import com.barter.hyl.app.model.HylMyCouponDetailModel;
import com.barter.hyl.app.view.FlowLayout;
import com.barter.hyl.app.view.RoundImageView;
import com.barter.hyl.app.view.TagAdapter;
import com.barter.hyl.app.view.TagFlowLayout;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CouponDetailAdapter extends BaseQuickAdapter<HylMyCouponDetailModel.DataBean.ListBean, BaseViewHolder> {

    List<HylMyCouponDetailModel.DataBean.ListBean> data;
    OnAddClickListener onAddClickListener;
    public CouponDetailAdapter(int layoutResId, @Nullable List<HylMyCouponDetailModel.DataBean.ListBean> data, OnAddClickListener onAddClickListener) {
        super(layoutResId, data);
        this.data = data;
        this.onAddClickListener = onAddClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, HylMyCouponDetailModel.DataBean.ListBean item) {
        helper.setText(R.id.tv_title,item.getProductName());
        helper.setText(R.id.tv_price,item.getMinMaxPrice());
        TextView tv_add = helper.getView(R.id.tv_add);
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        TagFlowLayout rv_spec = helper.getView(R.id.rv_spec);
        TextView tv_style = helper.getView(R.id.tv_style);

        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);

        TagAdapter unAbleAdapter = new TagAdapter<String>(item.getSpecList()){
            @Override
            public View getView(FlowLayout parent, int position, String spec) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_specss,rv_spec, false);
                TextView tv_spec = view.findViewById(R.id.tv_spec);
                tv_spec.setText(spec);
                return view;
            }
        };
        rv_spec.setAdapter(unAbleAdapter);
        unAbleAdapter.notifyDataChanged();

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onAddClickListener!=null) {
                    onAddClickListener.onAddClick(item);
                }
            }
        });

        if(item.getSpecList().size()>3) {
            tv_style.setVisibility(View.VISIBLE);
        }else {
            tv_style.setVisibility(View.GONE);
        }

        tv_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onAddClickListener!=null) {
                    onAddClickListener.onAddClick(item);
                }
            }
        });
    }

    public interface OnAddClickListener {
        void onAddClick(HylMyCouponDetailModel.DataBean.ListBean item);
    }
}
