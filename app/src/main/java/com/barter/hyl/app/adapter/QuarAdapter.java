package com.barter.hyl.app.adapter;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.barter.hyl.app.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class QuarAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public QuarAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        ImageView iv_pic = helper.getView(R.id.iv_pic);
        if(item!=null && !item.equals("")) {
            Glide.with(mContext).load(item).into(iv_pic);

        }
    }
}
