package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylPayListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ${王涛} on 2020/8/31
 */
public class HylPayListAdapter extends BaseQuickAdapter<HylPayListModel.DataBean,BaseViewHolder> {
    int selectionPosition = 0;
    ImageView iv_gou;
    TextView tv_title;
    ImageView iv_pic;
    String total;
    TextView tv_price;
    BigDecimal totals;
    public HylPayListAdapter(int layoutResId, @Nullable List<HylPayListModel.DataBean> data,String total) {
        super(layoutResId, data);
        this.total = total;
    }

    @Override
    protected void convert(BaseViewHolder helper, HylPayListModel.DataBean item) {
        iv_pic = helper.getView(R.id.iv_pic);
        iv_gou = helper.getView(R.id.iv_gou);
        LinearLayout ll_price = helper.getView(R.id.ll_price);
        tv_title = helper.getView(R.id.tv_title);
        tv_price = helper.getView(R.id.tv_price);
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_desc = helper.getView(R.id.tv_desc);
        tv_title.setText(item.getPayChannelName());
        if(total.contains("￥")) {
            totals = new BigDecimal(total.substring(1));
        }else {
            totals = new BigDecimal(total);
        }


        if(item.getPayChannel()==3) {
            iv_icon.setImageResource(R.mipmap.icon_chat);
        }else if(item.getPayChannel()==2) {
            iv_icon.setImageResource(R.mipmap.icon_pay);
        }else {
            iv_icon.setImageResource(R.mipmap.icon_dao);
        }
        if(item.getAmtVo()!=null) {
            String remainAmt = item.getAmtVo().getRemainAmt();
            BigDecimal remain = new BigDecimal(remainAmt);
            if(item.getPayChannel()==17) {
                if(item.getLimitType()==1) {
                    tv_desc.setText("总额度："+item.getAmtVo().getTotalAmt()+"元,剩余"
                            +item.getAmtVo().getRemainAmt()+"元,待还款金额");
                    tv_price.setText(item.getAmtVo().getUseAmt());
                    ll_price.setVisibility(View.VISIBLE);
                    if(totals.compareTo(remain)==1) {
                        //总金额大于剩余金额
                        iv_pic.setVisibility(View.GONE);
                        tv_title.setTextColor(Color.parseColor("#999999"));
                    }else {
                        iv_pic.setVisibility(View.VISIBLE);
                        tv_title.setTextColor(Color.parseColor("#333333"));
                    }
                }else {
                    ll_price.setVisibility(View.GONE);
                }
            }
        }

        iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnEventClickListener!=null) {
                    mOnEventClickListener.onEventClick(helper.getAdapterPosition());
                }
            }
        });
//        if(helper.getAdapterPosition()==0) {
//            helper.getView(R.id.iv_recomend).setVisibility(View.VISIBLE);
//        }else {
//            helper.getView(R.id.iv_recomend).setVisibility(View.GONE);
//        }
//
        if(selectionPosition==helper.getAdapterPosition()) {
            iv_pic.setImageResource(R.mipmap.checkbax_yes);
        }else {
            iv_pic.setImageResource(R.mipmap.checkbox_no);
        }
    }

    public void selectionPosition(int position){
        this.selectionPosition  = position;
        notifyDataSetChanged();
    }

    public interface OnEventClickListener {
        void onEventClick(int position);

    }

    OnEventClickListener mOnEventClickListener;
    public void setOnItemClickListener(OnEventClickListener onEventClickListener) {
        mOnEventClickListener = onEventClickListener;
    }


}
