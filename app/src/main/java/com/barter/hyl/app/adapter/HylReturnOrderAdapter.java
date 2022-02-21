package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王文博} on 2019/5/15
 */
public class HylReturnOrderAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private TextView mReasonContent;
    public HylReturnOrderAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        mReasonContent = helper.getView(R.id.tv_reason);
        mReasonContent.setText(item);
    }
}
