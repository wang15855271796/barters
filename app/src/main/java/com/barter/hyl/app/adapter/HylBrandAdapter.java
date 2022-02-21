package com.barter.hyl.app.adapter;

import android.graphics.Color;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylMarketBeanModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2021/8/26
 */
public class HylBrandAdapter extends BaseQuickAdapter<HylMarketBeanModel,BaseViewHolder> {
    List<HylMarketBeanModel> list;
    private OnItemClickListener onItemClickListener;
    private List<HylMarketBeanModel> selectList =new ArrayList<>();
    CheckBox cb_content;
    public HylBrandAdapter(int layoutResId, @Nullable List<HylMarketBeanModel> data) {
        super(layoutResId, data);
        this.list = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, HylMarketBeanModel item) {
        cb_content = helper.getView(R.id.cb_content);
        if (list!=null&&list.get(helper.getAdapterPosition()) !=null){
            if (list.get(helper.getAdapterPosition()).isState()){
                cb_content.setTextColor(Color.parseColor("#FF5000"));
            }else {
                cb_content.setTextColor(Color.parseColor("#636363"));
            }
            cb_content.setText(list.get(helper.getAdapterPosition()).getS());
        }

        cb_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_content.isChecked()){
                    selectList.add(list.get(helper.getAdapterPosition()));
                    cb_content.setTextColor(Color.parseColor("#FF5000"));
                }else {
                    selectList.remove(list.get(helper.getAdapterPosition()));
                    cb_content.setTextColor(Color.parseColor("#636363"));
                }
                onItemClickListener.onItemClick(selectList);
            }
        });
    }

    public void setStat() {
        for (HylMarketBeanModel bean:list) {
            bean.setState(false);

        }

        for (int i = 0; i < list.size(); i++) {
            selectList.remove(list.get(i));

        }
        onItemClickListener.onItemClick(selectList);

        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(List<HylMarketBeanModel> position);
    }
}
