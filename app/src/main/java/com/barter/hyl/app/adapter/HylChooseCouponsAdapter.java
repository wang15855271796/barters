package com.barter.hyl.app.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylChooseCouponModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author daff
 * @date 2018/9/23.
 * 备注 选择优惠券
 */
public class HylChooseCouponsAdapter extends BaseQuickAdapter<HylChooseCouponModel.DataBean, BaseViewHolder> {

    private TextView tv_style;
    private  TextView tv_user_factor;
    private  TextView tv_time;
    private  TextView tv_role;
    private  TextView tv_amount;
    TextView tv_desc;
    List<HylChooseCouponModel.DataBean> list;
    TextView tv_tip;
    ImageOnclick imageOnclick;
    ImageView iv_select;
    public HylChooseCouponsAdapter(int layoutResId, @Nullable List<HylChooseCouponModel.DataBean> data, ImageOnclick imageOnclick) {
        super(layoutResId, data);
        list=data;
        this.imageOnclick = imageOnclick;
    }

    public void setStat() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setFlags(false);
            iv_select.setBackgroundResource(R.mipmap.ic_pay_no);
        }
        notifyDataSetChanged();

    }

    @Override
    protected void convert(final BaseViewHolder helper, final HylChooseCouponModel.DataBean item) {
        tv_tip=helper.getView(R.id.tv_tip);
        tv_style=helper.getView(R.id.tv_style);
        tv_desc=helper.getView(R.id.tv_desc);
        tv_user_factor=helper.getView(R.id.tv_user_factor);
        tv_time=helper.getView(R.id.tv_time);
        tv_role=helper.getView(R.id.tv_role);
        tv_amount=helper.getView(R.id.tv_amount);
        iv_select = helper.getView(R.id.iv_select);

        tv_time.setText(item.getDateTime());
        tv_amount.setText(item.getAmount());

        if (item.getRole().size()>0){
            tv_role.setText(item.getRole().get(0));
            tv_role.setVisibility(View.VISIBLE);

        }else {
            tv_role.setText("");
            tv_role.setVisibility(View.INVISIBLE);
        }

        if(!TextUtils.isEmpty(item.getLimitAmtStr())) {
            tv_user_factor.setText(item.getLimitAmtStr());
            tv_user_factor.setVisibility(View.VISIBLE);
        }else {
            tv_user_factor.setVisibility(View.GONE);
        }
        tv_style.setText(item.getGiftName());

        iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageOnclick.Onclick(helper.getLayoutPosition(), item.getGiftDetailNo());
            }
        });

        iv_select.setBackgroundResource(item.isFlags() ? R.mipmap.ic_pay_ok : R.mipmap.ic_pay_no);
        if(item.getState().equals("ENABLED")){  // State== ENABLED   可用使用的优惠卷
            tv_amount.setTextColor(Color.parseColor("#F54022"));
            tv_user_factor.setTextColor(Color.parseColor("#333333"));
            tv_style.setTextColor(Color.parseColor("#333333"));
            tv_tip.setTextColor(Color.parseColor("#F54022"));
            iv_select.setEnabled(true);
        }else  if (item.getState().equals("USED")){//USED 已使用
            tv_desc.setText(item.getRole().get(0));
            tv_amount.setTextColor(Color.parseColor("#A1A1A1"));
            tv_user_factor.setTextColor(Color.parseColor("#A1A1A1"));
            tv_style.setTextColor(Color.parseColor("#A1A1A1"));
            tv_tip.setTextColor(Color.parseColor("#A1A1A1"));
            iv_select.setEnabled(false);
        }else if(item.getState().equals("OVERTIME")){ //OVERTIME 过期
            tv_amount.setTextColor(Color.parseColor("#A1A1A1"));
            tv_user_factor.setTextColor(Color.parseColor("#A1A1A1"));
            tv_style.setTextColor(Color.parseColor("#A1A1A1"));
            tv_tip.setTextColor(Color.parseColor("#A1A1A1"));
            iv_select.setEnabled(false);
        }else if(item.getState().equals("UN_ENABLED")) {
            tv_amount.setTextColor(Color.parseColor("#A1A1A1"));
            tv_user_factor.setTextColor(Color.parseColor("#A1A1A1"));
            tv_style.setTextColor(Color.parseColor("#A1A1A1"));
            tv_tip.setTextColor(Color.parseColor("#A1A1A1"));
            iv_select.setEnabled(false);
        }
    }

    public interface ImageOnclick {
        void Onclick(int position, String giftDetailNo);
    }
}