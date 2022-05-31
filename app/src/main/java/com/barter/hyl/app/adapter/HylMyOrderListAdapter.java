package com.barter.hyl.app.adapter;

import android.content.Intent;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylOrderDetailActivity;
import com.barter.hyl.app.activity.HylReturnGoodDetailActivity;
import com.barter.hyl.app.event.DelayOrderNumEvent;
import com.barter.hyl.app.event.TotalAmountHylEvent;
import com.barter.hyl.app.model.HylMyOrderListModel;
import com.barter.hyl.app.view.GlideModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/10.
 */

public class HylMyOrderListAdapter extends BaseQuickAdapter<HylMyOrderListModel.DataBean.ListBean, BaseViewHolder> {

    private int orderState;
    private ImageView commodityOne;
    private ImageView commodityTwo;
    private ImageView commodityThree;
    private ImageView commodityFour;
    private ImageView commodityMore;
    private TextView tv_status;
    private OnClick onClick;
    private LinearLayout linearLayoutItem;

    int orderDeliveryType;
    TextView tv_product_name;
    TextView tv_time;
    TextView tv_state;
    LinearLayout ll_choose;
    Map<Integer, Boolean> isCheck;
    List<HylMyOrderListModel.DataBean.ListBean> data;
    List<String> ids = new ArrayList<>();
    public HylMyOrderListAdapter(int layoutResId, @Nullable List<HylMyOrderListModel.DataBean.ListBean> data, Map<Integer, Boolean> isCheck, int orderState, int orderDeliveryType, OnClick onClick) {
        super(layoutResId, data);
        this.orderState = orderState;
        this.onClick = onClick;
        this.data = data;
        this.isCheck = isCheck;
        this.orderDeliveryType = orderDeliveryType;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HylMyOrderListModel.DataBean.ListBean item) {
        helper.setIsRecyclable(false);
        TextView tv_keep = helper.getView(R.id.tv_keep);
        RelativeLayout rl_canceled =  helper.getView(R.id.rl_canceled);
        TextView tv_delete_order =  helper.getView(R.id.tv_delete_order);
        TextView tv_buy_again =  helper.getView(R.id.tv_buy_again);
        RelativeLayout rl_wait_pay = helper.getView(R.id.rl_wait_pay);
        TextView tv_sure_receive = helper.getView(R.id.tv_sure_receive);
        TextView tv_cancel_order = helper.getView(R.id.tv_cancel_order);
        TextView tv_cancel = helper.getView(R.id.tv_cancel);
        TextView tv_now_pay = helper.getView(R.id.tv_now_pay);
        TextView tv_now_pay3 = helper.getView(R.id.tv_now_pay3);
        TextView tv_now_pay1 = helper.getView(R.id.tv_now_pay1);
        TextView tv_now_pay2 = helper.getView(R.id.tv_now_pay2);
        TextView tv_again_buy1 = helper.getView(R.id.tv_again_buy1);
        TextView tv_again_buy2 = helper.getView(R.id.tv_again_buy2);
        TextView tv_again_buy3 = helper.getView(R.id.tv_again_buy3);
        TextView tv_now_eval = helper.getView(R.id.tv_now_eval);
        RelativeLayout rl_receive_goods = helper.getView(R.id.rl_receive_goods);
        RelativeLayout rl_wait_eval= helper.getView(R.id.rl_wait_eval);
        RelativeLayout rl_wait_check= helper.getView(R.id.rl_wait_check);
        RelativeLayout rl_wait_dispatch= helper.getView(R.id.rl_wait_dispatch);
        ll_choose = helper.getView(R.id.ll_choose);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);
        tv_time = helper.getView(R.id.tv_time);
        tv_state = helper.getView(R.id.tv_state);
        tv_product_name = helper.getView(R.id.tv_product_name);
        commodityOne = helper.getView(R.id.commodityOne);
        commodityTwo = helper.getView(R.id.commodityTwo);
        commodityThree = helper.getView(R.id.commodityThree);
        commodityFour = helper.getView(R.id.commodityFour);
        commodityMore = helper.getView(R.id.commodityMore);
        tv_status = helper.getView(R.id.tv_status);
        linearLayoutItem = helper.getView(R.id.linearLayoutItem);
        tv_product_name.setText(item.getName());
        tv_time.setText(item.getDateTime());

        if(isShow) {
            if(item.isOfflinePay()) {
                ll_choose.setVisibility(View.GONE);
            }else {
                ll_choose.setVisibility(View.VISIBLE);
            }
        }else {
            ll_choose.setVisibility(View.GONE);
        }
        if(isCheck!=null&&isCheck.size()>0) {
            helper.setChecked(R.id.cb_choose, isCheck.get(helper.getAdapterPosition()));
        }
        helper.setText(R.id.tv_item_my_order_all_price, item.getTotalAmount());//总价

        if(item.getOrderStateStr()!=null) {
            tv_state.setVisibility(View.VISIBLE);
            tv_state.setText(item.getOrderStateStr());
        }else {
            tv_state.setVisibility(View.GONE);
        }


        cb_choose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ids.clear();
                if(buttonView.isPressed()) {
                    item.setSelected(isChecked);
                }
                EventBus.getDefault().post(new DelayOrderNumEvent(data));
                onClick.onEventClick(helper.getAdapterPosition(),getAllPrice(),ids);
            }
        });

        //删除订单
        tv_delete_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.deleteOnclick(item.getOrderId());
            }
        });

        //再次购买
        tv_buy_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.againBayOnclick(helper.getLayoutPosition());
            }
        });

        //取消订单
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.cancelOnclick(item.getOrderId());
            }
        });

        //取消订单
        tv_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.cancelOnclick(item.getOrderId());
            }
        });

        //立即评价
        tv_now_eval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.evaluateNowOnclick(helper.getLayoutPosition(),item.getOrderId());
            }
        });

        //再次购买
        tv_again_buy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.againBayOnclick(helper.getLayoutPosition());
            }
        });
        //再次购买
        tv_again_buy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.againBayOnclick(helper.getLayoutPosition());
            }
        });

        //再次购买
        tv_again_buy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.againBayOnclick(helper.getLayoutPosition());
            }
        });


        //立即付款
        tv_now_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //信用订单
                onClick.imageGo(item.getOrderId(),item.getTotalAmount(),item.getOrderType(),item.getId());
            }
        });
        //立即付款
        tv_now_pay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.imageGo(item.getOrderId(), item.getTotalAmount(),item.getOrderType(),item.getId());
            }
        });
        //立即付款
        tv_now_pay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.imageGo(item.getOrderId(), item.getTotalAmount(),item.getOrderType(),item.getId());
            }
        });

        //立即付款
        tv_now_pay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.imageGo(item.getOrderId(), item.getTotalAmount(),item.getOrderType(),item.getId());
            }
        });

        //确认收货
        tv_sure_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.requestConfirmGetGoods(item.getOrderId());
            }
        });

        if (orderState == 11) { //退货订单 不显示按钮
            tv_status.setText(item.getOrderStatusStr());
        } else {
            if(item.getOrderStatusStr()!=null) {
                tv_status.setText(item.getOrderStatusStr());
                if(item.getOrderType()==1) {
                    if(item.isPayFlag()) {
                        if(item.isOfflinePay()) {
                            //线下履约
                            tv_keep.setVisibility(View.VISIBLE);
                        }else {
                            tv_keep.setVisibility(View.GONE);
                        }
                        tv_now_pay1.setVisibility(View.GONE);
                        tv_now_pay.setVisibility(View.GONE);
                        tv_now_pay2.setVisibility(View.GONE);
                        tv_now_pay3.setVisibility(View.GONE);
                    }else {
                        if(item.isOfflinePay()) {
                            //线下履约
                            tv_keep.setVisibility(View.VISIBLE);
                            tv_now_pay1.setVisibility(View.GONE);
                            tv_now_pay.setVisibility(View.GONE);
                            tv_now_pay2.setVisibility(View.GONE);
                            tv_now_pay3.setVisibility(View.GONE);
                        }else {
                            tv_keep.setVisibility(View.GONE);
                            tv_now_pay1.setVisibility(View.VISIBLE);
                            tv_now_pay.setVisibility(View.VISIBLE);
                            tv_now_pay2.setVisibility(View.VISIBLE);
                            tv_now_pay3.setVisibility(View.VISIBLE);
                        }
                    }

                    if (item.getOrderStatusStr().equals("待发货-待接收")||item.getOrderStatusStr().equals("待发货-已接收")||item.getOrderStatusStr().equals("已评价")
                            ||item.getOrderStatusStr().equals("已退货")) { // 待收货显示 确认收货 和再次购买
                        rl_receive_goods.setVisibility(View.GONE);
                        rl_wait_eval.setVisibility(View.GONE);
                        rl_wait_check.setVisibility(View.GONE);
                        rl_wait_pay.setVisibility(View.GONE);
                        rl_wait_dispatch.setVisibility(View.VISIBLE);
                        rl_canceled.setVisibility(View.GONE);
                    } else if (item.getOrderStatusStr().equals("待收货")) { // 待收货显示 确认收货 和再次购买
                        rl_receive_goods.setVisibility(View.VISIBLE);
                        rl_wait_eval.setVisibility(View.GONE);
                        rl_wait_check.setVisibility(View.GONE);
                        rl_wait_pay.setVisibility(View.GONE);
                        rl_wait_dispatch.setVisibility(View.GONE);
                        rl_canceled.setVisibility(View.GONE);
                    }else if(item.getOrderStatusStr().equals("待审核")) {
                        rl_receive_goods.setVisibility(View.GONE);
                        rl_wait_eval.setVisibility(View.GONE);
                        rl_wait_check.setVisibility(View.VISIBLE);
                        rl_wait_pay.setVisibility(View.GONE);
                        rl_wait_dispatch.setVisibility(View.GONE);
                        rl_canceled.setVisibility(View.GONE);
                    }else if (item.getOrderStatusStr().equals("待评价")) {   // 立即评价 再次购买 立即付款
                        rl_receive_goods.setVisibility(View.GONE);
                        rl_wait_eval.setVisibility(View.VISIBLE);
                        rl_wait_check.setVisibility(View.GONE);
                        rl_wait_pay.setVisibility(View.GONE);
                        rl_wait_dispatch.setVisibility(View.GONE);
                        rl_canceled.setVisibility(View.GONE);
                    }else {
                        //已取消
                        rl_receive_goods.setVisibility(View.GONE);
                        rl_wait_eval.setVisibility(View.GONE);
                        rl_wait_check.setVisibility(View.GONE);
                        rl_wait_dispatch.setVisibility(View.GONE);
                        rl_wait_pay.setVisibility(View.GONE);
                        rl_canceled.setVisibility(View.VISIBLE);
                    }
                }else {
                    if (item.getOrderStatusStr().equals("待发货-待接收")||item.getOrderStatusStr().equals("待发货-已接收")||item.getOrderStatusStr().equals("已评价")
                            ||item.getOrderStatusStr().equals("已退货")) { // 待收货显示 确认收货 和再次购买
                        rl_receive_goods.setVisibility(View.GONE);
                        rl_wait_eval.setVisibility(View.GONE);
                        rl_wait_check.setVisibility(View.GONE);
                        rl_wait_dispatch.setVisibility(View.VISIBLE);
                        rl_wait_pay.setVisibility(View.GONE);
                    } else if (item.getOrderStatusStr().equals("待收货")) { // 待收货显示 确认收货 和再次购买
                        rl_receive_goods.setVisibility(View.VISIBLE);
                        rl_wait_eval.setVisibility(View.GONE);
                        rl_wait_check.setVisibility(View.GONE);
                        rl_wait_dispatch.setVisibility(View.GONE);
                        rl_wait_pay.setVisibility(View.GONE);
                    }else if(item.getOrderStatusStr().equals("待审核")) {
                        rl_receive_goods.setVisibility(View.GONE);
                        rl_wait_eval.setVisibility(View.GONE);
                        rl_wait_check.setVisibility(View.VISIBLE);
                        rl_wait_dispatch.setVisibility(View.GONE);
                        rl_wait_pay.setVisibility(View.GONE);
                    }else if (item.getOrderStatusStr().equals("待评价")) {   // 立即评价 再次购买 立即付款
                        rl_receive_goods.setVisibility(View.GONE);
                        rl_wait_eval.setVisibility(View.VISIBLE);
                        rl_wait_check.setVisibility(View.GONE);
                        rl_wait_dispatch.setVisibility(View.GONE);
                        rl_wait_pay.setVisibility(View.GONE);
                    }else if(item.getOrderStatusStr().equals("已取消")) {
                        rl_receive_goods.setVisibility(View.GONE);
                        rl_wait_eval.setVisibility(View.GONE);
                        rl_wait_check.setVisibility(View.GONE);
                        rl_wait_dispatch.setVisibility(View.GONE);
                        rl_wait_pay.setVisibility(View.GONE);
                        rl_canceled.setVisibility(View.VISIBLE);
                    } else {
                        //待付款
                        rl_receive_goods.setVisibility(View.GONE);
                        rl_wait_eval.setVisibility(View.GONE);
                        rl_wait_check.setVisibility(View.GONE);
                        rl_wait_dispatch.setVisibility(View.GONE);
                        rl_wait_pay.setVisibility(View.VISIBLE);
                    }
                }
            }
        }


        /**显示4张图*/
        if (item.getPics().size() >= 1) {
            if (item.getPics().get(0)!= null) {
                GlideModel.disPlayError(mContext, item.getPics().get(0), commodityOne);
            }

            commodityOne.setVisibility(View.VISIBLE);
        } else {

            commodityOne.setVisibility(View.GONE);
            commodityOne.setImageResource(R.mipmap.ic_launcher_round);
        }
        if (item.getPics().size() >= 2) {
            if (item.getPics().get(1) != null) {
                GlideModel.disPlayError(mContext, item.getPics().get(1), commodityTwo);
            }

            commodityTwo.setVisibility(View.VISIBLE);
        } else {
            commodityTwo.setVisibility(View.GONE);
            commodityTwo.setImageResource(R.mipmap.ic_launcher_round);
        }
        if ((item.getPics().size() >= 3)) {
            if (item.getPics().get(2) != null) {
                GlideModel.disPlayError(mContext, item.getPics().get(2), commodityThree);
            }

            commodityThree.setVisibility(View.VISIBLE);

        } else {
            commodityThree.setVisibility(View.GONE);
            commodityThree.setImageResource(R.mipmap.ic_launcher_round);
        }
        if (item.getPics().size() >= 4) {
            if (item.getPics().get(3) != null) {
                GlideModel.disPlayError(mContext, item.getPics().get(3), commodityFour);
            }

            commodityFour.setVisibility(View.VISIBLE);
        } else {
            commodityFour.setVisibility(View.GONE);
            commodityFour.setImageResource(R.mipmap.ic_launcher_round);
        }
        if (item.getPics().size() >= 4) {
            commodityMore.setVisibility(View.VISIBLE);
        } else {
            commodityMore.setVisibility(View.GONE);

        }

        linearLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderState==11) {
                    Intent intent =new Intent(mContext,HylReturnGoodDetailActivity.class);
                    intent.putExtra("returnMainId" ,item.getReturnMainId());
                    intent.putExtra("orderId" ,item.getOrderId());
                    mContext.startActivity(intent);
                }else {
                    Intent intent =new Intent(mContext,HylOrderDetailActivity.class);
                    intent.putExtra("returnMainId" ,item.getReturnMainId());
                    intent.putExtra("orderId" ,item.getOrderId());
                    intent.putExtra("status" ,item.getOrderStatus());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    String totalAmount;
    private String getAllPrice() {
        BigDecimal amount00 = new BigDecimal("0.00");
        BigDecimal amount000 = new BigDecimal("0.00");
        for (int i = 0; i < data.size(); i++) {
            boolean selected = data.get(i).isSelected();
            if(selected) {
                totalAmount= data.get(i).getTotalAmount();
                String substring = totalAmount.substring(1, totalAmount.length());
                BigDecimal amount0 = new BigDecimal(substring);
                amount00 = amount00.add(amount0);
                amount000 = amount00.setScale(2, RoundingMode.FLOOR);
            }
        }

        return amount000.doubleValue()+"";
    }

    boolean isShow;
    //是否展示checkbox
    public void setShow(boolean isShow) {
        this.isShow = isShow;
    }

    //全选
    List<HylMyOrderListModel.DataBean.ListBean> datas = new ArrayList<>();
    public void setSelectAll(boolean b) {
        ids.clear();
        datas.clear();
        for (int i = 0; i < data.size(); i++) {
            if(!data.get(i).isOfflinePay()) {
                datas.add(data.get(i));
            }
        }

        for(int i=0;i<datas.size();i++){
            datas.get(i).setSelected(b);

            if(datas.get(i).isSelected()) {
                ids.add(datas.get(i).getId());
            }
        }
        EventBus.getDefault().post(new TotalAmountHylEvent(getAllPrice(),ids));
    }


    public interface OnClick {

        void evaluateNowOnclick(int position, String orderId);

        void againBayOnclick(int position);

        void cancelOnclick(String orderId);

        void deleteOnclick(String orderId);

        void imageGo(String orderId, String totalAmount,int orderType,String id);

        void requestConfirmGetGoods(String orderId);

        void onEventClick(int position,String totalAmount,List<String> ids);
    }

}
