package com.barter.hyl.app.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.FullActiveActivity;
import com.barter.hyl.app.model.HylFullListModel;
import com.barter.hyl.app.view.RoundImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/8
 */
public class FullImageViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public FullImageViewAdapter(int item_full_img,  List<String> pics) {
        super(item_full_img, pics);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        Glide.with(mContext).load(item).into(iv_pic);
    }
}
