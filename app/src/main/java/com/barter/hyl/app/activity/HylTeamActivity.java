package com.barter.hyl.app.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylViewPagerAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.event.CartNumHylEvent;
import com.barter.hyl.app.event.JumpCartHylEvent;
import com.barter.hyl.app.fragment.HylImmedFragment;
import com.barter.hyl.app.fragment.HylInProgressFragment;
import com.barter.hyl.app.model.HylCartNumModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.flyco.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/7/29
 */
public class HylTeamActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tab_layout)
    SlidingTabLayout tab_layout;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.rl_cart)
    RelativeLayout rl_cart;
    private final String[] mTitles = {"进行中", "即将开始"};
    HylViewPagerAdapter adapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.team_activity);
    }

    @Override
    public void setViewData() {
        iv_image.setVisibility(View.VISIBLE);
        EventBus.getDefault().register(this);
        tv_title.setText("超值组合");
        getCartNum();
        adapter = new HylViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(HylInProgressFragment.getInstance());
        adapter.addFragment(HylImmedFragment.getInstance());
        viewPager.setAdapter(adapter);
        tab_layout.setViewPager(viewPager, mTitles);

    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
        rl_cart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.rl_cart:
                EventBus.getDefault().post(new JumpCartHylEvent());
                finish();
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartNum(CartNumHylEvent event) {
        getCartNum();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

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
}
