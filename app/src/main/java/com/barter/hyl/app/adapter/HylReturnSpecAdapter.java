package com.barter.hyl.app.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylReturnGoodModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王文博} on 2019/5/20
 */
public class HylReturnSpecAdapter extends BaseQuickAdapter<HylReturnGoodModel.DataBean.ProdsBean.DetailsBean, BaseViewHolder> {


    private TextView mTvSpec;
    private TextView mTvPrice;
    private OnItemClick click;
    TextView tv_old_price;
    int allReturn;
    public OnItemClick getClick() {
        return click;
    }

    public void setClick(OnItemClick click) {
        this.click = click;
    }

    public HylReturnSpecAdapter(int layoutResId, @Nullable List<HylReturnGoodModel.DataBean.ProdsBean.DetailsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylReturnGoodModel.DataBean.ProdsBean.DetailsBean item) {
        helper.setIsRecyclable(false);
        tv_old_price = helper.getView(R.id.tv_old_price);
        tv_old_price.getPaint().setAntiAlias(true);//抗锯齿
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        mTvPrice = helper.getView(R.id.tv_return_price);
        mTvSpec = helper.getView(R.id.tv_return_spec);
        mTvPrice.setText(item.getPrice());
        TextView tv_return = helper.getView(R.id.tv_return);

        helper.getView(R.id.tv_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.delete();
            }
        });
    }

    public interface OnItemClick {
        void delete();
    }
}

