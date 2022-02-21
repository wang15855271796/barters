package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylSkillListModel;
import com.barter.hyl.app.view.Snap;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/31
 */
public class HylSkillTimeAdapter extends BaseQuickAdapter<HylSkillListModel.DataBean,BaseViewHolder> {

    public HylSkillTimeAdapter(int layoutResId, @Nullable List<HylSkillListModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylSkillListModel.DataBean item) {
        Snap snap = helper.getView(R.id.snap);
        TextView tv_desc = helper.getView(R.id.tv_desc);
        if(item.getFlag()==0) {
            //未开始
            tv_desc.setText("距开始");
        }else {
            tv_desc.setText("距结束");
        }
        snap.setTime(false,false,item.getNowTime(),item.getStartTime(),item.getEndTime());
        snap.start();
    }
}
