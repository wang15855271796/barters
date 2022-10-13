package com.barter.hyl.app.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.CouponUseActivity;
import com.barter.hyl.app.model.FullActiveDetailModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.DensityUtil;

import java.util.List;

public class CouponAdapter extends BaseQuickAdapter<FullActiveDetailModel.DataBean.SendGiftsBean,BaseViewHolder> {

    boolean isOpen;
    public CouponAdapter(int layoutResId, @Nullable List<FullActiveDetailModel.DataBean.SendGiftsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FullActiveDetailModel.DataBean.SendGiftsBean item) {
        helper.setText(R.id.tv_amount,item.getAmount());
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_time,item.getDateTime());
        helper.setText(R.id.tv_num,item.getSendNum());
        ImageView iv_arrow = helper.getView(R.id.iv_arrow);
        RecyclerView rv_role = helper.getView(R.id.rv_role);
        helper.setText(R.id.tv_spec,item.getUseInfo());
        TextView tv_detail = helper.getView(R.id.tv_detail);

        if(item.getGiftProdUseType().equals("1")) {
            tv_detail.setVisibility(View.VISIBLE);
        }else if(item.getGiftProdUseType().equals("2")) {
            tv_detail.setVisibility(View.VISIBLE);
        }else {
            tv_detail.setVisibility(View.GONE);
        }

        rv_role.setLayoutManager(new LinearLayoutManager(mContext));
        RoleAdapter roleAdapter = new RoleAdapter(R.layout.item_text1,item.getRole());
        rv_role.setAdapter(roleAdapter);

        iv_arrow.setImageResource(R.mipmap.icon_arrow_down);
        ViewGroup.LayoutParams lp = rv_role.getLayoutParams();
        roleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if(!isOpen) {
                    iv_arrow.setImageResource(R.mipmap.icon_arrow_up);
                    lp.height = DensityUtil.dip2px(RecyclerView.LayoutParams.WRAP_CONTENT,mContext);
                }else {
                    lp.height = DensityUtil.dip2px(15,mContext);
                    iv_arrow.setImageResource(R.mipmap.icon_arrow_down);
                }
                rv_role.setLayoutParams(lp);
                isOpen = !isOpen;
            }
        });

        lp.height = DensityUtil.dip2px(15,mContext);
        rv_role.setLayoutParams(lp);

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
