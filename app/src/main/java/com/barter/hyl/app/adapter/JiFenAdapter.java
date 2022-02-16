package com.barter.hyl.app.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.MyJifenModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/1
 */
public class JiFenAdapter extends BaseQuickAdapter<MyJifenModel.DataBean.GiftsBean,BaseViewHolder> {

    Onclick onclick;
    public JiFenAdapter(int layoutResId, @Nullable List<MyJifenModel.DataBean.GiftsBean> data,Onclick onclick) {
        super(layoutResId, data);
        this.onclick = onclick;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MyJifenModel.DataBean.GiftsBean item) {
        TextView tv_role = helper.getView(R.id.tv_role);
        TextView tv_amount = helper.getView(R.id.tv_amount);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_change = helper.getView(R.id.tv_change);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_factor = helper.getView(R.id.tv_factor);
        tv_role.setText("兑换价："+item.getPoint()+"个积分");
        tv_time.setText(item.getEffectTime());
        tv_amount.setText(item.getAmount());
        tv_title.setText(item.getGiftName());
        tv_factor.setText(item.getUseDesc());
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int point = item.getPoint();
                onclick.changeJifen(helper.getAdapterPosition(),point);
            }
        });
    }

    public interface Onclick {
        void changeJifen(int position,int point);
    }
}
