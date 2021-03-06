package com.barter.hyl.app.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylSearchBillModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/22
 */
public class SearchAccountAdapter extends BaseQuickAdapter<HylSearchBillModel.DataBean.List1Bean,BaseViewHolder> {

    private TextView mTvContent;
    private ImageView iv_select;

    private int selectPosition = -1;
    public SearchAccountAdapter(int layoutResId, @Nullable List<HylSearchBillModel.DataBean.List1Bean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylSearchBillModel.DataBean.List1Bean item) {
        mTvContent = helper.getView(R.id.tv_content);
        iv_select = helper.getView(R.id.iv_select);

        mTvContent.setText(item.getValue());

        if (selectPosition == helper.getLayoutPosition()) {
            mTvContent.setBackgroundResource(R.drawable.shape_grey6);
            mTvContent.setTextColor(Color.parseColor("#FF2925"));
            iv_select.setVisibility(View.GONE);
        } else {
            mTvContent.setBackgroundResource(R.drawable.shape_grey5);
            mTvContent.setTextColor(Color.parseColor("#333333"));
            iv_select.setVisibility(View.GONE);
        }

    }

    public void selectPosition(int position) {
        this.selectPosition = position;

        notifyDataSetChanged();
    }

}
