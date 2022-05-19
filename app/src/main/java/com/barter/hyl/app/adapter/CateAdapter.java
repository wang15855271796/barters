package com.barter.hyl.app.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.barter.hyl.app.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import java.util.List;

/**
 * Created by ${王涛} on 2021/1/8
 */
public class CateAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    int pos = 0;
    Onclick onclick;
    public CateAdapter(int layoutResId, @Nullable List<String> data, Onclick onclick) {
        super(layoutResId, data);
        this.onclick = onclick;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_title,item);
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        RelativeLayout rl_root = helper.getView(R.id.rl_root);
        if(pos==helper.getAdapterPosition()) {
            helper.setTextColor(R.id.tv_title, Color.parseColor("#FF2925"));
        }else {
            helper.setTextColor(R.id.tv_title, Color.parseColor("#333333"));
        }
    }


    public void setPosition(int position) {
        this.pos = position;
    }

    public interface Onclick {
        void getCate(String item);
    }
}
