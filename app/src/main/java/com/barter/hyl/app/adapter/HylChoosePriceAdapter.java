package com.barter.hyl.app.adapter;

import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.event.CartListHylEvent;
import com.barter.hyl.app.event.CartNumHylEvent;
import com.barter.hyl.app.model.HylAddToCartModel;
import com.barter.hyl.app.model.HylCartNumModel;
import com.barter.hyl.app.model.HylChangeSpecModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/8/20
 */
public class HylChoosePriceAdapter extends BaseQuickAdapter<HylChangeSpecModel.DataBean.PricesBean,BaseViewHolder> {

    int productId;
    public HylChoosePriceAdapter(int layoutResId, @Nullable List<HylChangeSpecModel.DataBean.PricesBean> data, int productId) {
        super(layoutResId, data);
        this.productId = productId;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HylChangeSpecModel.DataBean.PricesBean item) {
        helper.setIsRecyclable(false);
        TextView tv_reduce = helper.getView(R.id.tv_reduce);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_old_price = helper.getView(R.id.tv_old_price);
        ImageView iv_cut = helper.getView(R.id.iv_cut);
        ImageView iv_add = helper.getView(R.id.iv_add);
        tv_price.setText(item.getPrice());

        final TextView tv_num = helper.getView(R.id.tv_num);
        tv_num.setText(item.getCartNum()+"");
        if(!item.getOldPrice().equals("")||item.getOldPrice()!=null) {
            tv_old_price.setVisibility(View.VISIBLE);
            tv_old_price.setText(item.getOldPrice());
        }else {
            tv_old_price.setVisibility(View.VISIBLE);
        }

        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_old_price.getPaint().setAntiAlias(true);//抗锯齿

        if(item.getDeductFlag()==0) {
            tv_reduce.setVisibility(View.GONE);
        }else {
            tv_reduce.setBackgroundResource(R.drawable.shape_orange);
            tv_reduce.setVisibility(View.VISIBLE);
        }

        iv_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(tv_num.getText().toString());
                if(num>0) {
                    num--;
                    addCartNum(1,item.getPriceId(),productId,num,tv_num);
                }else {
                    ToastUtil.showSuccessMsg(mContext,"当前数量为0");
                    addCartNum(1,item.getPriceId(),productId,0,tv_num);
                }
            }
        });

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(tv_num.getText().toString());
                num++;
                addCartNum(1,item.getPriceId(),productId,num,tv_num);
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
                                getCartNum();
                                ToastUtil.showSuccessMsg(mContext, hylAddToCartModel.getMessage());
                            }else {
                                EventBus.getDefault().post(new CartListHylEvent());
                                EventBus.getDefault().post(new CartNumHylEvent());
                                tv_num.setText(hylAddToCartModel.getData().getNum()+"");
                                ToastUtil.showSuccessMsg(mContext, hylAddToCartModel.getData().getMessage());
                            }

                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylAddToCartModel.getMessage());
                        }
                    }
                });
    }

    private void getCartNum() {
        DetailApi.getCartNum(mContext,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylCartNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylCartNumModel hylCartNumModel) {
                        if (hylCartNumModel.getCode()==1) {
                            EventBus.getDefault().post(new CartListHylEvent(hylCartNumModel));
                            EventBus.getDefault().post(new CartNumHylEvent());
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylCartNumModel.getMessage());
                        }
                    }
                });
    }

}
