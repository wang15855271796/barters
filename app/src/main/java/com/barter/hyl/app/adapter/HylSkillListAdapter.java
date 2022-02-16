package com.barter.hyl.app.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylSkillListModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/8/31
 */
public class HylSkillListAdapter extends BaseQuickAdapter<HylSkillListModel.DataBean.ActivesBean,BaseViewHolder> {

    OnAddClickListener onAddClickListener;
    int num = 0;
    List<HylSkillListModel.DataBean> list;
    public HylSkillListAdapter(int layoutResId, List<HylSkillListModel.DataBean> list, @Nullable List<HylSkillListModel.DataBean.ActivesBean> data, OnAddClickListener onAddClickListener) {
        super(layoutResId, data);
        this.onAddClickListener = onAddClickListener;
        this.list = list;
    }


    @Override
    protected void convert(BaseViewHolder helper, final HylSkillListModel.DataBean.ActivesBean item) {
        ImageView iv_sold = helper.getView(R.id.iv_sold);
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        ImageView iv_add = helper.getView(R.id.iv_add);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_spec = helper.getView(R.id.tv_spec);
        TextView tv_old_price = helper.getView(R.id.tv_old_price);
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_old_price.getPaint().setAntiAlias(true);//抗锯齿
        if(item.getOldPrice()!=""||item.getOldPrice()!=null) {
            tv_old_price.setVisibility(View.VISIBLE);
            tv_old_price.setText(item.getOldPrice());
        }else {
            tv_old_price.setVisibility(View.VISIBLE);
        }

        if(item.getFlag()==1) {
            //已开始
            iv_add.setVisibility(View.VISIBLE);
        }else {
            //未开始
            iv_add.setVisibility(View.GONE);
        }

        if(item.getInventFlag()==0) {
            //没售完
            iv_sold.setVisibility(View.GONE);
            iv_add.setImageResource(R.mipmap.icon_skill_add_cart);
        }else {
            iv_sold.setVisibility(View.VISIBLE);
            iv_add.setImageResource(R.mipmap.icon_unskill_add_cart);
        }
        ProgressBar pb = helper.getView(R.id.pb);
        TextView tv_surplus = helper.getView(R.id.tv_surplus);
        int remainNum = item.getRemainNum();
        int totalNum = item.getTotalNum();
        float present = (float) remainNum / totalNum * 100;
        pb.setProgress((int) present);
        tv_title.setText(item.getName());
        tv_surplus.setText("余量："+item.getRemainNum());
        tv_price.setText(item.getPrice());
        tv_spec.setText(item.getSpec());
        Glide.with(mContext).load(item.getPic()).into(iv_pic);

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClickListener.onAddClick(2,item.getActiveId(),1);
            }
        });
    }

    public interface OnAddClickListener {
        void onAddClick(int activeType,int activeId,int num);
    }

    //加入购物车
//    private void addCartNum(int businessType, int priceId, int businessId, final int num) {
//        DetailApi.addCart(mContext,businessType,priceId,businessId,num)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<HylAddToCartModel>() {
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
//                    public void onNext(HylAddToCartModel addToCartModel) {
//                        if (addToCartModel.getCode()==1) {
//                            if(addToCartModel.getData().getAddFlag()==0) {
//                                EventBus.getDefault().post(new CartListHylEvent());
//                                getCartNum();
//                            }else {
//                                EventBus.getDefault().post(new CartListHylEvent());
//                                ToastUtil.showSuccessMsg(mContext,addToCartModel.getMessage());
//                                tv_number.setText(addToCartModel.getData().getNum()+"");
//                            }
//                        } else {
//                            ToastUtil.showSuccessMsg(mContext, addToCartModel.getMessage());
//                        }
//                    }
//                });
//    }
}
