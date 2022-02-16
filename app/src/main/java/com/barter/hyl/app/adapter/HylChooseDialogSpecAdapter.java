package com.barter.hyl.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylAddCartModel;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/20
 */
public class HylChooseDialogSpecAdapter extends BaseAdapter {

    Context context;
    List<HylAddCartModel.DataBean.SpecsBean> prodSpecs;
    int selectPosition;
    Onclick onclick;

    public HylChooseDialogSpecAdapter(Context context, List<HylAddCartModel.DataBean.SpecsBean> specs, Onclick onclick) {
        this.context = context;
        this.prodSpecs = specs;
        this.onclick = onclick;
    }


    @Override
    public int getCount() {
        return prodSpecs.size();
    }

    @Override
    public Object getItem(int position) {
        return prodSpecs.isEmpty() ? null : prodSpecs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spec_hyl, null);
            holder = new Holder();
            holder.tv_spec = convertView.findViewById(R.id.tv_spec);
            holder.iv_reduce = convertView.findViewById(R.id.iv_reduce);
            holder.ll = convertView.findViewById(R.id.ll);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_spec.setText(prodSpecs.get(position).getSpec());

        if (selectPosition == position) {
            holder.tv_spec.setTextColor(Color.parseColor("#FF680A"));
            holder.tv_spec.setBackgroundColor(Color.parseColor("#FEF5EF"));
        } else {

            holder.tv_spec.setTextColor(Color.parseColor("#333333"));
            holder.tv_spec.setBackgroundColor(Color.parseColor("#eeeeee"));
        }

        holder.tv_spec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.addDialog(position);
            }
        });
        return convertView;
    }

    class Holder {
        public TextView tv_spec;
        public LinearLayout ll;
        public ImageView iv_reduce;
    }

    public void selectPosition(int position) {
        this.selectPosition = position;
        notifyDataSetChanged();
    }

    public interface Onclick {
        void addDialog(int position);
    }
}
