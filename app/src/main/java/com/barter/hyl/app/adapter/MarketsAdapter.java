package com.barter.hyl.app.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.ShopDetailActivity;
import com.barter.hyl.app.model.InfoListModel;
import com.barter.hyl.app.utils.StringUtil;
import com.barter.hyl.app.view.RoundImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MarketsAdapter extends BaseQuickAdapter<InfoListModel.DataBean.ListBean, BaseViewHolder> {
    ImageSpan imageSpan;
    public MarketsAdapter(int layoutResId, @Nullable List<InfoListModel.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InfoListModel.DataBean.ListBean item) {
        try {
            helper.setIsRecyclable(false);
//            String phoneNum = item.getPhone().substring(0, 3) + "****" + item.getPhone().substring(7, 11);
//            helper.setText(R.id.tv_phone,phoneNum);
            ImageView iv_player = helper.getView(R.id.iv_player);
            RoundImageView iv_pic = helper.getView(R.id.iv_pic);
            if(item.getVideoCoverUrl()!=null) {
                iv_player.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(item.getVideoCoverUrl()).into(iv_pic);
            }else {
                if(item.getPictureList()!=null&&item.getPictureList().size()>0) {
                    iv_player.setVisibility(View.GONE);
                    Glide.with(mContext).load(item.getPictureList().get(0)).into(iv_pic);
                }
            }

            if(item.getMsgType().equals("1")) {
                helper.setText(R.id.tv_style,"店铺转让");
            }else if(item.getMsgType().equals("2")) {
                helper.setText(R.id.tv_style,"器具转让");
            }else if(item.getMsgType().equals("3")) {
                helper.setText(R.id.tv_style, "厨师招聘");
            }else {
                helper.setText(R.id.tv_style, "其他信息");
            }

            TextView tv_title = helper.getView(R.id.tv_title);
            SpannableString spannableString = new SpannableString(""+item.getContent());
            spannableString.setSpan(imageSpan, 0, 1,  Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            tv_title.setText(spannableString);
            helper.setText(R.id.tv_time,item.getCreateTime()+"发布");
            helper.setText(R.id.tv_num,item.getBrowseNum()+"人看过");
            helper.setText(R.id.tv_address,"地址:"+item.getAreaName());
            helper.getView(R.id.rl_group).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ShopDetailActivity.class);
                    intent.putExtra("msgId",item.getMsgId());
                    mContext.startActivity(intent);
                }
            });
        }catch (Exception e) {

        }

    }
}
