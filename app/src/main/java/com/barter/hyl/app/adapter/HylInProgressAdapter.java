package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylTeamListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/7/30
 */
public class HylInProgressAdapter extends BaseQuickAdapter<HylTeamListModel.DataBean.ActivesBeanX,BaseViewHolder> {

    OnAddClickListener onAddClickListener;
    public HylInProgressAdapter(int layoutResId, @Nullable List<HylTeamListModel.DataBean.ActivesBeanX> data, OnAddClickListener onAddClickListener) {
        super(layoutResId, data);
        this.onAddClickListener = onAddClickListener;
    }


    @Override
    protected void convert(BaseViewHolder helper, HylTeamListModel.DataBean.ActivesBeanX item) {

        TextView tv_time = helper.getView(R.id.tv_time);
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        if(item.getDateTime()==null) {
            tv_time.setVisibility(View.GONE);
        }else {
            tv_time.setVisibility(View.VISIBLE);
            tv_time.setText(item.getDateTime());
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        HylInInnerProgressAdapter hylInInnerProgressAdapter = new HylInInnerProgressAdapter(R.layout.item_inner_progress_hyl,item.getActives(), new HylInInnerProgressAdapter.OnAddClickListener() {
            @Override
            public void onAddClick(int activeType,int activeId,int num) {
                onAddClickListener.onAddClick(activeType,activeId,num);
            }
        });
        recyclerView.setAdapter(hylInInnerProgressAdapter);
    }

    public interface OnAddClickListener {
        void onAddClick(int activeType,int activeId,int num);
    }
}
