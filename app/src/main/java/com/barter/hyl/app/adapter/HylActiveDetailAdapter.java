package com.barter.hyl.app.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.barter.hyl.app.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/27
 */
public class HylActiveDetailAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public HylActiveDetailAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView iv_image = helper.getView(R.id.iv_image);
        Glide.with(mContext).load(item).into(iv_image);

    }
}
