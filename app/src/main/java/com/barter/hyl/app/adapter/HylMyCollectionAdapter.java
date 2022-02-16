package com.barter.hyl.app.adapter;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylMyCollectionModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by ${王涛} on 2021/8/19
 */
public class HylMyCollectionAdapter extends BaseQuickAdapter<HylMyCollectionModel.DataBean,BaseViewHolder> {
    public Map<Integer, Boolean> isCheck;
    OnAddClickListener onAddClickListener;
    private OnEventClickListener mOnEventClickListener;

    public HylMyCollectionAdapter(int item_collection, List<HylMyCollectionModel.DataBean> list, Map<Integer, Boolean> isCheck, OnAddClickListener onAddClickListener) {
        super(item_collection, list);
        this.onAddClickListener = onAddClickListener;
        this.isCheck = isCheck;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HylMyCollectionModel.DataBean item) {
        helper.setIsRecyclable(false);
        FrameLayout fl_container = helper.getView(R.id.fl_container);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_spec = helper.getView(R.id.tv_spec);
        ImageView iv_add = helper.getView(R.id.iv_add);
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        ImageView iv_sold = helper.getView(R.id.iv_sold);
        tv_title.setText(item.getProductName());
        tv_price.setText(item.getMinMaxPrice());
        tv_spec.setText(item.getSpec());
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        helper.setChecked(R.id.cb_choose, isCheck.get(helper.getAdapterPosition()));
        if(isShow) {
            fl_container.setVisibility(View.VISIBLE);
//            rl_item_collection_data.setEnabled(false);
//            rl_item_collection_data.setClickable(false);
        }else {
            fl_container.setVisibility(View.GONE);
//            rl_item_collection_data.setEnabled(true);
//            rl_item_collection_data.setClickable(true);
        }


        if(item.getInventFlag()==0) {
            iv_sold.setVisibility(View.GONE);
            iv_add.setImageResource(R.mipmap.icon_add);
            iv_add.setEnabled(true);
        }else {
            iv_sold.setVisibility(View.VISIBLE);
            iv_add.setImageResource(R.mipmap.icon_add_saled);
            iv_add.setEnabled(false);
        }

        fl_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnEventClickListener.onEventClick(v, helper.getAdapterPosition());
            }
        });

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAddClickListener!=null) {
                    onAddClickListener.onAddClick(helper.getAdapterPosition());
                }
            }
        });


    }

    public interface OnEventClickListener {
        void onEventClick(View view, int position);
    }

    public void setOnItemClickListener(OnEventClickListener onEventClickListener) {
        mOnEventClickListener = onEventClickListener;
    }

    boolean isShow;
    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    public interface OnAddClickListener {
        void onAddClick(int position);
    }
}
