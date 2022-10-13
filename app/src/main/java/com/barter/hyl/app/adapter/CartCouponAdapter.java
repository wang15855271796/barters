package com.barter.hyl.app.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.barter.hyl.app.R;
import com.barter.hyl.app.dialog.CouponFullListDialog;
import com.barter.hyl.app.model.HylCartListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CartCouponAdapter extends BaseQuickAdapter<HylCartListModel.DataBean.ProdsBeanX.AdditionsBean,BaseViewHolder> {
    public CartCouponAdapter(int layoutResId, @Nullable List<HylCartListModel.DataBean.ProdsBeanX.AdditionsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylCartListModel.DataBean.ProdsBeanX.AdditionsBean item) {

        LinearLayout rl_root = helper.getView(R.id.rl_root);
        ImageView iv_head = helper.getView(R.id.iv_head);
        TextView tv_desc = helper.getView(R.id.tv_desc);
        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(item.getName());

        TextView tv_num = helper.getView(R.id.tv_num);
        tv_num.setText("*"+item.getSendNumDesc());

        if(item.getAdditionFlag()==2) {
            iv_head.setImageResource(R.mipmap.icon_grey_head);
            tv_name.setBackgroundResource(R.mipmap.icon_grey_content);
            tv_name.setTextColor(Color.parseColor("#ffffff"));
            tv_num.setTextColor(Color.parseColor("#B2B2B2"));
            tv_desc.setVisibility(View.VISIBLE);
        }else {
            iv_head.setImageResource(R.mipmap.icon_red_head);
            tv_name.setBackgroundResource(R.mipmap.icon_pink_content);
            tv_name.setTextColor(Color.parseColor("#FF0026"));
            tv_num.setTextColor(Color.parseColor("#FF0026"));
            tv_desc.setVisibility(View.GONE);
        }

        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CouponFullListDialog couponFullListDialog = new CouponFullListDialog(mContext,
                        item.getGiftPoolNo(),item.getType()+"",item.getName());
                couponFullListDialog.show();

            }
        });
    }
}
