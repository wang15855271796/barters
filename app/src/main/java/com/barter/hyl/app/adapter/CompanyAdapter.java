package com.barter.hyl.app.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.CompanyInfoModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CompanyAdapter extends BaseQuickAdapter<CompanyInfoModel.DataBean, BaseViewHolder> {

    public CompanyAdapter(int layoutResId, @Nullable List<CompanyInfoModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyInfoModel.DataBean item) {
        TextView tv_desc = helper.getView(R.id.tv_desc);
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        if(item.getCompanyPic()!=null&&!item.getCompanyPic().equals("")) {
            iv_pic.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getCompanyPic()).into(iv_pic);
        }else {
            iv_pic.setVisibility(View.GONE);
        }

        if(item.getCompanyDesc()!=null&&!item.getCompanyDesc().equals("")) {
            tv_desc.setText(item.getCompanyDesc());
        }
    }
}
