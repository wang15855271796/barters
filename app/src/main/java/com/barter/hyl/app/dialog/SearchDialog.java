package com.barter.hyl.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylChooseDialogSpecAdapter;
import com.barter.hyl.app.adapter.HylChoosePriceAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.event.CartListHylEvent;
import com.barter.hyl.app.event.JumpCartHylEvent;
import com.barter.hyl.app.model.HylAddCartModel;
import com.barter.hyl.app.model.HylCartNumModel;
import com.barter.hyl.app.model.HylChangeSpecModel;
import com.barter.hyl.app.model.HylSearchResultModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.utils.Utils;
import com.barter.hyl.app.view.DetailFlowLayout;
import com.barter.hyl.app.view.RoundImageView;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/9/16
 */
public class SearchDialog extends Dialog implements View.OnClickListener {
    Activity context;
    public View view;
    public Unbinder binder;

    HylChooseDialogSpecAdapter chooseSpecAdapter;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_sale)
    TextView tv_sale;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.tv_stock)
    TextView tv_stock;
    @BindView(R.id.fl_layout)
    DetailFlowLayout fl_layout;
    @BindView(R.id.iv_head)
    RoundImageView iv_head;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_price_total)
    TextView tv_price_total;
    @BindView(R.id.tv_free_desc)
    TextView tv_free_desc;
    @BindView(R.id.iv_cart)
    ImageView iv_cart;

    HylSearchResultModel.DataBean.ListBean listBean;
    public SearchDialog(Activity mActivity, HylSearchResultModel.DataBean.ListBean listBean) {
        super(mActivity, R.style.dialog);
        this.context = mActivity;
        this.listBean = listBean;
        init();

    }



    @Override
    public void show() {
        super.show();
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void cancel() {
        super.cancel();
        EventBus.getDefault().unregister(this);
    }

    //初始化布局
    private void init() {
        if(view == null) {
            view = View.inflate(context, R.layout.collection_dialog_hyl, null);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            binder = ButterKnife.bind(this, view);
            setContentView(view);

            getWindow().setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.width = Utils.getScreenWidth(context);
            getWindow().setAttributes(attributes);
        }
        iv_close.setOnClickListener(this);
        iv_cart.setOnClickListener(this);
        chooseSpecAdapter = new HylChooseDialogSpecAdapter(context,specs, new HylChooseDialogSpecAdapter.Onclick() {
            @Override
            public void addDialog(int position) {
                productId = specs.get(position).getProductId();
                changeSpec(productId);
                chooseSpecAdapter.selectPosition(position);
            }
        });

        fl_layout.setAdapter(chooseSpecAdapter);
        getCartNum();
        getData(listBean.getMainId());
    }

    /**
     * 获取弹窗数据
     */
    List<HylAddCartModel.DataBean.SpecsBean> specs = new ArrayList<>();
    List<HylChangeSpecModel.DataBean.PricesBean> prices = new ArrayList<>();
    int productId;
    private void getData(String mainId) {
        DetailApi.getCart(context,mainId)
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

                            Glide.with(context).load(specDetail.getDefaultPic()).into(iv_head);
                            tv_name.setText(specDetail.getProductName());
                            tv_sale.setText(specDetail.getSaleNum());
                            tv_price.setText(specDetail.getMinMaxPrice());
                            tv_stock.setText(specDetail.getInvent());
                            specs.clear();
                            specs.addAll(data.getSpecs());
                            chooseSpecAdapter.notifyDataSetChanged();

                            changeSpec(hylAddCartModel.getData().getSpecDetail().getProductId());
                        } else {
                            ToastUtil.showSuccessMsg(context, hylAddCartModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 切换规格
     * @param productId
     */
    HylChoosePriceAdapter hylChoosePriceAdapter;
    private void changeSpec(final int productId) {
        DetailApi.changeSpec(context,productId)
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
                            HylChangeSpecModel.DataBean data = hylChangeSpecModel.getData();
                            prices.clear();
                            prices.addAll(hylChangeSpecModel.getData().getPrices());

                            tv_name.setText(data.getProductName());
                            tv_sale.setText(data.getSaleNum());
                            tv_price.setText(data.getMinMaxPrice());
                            tv_stock.setText(data.getInvent());
                            Glide.with(context).load(data.getDefaultPic()).into(iv_head);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            hylChoosePriceAdapter = new HylChoosePriceAdapter(R.layout.item_choose_content,prices,productId);
                            recyclerView.setAdapter(hylChoosePriceAdapter);
                        } else {
                            ToastUtil.showSuccessMsg(context, hylChangeSpecModel.getMessage());
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                EventBus.getDefault().unregister(this);
                break;

            case R.id.iv_cart:
                EventBus.getDefault().post(new JumpCartHylEvent());
                dismiss();
                context.finish();
                break;
        }
    }
    /**
     * 获取金额和数量
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartNum(CartListHylEvent cartListHylEvent) {
        HylCartNumModel.DataBean data = cartListHylEvent.getCartNumModel().getData();

        if(data.getProdNum()>0) {
            tv_price_total.setText(data.getAmount());
            tv_num.setText(data.getProdNum()+"");
            tv_num.setVisibility(View.VISIBLE);
            tv_free_desc.setText("满"+data.getSendAmount()+"元免配送费");
        }else {
            tv_free_desc.setText("未选购商品");
            tv_num.setVisibility(View.GONE);
            tv_price_total.setText(data.getAmount());
        }
    }

    private void getCartNum() {
        DetailApi.getCartNum(context,1)
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
                            HylCartNumModel.DataBean data = hylCartNumModel.getData();
                            if(data.getProdNum()>0) {
                                tv_price_total.setText(data.getAmount());
                                tv_num.setText(data.getProdNum()+"");
                                tv_num.setVisibility(View.VISIBLE);
                                tv_free_desc.setText("满"+data.getSendAmount()+"元免配送费");
                            }else {
                                tv_free_desc.setText("未选购商品");
                                tv_num.setVisibility(View.GONE);
                                tv_price_total.setText(data.getAmount());
                            }
                        } else {
                            ToastUtil.showSuccessMsg(context, hylCartNumModel.getMessage());
                        }
                    }
                });
    }
}
