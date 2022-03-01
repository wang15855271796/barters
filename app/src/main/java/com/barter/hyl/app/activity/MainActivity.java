package com.barter.hyl.app.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.constant.StringHelper;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.event.CartNumHylEvent;
import com.barter.hyl.app.event.ChangeAccountHylEvent;
import com.barter.hyl.app.event.ChangeIvEvent;
import com.barter.hyl.app.event.ChangeNumHylEvent;
import com.barter.hyl.app.event.GoToMarketHylEvent;
import com.barter.hyl.app.event.GoTopEvent;
import com.barter.hyl.app.event.GoTopHylEvent;
import com.barter.hyl.app.event.InitEventBus;
import com.barter.hyl.app.event.JumpCartHylEvent;
import com.barter.hyl.app.event.JumpMarketEvent;
import com.barter.hyl.app.fragment.HylCartFragment;
import com.barter.hyl.app.fragment.HylHome1Fragment;
import com.barter.hyl.app.fragment.HylMarketsFragment;
import com.barter.hyl.app.fragment.HylMineFragment;
import com.barter.hyl.app.model.HylCartNumModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.R;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ll_home)
    LinearLayout ll_home;
    @BindView(R.id.ll_cart)
    LinearLayout ll_cart;
    @BindView(R.id.ll_goods)
    LinearLayout ll_goods;
    @BindView(R.id.ll_mine)
    LinearLayout ll_mine;
    @BindView(R.id.iv_home)
    ImageView iv_home;
    @BindView(R.id.iv_goods)
    ImageView iv_goods;
    @BindView(R.id.iv_cart)
    ImageView iv_cart;
    @BindView(R.id.iv_mine)
    ImageView iv_mine;

//    @BindView(R.id.tv_home)
//    TextView tv_home;
    @BindView(R.id.tv_goods)
    TextView tv_goods;
    @BindView(R.id.tv_cart)
    TextView tv_cart;
    @BindView(R.id.tv_mine)
    TextView tv_mine;
    @BindView(R.id.tv_number)
    TextView tv_number;
    String[] params = { Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final String TAB_HOME = "tab_home";
    private static final String TAB_MARKET = "tab_market";
    private static final String TAB_CART = "tab_cart";
    private static final String TAB_MINE = "tab_mine";
    private MyLocationListener myListener = new MyLocationListener();
    public LocationClient mLocationClient = null;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        //重新让新的Fragment指向了原本未被销毁的fragment，它就是onAttach方法对应的Fragment对象
        if (mTabHome == null && fragment instanceof HylHome1Fragment)
            mTabHome = fragment;
        if (mTabMarket == null && fragment instanceof HylMarketsFragment)
            mTabMarket = fragment;
        if (mTabCart == null && fragment instanceof HylCartFragment)
            mTabCart = fragment;
        if (mTabMine == null && fragment instanceof HylMineFragment)
            mTabMine = fragment;
        super.onAttachFragment(fragment);
    }


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }


    @Override
    public void setViewData() {
        SDKInitializer.initialize(getApplicationContext());
        EventBus.getDefault().register(this);

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        EventBus.getDefault().post(new InitEventBus());

        //注册监听函数
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true
        option.setOpenGps(true);
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(true);
        mLocationClient.setLocOption(option);

        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);

//        mLocationClient.start();
        switchTab(TAB_HOME);
        getCartNum();
    }

    @Override
    public void setClickListener() {
        ll_home.setOnClickListener(this);
        ll_cart.setOnClickListener(this);
        ll_goods.setOnClickListener(this);
        ll_mine.setOnClickListener(this);
//        iv_home.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

//            case R.id.iv_home:
//                EventBus.getDefault().post(new GoTopEvent());
//                break;
            case R.id.ll_home:
                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    switchTab(TAB_HOME);
                    EventBus.getDefault().post(new GoTopEvent());
                }else {
                    goLogin();
                }

                break;

            case R.id.ll_goods:
                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    switchTab(TAB_MARKET);
                }else {
                    goLogin();
                }

                break;

            case R.id.ll_cart:
                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    switchTab(TAB_CART);
                }else {
                    goLogin();
                }

                break;

            case R.id.ll_mine:
                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    switchTab(TAB_MINE);
                }else {
                    goLogin();
                }
                break;
        }
    }

    private FragmentTransaction mFragmentTransaction;
    private Fragment mTabHome;
    private Fragment mTabMarket;
    private Fragment mTabCart;
    private Fragment mTabMine;
    private void switchTab(String tab) {
        //开始事务
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        //隐藏所有的Fragment
        if (mTabHome != null) {
            mFragmentTransaction.hide(mTabHome);
        }
        if (mTabMarket != null) {
            mFragmentTransaction.hide(mTabMarket);
        }
        if (mTabCart != null) {
            mFragmentTransaction.hide(mTabCart);
        }
        if (mTabMine != null) {
            mFragmentTransaction.hide(mTabMine);
        }

        //重置所有的tabStyle
        iv_home.setImageResource(R.mipmap.icon_go_top);
//        tv_home.setTextColor(getResources().getColor(R.color.app_color_bottom_gray));
        iv_goods.setImageResource(R.mipmap.ic_tab_goods_unable);
        tv_goods.setTextColor(getResources().getColor(R.color.app_color_bottom_gray));
        iv_cart.setImageResource(R.mipmap.ic_tab_cart_unable);
        tv_cart.setTextColor(getResources().getColor(R.color.app_color_bottom_gray));
        iv_mine.setImageResource(R.mipmap.ic_tab_mine_unable);
        tv_mine.setTextColor(getResources().getColor(R.color.app_color_bottom_gray));
        //切换被选中的tab
        switch (tab) {
            case TAB_HOME:
                if (mTabHome == null) {
                    mTabHome = new HylHome1Fragment();
                    mFragmentTransaction.add(R.id.layout_home_container, mTabHome);
                } else {
                    mFragmentTransaction.show(mTabHome);
                }
//                tv_home.setVisibility(View.GONE);
//                iv_home.setImageResource(R.mipmap.icon_go_top);

                if (EasyPermissions.hasPermissions(this,params)) {//检查是否获取该权限
                    //全部允许
                } else {//第二次请求
                    //存在不允许的权限  对话框为什么一会出来一会不出来
                    EasyPermissions.requestPermissions(this, "需要加载必要的权限。", 1, params);
                }
                break;

            case TAB_MARKET:
                if (mTabMarket == null) {
                    mTabMarket = new HylMarketsFragment();
                    mFragmentTransaction.add(R.id.layout_home_container, mTabMarket);
                    EventBus.getDefault().postSticky(new GoTopHylEvent("",0));
                } else {
                    mFragmentTransaction.show(mTabMarket);
                }
//                tv_home.setVisibility(View.VISIBLE);
//                tv_home.setTextColor(getResources().getColor(R.color.app_color_bottom_gray));
                iv_goods.setImageResource(R.mipmap.ic_tab_goods_enable);
                tv_goods.setTextColor(getResources().getColor(R.color.app_tab_selected));

                break;
            case TAB_CART:
                if (mTabCart == null) {
                    mTabCart = new HylCartFragment();
                    mFragmentTransaction.add(R.id.layout_home_container, mTabCart);
                } else {
                    mFragmentTransaction.show(mTabCart);

                }
//                tv_home.setVisibility(View.VISIBLE);
//                tv_home.setTextColor(getResources().getColor(R.color.app_color_bottom_gray));
                iv_cart.setImageResource(R.mipmap.ic_tab_cart_enable);
                tv_cart.setTextColor(getResources().getColor(R.color.app_tab_selected));
                break;
            case TAB_MINE:

                if (mTabMine == null) {
                    mTabMine = new HylMineFragment();
                    mFragmentTransaction.add(R.id.layout_home_container, mTabMine);
                } else {
                    mFragmentTransaction.show(mTabMine);
                }
//                tv_home.setTextColor(getResources().getColor(R.color.app_color_bottom_gray));
//                tv_home.setVisibility(View.VISIBLE);
                iv_mine.setImageResource(R.mipmap.ic_tab_mine_enable);
                tv_mine.setTextColor(getResources().getColor(R.color.app_tab_selected));

//                Intent intent = new Intent(mActivity,LoginActivity.class);
//                startActivity(intent);
                break;
        }
        //提交事务
        mFragmentTransaction.commitAllowingStateLoss();
    }

    private void goLogin() {
        Intent intent = new Intent(mContext,LoginActivity.class);
        startActivity(intent);
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            UserInfoHelper.saveProvince(mContext,location.getProvince());
            UserInfoHelper.saveCity(mContext,location.getCity());
            UserInfoHelper.saveAreaName(mContext,location.getDistrict());
            UserInfoHelper.saveStreet(mContext,location.getStreet());
//            switchTab(TAB_HOME);
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeIv(ChangeIvEvent changeIvEvent) {
        if(changeIvEvent.getState()==HylHome1Fragment.AppBarLayoutState.COLLAPSED) {
            iv_home.setImageResource(R.mipmap.icon_go_top1);
        }else {
            iv_home.setImageResource(R.mipmap.icon_go_top);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void jumpMarket(GoToMarketHylEvent goToMarketHylEvent) {
        switchTab(TAB_MARKET);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void goCart(JumpCartHylEvent jumpCartHylEvent) {
        switchTab(TAB_CART);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void goCart(CartNumHylEvent cartNumHylEvent) {
        getCartNum();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void goCart(ChangeNumHylEvent changeNumHylEvent) {
        getCartNum();
    }

    //用户注册成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void logined(ChangeAccountHylEvent changeAccountHylEvent) {
        getCartNum();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void goMarket(JumpMarketEvent jumpMarketEvent) {
        switchTab(TAB_MARKET);
    }
    /**
     * 购物车数量
     */
    private void getCartNum() {
        DetailApi.getCartNum(mActivity,0)
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
                                tv_number.setText(hylCartNumModel.getData().getProdNum()+"");

                            }else {
                                tv_number.setVisibility(View.GONE);

                            }
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylCartNumModel.getMessage());
                        }
                    }
                });
    }

    private long mExitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                AppHelper.showMsg(this, "再按一次退出程序！");
                mExitTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
