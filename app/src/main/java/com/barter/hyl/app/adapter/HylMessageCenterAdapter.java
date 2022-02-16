package com.barter.hyl.app.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylMessageModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/7/30
 */
public class HylMessageCenterAdapter extends BaseQuickAdapter<HylMessageModel.DataBean.ListBean,BaseViewHolder> {

    public HylMessageCenterAdapter(int layoutResId, @Nullable List<HylMessageModel.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylMessageModel.DataBean.ListBean item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_time = helper.getView(R.id.tv_time);

        tv_time.setText(item.getDateTime());
        tv_title.setText(item.getTitle());

        if(item.getReadFlag()==0) {
            //未读
            tv_title.setTextColor(Color.parseColor("#333333"));
        }else {
            tv_title.setTextColor(Color.parseColor("#999999"));
        }
    }
}
