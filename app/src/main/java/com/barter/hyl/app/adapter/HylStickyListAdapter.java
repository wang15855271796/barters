package com.barter.hyl.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylGetWallertRecordByPageModel;
import com.barter.hyl.app.utils.DateUtils;
import com.bumptech.glide.Glide;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;

import java.text.ParseException;
import java.util.List;

/**
 * Created by ${王涛} on 2021/9/24
 */
public class HylStickyListAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    Onclick onclick;
    private List<HylGetWallertRecordByPageModel.DataBean.ListBean.RecordsBean> list;
    Activity mActivity;
    List<HylGetWallertRecordByPageModel.DataBean> lists;
    public HylStickyListAdapter(Activity mActivity, List<HylGetWallertRecordByPageModel.DataBean.ListBean.RecordsBean> list, List<HylGetWallertRecordByPageModel.DataBean> lists, Onclick onclick) {
        this.list = list;
        this.onclick = onclick;
        this.mActivity = mActivity;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.item_my_wallet_detail_hyl, null);
            holder = creatHolder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
//        if (list.get(position).isNullData()){
//            holder.ll_detail.setVisibility(View.GONE);
//        }else {
//            holder.ll_detail.setVisibility(View.VISIBLE);
//
//            holder.UpdateUI(parent.getContext(), list, position);
//        }
        holder.UpdateUI(parent.getContext(), list, position);
        return view;
    }

    private Holder creatHolder(View view) {
        return new Holder(view);
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        Log.e("rgh","getHeaderView");

        View headView = LayoutInflater.from(mActivity).inflate(R.layout.item_wallet_header, null);
        TextView tv_month_select = headView.findViewById(R.id.tv_month_select);
        TextView tv_expenditure = headView.findViewById(R.id.tv_expenditure);
        TextView tv_income = headView.findViewById(R.id.tv_income);
//        tv_income.setText("收入 ￥"+lists.get(position).getInAmt());
//        tv_expenditure.setText("支出 ￥"+lists.get(position).getOutAmt());
        tv_month_select.setText(list.get(position).getDateTime());
        tv_month_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
                    onclick.clicks();
                }
            }
        });
        return headView;
    }


    @Override
    public long getHeaderId(int position) {
        long time=0;
        try {
            time=DateUtils.getMonthTime(list.get(position).getDateTime(),
                    DateUtils.DATE_FORMAT_NORMAL, "yyyy-MM");
            Log.e("rgh","getHeaderId "+time);
        } catch (ParseException e) {
            time=0x1234;
            Log.e("rgh","getHeaderId ParseException " +time);
            e.printStackTrace();
        }
        return time;
    }

    class Holder {
        private LinearLayout ll_detail;
        private  TextView tv_time;
        private  TextView tv_price;
        private  TextView tv_title;
        private ImageView iv_pic;
        public Holder(View view) {
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            ll_detail =  view.findViewById(R.id.ll_detail);
        }

        public void UpdateUI(Context context, List<HylGetWallertRecordByPageModel.DataBean.ListBean.RecordsBean> data, int position) {
            HylGetWallertRecordByPageModel.DataBean.ListBean.RecordsBean recordsBean = data.get(position);
            tv_title.setText(recordsBean.getFlowRecordTypeName());
            tv_price.setText(recordsBean.getAmount());
            tv_time.setText(recordsBean.getCreateDate());
            Glide.with(context).load(recordsBean.getIconUrl()).into(iv_pic);
        }
    }

    public interface Onclick {
        void clicks();
    }
}
