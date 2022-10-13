package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.FullActiveActivity;
import com.barter.hyl.app.event.TotalAmountHylEvent;
import com.barter.hyl.app.model.HylCartListModel;
import com.barter.hyl.app.view.Arith;
import com.barter.hyl.app.view.RoundImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2021/8/24（购物车商品列表adapter）
 */
public class HylCartAdapter extends BaseQuickAdapter<HylCartListModel.DataBean.ProdsBeanX,BaseViewHolder> {
    List<HylCartListModel.DataBean.ProdsBeanX> data;
    private OnResfreshListener mOnResfreshListener;
    private IProductSelectCallback iProductSelectCallback;
    List<HylCartListModel.DataBean.ProdsBeanX.AdditionsBean> addition1 = new ArrayList<>();
    List<HylCartListModel.DataBean.ProdsBeanX.AdditionsBean> addition2 = new ArrayList<>();
    CartGoodsAdapter cartGoodsAdapter;
    public interface IProductSelectCallback {
        void update(List<HylCartListModel.DataBean.ProdsBeanX> data);
    }

    //主要是显示商品列表的
    public HylCartAdapter(int layoutResId, @Nullable List<HylCartListModel.DataBean.ProdsBeanX> data, IProductSelectCallback iProductSelectCallback) {
        super(layoutResId, data);
        this.data = data;
        this.iProductSelectCallback = iProductSelectCallback;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HylCartListModel.DataBean.ProdsBeanX item) {
        TextView tv_tip = helper.getView(R.id.tv_tip);
        RelativeLayout rl_tip = helper.getView(R.id.rl_tip);
        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        RecyclerView rv_coupon = helper.getView(R.id.rv_coupon);
        TextView tv_desc = helper.getView(R.id.tv_desc);

        if(item.getSendFullType()==0) {
            tv_desc.setText("去凑单");
        }else {
            tv_desc.setText("查看活动");
        }


//        tv_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(onclick!=null) {
//                    onclick.deleteItem(helper.getAdapterPosition(),item);
//                }
//            }
//        });
        rl_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FullActiveActivity.class);
                intent.putExtra("fullId",item.getFullId());
                mContext.startActivity(intent);
            }
        });
        if(item.getFullType()==1) {
            tv_tip.setText(item.getTips());
            rl_tip.setVisibility(View.VISIBLE);
        }else {
            rl_tip.setVisibility(View.GONE);
        }

        addition1 = new ArrayList<>();
        addition2 = new ArrayList<>();

        if(item.getAdditions()!=null) {
            for (int i = 0; i < item.getAdditions().size(); i++) {
                if(item.getAdditions().get(i).getType()==0) {
                    addition1.add(item.getAdditions().get(i));
                }else {
                    addition2.add(item.getAdditions().get(i));
                }
            }
        }

        CartGivenAdapter givenAdapter = new CartGivenAdapter(R.layout.item_given_goods_hyl,addition1);
        rv_goods.setAdapter(givenAdapter);
        rv_goods.setLayoutManager(new LinearLayoutManager(mContext));

        CartCouponAdapter cartCouponAdapter = new CartCouponAdapter(R.layout.item_given_coupon_hyl,addition2);
        rv_coupon.setAdapter(cartCouponAdapter);
        rv_coupon.setLayoutManager(new LinearLayoutManager(mContext));

        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        cartGoodsAdapter = new CartGoodsAdapter(R.layout.item_goods, this, data, item.getProds());
        recyclerView.setAdapter(cartGoodsAdapter);
        cartGoodsAdapter.notifyDataSetChanged();

//        TextView tv_delete = helper.getView(R.id.tv_delete);
//        RoundImageView iv_head = helper.getView(R.id.iv_head);
//        ImageView iv_icon = helper.getView(R.id.iv_icon);
//        TextView tv_title = helper.getView(R.id.tv_title);
//        CheckBox cb_item_out = helper.getView(R.id.cb_item_out);
//        RecyclerView rv_spec = helper.getView(R.id.rv_spec);
//        Glide.with(mContext).load(item.getDefaultPic()).into(iv_head);
//        tv_title.setText(item.getProductName());
//
//        rv_spec.setLayoutManager(new LinearLayoutManager(mContext));
//        HylCartSpecAdapter hylCartSpecAdapter = new HylCartSpecAdapter(R.layout.item_cart_spec_hyl,item.getSpecProductList(),this);
//        hylCartSpecAdapter.setNewData(item.getSpecProductList());
//        rv_spec.setAdapter(hylCartSpecAdapter);
//        hylCartSpecAdapter.setBusinessType(item.getBusinessType());
//        hylCartSpecAdapter.setValidListBean(item);
//
//
//        if(item.getBusinessType()!=1) {
//            if(item.getFlagUrl()!=null) {
//                Glide.with(mContext).load(item.getFlagUrl()).into(iv_icon);
//                iv_icon.setVisibility(View.VISIBLE);
//            }else {
//                iv_icon.setVisibility(View.GONE);
//            }
//        }else {
//            iv_icon.setVisibility(View.GONE);
//        }
//
//        cb_item_out.setOnCheckedChangeListener(null);
//        cb_item_out.setChecked(item.isSelected());
        if(mOnResfreshListener != null){
            boolean isSelect = false;

            for(int i = 0;i < data.size(); i++){
                if(!data.get(i).isSelect()){
                    isSelect = false;
                    break;
                }else{
                    isSelect = true;
                }
            }
            mOnResfreshListener.onResfresh(isSelect);
            iProductSelectCallback.update(data);
        }
//
//        cb_item_out.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(buttonView.isPressed()) {
//                    item.setSelected(isChecked);
//                    for (HylCartListModel.DataBean.ValidListBean.SpecProductListBean specProductList: item.getSpecProductList()) {
//                        specProductList.setSelected(isChecked);
//                        notifyDataSetChanged();
//                    }
//
//                }
//
//                EventBus.getDefault().post(new TotalAmountHylEvent(getAllPrice()));
//                iProductSelectCallback.update(data);
//            }
//        });
//

    }

    /**
     * 设置全选和不选
     * @param isChoose
     */
    //是否全选
    public void setSelectAll(boolean isChoose) {
        for (int i = 0; i < data.size(); i++) {
            List<HylCartListModel.DataBean.ProdsBeanX.ProdsBean> prods = data.get(i).getProds();
            data.get(i).setSelect(isChoose);
            for (int j = 0; j < prods.size(); j++) {
                prods.get(j).setSelected(isChoose);
            }
        }
        notifyDataSetChanged();
        EventBus.getDefault().post(new TotalAmountHylEvent(getAllPrice()));
        iProductSelectCallback.update(data);
//        notifyDataSetChanged();
//        EventBus.getDefault().post(new UpdateEvent(getAllPrice()));
    }


    public String getAllPrice() {
        BigDecimal allPrice =new BigDecimal("0");
        for (int i = 0; i < data.size(); i++) {
            List<HylCartListModel.DataBean.ProdsBeanX.ProdsBean> prods1 = data.get(i).getProds();
            for (int j = 0; j < prods1.size(); j++) {
                if(prods1.get(j).isSelected()) {
                    List<HylCartListModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean> productDescVOList = prods1.get(j).getProductDescVOList();
                    for (int k = 0; k < productDescVOList.size(); k++) {
                        BigDecimal cartNum = new BigDecimal(productDescVOList.get(k).getProductNum());
                        double totals = Arith.mul(Double.parseDouble(productDescVOList.get(k).getPrice()),cartNum);
                        allPrice = allPrice.add(BigDecimal.valueOf(totals));
                    }
                }
            }
        }
        return allPrice.toString();
    }

//    public void setAllselect(boolean b) {
//        for(int i=0;i<data.size();i++){
//            data.get(i).getProds().setSelected(b);
//            for (HylCartListModel.DataBean.ValidListBean.SpecProductListBean specProductList : data.get(i).getSpecProductList()){
//                specProductList.setSelected(b);
//            }
//        }
//
//        notifyDataSetChanged();
//        EventBus.getDefault().post(new TotalAmountHylEvent(getAllPrice()));
//
//        iProductSelectCallback.update(data);
//    }


    public void setResfreshListener(OnResfreshListener mOnResfreshListener){
        this.mOnResfreshListener = mOnResfreshListener;
    }


    public interface OnResfreshListener{
        void onResfresh(boolean isSelect);
    }

}
