package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylAddressListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/25
 */
public class HylAddressAdapter extends BaseQuickAdapter<HylAddressListModel.DataBean.ListBean,BaseViewHolder> {

    public OnAddressClickListener onAddressClickListener;
    public HylAddressAdapter(int layoutResId, @Nullable List<HylAddressListModel.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    public void setAddressClickListener(OnAddressClickListener onAddressClickListener) {
        this.onAddressClickListener = onAddressClickListener;
    }

    public interface OnAddressClickListener {
        void onAddressClick(int addressId,int position);
        void onAddressDeleteClick(int addressId);
        void onAddressEditClick(int addressId,int position);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HylAddressListModel.DataBean.ListBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_address = helper.getView(R.id.tv_address);
        TextView tv_delete = helper.getView(R.id.tv_delete);
        TextView tv_edit = helper.getView(R.id.tv_edit);
        LinearLayout ll_default = helper.getView(R.id.ll_default);
        tv_name.setText(item.getUserName());
        tv_address.setText(item.getDetailAddress());

        if (item.getIsDefault() == 0) {
            //不是默认地址
            helper.setChecked(R.id.cb_default, false);
        } else if (item.getIsDefault() == 1) {
            //是默认地址
            helper.setChecked(R.id.cb_default, true);
        }

        //修改默认地址
        ll_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddressClickListener.onAddressClick(item.getAddressId(),helper.getAdapterPosition());
            }
        });

        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddressClickListener.onAddressEditClick(item.getAddressId(),helper.getAdapterPosition());
            }
        });

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddressClickListener.onAddressDeleteClick(item.getAddressId());
            }
        });
    }
}
