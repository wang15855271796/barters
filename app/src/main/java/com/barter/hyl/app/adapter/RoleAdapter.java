package com.barter.hyl.app.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.barter.hyl.app.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class RoleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public RoleAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tv_role = helper.getView(R.id.tv_role);
        tv_role.setText(item);
    }
}
