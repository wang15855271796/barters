package com.barter.hyl.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylActiveDetailActivity;
import com.barter.hyl.app.model.HylTeamListModel;
import com.barter.hyl.app.view.RoundImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/30
 */
public class HylInInnerProgressAdapter extends BaseQuickAdapter<HylTeamListModel.DataBean.ActivesBeanX.ActivesBean,BaseViewHolder> {

    private RoundImageView iv_pic;
    TextView tv_old_price;
    private TextView tv_add;
    private RelativeLayout rl_root;
    RelativeLayout rl_price;
    TextView tv_price;
    OnAddClickListener onAddClickListener;
    int num = 0;
    Activity context;
    public HylInInnerProgressAdapter(int layoutResId, @Nullable List<HylTeamListModel.DataBean.ActivesBeanX.ActivesBean> data, OnAddClickListener onAddClickListener) {
        super(layoutResId, data);
        this.onAddClickListener = onAddClickListener;
    }


    @Override
    protected void convert(BaseViewHolder helper, final HylTeamListModel.DataBean.ActivesBeanX.ActivesBean item) {

        rl_price = helper.getView(R.id.rl_price);
        tv_price = helper.getView(R.id.tv_price);
        tv_old_price = helper.getView(R.id.tv_old_price);
        iv_pic = helper.getView(R.id.iv_pic);
        rl_root = helper.getView(R.id.rl_root);
        tv_add = helper.getView(R.id.tv_add);

        if(item.getFlag()==0) {
            //0 进行中
            tv_add.setVisibility(View.VISIBLE);
        }else {
            //1 未进行
            tv_add.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(item.getPic()).into(iv_pic);
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_spec,item.getSpec());
        helper.setText(R.id.tv_price,item.getPrice());

        if(item.getOldPrice()!=""||item.getOldPrice()!=null) {
            tv_old_price.setVisibility(View.VISIBLE);
            tv_old_price.setText(item.getOldPrice());
        }else {
            tv_old_price.setVisibility(View.VISIBLE);
        }
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_old_price.getPaint().setAntiAlias(true);//抗锯齿
        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,HylActiveDetailActivity.class);
                intent.putExtra("activeId",item.getActiveId());
                mContext.startActivity(intent);
            }
        });


        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = item.getCartNum();
                num++;
                onAddClickListener.onAddClick(item.getActiveType(),item.getActiveId(),num);
            }
        });

        if(item.getInventFlag()==1) {
            //售空
            tv_add.setText("  已售空  ");
            tv_add.setBackgroundResource(R.drawable.shape_detail_grey);
        }else {
            tv_add.setText("立即加购");
            tv_add.setBackgroundResource(R.drawable.shape_add_car_red);
        }
    }

    public interface OnAddClickListener {
        void onAddClick(int activeType,int activeId,int num);
    }
}
