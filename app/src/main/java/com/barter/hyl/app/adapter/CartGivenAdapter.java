package com.barter.hyl.app.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylCommonGoodsActivity;
import com.barter.hyl.app.model.HylCartListModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CartGivenAdapter extends BaseQuickAdapter<HylCartListModel.DataBean.ProdsBeanX.AdditionsBean,BaseViewHolder> {
    public CartGivenAdapter(int layoutResId, @Nullable List<HylCartListModel.DataBean.ProdsBeanX.AdditionsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylCartListModel.DataBean.ProdsBeanX.AdditionsBean item) {
        LinearLayout ll_root =  helper.getView(R.id.ll_root);
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_spec = helper.getView(R.id.tv_spec);
        TextView tv_bg = helper.getView(R.id.tv_bg);
        ImageView iv_flag = helper.getView(R.id.iv_flag);
        ImageView iv_finish = helper.getView(R.id.iv_finish);
        TextView tv_given_num = helper.getView(R.id.tv_given_num);
        tv_title.setText(item.getName());
        Glide.with(mContext).load(item.getPicUrl()).into(iv_icon);
        tv_spec.setText("规格:"+item.getSpec());
        tv_given_num.setText("赠:"+item.getSendNumDesc());

        if(item.getFlagUrl()!=""||item.getFlagUrl()!=null) {
            Glide.with(mContext).load(item.getFlagUrl()).into(iv_flag);
            iv_flag.setVisibility(View.VISIBLE);
        }else {
            iv_flag.setVisibility(View.GONE);
        }

        if(item.getFinishUrl()!=""||item.getFinishUrl()!=null) {
            Glide.with(mContext).load(item.getFinishUrl()).into(iv_finish);
            iv_finish.setVisibility(View.VISIBLE);
        }else {
            iv_finish.setVisibility(View.GONE);
        }

        if(item.getAdditionFlag()==2) {
            iv_finish.setVisibility(View.VISIBLE);
            tv_bg.setVisibility(View.VISIBLE);
        }else {
            iv_finish.setVisibility(View.GONE);
            tv_bg.setVisibility(View.GONE);
        }


        ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HylCommonGoodsActivity.class);
                intent.putExtra("mainId",item.getProductMainId());
                mContext.startActivity(intent);
            }
        });
    }
}
