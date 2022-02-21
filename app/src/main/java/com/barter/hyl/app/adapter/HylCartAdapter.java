package com.barter.hyl.app.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.event.TotalAmountHylEvent;
import com.barter.hyl.app.model.HylCartListModel;
import com.barter.hyl.app.view.Arith;
import com.barter.hyl.app.view.RoundImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ${王涛} on 2021/8/24（购物车商品列表adapter）
 */
public class HylCartAdapter extends BaseQuickAdapter<HylCartListModel.DataBean.ValidListBean,BaseViewHolder> {
    List<HylCartListModel.DataBean.ValidListBean> data;
    private OnResfreshListener mOnResfreshListener;
    private IProductSelectCallback iProductSelectCallback;
    public interface IProductSelectCallback {
        void update(List<HylCartListModel.DataBean.ValidListBean> data);
    }

    //主要是显示商品列表的
    public HylCartAdapter(int layoutResId, @Nullable List<HylCartListModel.DataBean.ValidListBean> data, IProductSelectCallback iProductSelectCallback) {
        super(layoutResId, data);
        this.data = data;
        this.iProductSelectCallback = iProductSelectCallback;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HylCartListModel.DataBean.ValidListBean item) {
        TextView tv_delete = helper.getView(R.id.tv_delete);
        RoundImageView iv_head = helper.getView(R.id.iv_head);
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_title = helper.getView(R.id.tv_title);
        CheckBox cb_item_out = helper.getView(R.id.cb_item_out);
        RecyclerView rv_spec = helper.getView(R.id.rv_spec);
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_head);
        tv_title.setText(item.getProductName());

        rv_spec.setLayoutManager(new LinearLayoutManager(mContext));
        HylCartSpecAdapter hylCartSpecAdapter = new HylCartSpecAdapter(R.layout.item_cart_spec_hyl,item.getSpecProductList(),this);
        hylCartSpecAdapter.setNewData(item.getSpecProductList());
        rv_spec.setAdapter(hylCartSpecAdapter);
        hylCartSpecAdapter.setBusinessType(item.getBusinessType());
        hylCartSpecAdapter.setValidListBean(item);


        if(item.getBusinessType()!=1) {
            if(item.getFlagUrl()!=null) {
                Glide.with(mContext).load(item.getFlagUrl()).into(iv_icon);
                iv_icon.setVisibility(View.VISIBLE);
            }else {
                iv_icon.setVisibility(View.GONE);
            }
        }else {
            iv_icon.setVisibility(View.GONE);
        }

        cb_item_out.setOnCheckedChangeListener(null);
        cb_item_out.setChecked(item.isSelected());
        if(mOnResfreshListener != null){
            boolean isSelect = false;

            for(int i = 0;i < data.size(); i++){
                if(!data.get(i).isSelected()){
                    isSelect = false;
                    break;
                }else{
                    isSelect = true;
                }
            }
            mOnResfreshListener.onResfresh(isSelect);
            iProductSelectCallback.update(data);
        }

        cb_item_out.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isPressed()) {
                    item.setSelected(isChecked);
                    for (HylCartListModel.DataBean.ValidListBean.SpecProductListBean specProductList: item.getSpecProductList()) {
                        specProductList.setSelected(isChecked);
                        notifyDataSetChanged();
                    }

                }

                EventBus.getDefault().post(new TotalAmountHylEvent(getAllPrice()));
                iProductSelectCallback.update(data);
            }
        });


    }

    /**
     * 设置全选和不选
     * @param b
     */
    public void setAllselect(boolean b) {
        for(int i=0;i<data.size();i++){
            data.get(i).setSelected(b);
            for (HylCartListModel.DataBean.ValidListBean.SpecProductListBean specProductList : data.get(i).getSpecProductList()){
                specProductList.setSelected(b);
            }
        }

        notifyDataSetChanged();
        EventBus.getDefault().post(new TotalAmountHylEvent(getAllPrice()));

        iProductSelectCallback.update(data);
    }

    public String getAllPrice() {
        BigDecimal allprice =new BigDecimal("0");
        if(data!=null){
            for (int i=0;i<data.size();i++){
                List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean> specProductList = data.get(i).getSpecProductList();
                for (int y=0;y<specProductList.size();y++){
                    if(specProductList.get(y).isSelected()){
                        List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean.ProductDescVOListBean> productDescVOList = specProductList.get(y).getProductDescVOList();
                        for (int j = 0; j <productDescVOList.size() ; j++) {
                            BigDecimal interestRate = new BigDecimal(productDescVOList.get(j).getProductNum()); //数量
                            double interest = Arith.mul(Double.parseDouble(productDescVOList.get(j).getPrice()), interestRate);
                            allprice=allprice.add(BigDecimal.valueOf(interest));
                        }
                    }
                }
            }
        }
        return allprice.toString();
    }

    public void setResfreshListener(OnResfreshListener mOnResfreshListener){
        this.mOnResfreshListener = mOnResfreshListener;
    }


    public interface OnResfreshListener{
        void onResfresh(boolean isSelect);
    }

}
