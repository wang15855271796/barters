package com.barter.hyl.app.adapter;

import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.barter.hyl.app.R;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.view.RoundImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Arrays;
import java.util.List;

public class ImageViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private RoundImageView imageView;
    List<String> data;
    public ImageViewAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        imageView = helper.getView(R.id.iv_image);
        Glide.with(mContext).load(item).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> result = Arrays.asList(item.split(","));
                AppHelper.showPhotoDetailDialog(mContext, data, helper.getAdapterPosition());
            }
        });
    }
}
