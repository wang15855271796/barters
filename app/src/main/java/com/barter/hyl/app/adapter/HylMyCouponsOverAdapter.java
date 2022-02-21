package com.barter.hyl.app.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylMyCouponModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

/**
 * Created by ${王涛} on 2020/8/7(过期)
 */
public class HylMyCouponsOverAdapter extends BaseQuickAdapter<HylMyCouponModel.DataBean.ListBean,BaseViewHolder> {

    private TextView tv_style;
    private  TextView tv_user_factor;
    private  TextView tv_time;
    private  TextView tv_role;
    private  TextView tv_amount;
    private ImageView iv_status;
    private Context context;
    List<HylMyCouponModel.DataBean.ListBean> list;
    TextView tv_tip;
    public HylMyCouponsOverAdapter(int layoutResId, @Nullable List<HylMyCouponModel.DataBean.ListBean> data, Context context) {
        super(layoutResId, data);
        list=data;
        this.context=context;
    }


    @Override
    protected void convert(final BaseViewHolder helper, HylMyCouponModel.DataBean.ListBean item) {
        tv_tip=helper.getView(R.id.tv_tip);
        tv_style=helper.getView(R.id.tv_style);
        tv_user_factor=helper.getView(R.id.tv_user_factor);
        tv_time=helper.getView(R.id.tv_time);
        tv_role=helper.getView(R.id.tv_role);
        tv_amount=helper.getView(R.id.tv_amount);
        iv_status=helper.getView(R.id.iv_status);
        if(!TextUtils.isEmpty(item.getUseDesc())) {
            tv_user_factor.setText(item.getUseDesc());
            tv_user_factor.setVisibility(View.VISIBLE);
        }else {
            tv_user_factor.setVisibility(View.GONE);
        }
        tv_style.setText(item.getGiftName());
        tv_time.setText(item.getDateTime());
        tv_amount.setText(item.getAmount());

        tv_role.setText(item.getRole().get(0));

        tv_role.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                queryProd(item.getGiftDetailNo());
            }
        });
        if(item.getState().equals("ENABLED")){  // State== ENABLED   可用使用的优惠卷
            iv_status.setVisibility(View.GONE);

            tv_amount.setTextColor(Color.parseColor("#F54022"));
            tv_user_factor.setTextColor(Color.parseColor("#333333"));
            tv_style.setTextColor(Color.parseColor("#333333"));
            tv_tip.setTextColor(Color.parseColor("#F54022"));

        }else  if (item.getState().equals("USED")){//USED 已使用
            iv_status.setVisibility(View.VISIBLE);
            iv_status.setImageResource(R.mipmap.ic_user);

            tv_amount.setTextColor(Color.parseColor("#A1A1A1"));
            tv_user_factor.setTextColor(Color.parseColor("#A1A1A1"));
            tv_style.setTextColor(Color.parseColor("#A1A1A1"));
            tv_tip.setTextColor(Color.parseColor("#A1A1A1"));

        }else if(item.getState().equals("OVERTIME")){ //OVERTIME 过期
            iv_status.setVisibility(View.VISIBLE);
            iv_status.setImageResource(R.mipmap.ic_overdue);

            tv_amount.setTextColor(Color.parseColor("#A1A1A1"));
            tv_user_factor.setTextColor(Color.parseColor("#A1A1A1"));
            tv_style.setTextColor(Color.parseColor("#A1A1A1"));
            tv_tip.setTextColor(Color.parseColor("#A1A1A1"));
        }
    }
}
