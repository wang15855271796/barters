package com.barter.hyl.app.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.CouponUseActivity;
import com.barter.hyl.app.model.FullActiveDetailModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CouponAdapter extends BaseQuickAdapter<FullActiveDetailModel.DataBean.SendGiftsBean,BaseViewHolder> {
    public CouponAdapter(int layoutResId, @Nullable List<FullActiveDetailModel.DataBean.SendGiftsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FullActiveDetailModel.DataBean.SendGiftsBean item) {
        helper.setText(R.id.tv_amount,item.getAmount());
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_time,item.getDateTime());
        helper.setText(R.id.tv_num,item.getSendNum());
        helper.setText(R.id.tv_role,item.getRole().get(0));
        helper.setText(R.id.tv_spec,item.getUseInfo());
        TextView tv_detail = helper.getView(R.id.tv_detail);

        if(item.getGiftProdUseType().equals("1")) {
            tv_detail.setVisibility(View.VISIBLE);
        }else if(item.getGiftProdUseType().equals("2")) {
            tv_detail.setVisibility(View.VISIBLE);
        }else {
            tv_detail.setVisibility(View.GONE);
        }

        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getGiftProdUseType().equals("1")) {
                    Intent intent = new Intent(mContext, CouponUseActivity.class);
                    intent.putExtra("type",item.getGiftProdUseType());
                    intent.putExtra("poolNo",item.getPoolNo());
                    intent.putExtra("name",item.getName());
                    mContext.startActivity(intent);
                    tv_detail.setVisibility(View.VISIBLE);
                }else if(item.getGiftProdUseType().equals("2")) {
                    Intent intent = new Intent(mContext, CouponUseActivity.class);
                    intent.putExtra("type",item.getGiftProdUseType());
                    intent.putExtra("poolNo",item.getPoolNo());
                    intent.putExtra("name",item.getName());
                    mContext.startActivity(intent);
                    tv_detail.setVisibility(View.VISIBLE);
                }else {
                    tv_detail.setVisibility(View.GONE);
                }
            }
        });
    }
}
