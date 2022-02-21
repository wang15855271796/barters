package com.barter.hyl.app.adapter;

import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.BillDetailActivity;
import com.barter.hyl.app.model.HylMyBillModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/22
 */
public class StickyListAdapter extends BaseQuickAdapter<HylMyBillModel.DataBean.ListBean.RecordsBean,BaseViewHolder> {

    public StickyListAdapter(int layoutResId, @Nullable List<HylMyBillModel.DataBean.ListBean.RecordsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final HylMyBillModel.DataBean.ListBean.RecordsBean item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_time = helper.getView(R.id.tv_time);
        LinearLayout ll_jump = helper.getView(R.id.ll_jump);

        tv_title.setText(item.getFlowRecordTypeName());
        tv_time.setText(item.getDateTime());

        ll_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,BillDetailActivity.class);
                intent.putExtra("id",item.getId());
                mContext.startActivity(intent);
            }
        });

        if(item.getRecordType()==2) {
            //支出
            tv_price.setText(item.getAmount());
            tv_price.setTextColor(Color.parseColor("#20A852"));
        }else {
            tv_price.setText(item.getAmount());
            tv_price.setTextColor(Color.parseColor("#FF2925"));
        }
    }
}
