package com.barter.hyl.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylMyOrdersViewPagerAdapter;
import com.barter.hyl.app.adapter.HylViewPagerAdapter;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.base.BaseFragment;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.fragment.HylAllOrderFragment;
import com.barter.hyl.app.fragment.HylCheckOrderFragment;
import com.barter.hyl.app.fragment.HylDeliveryOrderFragment;
import com.barter.hyl.app.fragment.EvaluatedOrderFragment;
import com.barter.hyl.app.fragment.KeepOrderFragment;
import com.barter.hyl.app.fragment.OverDueOrderFragment;
import com.barter.hyl.app.fragment.HylPaymentOrderFragment;
import com.barter.hyl.app.fragment.HylReceivedOrderFragment;
import com.barter.hyl.app.fragment.HylReturnOrderFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ${王涛} on 2021/8/19
 */
public class HylMyOrderActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.x_layout)
    XTabLayout xTabLayout;
    @BindView(R.id.x_layout_credit)
    XTabLayout xTabLayoutCredit;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_common_order)
    TextView tv_common_order;
    @BindView(R.id.tv_credit_order)
    TextView tv_credit_order;
    @BindView(R.id.tv_line_one)
    TextView tv_line_one;
    @BindView(R.id.tv_line_two)
    TextView tv_line_two;
    @BindView(R.id.viewPager1)
    ViewPager viewPager1;
    HylViewPagerAdapter adapter;
    List<String> mListTitles = new ArrayList();
    HylMyOrdersViewPagerAdapter hylMyOrdersViewPagerAdapter;
    int orderDeliveryType;
    private List<BaseFragment> mListFragment = new ArrayList<>();

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        orderDeliveryType = getIntent().getIntExtra("orderDeliveryType", 0);
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_my_order_hyl);
    }

    @Override
    public void setViewData() {
        tv_title.setText("我的订单");
        UserInfoHelper.saveDeliverType(mActivity, orderDeliveryType + "");
        if(orderDeliveryType==0) {
            initView();
        }else {
            initCredit();
        }
    }

    //信用订单
    private void initCredit() {
//        xTabLayout.removeAllTabs();
//        mListTitles.clear();
//        mListFragment.clear();
//        viewPager.removeAllViews();
//        mListTitles.addAll(Arrays.asList("全部", "待付款", "待提货", "待评价", "退货"));
//        mListFragment.add(new HylAllOrderFragment());
//        mListFragment.add(new HylPaymentOrderFragment());
//        mListFragment.add(new HylDeliveryOrderFragment());
//        mListFragment.add(new EvaluatedOrderFragment());
//        mListFragment.add(new HylReturnOrderFragment());
//
//        hylMyOrdersViewPagerAdapter = new HylMyOrdersViewPagerAdapter(getSupportFragmentManager(), mListTitles, mListFragment);
//        viewPager.setOffscreenPageLimit(5);
//        hylMyOrdersViewPagerAdapter.clear(viewPager);
//
//        viewPager.setAdapter(hylMyOrdersViewPagerAdapter);
//        xTabLayout.setupWithViewPager(viewPager);

        tv_credit_order.setTextColor(Color.parseColor("#FF000000"));
        tv_line_two.setVisibility(View.VISIBLE);
        tv_line_two.setBackgroundColor(Color.parseColor("#FFFF680A"));
        tv_line_one.setVisibility(View.GONE);
        tv_credit_order.setTextColor(Color.parseColor("#FFA1A1A1"));
        viewPager.setVisibility(View.GONE);
        viewPager1.setVisibility(View.VISIBLE);
        xTabLayoutCredit.removeAllTabs();
        xTabLayout.setVisibility(View.GONE);
        xTabLayoutCredit.setVisibility(View.VISIBLE);
        mListTitles.clear();
        mListFragment.clear();
        viewPager.removeAllViews();

        mListTitles.addAll(Arrays.asList("全部", "待履约","待审核","已逾期", "待发货", "待收货", "待评价", "售后"));

        mListFragment.add(new HylAllOrderFragment());
        mListFragment.add(new KeepOrderFragment());
        mListFragment.add(new HylCheckOrderFragment());
        mListFragment.add(new OverDueOrderFragment());
        mListFragment.add(new HylDeliveryOrderFragment());
        mListFragment.add(new HylReceivedOrderFragment());
        mListFragment.add(new EvaluatedOrderFragment());
        mListFragment.add(new HylReturnOrderFragment());
        hylMyOrdersViewPagerAdapter = new HylMyOrdersViewPagerAdapter(getSupportFragmentManager(), mListTitles, mListFragment);
        viewPager1.setOffscreenPageLimit(6);
        hylMyOrdersViewPagerAdapter.clear(viewPager);
        viewPager1.setAdapter(hylMyOrdersViewPagerAdapter);
        xTabLayoutCredit.setupWithViewPager(viewPager1);
    }



    //一般订单
    private void initView() {
        tv_common_order.setTextColor(Color.parseColor("#FF000000"));
        tv_line_one.setVisibility(View.VISIBLE);
        tv_line_one.setBackgroundColor(Color.parseColor("#FFFF680A"));
        tv_line_two.setVisibility(View.GONE);
        tv_credit_order.setTextColor(Color.parseColor("#FFA1A1A1"));
        viewPager.setVisibility(View.VISIBLE);
        viewPager1.setVisibility(View.GONE);
        xTabLayout.removeAllTabs();
        xTabLayout.setVisibility(View.VISIBLE);
        xTabLayoutCredit.setVisibility(View.GONE);
        mListTitles.clear();
        mListFragment.clear();
        viewPager.removeAllViews();

        mListTitles.addAll(Arrays.asList("全部", "待付款", "待发货", "待收货", "待评价", "售后"));

        mListFragment.add(new HylAllOrderFragment());
        mListFragment.add(new HylPaymentOrderFragment());
        mListFragment.add(new HylDeliveryOrderFragment());
        mListFragment.add(new HylReceivedOrderFragment());
        mListFragment.add(new EvaluatedOrderFragment());
        mListFragment.add(new HylReturnOrderFragment());
        hylMyOrdersViewPagerAdapter = new HylMyOrdersViewPagerAdapter(getSupportFragmentManager(), mListTitles, mListFragment);

        viewPager.setOffscreenPageLimit(6);
        hylMyOrdersViewPagerAdapter.clear(viewPager);
        viewPager.setAdapter(hylMyOrdersViewPagerAdapter);
        xTabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
        tv_common_order.setOnClickListener(this);
        tv_credit_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_common_order:
                tv_common_order.setTextColor(Color.parseColor("#FF000000"));
                tv_line_one.setVisibility(View.VISIBLE);
                tv_line_one.setBackgroundColor(Color.parseColor("#FFFF680A"));
                tv_line_two.setVisibility(View.GONE);
                tv_credit_order.setTextColor(Color.parseColor("#FFA1A1A1"));
                orderDeliveryType = 0;
                UserInfoHelper.saveDeliverType(mActivity, orderDeliveryType + "");

                initView();
                break;

            case R.id.tv_credit_order:
                orderDeliveryType = 1;
                tv_credit_order.setTextColor(Color.parseColor("#FF000000"));
                tv_line_two.setVisibility(View.VISIBLE);
                tv_line_two.setBackgroundColor(Color.parseColor("#FFFF680A"));
                tv_line_one.setVisibility(View.GONE);
                tv_credit_order.setTextColor(Color.parseColor("#FFA1A1A1"));
                UserInfoHelper.saveDeliverType(mActivity, orderDeliveryType + "");
                initCredit();
                break;
        }
    }
}
