package com.barter.hyl.app.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylSearchBillModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/22
 */
public class AccountSelectAdapter extends BaseQuickAdapter<HylSearchBillModel.DataBean.List2Bean,BaseViewHolder> {
    private TextView tv_content;
    private int selectPosition = -1;
    public AccountSelectAdapter(int layoutResId, @Nullable List<HylSearchBillModel.DataBean.List2Bean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylSearchBillModel.DataBean.List2Bean item) {
        tv_content=helper.getView(R.id.tv_content);
        tv_content.setText(item.getValue());

        if (selectPosition == helper.getLayoutPosition()) {
            tv_content.setBackgroundResource(R.drawable.shape_grey6);
            tv_content.setTextColor(Color.parseColor("#FF2925"));
        } else {
            tv_content.setBackgroundResource(R.drawable.shape_grey5);
            tv_content.setTextColor(Color.parseColor("#333333"));
        }

    }

    public void selectPosition(int position) {
        this.selectPosition = position;

        notifyDataSetChanged();
    }
}
