package com.barter.hyl.app.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.event.CartListHylEvent;
import com.barter.hyl.app.event.ChangeNumHylEvent;
import com.barter.hyl.app.model.HylAddToCartModel;
import com.barter.hyl.app.model.HylCartListModel;
import com.barter.hyl.app.view.CustomDialog;
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
 * Created by ${王涛} on 2021/8/24
 */
public class HylChooseCartPriceAdapter extends BaseQuickAdapter<HylCartListModel.DataBean.ValidListBean.SpecProductListBean.ProductDescVOListBean,BaseViewHolder> {

    int businessId;
    int businessType;
    TextView tv_ok;
    TextView tv_cancel;
    int cartId;
    public HylChooseCartPriceAdapter(int layoutResId, @Nullable List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean.ProductDescVOListBean> data, int businessId, int cartId, int businessType) {
        super(layoutResId, data);
        this.businessId = businessId;
        this.cartId = cartId;
        this.businessType = businessType;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HylCartListModel.DataBean.ValidListBean.SpecProductListBean.ProductDescVOListBean item) {
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_old_price = helper.getView(R.id.tv_old_price);
        ImageView iv_cut = helper.getView(R.id.iv_cut);
        ImageView iv_add = helper.getView(R.id.iv_add);
        final TextView tv_num = helper.getView(R.id.tv_num);

        tv_price.setText(item.getPriceStr());
        if(item.getOldPrice()!=""||item.getOldPrice()!=null) {
            tv_old_price.setVisibility(View.VISIBLE);
            tv_old_price.setText(item.getOldPrice());
        }else {
            tv_old_price.setVisibility(View.VISIBLE);
        }
        tv_num.setText(item.getProductNum()+"");


        iv_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<Integer> cartIds = new ArrayList<>();
                int num = Integer.parseInt(tv_num.getText().toString());

                if(num==1) {
                    CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
                    final CustomDialog dialog = builder.view(R.layout.dialog_delete_cart_hyl)
                            .style(R.style.DialogStyle)
                            .heightdp(128)
                            .widthdp(270)
                            .build();

                    dialog.show();
                    tv_ok = dialog.findViewById(R.id.tv_ok);
                    tv_cancel = dialog.findViewById(R.id.tv_cancel);
                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    tv_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cartIds.clear();
                            cartIds.add(cartId);
                            addCartNum(businessType,item.getProductCombinationPriceId(),businessId,0,tv_num);
                            dialog.dismiss();

                        }
                    });
                }else {
                    num--;
                    addCartNum(businessType,item.getProductCombinationPriceId(),businessId,num,tv_num);
                }
            }
        });

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(tv_num.getText().toString());
                num++;
                addCartNum(businessType,item.getProductCombinationPriceId(),businessId,num,tv_num);
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

                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylAddToCartModel.getMessage());
                        }
                    }
                });
    }
}
