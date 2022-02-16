package com.barter.hyl.app.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylPayListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2020/8/31
 */
public class HylPayListAdapter extends BaseQuickAdapter<HylPayListModel.DataBean,BaseViewHolder> {
    int selectionPosition = 0;
    ImageView iv_gou;
    TextView tv_title;
    ImageView iv_pic;
    public HylPayListAdapter(int layoutResId, @Nullable List<HylPayListModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylPayListModel.DataBean item) {
        iv_pic = helper.getView(R.id.iv_pic);
        iv_gou = helper.getView(R.id.iv_gou);
        tv_title = helper.getView(R.id.tv_title);
        tv_title.setText(item.getPayChannelName());

//        if(helper.getAdapterPosition()==0) {
//            helper.getView(R.id.iv_recomend).setVisibility(View.VISIBLE);
//        }else {
//            helper.getView(R.id.iv_recomend).setVisibility(View.GONE);
//        }
//
        if(selectionPosition==helper.getAdapterPosition()) {
            iv_pic.setImageResource(R.mipmap.checkbax_yes);
        }else {
            iv_pic.setImageResource(R.mipmap.checkbox_no);
        }
    }

    public void selectionPosition(int position){
        this.selectionPosition  = position;
        notifyDataSetChanged();
    }

}
