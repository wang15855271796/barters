package com.barter.hyl.app.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.event.ReturnUnitHylEvent;
import com.barter.hyl.app.model.HylReturnGoodModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ${王文博} on 2019/5/23
 */
public class HylReturnSpecNumAdapter extends BaseQuickAdapter<HylReturnGoodModel.DataBean.ProdsBean.DetailsBean.UnitsBean, BaseViewHolder> {

    private TextView tv_unit;
    List<HylReturnGoodModel.DataBean.ProdsBean.DetailsBean.UnitsBean> data;
    List<Integer> unitss = new ArrayList<>();
    public HylReturnSpecNumAdapter(int layoutResId, @Nullable List<HylReturnGoodModel.DataBean.ProdsBean.DetailsBean.UnitsBean> data) {
        super(layoutResId, data);
         this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, HylReturnGoodModel.DataBean.ProdsBean.DetailsBean.UnitsBean item) {
        helper.setIsRecyclable(false);
        tv_unit = helper.getView(R.id.tv_unit);
        tv_unit.setText(item.getUnitName());
        item.setUnitId(item.getUnitId());

        unitss.add(data.get(0).getUnitId());
        EventBus.getDefault().post(new ReturnUnitHylEvent(unitss));
    }
}
