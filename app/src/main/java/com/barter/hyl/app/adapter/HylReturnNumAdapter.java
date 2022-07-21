package com.barter.hyl.app.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.constant.StringHelper;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.model.HylReturnOrderDetailModel;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.HylReturnNumModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/9/26
 */
public class HylReturnNumAdapter extends BaseQuickAdapter<HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean, BaseViewHolder>{
    double totlaPrice = 0.0;

    public OnReturnClickListener listener;
    String orderId;
    private int additionFlag;
    int businessType;
    List<HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean> data;

    //    TextView total_price;
    public OnReturnClickListener getListener() {
        return listener;
    }

    public void setListener(OnReturnClickListener listener) {
        this.listener = listener;
    }

    // EditText et_num;

    public interface OnReturnClickListener {
        void onEventClick();

    }
    List<HylReturnOrderDetailModel.DataBean.ProdsBean> mListProduct;
    public HylReturnNumAdapter(int layoutResId, List<HylReturnOrderDetailModel.DataBean.ProdsBean> mListProduct, int businessType, @Nullable List<HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean> data, int additionFlag) {
        super(layoutResId, data);
        this.data = data;
        this.businessType = businessType;
        this.additionFlag = additionFlag;
        this.mListProduct = mListProduct;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean item) {
        orderId = UserInfoHelper.getOrderId(mContext);
        final TextView tv_num = helper.getView(R.id.tv_num);
        //单位
        final TextView tv_spec_num = helper.getView(R.id.tv_spec_num_return);
        final TextView total_price = helper.getView(R.id.tv_total_price);
        RelativeLayout rl_spec_num = helper.getView(R.id.rl_spec_num);
        //疑惑
        item.setItemUnitId(item.getUnits().get(0).getUnitId());

        tv_num.setText("0");
        item.setItemNum(0);
        item.setItemPrice(0.00);
        total_price.setText(0+"");
        tv_spec_num.setText(item.getUnits().get(0).getUnitName());

        //选择单位
        rl_spec_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(item, tv_spec_num, tv_num, total_price);
            }
        });

        //选择数量
        tv_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(item,total_price,helper.getAdapterPosition(),selectionPosition,tv_num,tv_spec_num);
            }
        });
    }

    //直接选择数量
    AlertDialog alertDialog;
    private void showDialog(final HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean item, final TextView total_price, final int position,
                            final int selectionPositions, final TextView tv_num, final TextView tv_spec_num) {
        alertDialog = new AlertDialog.Builder(mContext, R.style.DialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        window.setContentView(R.layout.dialog_cart_num_set_hyl);
        selectionPosition1 = position;
        selectionPosition = selectionPositions;
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
                    getReturnNum(item,total_price,Integer.parseInt(et_num.getText().toString()),tv_num,selectionPosition1,
                            selectionPosition,item.getItemUnitId(),tv_spec_num);
                }
            }
        });
    }

    int selectionPosition = 0;
    int selectionPosition1 = 0;
    int unitId;
    //单位选择
    public void show(final HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean item, final TextView tv_spec_num, final TextView tv_num, final TextView total_price) {
        final Dialog dialog = new Dialog(mContext, R.style.DialogTheme);
        View view = View.inflate(mContext, R.layout.return_spec, null);
        RecyclerView recyclerView = view.findViewById(R.id.rv_spec);
        HylReturnSpecsNumAdapter returnSpecNumAdapter = new HylReturnSpecsNumAdapter(R.layout.retrun_spec_num, item.getUnits());

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(returnSpecNumAdapter);

        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);

        dialog.show();

        returnSpecNumAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                unitId = item.getUnits().get(position).getUnitId();
                item.setItemUnitId(unitId);
                tv_spec_num.setText(item.getUnits().get(position).getUnitName());
                selectionPosition = position;
                getReturnNum(item,total_price,Integer.parseInt(tv_num.getText().toString()),tv_num,selectionPosition1,selectionPosition,unitId,tv_spec_num);
                dialog.dismiss();
            }
        });


    }

    /**
     * 退货数量
     * @param item
     * @param num
     * @param
     * @param selectionPosition
     * */
    private void getReturnNum(final HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean item, final TextView total_price, final int num,
                              final TextView tv_num, final int selectionPosition1, final int selectionPosition,int unitId,final TextView tv_spec_num) {
        OrderApi.returnNum(mContext,businessType,item.getDetailId()+"",num,unitId+"",orderId)
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
                            tv_spec_num.setText(item.getUnits().get(selectionPosition).getUnitName());
                            alertDialog.dismiss();
                            getReturnPrice(item,num,total_price,selectionPosition1,selectionPosition,unitId);
                        } else {
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
     * @param selectionPosition
     */
    private void getReturnPrice(final HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean item, final int num,
                                final TextView tv_price, final int selectionPosition1, final int selectionPosition,int unitId) {
        OrderApi.returnPrice(mContext,businessType,item.getDetailId()+"",num,unitId+"")
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
                                item.setItemNum(num);
                                item.setItemUnitId(item.getUnits().get(selectionPosition).getUnitId());
                                item.setItemDetailIds(data.get(selectionPosition1).getDetailId());
                                item.setItemPrice(Double.parseDouble(hylLoginModel.data));
                                //回调
                                if (listener != null) {
                                    listener.onEventClick();
                                }
                            }
                        } else {
                            AppHelper.showMsg(mContext, hylLoginModel.message);
                        }
                    }
                });
    }

}
