package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;

import android.widget.ImageView;

import com.barter.hyl.app.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/1
 */
class HylCommentImageViewAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public HylCommentImageViewAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView iv_image = helper.getView(R.id.iv_image);
        Glide.with(mContext).load(item).into(iv_image);
    }
}
