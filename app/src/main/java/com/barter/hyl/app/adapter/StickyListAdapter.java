package com.barter.hyl.app.adapter;

import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.BillDetailActivity;
import com.barter.hyl.app.model.HylMyBillModel;
import com.barter.hyl.app.view.EasyView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/22
 */
public class StickyListAdapter extends BaseQuickAdapter<HylMyBillModel.DataBean.ListBean.RecordsBean,BaseViewHolder> {
    OnItemDeleteListener onItemDeleteListener;
    public StickyListAdapter(int layoutResId, @Nullable List<HylMyBillModel.DataBean.ListBean.RecordsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final HylMyBillModel.DataBean.ListBean.RecordsBean item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_time = helper.getView(R.id.tv_time);
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        RelativeLayout rl_root = helper.getView(R.id.rl_root);
        LinearLayout ll_jump = helper.getView(R.id.ll_jump);
        EasyView es_swiper = helper.getView(R.id.es_swiper);
        LinearLayout ll_delete = helper.getView(R.id.ll_delete);
        LinearLayout ll_cancel = helper.getView(R.id.ll_cancel);
        tv_title.setText(item.getFlowRecordTypeName());
        tv_time.setText(item.getDateTime());
        if(item.getIconUrl()!=null && !item.getIconUrl().equals("")) {
            Glide.with(mContext).load(item.getIconUrl()).into(iv_pic);
        }
        es_swiper.setIos(false);//设置是否开启IOS阻塞式交互
        es_swiper.setLeftSwipe(true);//true往左滑动,false为往右侧滑动
        es_swiper.setSwipeEnable(true);//设置侧滑功能开关

        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,BillDetailActivity.class);
                intent.putExtra("id",item.getId());
                mContext.startActivity(intent);
            }
        });

        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                es_swiper.quickClose();
            }
        });

        ll_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemDeleteListener!=null) {
                    onItemDeleteListener.onItemDelete(helper.getAdapterPosition(),item.getId()+"");
                    es_swiper.quickClose();
                }
            }
        });

        ll_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if(item.getRecordType()==2) {
            //支出
            tv_price.setText(item.getAmount());
            tv_price.setTextColor(Color.parseColor("#FF2E00"));
        }else {
            tv_price.setText(item.getAmount());
            tv_price.setTextColor(Color.parseColor("#414141"));
        }
    }

    public interface OnItemDeleteListener {
        void onItemDelete(int pos,String id);
    }

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }

}
