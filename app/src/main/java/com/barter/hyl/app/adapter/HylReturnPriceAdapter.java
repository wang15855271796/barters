package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylReturnGoodModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/9
 */
public class HylReturnPriceAdapter extends BaseQuickAdapter<HylReturnGoodModel.DataBean.ProdsBean.DetailsBean,BaseViewHolder> {

    private OnAddItemClick click;

    public HylReturnPriceAdapter(int layoutResId, @Nullable List<HylReturnGoodModel.DataBean.ProdsBean.DetailsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylReturnGoodModel.DataBean.ProdsBean.DetailsBean item) {
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_show_price = helper.getView(R.id.tv_show_price);
        TextView tv_return = helper.getView(R.id.tv_return);
        tv_price.setText(item.getPrice());
        tv_show_price.setText(item.getShowPrice());

        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.change();
            }
        });
    }

    public void setClick(OnAddItemClick click) {
        this.click = click;
    }


    public interface OnAddItemClick {
        void change();
    }

}
