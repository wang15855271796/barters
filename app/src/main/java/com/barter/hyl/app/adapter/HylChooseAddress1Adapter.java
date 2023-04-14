package com.barter.hyl.app.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylAddressListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class HylChooseAddress1Adapter extends BaseQuickAdapter<HylAddressListModel.DataBean.ListBean, BaseViewHolder> {

    public HylChooseAddress1Adapter(int layoutResId, @Nullable List<HylAddressListModel.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylAddressListModel.DataBean.ListBean item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_num = helper.getView(R.id.tv_num);
        CheckBox iv_check = helper.getView(R.id.iv_check);
        ImageView iv_edit = helper.getView(R.id.iv_edit);
        tv_title.setText(item.getDetailAddress());
        tv_name.setText(item.getUserName());
        tv_num.setText(item.getContactPhone());
        iv_check.setVisibility(View.GONE);
        iv_edit.setVisibility(View.GONE);
        tv_title.setTextColor(Color.parseColor("#999999"));
        tv_name.setTextColor(Color.parseColor("#999999"));
        tv_num.setTextColor(Color.parseColor("#999999"));
    }
}
