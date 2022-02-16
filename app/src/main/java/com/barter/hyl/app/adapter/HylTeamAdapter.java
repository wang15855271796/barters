package com.barter.hyl.app.adapter;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HomeBaseModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/17
 */
public class HylTeamAdapter extends BaseQuickAdapter<HomeBaseModel.DataBean.TeamBean.ActiveListBeanX,BaseViewHolder> {

    List<HomeBaseModel.DataBean.TeamBean.ActiveListBeanX> data;
    public HylTeamAdapter(int item_active, List<HomeBaseModel.DataBean.TeamBean.ActiveListBeanX> teamList) {
        super(item_active, teamList);
        this.data = teamList;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBaseModel.DataBean.TeamBean.ActiveListBeanX item) {
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        TextView tv_price = helper.getView(R.id.tv_price);
        Glide.with(mContext).load(item.getPic()).into(iv_pic);
        tv_price.setText(item.getPrice());


    }
}
