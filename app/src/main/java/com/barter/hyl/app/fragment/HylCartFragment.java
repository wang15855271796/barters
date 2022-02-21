package com.barter.hyl.app.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylActiveDetailActivity;
import com.barter.hyl.app.activity.HylCommonGoodsActivity;
import com.barter.hyl.app.activity.HylOrderConfirmActivity;
import com.barter.hyl.app.activity.LoginActivity;
import com.barter.hyl.app.adapter.HylCartAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.base.BaseFragment;
import com.barter.hyl.app.event.CartListHylEvent;
import com.barter.hyl.app.event.ChangeAccountHylEvent;
import com.barter.hyl.app.event.ChangeNumHylEvent;
import com.barter.hyl.app.event.JumpMarketEvent;
import com.barter.hyl.app.event.TotalAmountHylEvent;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.HylCartListModel;
import com.barter.hyl.app.model.HylSettleModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.Arith;
import com.barter.hyl.app.view.FlowLayout;
import com.barter.hyl.app.view.RoundImageView;
import com.barter.hyl.app.view.TagAdapter;
import com.barter.hyl.app.view.TagFlowLayout;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/8/4
 */
public class HylCartFragment extends BaseFragment implements View.OnClickListener, HylCartAdapter.IProductSelectCallback{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rv_unable)
    TagFlowLayout rv_unable;
    @BindView(R.id.btn_sure)
    Button btn_sure;
    @BindView(R.id.imageGoBay)
    ImageView imageGoBay;
    @BindView(R.id.tv_arrow)
    TextView tv_arrow;
    HylCartAdapter hylCartAdapter;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.tv_price_desc)
    TextView tv_price_desc;
    @BindView(R.id.cb_select_all)
    CheckBox cb_select_all;
    @BindView(R.id.tv_clear)
    TextView tv_clear;
    @BindView(R.id.ll_delete)
    LinearLayout ll_delete;
    @BindView(R.id.rl_unable)
    RelativeLayout rl_unable;
    @BindView(R.id.ll_settle)
    LinearLayout ll_settle;
    @BindView(R.id.ll_NoData)
    LinearLayout ll_NoData;
    @BindView(R.id.ll_service)
    LinearLayout ll_service;
    @BindView(R.id.tv_free_tip)
    TextView tv_free_tip;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    @BindView(R.id.ll_go_market)
    LinearLayout ll_go_market;
    @Override
    public int setLayoutId() {
        return R.layout.cart_fragment_hyl;
    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        hylCartAdapter = new HylCartAdapter(R.layout.item_cart_hyl,validList,HylCartFragment.this);
        recyclerView.setAdapter(hylCartAdapter);
        hylCartAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(validList.get(position).getBusinessType()==1) {
                    Intent intent = new Intent(mActivity,HylCommonGoodsActivity.class);
                    intent.putExtra("mainId",validList.get(position).getProductMainId());
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(mActivity,HylActiveDetailActivity.class);
                    intent.putExtra("activeId",validList.get(position).getProductMainId());
                    startActivity(intent);
                }

            }
        });

        smart.autoRefresh();

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getCartList();
                smart.finishRefresh();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setClickListener() {
        btn_sure.setOnClickListener(this);
        cb_select_all.setOnClickListener(this);
        tv_clear.setOnClickListener(this);
        ll_delete.setOnClickListener(this);
        ll_go_market.setOnClickListener(this);
        imageGoBay.setOnClickListener(this);
    }


    /**
     * 清除失效商品
     */
    List<Integer> unCartIdList = new ArrayList<>();
    private void showClearDialog() {
        //确认要删除选中的商品吗
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.DialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_delete_cart_hyl);
        TextView mTvTitle = (TextView) window.findViewById(R.id.tv_title);
        TextView mTvCancel = (TextView) window.findViewById(R.id.tv_cancel);
        TextView mTvConfirm = (TextView) window.findViewById(R.id.tv_ok);
        mTvTitle.setText("确定清空失效的商品吗?");

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        unCartIdList.clear();
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i <inValidList.size() ; i++) {
                    List<HylCartListModel.DataBean.InValidListBean.SpecProductListBeanX> specProductList = inValidList.get(i).getSpecProductList();

                    for (int j = 0; j <specProductList.size() ; j++) {
                        int cartId = specProductList.get(j).getCartId();
                        unCartIdList.add(cartId);
                    }
                }

                requestDeleteCart(unCartIdList.toString());
                alertDialog.dismiss();
            }
        });
    }

    /**
     * 删除购物车接口
     * @param cartId
     */
    private void requestDeleteCart(String cartId) {
        DetailApi.deleteCartList(mActivity,cartId)
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
                            getCartList();
                            //更新底部按钮购物车的数字显示
                            EventBus.getDefault().post(new ChangeNumHylEvent());
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, baseModel.message);
                        }
                    }
                });
    }

    /**
     * 结算
     * @param cartId
     */
    private void settle(String cartId,String giftNo) {
        DetailApi.settle(mActivity,cartId,giftNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylSettleModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(HylSettleModel hylSettleModel) {
                    if (hylSettleModel.getCode() == 1) {
                        HylSettleModel.DataBean data = hylSettleModel.getData();
                        Intent intent = new Intent(getActivity(), HylOrderConfirmActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("cartIds",cartIdList);
                        bundle.putString("totalAmount", data.getTotalAmount());
                        bundle.putSerializable("data", data);  //这里的原因
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        ToastUtil.showSuccessMsg(mActivity, hylSettleModel.getMessage());
                    }
                }
            });
    }
    /**
     * 获取角标数据
     * @param
     */
    List<HylCartListModel.DataBean.ValidListBean> validList = new ArrayList<>();
    List<HylCartListModel.DataBean.InValidListBean> inValidList = new ArrayList<>();
    RoundImageView iv_head;
    TextView tv_title;
    Double sendAmount;
    private void getCartList() {
        DetailApi.getCartList(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylCartListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylCartListModel hylCartListModel) {
                        if (hylCartListModel.getCode()==1) {
                            HylCartListModel.DataBean data = hylCartListModel.getData();

                            validList.clear();
                            validList.addAll(data.getValidList());
                            if(validList.size()>0) {
                                ll_settle.setVisibility(View.VISIBLE);
                            }else {
                                ll_settle.setVisibility(View.GONE);
                            }
                            cartIdList.clear();

                            sendAmount = Double.parseDouble(hylCartListModel.getData().getSendAmount());
                            for (int i = 0; i <validList.size() ; i++) {
                                List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean> specProductList = validList.get(i).getSpecProductList();
                                for (int j = 0; j <specProductList.size() ; j++) {
                                    if(specProductList.get(j).isSelected()) {
                                        int cartId = specProductList.get(j).getCartId();
                                        cartIdList.add(cartId+"");
                                    }
                                }
                            }

                            inValidList.clear();
                            inValidList.addAll(data.getInValidList());

                            if(validList.size()==0&&inValidList.size()==0) {
                                ll_NoData.setVisibility(View.VISIBLE);
                            }else {
                                ll_NoData.setVisibility(View.GONE);
                            }
                            if(inValidList.size()==0) {
                                rl_unable.setVisibility(View.GONE);
                            }else {
                                rl_unable.setVisibility(View.VISIBLE);
                            }
                            if(validList!=null&&validList.size()>0) {
                                getAllPrice(validList);
                            }else {
                                ll_service.setVisibility(View.GONE);
                            }

                            hylCartAdapter.setResfreshListener(new HylCartAdapter.OnResfreshListener() {
                                @Override
                                public void onResfresh(boolean isSelect) {
                                    mSelect = isSelect;
                                }
                            });

                            rv_unable.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                @Override
                                public void onGlobalLayout() {
                                    boolean isOverFlow = rv_unable.isOverFlow();
                                    boolean isLimit = rv_unable.isLimit();
                                    if (isLimit && isOverFlow) {
                                        tv_arrow.setVisibility(View.VISIBLE);
                                    } else {
                                        tv_arrow.setVisibility(View.GONE);
                                    }
                                }
                            });

                            final TagAdapter unAbleAdapter = new TagAdapter<HylCartListModel.DataBean.InValidListBean>(inValidList){

                                @Override
                                public View getView(FlowLayout parent, int position, HylCartListModel.DataBean.InValidListBean inValidListBean) {
                                    View view = LayoutInflater.from(mActivity).inflate(R.layout.item_uncart_hyl,rv_unable, false);
                                    iv_head = view.findViewById(R.id.iv_head);
                                    tv_title = view.findViewById(R.id.tv_title);
                                    tv_title.setText(inValidListBean.getProductName());
                                    Glide.with(mActivity).load(inValidListBean.getDefaultPic()).into(iv_head);
                                    return view;
                                }
                            };

                            tv_arrow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    rv_unable.setLimit(false);
                                    unAbleAdapter.notifyDataChanged();
                                }
                            });
                            rv_unable.setAdapter(unAbleAdapter);
                            hylCartAdapter.notifyDataSetChanged();

                        }else if(hylCartListModel.getCode()==-10001) {
                            Intent intent = new Intent(mActivity,LoginActivity.class);
                            startActivity(intent);
                        }else {
                            ToastUtil.showSuccessMsg(mActivity, hylCartListModel.getMessage());
                        }


                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAmount(TotalAmountHylEvent totalAmountHylEvent) {
        tv_total_price.setText(totalAmountHylEvent.getAllPrice()+"元");
        Double allPrice = Double.valueOf(totalAmountHylEvent.getAllPrice());

        if(sendAmount> allPrice) {
            double diff = sendAmount - allPrice;
            double result = Double.parseDouble(String.format("%.2f", diff));
            tv_price_desc.setText(""+result);
            ll_service.setVisibility(View.VISIBLE);
            tv_free_tip.setVisibility(View.GONE);
        }else {
            ll_service.setVisibility(View.GONE);
            tv_free_tip.setVisibility(View.VISIBLE);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartList(CartListHylEvent cartListHylEvent) {
        getCartList();
    }


    @Override
    public void onResume() {
        super.onResume();
        cb_select_all.setChecked(true);
    }

    private BigDecimal allprice;
    private void getAllPrice(List<HylCartListModel.DataBean.ValidListBean> validList) {
        allprice = new BigDecimal("0");
        if(validList!=null){
            for (int i=0;i<validList.size();i++){
                List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean> specProductList = validList.get(i).getSpecProductList();
                for (int y=0;y<specProductList.size();y++){
                    if(specProductList.get(y).isSelected()){

                        List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean.ProductDescVOListBean> productDescVOList = specProductList.get(y).getProductDescVOList();
                        for (int j = 0; j <productDescVOList.size() ; j++) {
                            BigDecimal interestRate = new BigDecimal(productDescVOList.get(j).getProductNum()); //数量
                            double interest = Arith.mul(Double.parseDouble(productDescVOList.get(j).getPrice()), interestRate);
                            allprice = allprice.add(BigDecimal.valueOf(interest));
                        }
                    }
                }
            }

            tv_total_price.setText("￥"+allprice);

            double allprices = allprice.doubleValue();
            tv_total_price.setText("￥"+allprice);

            if(sendAmount> allprices) {
                double diff = sendAmount - allprices;
                double result = Double.parseDouble(String.format("%.2f", diff));
                tv_price_desc.setText(""+result);
                ll_service.setVisibility(View.VISIBLE);
                tv_free_tip.setVisibility(View.GONE);
            }else {
                ll_service.setVisibility(View.GONE);
                tv_free_tip.setVisibility(View.VISIBLE);
            }
        }
    }

    boolean mSelect;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imageGoBay:
                EventBus.getDefault().post(new JumpMarketEvent());
                break;
            case R.id.ll_go_market:
                EventBus.getDefault().post(new JumpMarketEvent());
                break;

            case R.id.btn_sure:
                cartIdList.clear();
                if(choosedData==null) {
                    for (int i = 0; i <validList.size() ; i++) {
                        List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean> specProductList = validList.get(i).getSpecProductList();
                        for (int j = 0; j <specProductList.size() ; j++) {
                            if(specProductList.get(j).isSelected()) {
                                int cartId = specProductList.get(j).getCartId();
                                cartIdList.add(cartId+"");
                            }
                        }
                    }
                    if(cartIdList.size()==0) {
                        ToastUtil.showSuccessMsg(getActivity(),"请选择要删除的商品");
                    }else {
                        deleteValidList();
                    }

                }else {
                    for (int i = 0; i < choosedData.size(); i++) {
                        List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean> specProductList = choosedData.get(i).getSpecProductList();
                        for (int j = 0; j < specProductList.size(); j++) {
                            if (specProductList.get(j).isSelected()) {
                                int cartId = specProductList.get(j).getCartId();
                                cartIdList.add(cartId+"");
                            }
                        }
                    }
                }

                if(cartIdList.size()>0) {
                    settle(cartIdList.toString(),"");
                }else {
                    ToastUtil.showSuccessMsg(mActivity,"请选择要购买的商品");
                }


                break;

            case R.id.cb_select_all:
                if (mSelect) {
                    cb_select_all.setChecked(false);
                    hylCartAdapter.setAllselect(false);
                } else {
                    cb_select_all.setChecked(true);
                    hylCartAdapter.setAllselect(true);
                }
                break;

            case R.id.tv_clear:
                showClearDialog();
                break;

            case R.id.ll_delete:
                cartIdList.clear();
                if(choosedData==null) {
                    for (int i = 0; i <validList.size() ; i++) {
                        List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean> specProductList = validList.get(i).getSpecProductList();
                        for (int j = 0; j <specProductList.size() ; j++) {
                            if(specProductList.get(j).isSelected()) {
                                int cartId = specProductList.get(j).getCartId();
                                cartIdList.add(cartId+"");
                            }
                        }
                    }
                    if(cartIdList.size()==0) {
                        ToastUtil.showSuccessMsg(getActivity(),"请选择要删除的商品");
                    }else {
                        deleteValidList();
                    }

                }else {
                    for (int i = 0; i <choosedData.size() ; i++) {
                        List<HylCartListModel.DataBean.ValidListBean.SpecProductListBean> specProductList = choosedData.get(i).getSpecProductList();
                        for (int j = 0; j <specProductList.size() ; j++) {
                            if(specProductList.get(j).isSelected()) {
                                int cartId = specProductList.get(j).getCartId();
                                cartIdList.add(cartId+"");
                            }
                        }
                    }
                    if(cartIdList.size()==0) {
                        ToastUtil.showSuccessMsg(getActivity(),"请选择要删除的商品");
                    }else {
                        deleteValidList();
                    }
                }



                break;
        }
    }

    /**
     * 清除选中的商品
     */
   ArrayList<String> cartIdList = new ArrayList<>();
    private void deleteValidList() {
        //确认要删除选中的商品吗
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.DialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_delete_cart_hyl);
        TextView mTvTitle = (TextView) window.findViewById(R.id.tv_title);
        TextView mTvCancel = (TextView) window.findViewById(R.id.tv_cancel);
        TextView mTvConfirm = (TextView) window.findViewById(R.id.tv_ok);
        mTvTitle.setText("确定清空选中的商品吗?");

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDeleteCart(cartIdList.toString());
                alertDialog.dismiss();
            }
        });
    }

    List<HylCartListModel.DataBean.ValidListBean> choosedData;
    boolean isSelect;
    @Override
    public void update(List<HylCartListModel.DataBean.ValidListBean> data) {
        this.choosedData = data;
        btn_sure.setText("结算");
        for (int i = 0; i < data.size(); i++) {
            if(!data.get(i).isSelected()){
                isSelect = false;
                break;
            }else{
                isSelect = true;
            }
        }


        cb_select_all.setChecked(isSelect);
    }

    /**
     * 账号切换时信息更新
     * @param changeAccountHylEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartList(ChangeAccountHylEvent changeAccountHylEvent) {
        getCartList();
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
