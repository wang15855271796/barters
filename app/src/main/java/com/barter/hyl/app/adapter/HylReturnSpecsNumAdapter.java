package com.barter.hyl.app.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.event.ReturnUnitHylEvent;
import com.barter.hyl.app.model.HylReturnOrderDetailModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2021/9/26
 */
public class HylReturnSpecsNumAdapter extends BaseQuickAdapter<HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean.UnitsBean, BaseViewHolder> {
    private TextView tv_unit;
    List<HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean.UnitsBean> data;
    List<Integer> unitss = new ArrayList<>();
    public HylReturnSpecsNumAdapter(int layoutResId, @Nullable List<HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean.UnitsBean> data) {
        super(layoutResId, data);
        this.data = data;
    }


    @Override
    protected void convert(BaseViewHolder helper, HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean.UnitsBean item) {
        helper.setIsRecyclable(false);
        tv_unit = helper.getView(R.id.tv_unit);
        tv_unit.setText(item.getUnitName());
        item.setUnitId(item.getUnitId());

        unitss.add(data.get(0).getUnitId());
        EventBus.getDefault().post(new ReturnUnitHylEvent(unitss));

    }
}
