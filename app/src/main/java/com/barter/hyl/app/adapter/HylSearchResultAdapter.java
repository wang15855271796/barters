package com.barter.hyl.app.adapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylSearchResultModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import java.util.List;

/**
 * If I become novel would you like ?
 * Created by WinSinMin on 2018/4/16.
 * 搜索推荐Adapter
 */

public class HylSearchResultAdapter extends BaseQuickAdapter<HylSearchResultModel.DataBean.ListBean,BaseViewHolder> {
    OnAddClickListener onAddClickListener;
    public HylSearchResultAdapter(int item_collection, List<HylSearchResultModel.DataBean.ListBean> list, OnAddClickListener onAddClickListener) {
        super(item_collection, list);
        this.onAddClickListener = onAddClickListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HylSearchResultModel.DataBean.ListBean item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_price = helper.getView(R.id.tv_price);
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        ImageView iv_add = helper.getView(R.id.iv_add);
        ImageView iv_sold = helper.getView(R.id.iv_sold);
        TextView tv_spec = helper.getView(R.id.tv_spec);

        View v_show = helper.getView(R.id.v_show);
        tv_title.setText(item.getProductName());
        tv_price.setText(item.getMinMaxPrice());
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        tv_spec.setText(item.getSpec());
        v_show.setVisibility(View.VISIBLE);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAddClickListener!=null) {
                    onAddClickListener.onAddClick(item.getMainId(),helper.getAdapterPosition());
                }
            }
        });
    }

    public interface OnAddClickListener {
        void onAddClick(String mainId,int position);
    }

}