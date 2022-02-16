package com.barter.hyl.app.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.event.TotalAmountHylEvent;
import com.barter.hyl.app.model.HylCartListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2021/8/24（购物车商品规格adapter）
 */
public class HylCartSpecAdapter extends BaseQuickAdapter<HylCartListModel.DataBean.ValidListBean.SpecProductListBean,BaseViewHolder> {

    List<HylCartListModel.DataBean.ValidListBean> listAll = new ArrayList<>();
    //购物车商品规格列表
    List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean> specProductListBeanList;
    //购物车商品对象
    HylCartListModel.DataBean.ValidListBean validListBean;
    HylCartAdapter hylCartAdapter;
    int businessType;
    //优惠券列表
//    List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean.AdditionVOListBean>additionVOList1 = new ArrayList<>();
//    //赠送商品列表
//    List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean.AdditionVOListBean>additionVOList2 = new ArrayList<>();

//    //规格列表adapter
//    public HylCartSpecAdapter(int layoutResId, @Nullable List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean> specProductListBeanList,
//                           HylCartListModel.DataBean.ValidListBean validListBean, HylCartAdapter hylCartAdapter, int businessType) {
//        super(layoutResId, specProductListBeanList);
//        this.specProductListBeanList = specProductListBeanList;
//        this.validListBean = validListBean;
//        this.hylCartAdapter = hylCartAdapter;
//        this.businessType = businessType;
//    }
    public HylCartSpecAdapter(int layoutResId, @Nullable List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean> specProductListBeanList, HylCartAdapter hylCartAdapter){
        super(layoutResId, specProductListBeanList);
        this.specProductListBeanList = specProductListBeanList;
        this.hylCartAdapter = hylCartAdapter;

    }
    public void  setBusinessType(int businessType){
        this.businessType=businessType;
    }
    public void  setValidListBean( HylCartListModel.DataBean.ValidListBean validListBean){
        this.validListBean=validListBean;
    }
    @Override
    protected void convert(BaseViewHolder helper, final HylCartListModel.DataBean.ValidListBean.SpecProductListBean item) {
//        helper.setIsRecyclable(false);
        TextView tv_spec = helper.getView(R.id.tv_spec);
        ImageView iv_send = helper.getView(R.id.iv_send);
        TextView tv_limit = helper.getView(R.id.tv_limit);
        CheckBox cb_item_in = helper.getView(R.id.cb_item_in);
        RecyclerView rv_price = helper.getView(R.id.rv_price);
        //ni这边写的有点乱。 你自己看看就是如果有优惠券你就隐藏掉赠品Recycle 如果有赠品隐藏点优惠券 你自己写一下。
        RecyclerView rv_given_coupon = helper.getView(R.id.rv_given_coupon);
        RecyclerView rv_given_goods = helper.getView(R.id.rv_given_goods);
        rv_given_goods.setLayoutManager(new LinearLayoutManager(mContext));
        HylGivenGoodsAdapter hylGivenGoodsAdapter = new HylGivenGoodsAdapter(R.layout.item_given_goods_hyl,null);
        rv_given_coupon.setLayoutManager(new LinearLayoutManager(mContext));
        rv_given_goods.setAdapter(hylGivenGoodsAdapter);
        HylGivenCouponAdapter hylGivenCouponAdapter = new HylGivenCouponAdapter(R.layout.item_given_coupon_hyl,null);
        //优惠券
        rv_given_coupon.setAdapter(hylGivenCouponAdapter);

        List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean.AdditionVOListBean> additionVOList = item.getAdditionVOList();
            //优惠券集合
        List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean.AdditionVOListBean> additionVOList1 = new ArrayList<>();
            //赠品集合
        List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean.AdditionVOListBean>additionVOList2  = new ArrayList<>();
          if (additionVOList!=null&&additionVOList.size()!=0){
              for (int j = 0; j < additionVOList.size(); j++) {
                  int type = additionVOList.get(j).getType();
                  if(type==0) {
                      // 2.赠商品 不行 还是都展示
                      additionVOList2.add(additionVOList.get(j));
                  }else if (type==1){
                      // 1.赠优惠券
                      additionVOList1.add(additionVOList.get(j));
                  }
              }

              if (additionVOList2!=null&&additionVOList2.size()!=0){
                  hylGivenGoodsAdapter.setNewData(additionVOList2);
              }else {
                  hylGivenGoodsAdapter.setNewData(null);
              }
            if (additionVOList1!=null&&additionVOList1.size()!=0){
                hylGivenCouponAdapter.setNewData(additionVOList1);
            }else {
                  hylGivenCouponAdapter.setNewData(null);
            }

              } else {

              hylGivenGoodsAdapter.setNewData(null);

              hylGivenCouponAdapter.setNewData(null);
          }
        cb_item_in.setOnCheckedChangeListener(null);
        cb_item_in.setChecked(item.isSelected());
        tv_spec.setText("规格："+item.getSpec());
        if(item.getBuyNumLimit()=="") {
            tv_limit.setVisibility(View.GONE);
        }else {
            tv_limit.setVisibility(View.VISIBLE);
            tv_limit.setText(item.getBuyNumLimit());
        }

        cb_item_in.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //更改item选中状态同时进行实体内的选中状态改变
                item.setSelected(isChecked);
                listAll.clear();
                boolean noSelect = false;
                //内层item选中状态改变后要遍历判断是否全选，以改变外层item的选中状态
                for (HylCartListModel.DataBean.ValidListBean.SpecProductListBean validListBean : specProductListBeanList) {
                    if (!validListBean.isSelected()) {
                        noSelect = true;
                    }
                }
                if (!noSelect) {
                    validListBean.setSelected(!noSelect);
                    hylCartAdapter.notifyDataSetChanged();
                } else {
                    validListBean.setSelected(!noSelect);
                    hylCartAdapter.notifyDataSetChanged();

                }

                EventBus.getDefault().post(new TotalAmountHylEvent(hylCartAdapter.getAllPrice()));
            }
        });

        if(item.getNotSend()==1) {
            iv_send.setImageResource(R.mipmap.icon_not_send);
            iv_send.setVisibility(View.VISIBLE);
        }else {
            iv_send.setVisibility(View.GONE);
        }

        rv_price.setLayoutManager(new LinearLayoutManager(mContext));
        HylChooseCartPriceAdapter hylChooseCartPriceAdapter = new HylChooseCartPriceAdapter(R.layout.item_choose_content,
                item.getProductDescVOList(),item.getBusinessId(),item.getCartId(),businessType);
        rv_price.setAdapter(hylChooseCartPriceAdapter);
        hylChooseCartPriceAdapter.notifyDataSetChanged();


    }
}
