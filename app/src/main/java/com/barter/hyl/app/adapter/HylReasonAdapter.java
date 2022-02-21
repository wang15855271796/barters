package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;

import android.widget.TextView;

import com.barter.hyl.app.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/14
 */
public class HylReasonAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    int selectionPosition = -1;
    public HylReasonAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tv_reason = helper.getView(R.id.tv_reason);
        tv_reason.setText(item);

        if(selectionPosition==helper.getAdapterPosition()) {
            helper.setVisible(R.id.iv_icon,true);
        }else {
            helper.setVisible(R.id.iv_icon,false);
        }

    }

    public void selectionPosition(int position){
        this.selectionPosition  = position;
    }

}
