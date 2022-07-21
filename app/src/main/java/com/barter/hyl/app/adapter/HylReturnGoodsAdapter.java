package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylReturnGoodModel;
import com.barter.hyl.app.view.EasySwipeMenuLayout;
import com.barter.hyl.app.view.RoundImageView;
import com.barter.hyl.app.view.State;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/9
 */
public class HylReturnGoodsAdapter extends BaseQuickAdapter<HylReturnGoodModel.DataBean.ProdsBean,BaseViewHolder> {

    String orderId;
    HylReturnUnitAdapter hylReturnUnitAdapter;
    public HylReturnGoodsAdapter(int layoutResId, @Nullable List<HylReturnGoodModel.DataBean.ProdsBean> data, String orderId) {
        super(layoutResId, data);
        this.orderId = orderId;
    }

    @Override
    protected void convert(BaseViewHolder helper, HylReturnGoodModel.DataBean.ProdsBean item) {
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        TextView tv_name = helper.getView(R.id.tv_name);
        RecyclerView rv_spec = helper.getView(R.id.rv_spec);
        TextView tv_spec = helper.getView(R.id.tv_spec);
        RecyclerView rv_num = helper.getView(R.id.rv_num);
        final EasySwipeMenuLayout easySwipeMenuLayout = helper.getView(R.id.es_layout);

        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        tv_name.setText(item.getProdName());
        tv_spec.setText(item.getSpec());
        //价格adapter
        rv_spec.setLayoutManager(new LinearLayoutManager(mContext));
        HylReturnPriceAdapter returnSpecAdapter = new HylReturnPriceAdapter(R.layout.item_show_return_price,item.getDetails());
        rv_spec.setAdapter(returnSpecAdapter);

        //单位adapter
        hylReturnUnitAdapter = new HylReturnUnitAdapter(R.layout.item_return_unit_hyl,item.getDetails(),item.getBusinessType(),orderId);
        rv_num.setAdapter(hylReturnUnitAdapter);
        rv_num.setLayoutManager(new GridLayoutManager(mContext,1));
        final boolean[] isSelect = {true};
        returnSpecAdapter.setClick(new HylReturnPriceAdapter.OnAddItemClick() {
            @Override
            public void change() {
                if (isSelect[0]) {
                    easySwipeMenuLayout.handlerSwipeMenu(State.RIGHTOPEN);
                    isSelect[0] = false;
                } else {
                    easySwipeMenuLayout.handlerSwipeMenu(State.CLOSE);
                    isSelect[0] = true;
                }
            }
        });

//        hylReturnUnitAdapter.setListener();

    }


    OnReturnClickListener listener;
    public void setListener(OnReturnClickListener listener) {
        this.listener = listener;
    }

    public interface OnReturnClickListener {
        void onClick();

    }

}
