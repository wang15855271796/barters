package com.barter.hyl.app.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/23
 */
public class HylSearchRecommendAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public HylSearchRecommendAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tv_spec = helper.getView(R.id.tv_spec);
        tv_spec.setText(item);

    }
}
