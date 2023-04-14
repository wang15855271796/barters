package com.barter.hyl.app.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
    ImageView iv_player;
    public ImageViewAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        imageView = helper.getView(R.id.iv_image);
        iv_player = helper.getView(R.id.iv_player);
        Glide.with(mContext).load(item).into(imageView);
        if(item.contains(".mp4")) {
            iv_player.setVisibility(View.VISIBLE);
        }else {
            iv_player.setVisibility(View.GONE);
        }
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<String> result = Arrays.asList(item.split(","));
//                AppHelper.showPhotoDetailDialog(mContext, data, helper.getAdapterPosition());
//            }
//        });
    }
}
