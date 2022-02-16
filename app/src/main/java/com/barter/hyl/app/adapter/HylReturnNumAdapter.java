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
//        item.setItemPrice(Double.parseDouble(item.getTotalPrice()));
//        item.setItemPrice(item.getPrice());
        final TextView tv_num = helper.getView(R.id.tv_num);
        final TextView tv_spec_num = helper.getView(R.id.tv_spec_num_return);
        final TextView total_price = helper.getView(R.id.tv_total_price);
        RelativeLayout rl_spec_num = helper.getView(R.id.rl_spec_num);
//        tv_num.setText(item.getNum() + "");
//        item.setItemUnitId(item.getUnitId());
        //疑惑
        item.setItemUnitId(item.getUnits().get(0).getUnitId());
//        if (et_num.getTag() instanceof TextWatcher) {
//            et_num.removeTextChangedListener((TextWatcher) et_num.getTag());
//        }

        tv_num.setText("0");
        item.setItemNum(0);
        item.setItemPrice(0.00);
        total_price.setText(0+"");
//        item.setDetailId(item.getDetailId());
//        TextWatcher watcher = new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        String text = s.toString();
//                        int len = s.toString().length();
//                        if (len > 1 && text.startsWith("0")) {
//                            s.replace(0, 1, "");
//                        }
//                        if (et_num.getText().toString() == null || et_num.getText().toString().equals("")) {
//                            item.setItemPrice(0.0);
//                            item.setItemNum(0);
//                        } else {
//                            getReturnNum(item, Integer.parseInt(text),et_num,helper.getAdapterPosition());
//                            String s1 = new BigDecimal(item.getPrice()).multiply(new BigDecimal(et_num.getText().toString())).setScale(2).doubleValue()  + "";
//                            BigDecimal bg = new BigDecimal(s1);
//                            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                            total_price.setText(f1+"");
//                            item.setItemPrice(Double.parseDouble(total_price.getText().toString()));
//                            item.setItemNum(Integer.parseInt(et_num.getText().toString()));

//                            if (new BigDecimal(item.getPrice()).multiply(new BigDecimal(et_num.getText().toString())).setScale(2).doubleValue()>0){
//                                String s1 = new BigDecimal(item.getPrice()).multiply(new BigDecimal(et_num.getText().toString())).setScale(2).doubleValue()  + "";
//                                BigDecimal bg = new BigDecimal(s1);
//                                double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                                total_price.setText(f1+"");
//                                item.setItemPrice(Double.parseDouble(total_price.getText().toString()));
//                            }else {
//                                total_price.setText(0+"");
//                                item.setItemPrice(0);
//
//                            }

//                        }
//                        if (et_num.getText().toString() == null || et_num.getText().toString().equals("")) {
//                            et_num.setText("0");
//                        } else if (Integer.parseInt(et_num.getText().toString()) > item.getNum()) {
//                            AppHelper.showMsg(mContext, "最大数量是" + item.getNum());
//                            et_num.setText(item.getNum() + "");
//                        }

//                        et_num.setSelection(et_num.getText().length());
//                        if (listener != null) {
//                            listener.onEventClick();
//                        }

//                    }
//                };
//        et_num.addTextChangedListener(watcher);
//        et_num.setTag(watcher);
//        et_num.setSelection(et_num.getText().length());
        tv_spec_num.setText(item.getUnits().get(0).getUnitName());
//         total_price.setText(item.getPrice()+"");

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

    AlertDialog alertDialog;
    private void showDialog(final HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean item, final TextView total_price, final int position, final int selectionPosition, final TextView tv_num, final TextView tv_spec_num) {
        alertDialog = new AlertDialog.Builder(mContext, R.style.DialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        window.setContentView(R.layout.dialog_cart_num_set_hyl);
        selectionPosition1 = position;
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
                    getReturnNum(item,total_price,Integer.parseInt(et_num.getText().toString()),tv_num,selectionPosition1,selectionPosition,tv_spec_num);
                }
            }
        });
    }

    int selectionPosition = 0;
    int selectionPosition1 = 0;
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

                item.setItemUnitId(item.getUnits().get(position).getUnitId());
                tv_spec_num.setText(item.getUnits().get(position).getUnitName());

                selectionPosition = position;
                getReturnNum(item,total_price,Integer.parseInt(tv_num.getText().toString()),tv_num,selectionPosition1,selectionPosition,tv_spec_num);
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
                              final TextView tv_num, final int selectionPosition1, final int selectionPosition, final TextView tv_spec_num) {
        OrderApi.returnNum(mContext,businessType,item.getDetailId()+"",num,item.getUnits().get(selectionPosition).getUnitId()+"",orderId)
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
                            getReturnPrice(item,num,total_price,selectionPosition1,selectionPosition);
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
     * @param selectionPosition
     */
    private void getReturnPrice(final HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean item, final int num,
                                final TextView tv_price, final int selectionPosition1, final int selectionPosition) {
        OrderApi.returnPrice(mContext,businessType,item.getDetailId()+"",num,item.getUnits().get(selectionPosition).getUnitId()+"")
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
//                                Log.e("当前itemDetailId",selectionPosition+"");
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













//    public void getDetuctPrice(String orderId, int businessId, int businessType, int additionFlag, int unitId, int priceId, int maxNum, String price, TextView total_price, HylReturnOrderDetailModel.DataBean.ProductsBean.DetailsBean item) {
//        GetReturnGoodDeductAPI.requestDetuctSpec(mContext, orderId, businessId, businessType, additionFlag, unitId, priceId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<ReturnDetuctAmountModel>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(ReturnDetuctAmountModel returnDetuctAmountModel) {
//
//                        if (returnDetuctAmountModel.isSuccess()) {
//                            item.setDeductPrice(returnDetuctAmountModel.getData());
//                            if (new BigDecimal(price).multiply(new BigDecimal(maxNum)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() - new BigDecimal(returnDetuctAmountModel.getData()).multiply(new BigDecimal(maxNum)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() > 0) {
//                                String s1 = new BigDecimal(item.getPrice()).multiply(new BigDecimal(maxNum)).setScale(2).doubleValue() - new BigDecimal(item.getDeductPrice()).multiply(new BigDecimal(maxNum)).setScale(2).doubleValue() + "";
//                                BigDecimal bg = new BigDecimal(s1);
//                                double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                                total_price.setText(f1+"");
//                                //  total_price.setText(new BigDecimal(price).multiply(new BigDecimal(maxNum)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() - new BigDecimal(returnDetuctAmountModel.getData()).multiply(new BigDecimal(maxNum)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
//                                item.setItemPrice(Double.parseDouble(total_price.getText().toString()));
//                            } else {
//                                total_price.setText(0 + "");
//                                item.setItemPrice(0);
//                            }
//
//
//                            if (listener != null) {
//                                listener.onEventClick();
//                            }
//
//                        } else {
//                            AppHelper.showMsg(mContext, returnDetuctAmountModel.getMessage());
//                        }
//                    }
//                });
//
//
//    }
}
