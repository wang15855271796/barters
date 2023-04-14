package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylAddressListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/10
 */
public class HylChooseAddresssAdapter extends BaseQuickAdapter<HylAddressListModel.DataBean.ListBean,BaseViewHolder> {

    Onclick onclick;
    private OnEventClickListener mOnEventClickListener;

    public HylChooseAddresssAdapter(int layoutResId, @Nullable List<HylAddressListModel.DataBean.ListBean> data, Onclick onclick) {
        super(layoutResId, data);
        this.onclick = onclick;
    }

    public interface OnEventClickListener {
        void onEventClick(View view, int position, String flag);
    }

    public void setOnItemChangeClickListener(OnEventClickListener onEventClickListener) {
        mOnEventClickListener = onEventClickListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, HylAddressListModel.DataBean.ListBean item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_num = helper.getView(R.id.tv_num);
        ImageView iv_edit = helper.getView(R.id.iv_edit);
        CheckBox iv_check = helper.getView(R.id.iv_check);
        tv_title.setText(item.getDetailAddress());
        tv_name.setText(item.getUserName());
        tv_num.setText(item.getContactPhone());
        iv_check.setVisibility(View.VISIBLE);
        if (item.isDefault == 0) {
            //不是默认地址
            helper.setChecked(R.id.iv_check, false);
        } else if (item.isDefault == 1) {
            //是默认地址
            helper.setChecked(R.id.iv_check, true);

        }

        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
                    onclick.jump(helper.getAdapterPosition());
                }
            }
        });

        //切换默认地址
        helper.getView(R.id.rl_item_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("efsfes.......",item.getAddressId()+"--");
                mOnEventClickListener.onEventClick(view, helper.getAdapterPosition(), "default");
            }
        });

    }

    public interface Onclick {
        void jump(int position);
    }

}
