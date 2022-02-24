package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.FullActiveActivity;
import com.barter.hyl.app.model.HylFullListModel;
import com.barter.hyl.app.view.RoundImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/31
 */
public class HylFullListAdapter extends BaseQuickAdapter<HylFullListModel.DataBean,BaseViewHolder> {

    public HylFullListAdapter(int layoutResId, @Nullable List<HylFullListModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylFullListModel.DataBean item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        tv_title.setText(item.getName());
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
        FullImageViewAdapter fullImageViewAdapter = new FullImageViewAdapter(R.layout.item_full_img,item.getPics());
        recyclerView.setAdapter(fullImageViewAdapter);

        fullImageViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, FullActiveActivity.class);
                intent.putExtra("fullId",item.getFullId());
                mContext.startActivity(intent);
            }
        });
    }
}
