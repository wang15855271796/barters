package com.barter.hyl.app.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.constant.StringHelper;
import com.barter.hyl.app.event.ReturnUnitHylEvent;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.HylReturnGoodModel;
import com.barter.hyl.app.model.HylReturnNumModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/9/9
 */
class HylReturnUnitAdapter extends BaseQuickAdapter<HylReturnGoodModel.DataBean.ProdsBean.DetailsBean,BaseViewHolder> {

    int businessType;
    String orderId;
    TextView tv_price;
    TextView tv_unit;
    TextView tv_num;
    List<Integer> unitss = new ArrayList<>();
    public HylReturnUnitAdapter(int layoutResId, @Nullable List<HylReturnGoodModel.DataBean.ProdsBean.DetailsBean> data, int businessType, String orderId) {
        super(layoutResId, data);
        this.businessType = businessType;
        this.orderId = orderId;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HylReturnGoodModel.DataBean.ProdsBean.DetailsBean item) {
        tv_unit = helper.getView(R.id.tv_unit);
        tv_unit.setText(item.getUnits().get(0).getUnitName());
        tv_price = helper.getView(R.id.tv_price);
        tv_num = helper.getView(R.id.tv_num);
        unitss.add(item.getUnits().get(0).getUnitId());
        EventBus.getDefault().post(new ReturnUnitHylEvent(unitss));
        tv_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialog(item,helper.getAdapterPosition(),tv_num);
            }
        });

        tv_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shows(item,tv_unit);
            }
        });
    }

    String unitName;

    public void shows(final HylReturnGoodModel.DataBean.ProdsBean.DetailsBean item, final TextView tv_unit) {
        final Dialog dialog = new Dialog(mContext, R.style.DialogTheme);
        View view = View.inflate(mContext, R.layout.return_spec, null);
        RecyclerView recyclerView = view.findViewById(R.id.rv_spec);
        HylReturnSpecNumAdapter hylReturnSpecNumAdapter = new HylReturnSpecNumAdapter(R.layout.retrun_spec_num,item.getUnits());

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(hylReturnSpecNumAdapter);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);

        dialog.show();

        hylReturnSpecNumAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                unitName = item.getUnits().get(position).getUnitName();
                unitss.add(item.getUnits().get(position).getUnitId());
                EventBus.getDefault().post(new ReturnUnitHylEvent(unitss));
                tv_unit.setText(unitName);
                if (tv_num.getText().toString() != null && StringHelper.notEmptyAndNull(tv_num.getText().toString())) {
                    getReturnNum(item,Integer.parseInt(tv_num.getText().toString()),tv_num,position);
                }

                dialog.dismiss();
            }
        });


    }

    AlertDialog alertDialog;
    private void showDialog(final HylReturnGoodModel.DataBean.ProdsBean.DetailsBean item, final int position, final TextView tv_num) {
        alertDialog = new AlertDialog.Builder(mContext, R.style.DialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        window.setContentView(R.layout.dialog_cart_num_set_hyl);

        final EditText et_num = window.findViewById(R.id.et_num);
        TextView tv_ok = window.findViewById(R.id.tv_ok);
        TextView tv_cancel = window.findViewById(R.id.tv_cancel);

        window.setGravity(Gravity.CENTER);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_num.getText().toString() != null && StringHelper.notEmptyAndNull(et_num.getText().toString())) {
                    getReturnNum(item,Integer.parseInt(et_num.getText().toString()),tv_num,position);

                }
            }
        });
    }



    /**
     * 退货数量
     * @param item
     * @param num
     * @param
     * @param position
     */
    private void getReturnNum(final HylReturnGoodModel.DataBean.ProdsBean.DetailsBean item, final int num, final TextView tv_num, final int position) {
        OrderApi.returnNum(mContext,businessType,item.getDetailId()+"",num,item.getUnits().get(position).getUnitId()+"",orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylReturnNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylReturnNumModel hylReturnNumModel) {
                        if (hylReturnNumModel.code==1) {
                            tv_num.setText(hylReturnNumModel.getData()+"");
                            item.setNum(hylReturnNumModel.getData());
                            alertDialog.dismiss();
                            getReturnPrice(item, hylReturnNumModel.getData(),tv_price,position);
                        } else {
                            tv_num.setText(hylReturnNumModel.getData()+"");
                            ToastUtil.showSuccessMsg(mContext, hylReturnNumModel.message);
                        }
                    }
                });
    }

    /**
     * 退货金额
     * @param item
     * @param num
     * @param tv_price
     * @param position
     */
    private void getReturnPrice(HylReturnGoodModel.DataBean.ProdsBean.DetailsBean item, final int num, final TextView tv_price, int position) {
        OrderApi.returnPrice(mContext,businessType,item.getDetailId()+"",num,item.getUnits().get(position).getUnitId()+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylLoginModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylLoginModel hylLoginModel) {
                        if (hylLoginModel.code==1) {
                            if(hylLoginModel.data!=null) {
                                tv_price.setText(hylLoginModel.data);
                            }
                        } else {
                            AppHelper.showMsg(mContext, hylLoginModel.message);
                        }
                    }
                });
    }

}

