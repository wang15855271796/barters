package com.barter.hyl.app.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylChooseDialogSpecAdapter;
import com.barter.hyl.app.adapter.HylChoosePriceAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.event.CartNumHylEvent;
import com.barter.hyl.app.event.JumpCartHylEvent;
import com.barter.hyl.app.model.HylAddCartModel;
import com.barter.hyl.app.model.HylCartNumModel;
import com.barter.hyl.app.model.HylChangeSpecModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.DetailFlowLayout;
import com.barter.hyl.app.view.RoundImageView;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/8/23
 */
public class HylSearchDialogFragment extends DialogFragment {

    HylChooseDialogSpecAdapter chooseSpecAdapter;
    TextView tv_name;
    TextView tv_sale;
    TextView tv_price;
    DetailFlowLayout fl_layout;
    RoundImageView iv_head;
    RecyclerView recyclerView;
    HylChoosePriceAdapter hylChoosePriceAdapter;
    ImageView iv_close;
    TextView tv_num;
    TextView tv_price_total;
    TextView tv_free_desc;
    ImageView iv_cart;
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int mainId = bundle.getInt("mainId");
        EventBus.getDefault().register(this);
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.collection_dialog_hyl);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 3 / 4;
        window.setAttributes(lp);
        iv_cart = dialog.findViewById(R.id.iv_cart);
        tv_free_desc = dialog.findViewById(R.id.tv_free_desc);
        tv_price_total = dialog.findViewById(R.id.tv_price_total);
        iv_close = dialog.findViewById(R.id.iv_close);
        iv_head = dialog.findViewById(R.id.iv_head);
        tv_name = dialog.findViewById(R.id.tv_name);
        tv_sale = dialog.findViewById(R.id.tv_sale);
        tv_price = dialog.findViewById(R.id.tv_price);
        fl_layout = dialog.findViewById(R.id.fl_layout);
        recyclerView = dialog.findViewById(R.id.recyclerView);
        tv_num = dialog.findViewById(R.id.tv_num);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        getData(mainId);
        getCartNum();

        chooseSpecAdapter = new HylChooseDialogSpecAdapter(getActivity(),specs, new HylChooseDialogSpecAdapter.Onclick() {
            @Override
            public void addDialog(int position) {
                productId = specs.get(position).getProductId();
                changeSpec(productId);
                chooseSpecAdapter.selectPosition(position);
            }
        });

        fl_layout.setAdapter(chooseSpecAdapter);

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                EventBus.getDefault().post(new JumpCartHylEvent());
            }
        });
        return dialog;
    }


    /**
     * 切换规格
     * @param productId
     */
    private void changeSpec(final int productId) {
        DetailApi.changeSpec(getActivity(),productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylChangeSpecModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylChangeSpecModel hylChangeSpecModel) {
                        if (hylChangeSpecModel.getCode()==1) {
                            prices.clear();
                            prices.addAll(hylChangeSpecModel.getData().getPrices());

                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            hylChoosePriceAdapter = new HylChoosePriceAdapter(R.layout.item_choose_content,prices,productId);
                            recyclerView.setAdapter(hylChoosePriceAdapter);

                            Glide.with(getActivity()).load(hylChangeSpecModel.getData().getDefaultPic()).into(iv_head);
                            tv_sale.setText(hylChangeSpecModel.getData().getSaleNum());
                            tv_price.setText(hylChangeSpecModel.getData().getMinMaxPrice());
                        } else {
                            ToastUtil.showSuccessMsg(getActivity(), hylChangeSpecModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 获取弹窗数据
     */
    List<HylAddCartModel.DataBean.SpecsBean> specs = new ArrayList<>();
    List<HylChangeSpecModel.DataBean.PricesBean> prices = new ArrayList<>();
    int productId;
    private void getData(int mainId) {
        DetailApi.getCart(getActivity(),mainId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylAddCartModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylAddCartModel hylAddCartModel) {
                        if (hylAddCartModel.getCode()==1) {
                            HylAddCartModel.DataBean data = hylAddCartModel.getData();
                            HylAddCartModel.DataBean.SpecDetailBean specDetail = data.getSpecDetail();
                            productId = data.getSpecs().get(0).getProductId();

                            Glide.with(getActivity()).load(specDetail.getDefaultPic()).into(iv_head);
                            tv_name.setText(specDetail.getProductName());
                            tv_sale.setText(specDetail.getSaleNum());
                            tv_price.setText(specDetail.getMinMaxPrice());

                            specs.clear();
                            specs.addAll(data.getSpecs());
                            chooseSpecAdapter.notifyDataSetChanged();

                            changeSpec(productId);
                        } else {
                            ToastUtil.showSuccessMsg(getActivity(), hylAddCartModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 购物车数量
     * @param cartNumHylEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartNum(CartNumHylEvent cartNumHylEvent) {
        getCartNum();
    }



    /**
     * 获取角标数据
     * @param
     */
    private void getCartNum() {
        DetailApi.getCartNum(getActivity(),1)
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
                            if(hylCartNumModel.getData().getProdNum()>0) {
                                tv_num.setVisibility(View.VISIBLE);
                                tv_num.setText(hylCartNumModel.getData().getProdNum()+"");
                                tv_price_total.setText(hylCartNumModel.getData().getAmount());
                                tv_free_desc.setText("满"+ hylCartNumModel.getData().getSendAmount()+"元免配送费");
                            }else {
                                tv_num.setVisibility(View.GONE);
                                tv_free_desc.setText("未选购商品");
                                tv_price_total.setText(hylCartNumModel.getData().getAmount());
                            }
                        } else {
                            ToastUtil.showSuccessMsg(getActivity(), hylCartNumModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
