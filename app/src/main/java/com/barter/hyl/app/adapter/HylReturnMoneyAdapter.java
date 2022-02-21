package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;

import android.widget.TextView;

import com.barter.hyl.app.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/13
 */
public class HylReturnMoneyAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public HylReturnMoneyAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tv_reason = helper.getView(R.id.tv_reason);
        tv_reason.setText(item);
    }
}
