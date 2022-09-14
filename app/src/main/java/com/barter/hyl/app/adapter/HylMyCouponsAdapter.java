package com.barter.hyl.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.CouponDetailActivity;
import com.barter.hyl.app.activity.MainActivity;
import com.barter.hyl.app.event.GoToMarketHylEvent;
import com.barter.hyl.app.model.HylMyCouponModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by ${daff} on 2018/9/20
 */
public class HylMyCouponsAdapter extends BaseQuickAdapter<HylMyCouponModel.DataBean.ListBean,BaseViewHolder> {
    private TextView tv_style;
    private  TextView tv_user_factor;
    private  TextView tv_time;
    private  TextView tv_role;
    private  TextView tv_amount;
    private ImageView iv_status;
    private  Context context;
    List<HylMyCouponModel.DataBean.ListBean> list;
    RelativeLayout rl_grey;
    TextView tv_tip;
    TextView tv_use;
    TextView tv_look;
    public HylMyCouponsAdapter(int layoutResId, @Nullable List<HylMyCouponModel.DataBean.ListBean> data, Context context) {
        super(layoutResId, data);
        list=data;
        this.context=context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, HylMyCouponModel.DataBean.ListBean item) {
        tv_use = helper.getView(R.id.tv_use);
        tv_tip=helper.getView(R.id.tv_tip);
        tv_style=helper.getView(R.id.tv_style);
        tv_user_factor=helper.getView(R.id.tv_user_factor);
        tv_time=helper.getView(R.id.tv_time);
        tv_role=helper.getView(R.id.tv_role);
        tv_amount=helper.getView(R.id.tv_amount);
        iv_status=helper.getView(R.id.iv_status);
        rl_grey = helper.getView(R.id.rl_grey);
        tv_role.setText(item.getRole().get(0));
        tv_look = helper.getView(R.id.tv_look);

        if(!TextUtils.isEmpty(item.getUseDesc())) {
            tv_user_factor.setText(item.getUseDesc());
            tv_user_factor.setVisibility(View.VISIBLE);
        }else {
            tv_user_factor.setVisibility(View.GONE);
        }
        tv_style.setText(item.getGiftName());

        tv_time.setText(item.getDateTime());
        tv_amount.setText(item.getAmount());

        tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getUseProd()==0) {
                    //分类
                    context.startActivity(new Intent(context, MainActivity.class));
                    EventBus.getDefault().post(new GoToMarketHylEvent());
                }else {
                    Intent intent = new Intent(mContext, CouponDetailActivity.class);
                    intent.putExtra("poolNo",item.getPoolNo());
                    intent.putExtra("giftName",item.getGiftName());
                    mContext.startActivity(intent);
                }
            }
        });

        if(item.getState().equals("ENABLED")){  // State== ENABLED   可用使用的优惠卷
            iv_status.setVisibility(View.GONE);
            rl_grey.setVisibility(View.GONE);

            tv_amount.setTextColor(Color.parseColor("#F54022"));
            tv_user_factor.setTextColor(Color.parseColor("#333333"));
            tv_style.setTextColor(Color.parseColor("#333333"));
            tv_tip.setTextColor(Color.parseColor("#F54022"));

        }else  if (item.getState().equals("USED")){//USED 已使用
            rl_grey.setVisibility(View.VISIBLE);
            iv_status.setVisibility(View.VISIBLE);
            iv_status.setImageResource(R.mipmap.ic_user);

            tv_amount.setTextColor(Color.parseColor("#A1A1A1"));
            tv_user_factor.setTextColor(Color.parseColor("#A1A1A1"));
            tv_style.setTextColor(Color.parseColor("#A1A1A1"));
            tv_tip.setTextColor(Color.parseColor("#A1A1A1"));

        }else if(item.getState().equals("OVERTIME")){ //OVERTIME 过期
            iv_status.setVisibility(View.VISIBLE);
            rl_grey.setVisibility(View.GONE);
            iv_status.setImageResource(R.mipmap.ic_overdue);

            tv_amount.setTextColor(Color.parseColor("#A1A1A1"));
            tv_user_factor.setTextColor(Color.parseColor("#A1A1A1"));
            tv_style.setTextColor(Color.parseColor("#A1A1A1"));
            tv_tip.setTextColor(Color.parseColor("#A1A1A1"));
        }
    }

}
