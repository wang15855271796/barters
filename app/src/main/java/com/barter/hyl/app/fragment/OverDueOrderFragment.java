package com.barter.hyl.app.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.JudgeActivity;
import com.barter.hyl.app.activity.HylOrderDetailActivity;
import com.barter.hyl.app.adapter.HylMyOrderListAdapter;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.base.BaseFragment;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.event.CartListHylEvent;
import com.barter.hyl.app.event.CartNumHylEvent;
import com.barter.hyl.app.event.DelayOrderNumEvent;
import com.barter.hyl.app.event.TotalAmountHylEvent;
import com.barter.hyl.app.model.HylAmountNumModel;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.HylMyOrderListModel;
import com.barter.hyl.app.model.HylPayInfoModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/9/7
 */
public class OverDueOrderFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ptr_my_orders)
    PtrClassicFrameLayout mPtr;
    @BindView(R.id.rl_keep_order)
    RelativeLayout rl_keep_order;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.rl_foot)
    RelativeLayout rl_foot;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.cb_all_select)
    CheckBox cb_all_select;
    @BindView(R.id.ll_all_select)
    LinearLayout ll_all_select;
    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;
    @BindView(R.id.tv_pay2)
    TextView tv_pay2;
    @BindView(R.id.iv_no_data)
    ImageView iv_no_data;

    int pageNum = 1;
    int pageSize = 10;
    int orderDeliveryType;
    String orderState = "17";
    HylMyOrderListAdapter hylMyOrderListAdapter;
    boolean isShow;
    List<String> ids = new ArrayList<>();
    String totalAmounts;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_keep_my_order;
    }


    @Override
    public void onResume() {
        super.onResume();
        ids.clear();
        selectedNum = 0;
        cb_all_select.setChecked(false);
        tv_total_amount.setText("0.0");

        if (getUserVisibleHint()){
            pageNum = 1;
            getMyOrderList();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ((isVisibleToUser && isResumed())) {
            onResume();
        } else if (!isVisibleToUser) {
            onPause();
        }
    }

    @Override
    public void setViewData() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        list.clear();
        orderDeliveryType = Integer.parseInt(UserInfoHelper.getDeliverType(mActivity));
        getAmountNum();
        mPtr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNum = 1;
                cb_all_select.setChecked(false);
                tv_total_amount.setText("0.0");
                ids.clear();
                getMyOrderList();
            }
        });

        if(orderDeliveryType==0) {
            hylMyOrderListAdapter = new HylMyOrderListAdapter(R.layout.item_my_order_hyl, list, isCheck,17, orderDeliveryType, new HylMyOrderListAdapter.OnClick() {
                @Override
                public void evaluateNowOnclick(int position, String orderId) {
                    //立即评价
                    getComment(orderId);
                }

                @Override
                public void againBayOnclick(int position) {
                    getBuyAgain(position);
                }

                @Override
                public void cancelOnclick(String orderId) {
                    //取消订单
                    showCancelDialog(orderId);
                }

                @Override
                public void deleteOnclick(String orderId) {
                    //删除订单
                    showDeleteDialog(orderId);
                }

                @Override
                public void imageGo(String orderId, String totalAmount,int orderType,String id) {
                    payOrder(orderId,totalAmount,orderType,id);

                }

                @Override
                public void requestConfirmGetGoods(String orderId) {
                    //确认收货订单
                    showConfirmOrderDialog(orderId);
                }

                @Override
                public void onEventClick(int position,String totalAmount,List<String> idList) {
                    //是否批量
//                    ids = idList;
                    isAll(position,totalAmount);
                }
            });
        }else {
            hylMyOrderListAdapter = new HylMyOrderListAdapter(R.layout.item_my_order_hyl, list, isCheck,17, orderDeliveryType, new HylMyOrderListAdapter.OnClick() {
                @Override
                public void evaluateNowOnclick(int position, String orderId) {
                    //立即评价
                    getComment(orderId);
                }

                @Override
                public void againBayOnclick(int position) {
                    getBuyAgain(position);
                }

                @Override
                public void cancelOnclick(String orderId) {
                    //取消订单
                    showCancelDialog(orderId);
                }

                @Override
                public void deleteOnclick(String orderId) {
                    //删除订单
                    showDeleteDialog(orderId);
                }

                @Override
                public void imageGo(String orderId, String totalAmount,int orderType,String id) {
                    payOrder(orderId,totalAmount,orderType,id);
                }

                @Override
                public void requestConfirmGetGoods(String orderId) {
                    //确认收货订单
                    showConfirmOrderDialog(orderId);
                }

                @Override
                public void onEventClick(int position,String totalAmount, List<String> ids) {
                    //是否批量
                    totalAmounts = totalAmount;
                    isAll(position,totalAmount);
                }
            });
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(hylMyOrderListAdapter);
        hylMyOrderListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity,HylOrderDetailActivity.class);
                intent.putExtra("orderId",list.get(position).getOrderId());
                startActivity(intent);
            }
        });

        hylMyOrderListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum++;
                getMyOrderList();
                isAllSelected = false;
                cb_all_select.setChecked(false);
            }
        });
    }


    private void showConfirmOrderDialog(final String orderId) {
        final AlertDialog dialog = new AlertDialog.Builder(mActivity, R.style.DialogStyle).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Window window = dialog.getWindow();
        window.setContentView(R.layout.confirm_order_dialog_hyl);
        TextView tv_ok = window.findViewById(R.id.tv_ok);
        TextView tv_cancel = window.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                confirmOrder(orderId);
            }
        });
    }

    /**
     * 确认收货订单
     * @param orderId
     */
    private void  confirmOrder(String orderId){
        OrderApi.confirmOrder(mActivity, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.code==1) {
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }
                    }
                });
    }



    /**
     * 支付一般订单
     * @param totalAmount
     */
    private void payOrder(String orderId,String totalAmount,int orderType,String id) {
        HylMyPaymentDialogFragment paymentOrderFragment = new HylMyPaymentDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("orderId", orderId);
        bundle.putString("memo", "");
        bundle.putString("total",totalAmount);
        bundle.putString("giftNo","");
        bundle.putString("orderType",orderType+"");
        bundle.putString("orderDeliveryType","0");
        paymentOrderFragment.setArguments(bundle);
        paymentOrderFragment.show(getFragmentManager(),"paymentFragment");
        paymentOrderFragment.setCancelable(false);
    }

    /**
     * 支付信用订单
     * @param totalAmount
     */
    private void payDelayOrder(String totalAmount) {
        HylDelayPayDialogFragment paymentOrderFragment = new HylDelayPayDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ids", (Serializable) ids);
        bundle.putString("total",totalAmount);
        paymentOrderFragment.setArguments(bundle);
        paymentOrderFragment.show(getActivity().getFragmentManager(),"paymentFragment");
        paymentOrderFragment.setCancelable(false);
    }


    /**
     * 删除订单
     */
    private void showDeleteDialog(final String orderId) {
        final AlertDialog mDialog = new AlertDialog.Builder(mActivity).create();
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.dialog_delete_order_hyl);
        TextView mBtnCancel = (TextView) mDialog.getWindow().findViewById(R.id.btnCancel);
        TextView mBtnOK = (TextView) mDialog.getWindow().findViewById(R.id.btnOK);

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                //删除订单
                deleteOrder(orderId);
            }
        });
    }


    /**
     * 取消订单
     */
    private void showCancelDialog(final String orderId) {
        final AlertDialog mDialog = new AlertDialog.Builder(mActivity).create();
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.dailog_cancel_hyl);
        TextView mBtnCancel = (TextView) mDialog.getWindow().findViewById(R.id.btnCancel);
        TextView mBtnOK = (TextView) mDialog.getWindow().findViewById(R.id.btnOK);

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });


        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                //取消订单的接口
                cancelOrder(orderId);
            }
        });

    }

    /**
     * 删除订单的接口
     * @param orderId
     */
    private void deleteOrder(String orderId) {
        OrderApi.deleteOrder(mActivity,orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.code==1) {
                            mPtr.autoRefresh(true);
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }else {
                            ToastUtil.showSuccessMsg(mActivity, baseModel.message);
                        }
                    }
                });
    }

    /**
     * 取消订单的接口
     * @param orderId
     */
    private void cancelOrder(String orderId) {
        OrderApi.cancleOrder(mActivity,orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.code==1) {
                            mPtr.autoRefresh(true);
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }else {
                            ToastUtil.showSuccessMsg(mActivity, baseModel.message);
                        }
                    }
                });
    }


    /**
     * 再次购买
     */
    private void getBuyAgain(int position) {
        OrderApi.getAgainBuy(mActivity, hylMyOrderListModels.getData().getList().get(position).getOrderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylLoginModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylLoginModel hylLoginModel) {
                        if (hylLoginModel.code==1) {
                            if(hylLoginModel.data!=null) {
                                EventBus.getDefault().post(new CartNumHylEvent());
                                EventBus.getDefault().post(new CartListHylEvent());
                                ToastUtil.showSuccessMsg(mActivity, hylLoginModel.data);
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity, hylLoginModel.message);
                        }
                    }
                });
    }

    /**
     * 立即评价
     * @param orderId
     */
    private void getComment(String orderId) {
        Intent intent = new Intent(mActivity,JudgeActivity.class);
        intent.putExtra("orderId",orderId);
        startActivity(intent);
    }

    //是否批量
    int selectedNum;
    private Map<Integer, Boolean> isCheck = new HashMap<>();//存储选择状态
    private void isAll(int position,String totalAmount) {
        Double totalAmounts = Double.valueOf(totalAmount);
        double result = Double.parseDouble(String.format("%.2f", totalAmounts));
        tv_total_amount.setText(result+"");

        if (isCheck.get(position)) {
            //如果取消，则设置map集合中为false
            isCheck.put(position, false);
            selectedNum--;
        } else {
            selectedNum++;
            //如果选中，则设置map集合中为true
            isCheck.put(position, true);
        }
        if (selectedNum == lists.size()) {
            //如果用户一个一个单选,选到全部商品,上面的全选自动选中
            cb_all_select.setChecked(true);
            isAllSelected = true;
        } else {
            isAllSelected = false;
            cb_all_select.setChecked(false);
        }

        hylMyOrderListAdapter.notifyDataSetChanged();
    }


    @Override
    public void setClickListener() {
        tv_pay.setOnClickListener(this);
        ll_all_select.setOnClickListener(this);
        tv_pay2.setOnClickListener(this);
    }

    List<HylMyOrderListModel.DataBean.ListBean> list = new ArrayList<>();
    List<HylMyOrderListModel.DataBean.ListBean> lists = new ArrayList<>();
    HylMyOrderListModel hylMyOrderListModels;
    private void getMyOrderList() {
        OrderApi.getMyOrderList(mActivity,pageNum,pageSize,orderDeliveryType,orderState)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylMyOrderListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylMyOrderListModel hylMyOrderListModel) {
                        if (hylMyOrderListModel.getCode()==1) {
                            if(hylMyOrderListModel.getData()!=null) {
                                mPtr.refreshComplete();
                                hylMyOrderListModels = hylMyOrderListModel;

//                                list.addAll(hylMyOrderListModel.getData().getList());
//                                hylMyOrderListAdapter.notifyDataSetChanged();
                                updateOrderList();
                                if(hylMyOrderListModel.getData().getList().size()>0) {
                                    rl_keep_order.setVisibility(View.GONE);
                                }else {
                                    rl_keep_order.setVisibility(View.GONE);
                                }
                                lists.clear();
                                for (int i = 0; i < list.size(); i++) {
                                    if(!list.get(i).isOfflinePay()) {
                                        lists.add(list.get(i));
                                    }
                                }

                                isCheck.clear();
                                for (int i = 0; i < list.size(); i++) {
                                    isCheck.put(i, false);
                                }

                                if (hylMyOrderListModel.getData().isHasNextPage()) {
                                    //有下一页数据
                                    hylMyOrderListAdapter.loadMoreComplete();
                                } else {
                                    //没有下一页数据了
                                    hylMyOrderListAdapter.loadMoreEnd();
                                }

                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity, hylMyOrderListModel.getMessage());
                        }
                    }
                });
    }

    private void updateOrderList() {
        if (pageNum == 1) {
            //第一次加载
            if (hylMyOrderListModels.getData() != null && hylMyOrderListModels.getData().getList().size() > 0) {
                iv_no_data.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                list.clear();
                list.addAll(hylMyOrderListModels.getData().getList());
                hylMyOrderListAdapter.notifyDataSetChanged();
            } else {
                iv_no_data.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        } else {
            //加载更多数据
            list.addAll(hylMyOrderListModels.getData().getList());
            hylMyOrderListAdapter.notifyDataSetChanged();
        }
        if (hylMyOrderListModels.getData().isHasNextPage()) {
            //有下一页数据
            hylMyOrderListAdapter.loadMoreComplete();
        } else {
            //没有下一页数据了
            hylMyOrderListAdapter.loadMoreEnd();
        }
    }

    private boolean isAllSelected;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pay:
                isShow = !isShow;
                hylMyOrderListAdapter.setShow(isShow);
                hylMyOrderListAdapter.notifyDataSetChanged();

                if(isShow) {
                    tv_pay.setText("取消批量付款");
                    rl_foot.setVisibility(View.VISIBLE);
                }else {
                    tv_pay.setText("批量付款");
                    rl_foot.setVisibility(View.GONE);
                }
                break;

            case R.id.ll_all_select:
                isCheck.clear();
                if (isAllSelected) {
                    selectedNum = 0;
                    //正在被全选
                    hylMyOrderListAdapter.setSelectAll(false);
                    cb_all_select.setChecked(false);
                    for (int i = 0; i < list.size(); i++) {
                        isCheck.put(i, false);
                    }
                    hylMyOrderListAdapter.notifyDataSetChanged();
                    isAllSelected = false;
                } else {
                    //没有全选中
                    selectedNum = lists.size();
                    hylMyOrderListAdapter.setSelectAll(true);
                    cb_all_select.setChecked(true);
                    for (int i = 0; i < list.size(); i++) {
                        isCheck.put(i, true);
                    }
                    hylMyOrderListAdapter.notifyDataSetChanged();
                    isAllSelected = true;
                }
                break;

            case R.id.tv_pay2:
                //信用订单
                if(ids.size()!=0) {
                    payDelayOrder(totalAmounts);
                }else {
                    ToastUtil.showSuccessMsg(mActivity,"请选择订单");
                }

                break;
        }
    }

    /**
     * 提交订单
     */
    private void getAmountNum() {
        OrderApi.getAmountNum(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylAmountNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HylAmountNumModel hylAmountNumModel) {
                        if(hylAmountNumModel.code==1) {
                            if(hylAmountNumModel.getData()!=null) {
                                tv_num.setText(hylAmountNumModel.getData().getOrderNum()+"");
                                tv_price.setText(hylAmountNumModel.getData().getOrderAmt());
                            }
                        }
                    }
                });
    }

    /**
     * 货到付款支付接口
     */
    private void getDelayPay() {
        OrderApi.getDelayPay(getContext(),ids.toString(),16)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylPayInfoModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HylPayInfoModel payListModel) {
                        if(payListModel.getCode()==1) {
                            if(payListModel.getData()!=null) {

                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAmount(TotalAmountHylEvent totalAmountHylEvent) {
        totalAmounts = totalAmountHylEvent.getAllPrice();
        tv_total_amount.setText(totalAmountHylEvent.getAllPrice());
        ids = totalAmountHylEvent.getIds();
    }

    List<HylMyOrderListModel.DataBean.ListBean> data = new ArrayList<>();
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAmount(DelayOrderNumEvent delayOrderNumEvent) {
        ids.clear();
        data = delayOrderNumEvent.getData();
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).isSelected()) {
                ids.add(data.get(i).getId());
            }else {
                ids.remove(data.get(i).getId());
            }
        }
    }








    public static Date getNowDate() {
        Date currentTimes = new Date();
        SimpleDateFormat formatters = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStrings = formatters.format(currentTimes);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatters.parse(dateStrings, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTimes = new Date();
        SimpleDateFormat formatters = new SimpleDateFormat("yyyy");
        String dateStrings = formatters.format(currentTimes);
        return dateStrings;
    }
}
