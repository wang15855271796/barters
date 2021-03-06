package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylChooseSpecAdapter;
import com.barter.hyl.app.adapter.HylImageDetailAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.banner.Banner;
import com.barter.hyl.app.banner.BannerConfig;
import com.barter.hyl.app.banner.GlideImageLoader;
import com.barter.hyl.app.banner.Transformer;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.dialog.CommonDetailDialog;
import com.barter.hyl.app.dialog.FullDetailDialog;
import com.barter.hyl.app.dialog.ProductDescDialog;
import com.barter.hyl.app.event.CartNumHylEvent;
import com.barter.hyl.app.event.JumpCartHylEvent;
import com.barter.hyl.app.model.HylCartNumModel;
import com.barter.hyl.app.model.HylCollectionModel;
import com.barter.hyl.app.model.HylCommonDetailModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.DetailFlowLayout;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
 * Created by ${王涛} on 2021/8/10
 */
public class HylCommonGoodsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_sale)
    TextView tv_sale;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.fl_container)
    DetailFlowLayout fl_container;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.iv_head)
    ImageView iv_head;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.rv_image)
    RecyclerView rv_image;
    @BindView(R.id.iv_collection)
    ImageView iv_collection;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rl_desc)
    RelativeLayout rl_desc;
    @BindView(R.id.ll_comment)
    LinearLayout ll_comment;
    @BindView(R.id.tv_add_car)
    TextView tv_add_car;
    @BindView(R.id.tv_fee)
    TextView tv_fee;
    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.ll_car)
    LinearLayout ll_car;
    @BindView(R.id.rl_coupon)
    RelativeLayout rl_coupon;
    @BindView(R.id.tv_full_desc)
    TextView tv_full_desc;
    @BindView(R.id.tv_detail)
    TextView tv_detail;
    @BindView(R.id.ll_no_eval)
    LinearLayout ll_no_eval;
    @BindView(R.id.ll_eval)
    LinearLayout ll_eval;
    int mainId;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        mainId = getIntent().getIntExtra("mainId",0);
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.common_detail_activity_hyl);
    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        getDetail(mainId);
        getCartNum();
    }



    @Override
    public void setClickListener() {
        iv_collection.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        rl_desc.setOnClickListener(this);
        ll_comment.setOnClickListener(this);
        tv_add_car.setOnClickListener(this);
        ll_car.setOnClickListener(this);
        tv_detail.setOnClickListener(this);
    }


    /**
     * 收藏
     */
    private void getCollectionStatus(int mainId) {
        DetailApi.getCollection(mContext,mainId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylCollectionModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylCollectionModel hylCollectionModel) {
                        if (hylCollectionModel.code==1) {
                            if(hylCollectionModel.getData()==1) {
                                iv_collection.setImageResource(R.mipmap.ic_unlove);
                            }else {
                                iv_collection.setImageResource(R.mipmap.ic_love);
                            }

                            ToastUtil.showSuccessMsg(mActivity, hylCollectionModel.message);
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylCollectionModel.message);
                        }
                    }
                });
    }
    /**
     * 获取详情
     */
    HylCommonDetailModel.DataBean data;
    List<HylCommonDetailModel.DataBean.SpecsBean> specs = new ArrayList<>();
    List<String> detailPics = new ArrayList<>();
    private void getDetail(int mainId) {
        DetailApi.getDetail(mContext,mainId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylCommonDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylCommonDetailModel hylCommonDetailModel) {
                        if (hylCommonDetailModel.getCode()==1) {
                            data = hylCommonDetailModel.getData();
                            tv_title.setText(data.getProductName());
                            tv_price.setText(data.getMinMaxPrice());
                            tv_sale.setText(data.getSaleNum());
                            tv_desc.setText(data.getIntrduction());

                            tv_num.setText("商品评价"+data.getCommentNum());
                            if(data.getIntrduction()==null||data.getIntrduction().equals("")) {
                                rl_desc.setVisibility(View.GONE);
                            }else {
                                tv_desc.setText(data.getIntrduction());
                                rl_desc.setVisibility(View.VISIBLE);
                            }

                            if(data.getFullActiveFlag()==0) {
                                rl_coupon.setVisibility(View.GONE);
                            }else {
                                rl_coupon.setVisibility(View.VISIBLE);
                            }

                            if(data.getFullRoles()!=null&&data.getFullRoles().size()>0) {
                                String buySpec = data.getFullRoles().get(0).getBuySpec();
                                String sendProd = data.getFullRoles().get(0).getSendProd();
                                tv_full_desc.setText(buySpec+sendProd);
                            }

                            if(data.getCollectFlag()==0) {
                                iv_collection.setImageResource(R.mipmap.ic_unlove);
                            }else {
                                iv_collection.setImageResource(R.mipmap.ic_love);
                            }
                            if(data.getComment()!=null) {
                                HylCommonDetailModel.DataBean.CommentBean comment = data.getComment();
                                Glide.with(mActivity).load(comment.getHeadPic()).into(iv_head);
                                tv_username.setText(comment.getReplayName());
                                tv_date.setText(comment.getCommentTime());
                                tv_content.setText(comment.getContent());
                                ll_no_eval.setVisibility(View.GONE);
                                ll_eval.setVisibility(View.VISIBLE);
                            }else {
                                ll_eval.setVisibility(View.GONE);
                                ll_no_eval.setVisibility(View.VISIBLE);
                            }

                            //规格
                            specs.clear();
                            specs.addAll(data.getSpecs());
                            getSpec(specs);
                            List<String> topPics = data.getTopPics();

                            //底部详情图片
                            detailPics.clear();
                            detailPics.addAll(data.getDetailPics());
                            HylImageDetailAdapter hylImageDetailAdapter = new HylImageDetailAdapter(R.layout.item_imageview,detailPics);
                            rv_image.setLayoutManager(new LinearLayoutManager(mActivity));

                            rv_image.setAdapter(hylImageDetailAdapter);

                            //banner
                            getBanner(topPics);
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylCommonDetailModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 购物车数量
     */
    private void getCartNum() {
        DetailApi.getCartNum(mActivity,1)
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
                                tv_number.setVisibility(View.VISIBLE);
                                tv_fee.setVisibility(View.VISIBLE);
                                tv_number.setText(hylCartNumModel.getData().getProdNum()+"");
                                tv_total_amount.setText("￥"+ hylCartNumModel.getData().getAmount());
                                tv_fee.setText("满"+ hylCartNumModel.getData().getSendAmount()+"元免配送费");
                            }else {
                                tv_number.setVisibility(View.GONE);
                                tv_total_amount.setText("未选购商品");
                                tv_fee.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylCartNumModel.getMessage());
                        }
                    }
                });
    }

    HylChooseSpecAdapter hylChooseSpecAdapter;
    private void getSpec(List<HylCommonDetailModel.DataBean.SpecsBean> specs) {


        hylChooseSpecAdapter = new HylChooseSpecAdapter(mContext,specs, new HylChooseSpecAdapter.Onclick() {
            @Override
            public void addDialog(int position) {
                CommonDetailDialog commonDialog = new CommonDetailDialog(mActivity,data,position);
                commonDialog.show();
            }
        });
        fl_container.setAdapter(hylChooseSpecAdapter);
    }

    /**
     * banner图片
     * @param topPics
     */
    //banner集合
    List<String> bannerList = new ArrayList<>();
    private void getBanner(List<String> topPics) {
        bannerList.clear();
        for (int i = 0; i < topPics.size(); i++) {
            bannerList.add(topPics.get(i));
        }
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        bannerList.addAll(topPics);
        banner.setImages(bannerList);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        banner.stopAutoPlay();
        EventBus.getDefault().unregister(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_detail:
                FullDetailDialog fullDetailDialog = new FullDetailDialog(mActivity, data) {
                    @Override
                    public void Confirm() {
                        dismiss();
                    }
                };
                fullDetailDialog.show();
                break;

            case R.id.ll_car:
//                finish();
                Intent intents = new Intent(mContext,MainActivity.class);
                startActivity(intents);
                EventBus.getDefault().post(new JumpCartHylEvent());
                break;

            case R.id.tv_add_car:
                CommonDetailDialog commonDetailDialog = new CommonDetailDialog(mActivity,data,0);
                commonDetailDialog.show();
                break;

            case R.id.iv_collection:
                getCollectionStatus(mainId);
                break;

            case R.id.iv_back:
                finish();
                break;

            case R.id.rl_desc:
                ProductDescDialog productDescDialog = new ProductDescDialog(mActivity,data.getIntrduction());
                productDescDialog.show();
                break;

            case R.id.ll_comment:
                Intent intent = new Intent(mActivity,HylCommentActivity.class);
                intent.putExtra("mainId",mainId);
                startActivity(intent);
                break;
        }
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));//设置状态栏颜色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        }
    }

    /**
     *
     * @param
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cartNum(CartNumHylEvent cartNumHylEvent) {
        getCartNum();
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
