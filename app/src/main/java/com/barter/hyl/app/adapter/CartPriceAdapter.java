package com.barter.hyl.app.adapter;

import android.app.AlertDialog;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.barter.hyl.app.R;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.event.CartListHylEvent;
import com.barter.hyl.app.event.ChangeNumHylEvent;
import com.barter.hyl.app.model.HylAddToCartModel;
import com.barter.hyl.app.model.HylCartListModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CartPriceAdapter extends BaseQuickAdapter<HylCartListModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean,BaseViewHolder> {
    HylCartListModel.DataBean.ProdsBeanX.ProdsBean prods;
    HylCartAdapter cartAdapter;
    public CartPriceAdapter(int layoutResId, HylCartListModel.DataBean.ProdsBeanX.ProdsBean item, @Nullable List<HylCartListModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean> data, HylCartAdapter cartAdapter) {
        super(layoutResId, data);
        this.prods = item;
        this.cartAdapter = cartAdapter;
    }

    @Override
    protected void convert(BaseViewHolder helper, HylCartListModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean item) {
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_old_price = helper.getView(R.id.tv_old_price);
//        TextView tv_unit = helper.getView(R.id.tv_unit);
        ImageView iv_add = helper.getView(R.id.iv_add);
        ImageView iv_cut = helper.getView(R.id.iv_cut);
        TextView tv_num = helper.getView(R.id.tv_num);
        tv_price.setText(item.getPriceStr());
//        tv_unit.setText(item.getUnitDesc());

        tv_num.setText(item.getProductNum()+"");
        if(item.getOldPrice()!=null&&item.getOldPrice()!="") {
            tv_old_price.setText(item.getOldPrice());
            tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tv_old_price.getPaint().setAntiAlias(true);//抗锯齿
        }

        tv_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCartNum(item,tv_num);
            }
        });

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(tv_num.getText().toString());
                num = num + 1;
                addCartNum(prods.getBusinessType(),item.getProductCombinationPriceId(),prods.getBusinessId(),num,tv_num);
            }
        });

        iv_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(tv_num.getText().toString());
                if(num>0) {
                    num = num - 1;
                    addCartNum(prods.getBusinessType(),item.getProductCombinationPriceId(),prods.getBusinessId(),num,tv_num);
                }else {
                    ToastUtil.showSuccessMsg(mContext,"最小数量为0");
                }
            }
        });
    }

    AlertDialog alertDialog;
    private void getCartNum(HylCartListModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean item,TextView tv_num) {
        alertDialog = new AlertDialog.Builder(mContext, R.style.DialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        window.setContentView(R.layout.dialog_cart_num_set_hyl);
        EditText et_num = window.findViewById(R.id.et_num);
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
                int num = Integer.parseInt(et_num.getText().toString());
                addCartNum(prods.getBusinessType(),item.getProductCombinationPriceId(),prods.getBusinessId(),num,tv_num);
            }
        });
    }

    //加入购物车
    private void addCartNum(int businessType, int priceId, int businessId, final int num, final TextView tv_num) {
        DetailApi.addCart(mContext,businessType,priceId,businessId,num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylAddToCartModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylAddToCartModel hylAddToCartModel) {
                        if (hylAddToCartModel.getCode()==1) {
                            if(hylAddToCartModel.getData().getAddFlag()==0) {
                                tv_num.setText(num+"");
                                EventBus.getDefault().post(new CartListHylEvent());
                                EventBus.getDefault().post(new ChangeNumHylEvent());
                                ToastUtil.showSuccessMsg(mContext, hylAddToCartModel.getMessage());
                            }else {
                                EventBus.getDefault().post(new ChangeNumHylEvent());
                                tv_num.setText(hylAddToCartModel.getData().getNum()+"");
                                EventBus.getDefault().post(new CartListHylEvent());
                                ToastUtil.showSuccessMsg(mContext, hylAddToCartModel.getData().getMessage());
                            }
                            alertDialog.dismiss();
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylAddToCartModel.getMessage());
                        }
                    }
                });
    }
}
