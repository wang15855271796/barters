package com.barter.hyl.app.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylGoodCateModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/25
 */
public class HylSecondAdapter extends BaseQuickAdapter<HylGoodCateModel.DataBean.SecondsBean,BaseViewHolder> {

    private ImageView iv_icon;
    private int selectPosition;
    OnItemClickListener onItemClickListener;
    OnEventClickListener mOnEventClickListener;
    public HylSecondAdapter(int layoutResId, @Nullable List<HylGoodCateModel.DataBean.SecondsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, HylGoodCateModel.DataBean.SecondsBean item) {
        iv_icon = helper.getView(R.id.iv_icon);
        View iv_point = helper.getView(R.id.iv_point);
//        if(helper.getAdapterPosition()==1) {
//            iv_icon.setImageResource(R.mipmap.icon_hot);
//            iv_icon.setVisibility(View.VISIBLE);
//        }else if(helper.getAdapterPosition()==2){
//            iv_icon.setImageResource(R.mipmap.icon_tip);
//            iv_icon.setVisibility(View.VISIBLE);
//        }else if(helper.getAdapterPosition()==3){
//            iv_icon.setImageResource(R.mipmap.icon_new);
//            iv_icon.setVisibility(View.VISIBLE);
//        }else if(helper.getAdapterPosition()==4){
//            iv_icon.setImageResource(R.mipmap.icon_coupon);
//            iv_icon.setVisibility(View.VISIBLE);
//        }else {
//            iv_icon.setVisibility(View.GONE);
//        }

        TextView tv_name = helper.getView(R.id.tv_name);
        LinearLayout rl_bg = helper.getView(R.id.rl_bg);
        tv_name.setText(item.getName());
        if(selectPosition == helper.getLayoutPosition()) {
            iv_point.setBackgroundColor(Color.parseColor("#FF2925"));
            tv_name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_name.setTextColor(Color.parseColor("#333333"));
            rl_bg.setBackgroundColor(Color.parseColor("#ffffff"));
        }else {
            tv_name.setBackground(null);
            iv_point.setBackgroundColor(Color.parseColor("#f3f3f3"));
            tv_name.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tv_name.setTextColor(Color.parseColor("#333333"));
            rl_bg.setBackgroundColor(Color.parseColor("#f3f3f3"));
        }

        if (onItemClickListener != null) {
            rl_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    onItemClickListener.onItemClick(v,helper.getAdapterPosition());

                }
            });
        }

    }

    public interface OnEventClickListener {
        void onEventClick(int position, int secondId);

    }

    public void setOnItemClickListeners(OnEventClickListener onEventClickListener) {
        mOnEventClickListener = onEventClickListener;
    }


//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }

    public void selectPosition(int position) {
        this.selectPosition = position;
        notifyDataSetChanged();
    }

}
