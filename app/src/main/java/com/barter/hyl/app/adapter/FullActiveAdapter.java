package com.barter.hyl.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.LoginActivity;
import com.barter.hyl.app.constant.StringHelper;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.dialog.FullDetailDialog;
import com.barter.hyl.app.model.FullActiveDetailModel;
import com.barter.hyl.app.view.RoundImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/8
 */
public class FullActiveAdapter extends BaseQuickAdapter<FullActiveDetailModel.DataBean.ProdsBean, BaseViewHolder> {
    Activity activity;
    public FullActiveAdapter(int layoutResId, @Nullable List<FullActiveDetailModel.DataBean.ProdsBean> data, Activity activity) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, FullActiveDetailModel.DataBean.ProdsBean item) {
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        ImageView iv_not_send = helper.getView(R.id.iv_not_send);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_spec = helper.getView(R.id.tv_spec);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_ok = helper.getView(R.id.tv_ok);
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        tv_price.setText(item.getMinMaxPrice());
        tv_title.setText(item.getProductName());
        tv_spec.setText(item.getSpec());

        if(item.getNotSend()==1) {
            iv_not_send.setVisibility(View.VISIBLE);
        }else {
            iv_not_send.setVisibility(View.GONE);
        }

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    FullDetailDialog fullDialog = new FullDetailDialog(activity, item.getProductMainId());
                    fullDialog.show();
                }else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }

            }
        });
    }
}
