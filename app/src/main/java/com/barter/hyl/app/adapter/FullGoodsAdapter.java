package com.barter.hyl.app.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylCommonGoodsActivity;
import com.barter.hyl.app.model.FullActiveDetailModel;
import com.barter.hyl.app.view.RoundImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import java.util.List;

public class FullGoodsAdapter extends BaseQuickAdapter<FullActiveDetailModel.DataBean.SendGiftsBean, BaseViewHolder> {

    public FullGoodsAdapter(int layoutResId, @Nullable List<FullActiveDetailModel.DataBean.SendGiftsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FullActiveDetailModel.DataBean.SendGiftsBean item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_spec,item.getSpec());
        helper.setText(R.id.tv_num,item.getSendNum());
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        Glide.with(mContext).load(item.getPicture()).into(iv_pic);
        LinearLayout ll_root = helper.getView(R.id.ll_root);
        ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HylCommonGoodsActivity.class);
                intent.putExtra("mainId",item.getProductMainId());
                mContext.startActivity(intent);
            }
        });
    }
}
