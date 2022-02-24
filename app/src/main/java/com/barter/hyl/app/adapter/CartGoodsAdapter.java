package com.barter.hyl.app.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylActiveDetailActivity;
import com.barter.hyl.app.activity.HylCommonGoodsActivity;
import com.barter.hyl.app.event.TotalAmountHylEvent;
import com.barter.hyl.app.model.HylCartListModel;
import com.barter.hyl.app.view.RoundImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CartGoodsAdapter extends BaseQuickAdapter<HylCartListModel.DataBean.ProdsBeanX.ProdsBean, BaseViewHolder> {
    List<HylCartListModel.DataBean.ProdsBeanX.ProdsBean> data;
    List<HylCartListModel.DataBean.ProdsBeanX> prodsBeanX;
    HylCartAdapter cartAdapter;
    public CartGoodsAdapter(int layoutResId, HylCartAdapter cartAdapter, List<HylCartListModel.DataBean.ProdsBeanX> prodsBeanX,  @Nullable List<HylCartListModel.DataBean.ProdsBeanX.ProdsBean> data) {
        super(layoutResId, data);
        this.data = data;
        this.cartAdapter = cartAdapter;
        this.prodsBeanX = prodsBeanX;
    }

    @Override
    protected void convert(BaseViewHolder helper, HylCartListModel.DataBean.ProdsBeanX.ProdsBean item) {
        ImageView iv_send = helper.getView(R.id.iv_send);
//        ImageView iv_icon = helper.getView(R.id.iv_icon);
        LinearLayout ll_root = helper.getView(R.id.ll_root);
//        ImageView iv_operate = helper.getView(R.id.iv_operate);
        RoundImageView iv_head = helper.getView(R.id.iv_head);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_spec = helper.getView(R.id.tv_spec);
        CheckBox cb_item_out = helper.getView(R.id.cb_item_out);
        CheckBox cb_spec = helper.getView(R.id.cb_spec);
        tv_spec.setText("规格:"+item.getSpec());

        if(item.getNotSend()!=null) {
            if(item.getNotSend().equals("1")||item.getNotSend().equals("1.0")) {
                iv_send.setImageResource(R.mipmap.icon_not_send);
                iv_send.setVisibility(View.VISIBLE);
            }else {
                iv_send.setVisibility(View.GONE);
            }
        }
//        if(item.getFlagUrl()!=null&&item.getFlagUrl()!="") {
//            Glide.with(mContext).load(item.getFlagUrl()).into(iv_icon);
//        }

//        if(item.getSelfProd()!=null&&!item.getSelfProd().equals("")) {
//            Glide.with(mContext).load(item.getSelfProd()).into(iv_operate);
//        }

        tv_title.setText(item.getProductName());
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_head);

        RecyclerView rv_price = helper.getView(R.id.rv_price);
        rv_price.setLayoutManager(new LinearLayoutManager(mContext));
        CartPriceAdapter cartPriceAdapter = new CartPriceAdapter(R.layout.item_choose_content,item,item.getProductDescVOList(),cartAdapter);
        rv_price.setAdapter(cartPriceAdapter);

        cb_item_out.setChecked(item.isSelected());
        cb_spec.setChecked(item.isSelected());
        cb_item_out.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isPressed()) {
                    item.setSelected(isChecked);
                    cb_spec.setChecked(isChecked);
                }

                for (HylCartListModel.DataBean.ProdsBeanX prodsBeanX: prodsBeanX) {
                    prodsBeanX.setSelect(isChecked);
                }

                cartAdapter.notifyDataSetChanged();
                EventBus.getDefault().post(new TotalAmountHylEvent(cartAdapter.getAllPrice()));
            }
        });

        cb_spec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.isSelected()) {
                    item.setSelected(false);
                    cb_spec.setChecked(false);
                    cb_item_out.setChecked(false);
                }else {
                    item.setSelected(true);
                    cb_item_out.setChecked(true);
                    cb_spec.setChecked(true);
                }
            }
        });

        ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getBusinessType()==1) {
                    Intent intent = new Intent(mContext, HylCommonGoodsActivity.class);
                    intent.putExtra("mainId",item.getProductMainId());
                    mContext.startActivity(intent);
                }else if(item.getBusinessType()==2){
                    Intent intent = new Intent(mContext, HylActiveDetailActivity.class);
                    intent.putExtra("activeId",item.getBusinessId());
                    mContext.startActivity(intent);
                }else {
                    Intent intent = new Intent(mContext,HylActiveDetailActivity.class);
                    intent.putExtra("activeId",item.getBusinessId());
                    mContext.startActivity(intent);
                }
            }
        });
    }
}
