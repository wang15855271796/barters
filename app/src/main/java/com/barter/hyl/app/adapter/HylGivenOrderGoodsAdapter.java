package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylSettleModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/27
 */
public class HylGivenOrderGoodsAdapter extends BaseQuickAdapter<HylSettleModel.DataBean.AdditionsBean,BaseViewHolder> {

    public HylGivenOrderGoodsAdapter(int layoutResId, @Nullable List<HylSettleModel.DataBean.AdditionsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylSettleModel.DataBean.AdditionsBean item) {
        helper.setIsRecyclable(false);
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_icon);
        ImageView iv_finish = helper.getView(R.id.iv_finish);
        ImageView iv_flag = helper.getView(R.id.iv_flag);
        TextView tv_bg = helper.getView(R.id.tv_bg);
        helper.setText(R.id.tv_title,item.getProdName());
        helper.setText(R.id.tv_spec,"规格:"+item.getSpec());
        helper.setVisible(R.id.tv_stock,false);
        helper.setText(R.id.tv_given_num,"赠:"+item.getSendNum());

        if(item.getAdditionFlag()!=null) {
            iv_flag.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getAdditionFlag()).into(iv_flag);
        }else {
            iv_finish.setVisibility(View.GONE);
        }
    }
}
