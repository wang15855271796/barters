package com.barter.hyl.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylActiveDetailAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.banner.Banner;
import com.barter.hyl.app.banner.BannerConfig;
import com.barter.hyl.app.banner.GlideImageLoader;
import com.barter.hyl.app.banner.Transformer;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.StringHelper;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.dialog.AuthDialog;
import com.barter.hyl.app.event.CartListHylEvent;
import com.barter.hyl.app.event.JumpCartHylEvent;
import com.barter.hyl.app.model.HylActiveDetailModel;
import com.barter.hyl.app.model.HylAddToCartModel;
import com.barter.hyl.app.model.HylCartNumModel;
import com.barter.hyl.app.utils.SharedPreferencesUtil;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.Snap;

import org.greenrobot.eventbus.EventBus;

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
 * Created by ${王涛} on 2021/8/9
 */
public class HylActiveDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_old_price)
    TextView tv_old_price;
    @BindView(R.id.tv_limit_num)
    TextView tv_limit_num;
    @BindView(R.id.tv_place)
    TextView tv_place;
    @BindView(R.id.tv_spec)
    TextView tv_spec;
    @BindView(R.id.tv_price1)
    TextView tv_price1;
    @BindView(R.id.tv_old_price1)
    TextView tv_old_price1;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.rv_iv)
    RecyclerView rv_iv;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;
    @BindView(R.id.iv_add)
    ImageView iv_add;
    @BindView(R.id.iv_sub)
    ImageView iv_sub;
    @BindView(R.id.tv_fee)
    TextView tv_fee;
    @BindView(R.id.tv_add_car)
    TextView tv_add_car;
    @BindView(R.id.rl_bg_status)
    RelativeLayout rl_bg_status;
    @BindView(R.id.tv_time_desc)
    TextView tv_time_desc;
    @BindView(R.id.ll_car)
    LinearLayout ll_car;
    @BindView(R.id.tv_surplus)
    TextView tv_surplus;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;
    @BindView(R.id.snap)
    Snap snap;
    String activeId;

    HylActiveDetailAdapter hylActiveDetailAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        activeId = getIntent().getStringExtra("activeId");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.active_detail_activity_hyl);
    }

    @Override
    public void setViewData() {
        getDetail(activeId);
        getCartNum();
        hylActiveDetailAdapter = new HylActiveDetailAdapter(R.layout.item_imageview,detailPics);
        rv_iv.setLayoutManager(new LinearLayoutManager(mActivity));
        rv_iv.setAdapter(hylActiveDetailAdapter);

    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));//设置状态栏颜色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        }
    }
    /**
     * 获取详情
     */
    List<String> detailPics = new ArrayList<>();
    HylActiveDetailModel.DataBean data;
    private void getDetail(String activeId) {
        DetailApi.getActiveDetail(mContext,activeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylActiveDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylActiveDetailModel hylActiveDetailModel) {
                        if (hylActiveDetailModel.getCode()==1) {
                            data = hylActiveDetailModel.getData();

                            tv_surplus.setText(data.getInvent());
                            tv_num.setText(data.getCartNum()+"");
                            tv_title.setText(data.getName());
                            tv_price.setText("￥"+data.getPrice());

                            if(data.getOldPrice()!=null&&data.getOldPrice()!="") {
                                tv_old_price.setText("￥"+data.getOldPrice());
                                tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                tv_old_price.getPaint().setAntiAlias(true);//抗锯齿

                                tv_old_price1.setText(data.getOldPrice());
                                tv_old_price1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                tv_old_price1.getPaint().setAntiAlias(true);//抗锯齿
                            }

                            tv_limit_num.setText(data.getLimitNum());
                            tv_place.setText(data.getPlace());
                            tv_spec.setText(data.getSpec());
                            tv_price1.setText("￥"+data.getPrice());

                            tv_desc.setText(data.getIntrduction());
                            int remainNum = data.getRemainNum();
                            int totalNum = data.getTotalNum();
                            float present = (float) remainNum / totalNum * 100;
                            pb.setProgress((int) present);
                            List<String> topPics = data.getTopPics();

                            getButtonState(data);

                            detailPics.clear();
                            detailPics.addAll(data.getDetailPics());
                            hylActiveDetailAdapter.notifyDataSetChanged();


                            getBanner(topPics);
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylActiveDetailModel.getMessage());
                        }
                    }
                });
    }

    //按钮状态
    private void getButtonState(HylActiveDetailModel.DataBean data) {
        if (data.getActiveType() == 2) {
            //秒杀
            if (data.getInventFlag() == 1) {
                tv_time.setText("已售空");
                tv_add_car.setEnabled(false);
                snap.setVisibility(View.GONE);
                tv_time.setVisibility(View.VISIBLE);
                tv_time_desc.setVisibility(View.GONE);
                tv_add_car.setText("已售空");
                rl_bg_status.setBackgroundColor(Color.parseColor("#c2c2c2"));
            } else {
                if (data.getFlag() == 1) {
                    //时间转化
                    snap.setTime(true, true, data.getNowTime(), data.getStartTime(), data.getEndTime());
                    snap.changeBackGround(Color.parseColor("#F54022"));
                    snap.start();
                    tv_add_car.setEnabled(true);
                    tv_time.setVisibility(View.GONE);
                    snap.setVisibility(View.VISIBLE);
                    tv_time_desc.setVisibility(View.GONE);
                    tv_add_car.setText("加入购物车");
                    rl_bg_status.setBackgroundColor(Color.parseColor("#F54022"));
                } else {

                    tv_time.setText("待开始");
                    tv_time.setVisibility(View.VISIBLE);
                    snap.setVisibility(View.GONE);
                    String date = chageDate();
                    tv_add_car.setText(date);
                    tv_add_car.setEnabled(false);
                    tv_time_desc.setVisibility(View.VISIBLE);
                    rl_bg_status.setBackgroundColor(Color.parseColor("#F54022"));
                }
                tv_add_car.setEnabled(true);
            }
        } else {
            //组合
            if (data.getInventFlag() == 1) {
                tv_time.setText("已售空");
                tv_add_car.setEnabled(false);
                snap.setVisibility(View.GONE);
                tv_time.setVisibility(View.VISIBLE);
                tv_time_desc.setVisibility(View.GONE);
                tv_add_car.setText("已售空");
                rl_bg_status.setBackgroundColor(Color.parseColor("#c2c2c2"));
            } else {
                if (data.getFlag() == 1) {
                    snap.setVisibility(View.GONE);
                    tv_add_car.setEnabled(true);
                    tv_time.setVisibility(View.VISIBLE);
                    tv_time.setText("进行中");
                    tv_time_desc.setVisibility(View.GONE);
                    tv_add_car.setText("加入购物车");
                    rl_bg_status.setBackgroundColor(Color.parseColor("#F54022"));
                } else {
                    tv_time.setText("待开始");
                    tv_time.setVisibility(View.VISIBLE);
                    snap.setVisibility(View.GONE);
                    tv_add_car.setText(data.getActiveStartTime());
                    tv_add_car.setEnabled(false);
                    tv_time_desc.setVisibility(View.VISIBLE);
                    rl_bg_status.setBackgroundColor(Color.parseColor("#F54022"));
                }
                tv_add_car.setEnabled(true);
            }

        }

    }
    //日期转化
    private String chageDate() {
        long startTime = data.getStartTime();
        return new SimpleDateFormat("MM月dd日 HH:mm").format(new Date(startTime));
    }

    /**
     * banner图片
     * @param topPics
     */
    //banner集合
    List<String> bannerList = new ArrayList<>();
    private void getBanner(List<String> topPics) {
        bannerList.clear();
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
    }


    @Override
    public void setClickListener() {
        iv_add.setOnClickListener(this);
        iv_sub.setOnClickListener(this);
        tv_add_car.setOnClickListener(this);
        ll_car.setOnClickListener(this);
        rl_back.setOnClickListener(this);
    }

    /**
     * 获取角标数据
     * @param
     */
    private void getCartNum() {
        DetailApi.getCartNum(mContext,1)
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

                            tv_fee.setText("满"+ hylCartNumModel.getData().getSendAmount()+"元免配送费");
                            tv_total_amount.setText("￥"+ hylCartNumModel.getData().getAmount());
                            if(hylCartNumModel.getData().getProdNum()>0) {
                                tv_number.setVisibility(View.VISIBLE);
                                tv_number.setText(hylCartNumModel.getData().getProdNum()+"");
                            }else {
                                tv_number.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylCartNumModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.ll_car:
                //跳转到购物车界面
                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    Intent intents = new Intent(mContext,MainActivity.class);
                    startActivity(intents);
                    EventBus.getDefault().post(new JumpCartHylEvent());
                }else {
                    Intent intent = new Intent(mContext,LoginActivity.class);
                    startActivity(intent);
                }

                break;

            case R.id.iv_add:
                int num1 = Integer.parseInt(tv_num.getText().toString());
                num1++;
                tv_num.setText(num1+"");
                getCartNum();
                break;

            case R.id.iv_sub:
                int num2 = Integer.parseInt(tv_num.getText().toString());
                if(num2==0) return;
                num2--;
                tv_num.setText(num2+"");
                getCartNum();
                break;

            case R.id.tv_add_car:
                String authorFlag = SharedPreferencesUtil.getString(mContext, "authorFlag");
                if(authorFlag.equals("1")) {
                    if(tv_num.getText().toString().equals("0")) {
                        ToastUtil.showSuccessMsg(mContext,"请添加商品数量");
                    }else{
                        addCartNum(data.getActiveType(),-100,data.getActiveId(),Integer.parseInt(tv_num.getText().toString()));
                    }
                }else {
                    AuthDialog authDialog = new AuthDialog(mContext);
                    authDialog.show();
                }


                break;
        }
    }

    //加入购物车
    private void addCartNum(int businessType, int priceId, int businessId, final int num) {
        DetailApi.addCart(mContext,businessType,priceId,businessId,num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylAddToCartModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylAddToCartModel hylAddToCartModel) {
                        if (hylAddToCartModel.getCode()==1) {
                            if(hylAddToCartModel.getData().getAddFlag()==0) {
                                EventBus.getDefault().post(new CartListHylEvent());
                                getCartNum();
                                ToastUtil.showSuccessMsg(mContext, hylAddToCartModel.getMessage());
                            }else {
                                EventBus.getDefault().post(new CartListHylEvent());
                                ToastUtil.showSuccessMsg(mContext, hylAddToCartModel.getData().getMessage());
                                tv_num.setText(hylAddToCartModel.getData().getNum()+"");
                                getCartNum();
                                ToastUtil.showSuccessMsg(mContext, hylAddToCartModel.getMessage());
                            }

                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylAddToCartModel.getMessage());
                        }
                    }
                });
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
