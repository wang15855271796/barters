package com.barter.hyl.app.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.barter.hyl.app.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/24
 */
class CheckJudgeIvAdpater extends BaseQuickAdapter<String,BaseViewHolder> {

    public CheckJudgeIvAdpater(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        Glide.with(mContext).load(item).into(iv_pic);
    }
}
