package com.barter.hyl.app.adapter;

import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylReturnOrderDetailModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/26
 */
public class HylReturnSpecsAdapter extends BaseQuickAdapter<HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean, BaseViewHolder> {

    private TextView mTvSpec;
    private TextView mTvPrice;
    private HylReturnSpecAdapter.OnItemClick click;
    TextView tv_old_price;
    String allReturn;
    public HylReturnSpecAdapter.OnItemClick getClick() {
        return click;
    }

    public void setClick(HylReturnSpecAdapter.OnItemClick click) {
        this.click = click;
    }

    public HylReturnSpecsAdapter(int layoutResId, @Nullable List<HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean> data, String allReturn) {
        super(layoutResId, data);
        this.allReturn = allReturn;
    }

    @Override
    protected void convert(BaseViewHolder helper, HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean item) {
        helper.setIsRecyclable(false);
        tv_old_price = helper.getView(R.id.tv_old_price);
        tv_old_price.getPaint().setAntiAlias(true);//抗锯齿
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        mTvPrice = helper.getView(R.id.tv_return_price);
        mTvSpec = helper.getView(R.id.tv_return_spec);
        tv_old_price.setText(item.getPrice()+"");
        mTvSpec.setText(item.getShowPrice());
        mTvPrice.setText(item.getPrice()+"");
        TextView tv_return = helper.getView(R.id.tv_return);
        if(allReturn.equals("1")) {
            tv_return.setEnabled(false);
        }else {
            tv_return.setEnabled(true);
        }
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
