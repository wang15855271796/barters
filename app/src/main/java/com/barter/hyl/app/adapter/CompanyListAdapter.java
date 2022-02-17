package com.barter.hyl.app.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.barter.app.model.CompanyListModel;
import com.barter.hyl.app.R;
import com.bumptech.glide.Glide;
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
        if(pos==helper.getAdapterPosition()) {
            iv_choose.setVisibility(View.VISIBLE);
        }else {
            iv_choose.setVisibility(View.GONE);
        }
    }

    public void setSelectionPos(int position) {
        this.pos = position;
    }
}
