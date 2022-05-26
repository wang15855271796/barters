package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.app.model.CompanyListModel;
import com.barter.hyl.app.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CompanyListAdapter extends BaseQuickAdapter<CompanyListModel.DataBean, BaseViewHolder> {

    int pos = -1;
    public CompanyListAdapter(int layoutResId, @Nullable List<CompanyListModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyListModel.DataBean item) {
        helper.setText(R.id.tv_short,item.getShortName());
        helper.setText(R.id.tv_long,item.getCompanyName());
        ImageView iv_choose = helper.getView(R.id.iv_choose);
        TextView tv_phone = helper.getView(R.id.tv_phone);
        TextView tv_dis = helper.getView(R.id.tv_dis);
        if(item.getEnabled()==0) {
            iv_choose.setVisibility(View.GONE);
            tv_phone.setVisibility(View.VISIBLE);
            tv_dis.setVisibility(View.VISIBLE);
            iv_choose.setVisibility(View.GONE);
            helper.setTextColor(R.id.tv_short, Color.parseColor("#999999"));
            helper.setTextColor(R.id.tv_long,Color.parseColor("#999999"));
            if(item.getCustomerPhone()!=null&&!item.getCustomerPhone().equals("")) {
                tv_phone.setText("客服电话："+item.getCustomerPhone());
            }
        }else {
            helper.setTextColor(R.id.tv_short, Color.parseColor("#333333"));
            helper.setTextColor(R.id.tv_long,Color.parseColor("#999999"));
            tv_phone.setVisibility(View.GONE);
            tv_dis.setVisibility(View.GONE);
            if(pos==helper.getAdapterPosition()) {
                iv_choose.setVisibility(View.VISIBLE);
            }else {
                iv_choose.setVisibility(View.GONE);
            }
        }




    }

    public void setSelectionPos(int position) {
        this.pos = position;
    }
}
