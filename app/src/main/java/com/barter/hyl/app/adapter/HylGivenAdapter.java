package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylSettleModel;
import com.barter.hyl.app.view.RoundImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/24
 */
public class HylGivenAdapter extends BaseQuickAdapter<HylSettleModel.DataBean.ProdsBean.AdditionsBean,BaseViewHolder> {

    public HylGivenAdapter(int layoutResId, @Nullable List<HylSettleModel.DataBean.ProdsBean.AdditionsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylSettleModel.DataBean.ProdsBean.AdditionsBean item) {
        RoundImageView iv_icon = helper.getView(R.id.iv_icon);
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_icon);
        ImageView iv_finish = helper.getView(R.id.iv_finish);
        ImageView iv_flag =  helper.getView(R.id.iv_flag);

        if(item.getAdditionFlag()!=""||item.getAdditionFlag()!=null) {
            Glide.with(mContext).load(item.getAdditionFlag()).into(iv_flag);
            iv_flag.setVisibility(View.VISIBLE);
        }else {
            iv_flag.setVisibility(View.GONE);
        }

        TextView tv_bg = helper.getView(R.id.tv_bg);
        helper.setText(R.id.tv_title,item.getProdName());
        helper.setText(R.id.tv_spec,"规格:"+item.getSpec());
        helper.setText(R.id.tv_given_num,"赠:"+item.getSendNum());

        if(item.getAdditionFlag().equals("2")) {
            iv_finish.setVisibility(View.VISIBLE);
            tv_bg.setVisibility(View.VISIBLE);
        }else {
            iv_finish.setVisibility(View.GONE);
            tv_bg.setVisibility(View.GONE);
        }
    }
}
