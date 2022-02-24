package com.barter.hyl.app.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylCartListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${王涛} on 2021/9/15
 */
public class HylGivenCouponAdapter extends BaseQuickAdapter<HylCartListModel.DataBean,BaseViewHolder> {
    public HylGivenCouponAdapter(int layoutResId, @Nullable List<HylCartListModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HylCartListModel.DataBean item) {

    }
//    List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean.AdditionVOListBean> additionVOList;
//    public HylGivenCouponAdapter(int layoutResId, List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean.AdditionVOListBean> additionVOList) {
//        super(layoutResId, additionVOList);
////        this.additionVOList = additionVOList;
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, HylCartListModel.DataBean.ValidListBean.SpecProductListBean.AdditionVOListBean item) {
//        LinearLayout rl_root = helper.getView(R.id.rl_root);
//        ImageView iv_head = helper.getView(R.id.iv_head);
//        TextView tv_desc = helper.getView(R.id.tv_desc);
////        if(additionVOList.equals(null)) {
////            rl_root.setVisibility(View.GONE);
////            return;
////        }else {
////            rl_root.setVisibility(View.VISIBLE);
////        }
//        TextView tv_name = helper.getView(R.id.tv_name);
//        tv_name.setText(item.getName());
//
//        TextView tv_num = helper.getView(R.id.tv_num);
//        tv_num.setText("*"+item.getSendNum());
//
//
//        if(item.getAdditionFlag()==2) {
//            iv_head.setImageResource(R.mipmap.icon_grey_head);
//            tv_name.setBackgroundResource(R.mipmap.icon_grey_content);
//            tv_name.setTextColor(Color.parseColor("#ffffff"));
//            tv_num.setTextColor(Color.parseColor("#B2B2B2"));
//            tv_desc.setVisibility(View.VISIBLE);
//        }else {
//            iv_head.setImageResource(R.mipmap.icon_red_head);
//            tv_name.setBackgroundResource(R.mipmap.icon_pink_content);
//            tv_name.setTextColor(Color.parseColor("#FF0026"));
//            tv_num.setTextColor(Color.parseColor("#FF0026"));
//            tv_desc.setVisibility(View.GONE);
//        }
//    }
}
