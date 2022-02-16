package com.barter.hyl.app.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylChooseCouponsAdapter;
import com.barter.hyl.app.adapter.HylViewPagerAdapters;
import com.barter.hyl.app.api.CategoriApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.event.ChooseCouponHylEvent;
import com.barter.hyl.app.fragment.HylChooseCouponFragment;
import com.barter.hyl.app.fragment.HylCouponsUnUseFragment;
import com.barter.hyl.app.model.HylChooseCouponModel;
import com.barter.hyl.app.utils.ToastUtil;

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
 * Created by ${王涛} on 2021/8/26
 */
public class HylCouponActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    List<Integer> cartIds;
    private List<Fragment> list_fragment=new ArrayList<>();
    private List<String> stringList =new ArrayList<>();
    private String giftDetailNo="";
    private HylChooseCouponsAdapter adapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        cartIds = (List<Integer>) getIntent().getSerializableExtra("cartIds");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.coupon_activity_hyl);
    }

    @Override
    public void setViewData() {
        tv_title.setText("优惠券");
        stringList.add("可使用");
        stringList.add("不可使用");
        //可使用
        list_fragment.add(HylChooseCouponFragment.newInstance(cartIds));
        //不可使用
        list_fragment.add(HylCouponsUnUseFragment.newInstance(cartIds));

        HylViewPagerAdapters viewPagerAdapter = new HylViewPagerAdapters(getSupportFragmentManager(),list_fragment,stringList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        userChooseDeduct(0);
        setRecyclerView();
    }

    private void setRecyclerView() {

        adapter = new HylChooseCouponsAdapter(R.layout.item_choose_copons, list, new HylChooseCouponsAdapter.ImageOnclick() {
            @Override
            public void Onclick(int position, String giftDetailNo) {
                HylChooseCouponModel.DataBean info = list.get(position);
                statModel = false;
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        list.get(i).setFlags(!list.get(i).isFlags());
                        if (list.get(i).isFlags()) {
                            EventBus.getDefault().post(new ChooseCouponHylEvent(info.getGiftDetailNo()));

                            finish();
                        } else {
                            finish();
                        }
                    } else {
                        list.get(i).setFlags(false);
                    }
                }

                adapter.notifyDataSetChanged();

            }
        });
//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerView.setAdapter(adapter);

    }

    HylChooseCouponModel models;
    boolean statModel;
    private List<HylChooseCouponModel.DataBean> list = new ArrayList<>();
    private void userChooseDeduct(int flag) {
        CategoriApi.couponList(mContext, flag,cartIds.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylChooseCouponModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylChooseCouponModel hylChooseCouponModel) {
                        if (hylChooseCouponModel.getCode()==1) {
                            models = hylChooseCouponModel;

                            if (hylChooseCouponModel.getData().size()> 0) {

                                list.addAll(hylChooseCouponModel.getData());
                                adapter.notifyDataSetChanged();
                                for (int i = 0; i < list.size(); i++) {
                                    if (hylChooseCouponModel.getData().get(i).getGiftDetailNo().equals(giftDetailNo)) {
                                        hylChooseCouponModel.getData().get(i).setFlags(true);
                                    } else {
                                        hylChooseCouponModel.getData().get(i).setFlags(false);
                                    }
                                }
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylChooseCouponModel.getMessage());
                        }

                    }
                });
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;


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
