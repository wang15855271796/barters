package com.barter.hyl.app.adapter;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylMessageCenterActivity;
import com.barter.hyl.app.model.HomeBaseModel;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/28
 */
public class MarqueeAdapter extends RecyclerView.Adapter<MarqueeAdapter.ViewHolder>  {

    List<HomeBaseModel.DataBean.NoticeListBean> data;
    Activity activity;
    public void setData(List<HomeBaseModel.DataBean.NoticeListBean> data, int itemCount, FragmentActivity activity) {
        this.data = data;
        this.activity = activity;
        int remainder = data.size() % itemCount;
        if (remainder > 0) {
            for (int i = 0; i >= itemCount; i++) {
                this.data.remove(data.size()-1);
            }
        }
        if (data.size() > itemCount) {
            for (int i = 0; i < itemCount; i++) {
                this.data.add(data.size(), data.get(i));
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marquee, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        HomeBaseModel.DataBean.NoticeListBean noticeListBean = data.get(position);
        holder.tv_notice_desc.setText(noticeListBean.getTitle());
        holder.tv_time.setText(noticeListBean.getDateTime());
        holder.rl_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,HylMessageCenterActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data == null || data.size() == 0) {
            return 0;
        } else {
            return data.size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_notice_desc;
        private TextView tv_time;
        private RelativeLayout rl_notice;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_notice_desc = (TextView) itemView.findViewById(R.id.tv_notice_desc);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            rl_notice = itemView.findViewById(R.id.rl_notice);
        }

    }

//    public MarqueeAdapter(int layoutResId, @Nullable List<HomeBaseModel.DataBean.NoticeListBean> data) {
//        super(layoutResId, data);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, HomeBaseModel.DataBean.NoticeListBean item) {
//
//    }
}
