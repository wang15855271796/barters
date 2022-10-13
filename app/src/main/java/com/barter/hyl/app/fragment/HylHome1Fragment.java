package com.barter.hyl.app.fragment;

import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.NonNull;

import com.barter.hyl.app.activity.HylMessageCenterActivity;
import com.barter.hyl.app.activity.TestActivity;
import com.barter.hyl.app.view.MyCompanyScrollView;
import com.google.android.material.appbar.AppBarLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.ChooseCompanyActivity;
import com.barter.hyl.app.activity.HylActiveDetailActivity;
import com.barter.hyl.app.activity.HylActiveListActivity;
import com.barter.hyl.app.activity.HylCommonGoodsActivity;
import com.barter.hyl.app.activity.HylFullListActivity;
import com.barter.hyl.app.activity.LoginActivity;
import com.barter.hyl.app.activity.HylSearchStartActivity;
import com.barter.hyl.app.activity.HylTeamActivity;
import com.barter.hyl.app.adapter.HylFullAdapter;
import com.barter.hyl.app.adapter.HylSkillAdapter;
import com.barter.hyl.app.adapter.HylTeamAdapter;
import com.barter.hyl.app.adapter.HylRvIconAdapter;
import com.barter.hyl.app.adapter.HylViewPagerAdapter;
import com.barter.hyl.app.adapter.MarqueeAdapter;
import com.barter.hyl.app.api.HomeApi;
import com.barter.hyl.app.banner.Banner;
import com.barter.hyl.app.banner.BannerConfig;
import com.barter.hyl.app.banner.GlideImageLoader;
import com.barter.hyl.app.banner.Transformer;
import com.barter.hyl.app.banner.listener.OnBannerListener;
import com.barter.hyl.app.constant.AppConstant;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.dialog.CouponListDialog;
import com.barter.hyl.app.dialog.Privacy4Dialog;
import com.barter.hyl.app.dialog.SystemActiveDialog;
import com.barter.hyl.app.event.ChangeAccountHylEvent;
import com.barter.hyl.app.event.ChangeIvEvent;
import com.barter.hyl.app.event.GoToMarketHylEvent;
import com.barter.hyl.app.event.GoTopEvent;
import com.barter.hyl.app.event.GoTopHylEvent;
import com.barter.hyl.app.model.HomeBaseModel;
import com.barter.hyl.app.model.HylMessageNumModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.utils.Utils;
import com.barter.hyl.app.view.AbScreenUtils;
import com.barter.hyl.app.view.HIndicators;
import com.barter.hyl.app.view.Snap;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.frankfancode.marqueeview.MarqueeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
 * Created by ${王涛} on 2021/8/4
 */
public class HylHome1Fragment extends BasesFragment implements View.OnClickListener {
    @BindView(R.id.rv_skill)
    RecyclerView rv_skill;
    @BindView(R.id.rv_team)
    RecyclerView rv_team;
    @BindView(R.id.rv_full)
    RecyclerView rv_full;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tab_layout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    HylViewPagerAdapter adapter;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_company)
    TextView tv_company;
    @BindView(R.id.rl_red)
    RelativeLayout rl_red;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.rv_icon)
    RecyclerView rv_icon;
    @BindView(R.id.indicator)
    HIndicators indicator;
    @BindView(R.id.tv_skill_title)
    TextView tv_skill_title;
    @BindView(R.id.tv_team)
    TextView tv_team;
    @BindView(R.id.tv_full)
    TextView tv_full;
    @BindView(R.id.tv_team_small)
    TextView tv_team_small;
    @BindView(R.id.tv_full_small)
    TextView tv_full_small;
    @BindView(R.id.rl_message)
    RelativeLayout rl_message;
    @BindView(R.id.ll_full)
    LinearLayout ll_full;
    @BindView(R.id.ll_team)
    LinearLayout ll_team;
    @BindView(R.id.ll_skill)
    LinearLayout ll_skill;
    @BindView(R.id.snap)
    Snap snap;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    @BindView(R.id.tv_time_desc)
    TextView tv_time_desc;
    @BindView(R.id.iv_team)
    ImageView iv_team;
    @BindView(R.id.iv_full)
    ImageView iv_full;
    @BindView(R.id.ll_icon)
    LinearLayout ll_icon;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.iv_location)
    ImageView iv_location;
    @BindView(R.id.tv_call)
    TextView tv_call;
    @BindView(R.id.my_scroll)
    MyCompanyScrollView my_scroll;
    @BindView(R.id.tv_phone_num)
    TextView tv_phone_num;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_introduce)
    TextView tv_introduce;
    @BindView(R.id.tv_stop)
    TextView tv_stop;
    public AppBarLayoutState state;
    public enum AppBarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }
    private ViewGroup.MarginLayoutParams searchLayoutParams,titleLayoutParams;
    private final String[] mTitles = {"热销商品", "常购清单"};
    private float LL_SEARCH_MIN_TOP_MARGIN, LL_SEARCH_MAX_TOP_MARGIN, LL_SEARCH_MAX_WIDTH, LL_SEARCH_MIN_WIDTH, TV_TITLE_MAX_TOP_MARGIN;
    @Override
    public int setLayoutId() {
        return R.layout.home_fragment;
    }

    HylSkillAdapter hylSkillAdapter;
    HylTeamAdapter hylTeamAdapter;
    HylFullAdapter hylFullAdapter;
    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);

        adapter = new HylViewPagerAdapter(getFragmentManager());
        //热销商品
        adapter.addFragment(HylHotFragment.getInstance());
        //常购清单
        adapter.addFragment(HylCommonFragment.getInstance());
        viewPager.setAdapter(adapter);
        tab_layout.setViewPager(viewPager, mTitles);
        smart.autoRefresh();

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getBaseData();
                smart.finishRefresh();
            }
        });

        searchLayoutParams = (ViewGroup.MarginLayoutParams) ll_search.getLayoutParams();
        titleLayoutParams = (ViewGroup.MarginLayoutParams) tv_company.getLayoutParams();
        LL_SEARCH_MIN_TOP_MARGIN = AbScreenUtils.dp2px(mActivity, 4.5f);//布局关闭时顶部距离
        LL_SEARCH_MAX_TOP_MARGIN = AbScreenUtils.dp2px(mActivity, 49f);//布局默认展开时顶部距离
        LL_SEARCH_MAX_WIDTH = Utils.getScreenWidth(mActivity) - AbScreenUtils.dp2px(mActivity, 30f);//布局默认展开时的宽度
        LL_SEARCH_MIN_WIDTH = Utils.getScreenWidth(mActivity) - AbScreenUtils.dp2px(mActivity, 100f);//布局关闭时的宽度
        TV_TITLE_MAX_TOP_MARGIN =  AbScreenUtils.dp2px(mActivity, 11.5f);

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int dy) {
                if(dy==0) {
                    if(state!=AppBarLayoutState.EXPANDED) {
                        //展开
                        state=AppBarLayoutState.EXPANDED;
                        EventBus.getDefault().post(new ChangeIvEvent(state));
                    }
                }else if(Math.abs(dy)>=appBarLayout.getTotalScrollRange()) {
                    if(state!=AppBarLayoutState.COLLAPSED) {
                        //折叠
                        state=AppBarLayoutState.COLLAPSED;
                        EventBus.getDefault().post(new ChangeIvEvent(state));
                    }
                }else {
                    if(state!=AppBarLayoutState.INTERNEDIATE) {
                        state=AppBarLayoutState.INTERNEDIATE;
                        EventBus.getDefault().post(new ChangeIvEvent(state));
                    }
                }
                float abs = Math.abs(dy*0.50f);
                float searchLayoutNewTopMargin = LL_SEARCH_MAX_TOP_MARGIN - abs;

                //安居客效果
                float searchLayoutNewWidth = LL_SEARCH_MAX_WIDTH - abs * 1.7f;//此处 * 1.3f 可以设置搜索框宽度缩放的速率
                //京东效果
//              float searchLayoutNewWidth = LL_SEARCH_MAX_WIDTH - abs * 3.0f;//此处 * 1.3f 可以设置搜索框宽度缩放的速率

                float titleNewTopMargin = (float) (TV_TITLE_MAX_TOP_MARGIN - abs * 0.5);

                //处理布局的边界问题
                searchLayoutNewWidth = searchLayoutNewWidth < LL_SEARCH_MIN_WIDTH ? LL_SEARCH_MIN_WIDTH : searchLayoutNewWidth;

                if (searchLayoutNewTopMargin < LL_SEARCH_MIN_TOP_MARGIN) {
                    searchLayoutNewTopMargin = LL_SEARCH_MIN_TOP_MARGIN;
                }

                if (searchLayoutNewWidth < LL_SEARCH_MIN_WIDTH) {
                    searchLayoutNewWidth = LL_SEARCH_MIN_WIDTH;
                }

                float titleAlpha = 255 * titleNewTopMargin / TV_TITLE_MAX_TOP_MARGIN;
                if (titleAlpha < 0) {
                    titleAlpha = 0;
                }
                float v = titleAlpha / 140;
                tv_company.setAlpha(v);
                titleLayoutParams.topMargin = (int) titleNewTopMargin;
                tv_company.setLayoutParams(titleLayoutParams);

                searchLayoutParams.topMargin = (int) searchLayoutNewTopMargin;
                searchLayoutParams.width = (int) searchLayoutNewWidth;
                ll_search.setLayoutParams(searchLayoutParams);
            }
        });
    }

    /**
     * 获取基础信息
     */
    HylRvIconAdapter hylRvIconAdapter;
    List<HomeBaseModel.DataBean.SpikeBean.ActiveListBean>activeLists = new ArrayList();
    List<HomeBaseModel.DataBean.TeamBean.ActiveListBeanX> teamList = new ArrayList<>();
    List<HomeBaseModel.DataBean.FullActiveBean.ActiveListBeanXX> fullList = new ArrayList<>();

    HomeBaseModel.DataBean data;
    private void getBaseData() {
        HomeApi.getBaseData(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeBaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HomeBaseModel baseModel) {
                        if (baseModel.getCode()==1) {
                            data = baseModel.getData();
                            tv_company.setText(data.getCompanyName());

                            //banner模块
                            if(data.getBanners()!=null&&data.getBanners().size()>0) {
                                List<HomeBaseModel.DataBean.BannersBean> banners = data.getBanners();
                                scrollBanner(banners);
                                banner.setVisibility(View.VISIBLE);
                                rl_red.setVisibility(View.VISIBLE);

                            }else {
                                rl_red.setVisibility(View.GONE);
                                banner.setVisibility(View.GONE);
                            }

                            //滚动广告
                            if(data.getNoticeList()!=null&&data.getNoticeList().size()>0) {
                                MarqueeAdapter marqueeAdapter = new MarqueeAdapter();
                                marqueeAdapter.setData(data.getNoticeList(),1,getActivity());
                                marqueeView.setAdapter(marqueeAdapter);
                                marqueeView.setVisibility(View.VISIBLE);
                                marqueeView.startScroll();
                            }else {
                                marqueeView.setVisibility(View.GONE);
                            }
                            //分类模块
                            if(data.getClassifies()!=null) {
                                getClassify(data);
                                ll_icon.setVisibility(View.VISIBLE);
                            }else {
                                ll_icon.setVisibility(View.GONE);
                            }

                            //是否展示公司信息
                            if(data.getShowCompanyFLag()==1) {
                                my_scroll.setVisibility(View.VISIBLE);
                                //公司是否停业
                                if(data.getCompanyEnabled()==0) {
                                    tv_stop.setVisibility(View.VISIBLE);
                                }else {
                                    tv_stop.setVisibility(View.GONE);
                                }
                            }else {
                                if(data.getCompanyEnabled()==0) {
                                    tv_stop.setVisibility(View.VISIBLE);
                                }else {
                                    tv_stop.setVisibility(View.GONE);
                                }
                                my_scroll.setVisibility(View.GONE);
                            }


                            if(data.getCompanyPhone()!=null) {
                                tv_phone_num.setText(data.getCompanyPhone());
                            }

                            if(data.getCompanyAddress()!=null) {
                                tv_address.setText(data.getCompanyAddress());
                            }

                            if(data.getCompanyDesc()!=null) {
                                tv_introduce.setText("公司介绍:"+data.getCompanyDesc());
                            }

                            //秒杀
                            if(data.getSpike()!=null) {
                                HomeBaseModel.DataBean.SpikeBean spike = data.getSpike();
                                tv_skill_title.setText(spike.getTitle());
                                activeLists.clear();
                                activeLists.addAll(spike.getActiveList());

                                if(activeLists.size()>3) {
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity,3);
                                    hylSkillAdapter = new HylSkillAdapter(R.layout.item_active_hyl,activeLists);
                                    rv_skill.setLayoutManager(gridLayoutManager);
                                }else {
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false);
                                    hylSkillAdapter = new HylSkillAdapter(R.layout.item_active_hyl,activeLists);
                                    rv_skill.setLayoutManager(linearLayoutManager);
                                }
                                rv_skill.setAdapter(hylSkillAdapter);

                                hylSkillAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent = new Intent(mActivity,HylActiveListActivity.class);
                                        intent.putExtra("activeId",activeLists.get(position).getActiveId());
                                        startActivity(intent);
                                    }
                                });

                                hylSkillAdapter.notifyDataSetChanged();
                                ll_skill.setVisibility(View.VISIBLE);

                                if(spike.getNowTime()>spike.getStartTime()) {
                                    //已开始
                                    tv_time_desc.setText("距结束");
                                }else {
                                    tv_time_desc.setText("距开始");
                                }
                                snap.changeTextColor(Color.parseColor("#FF2925"));
                                snap.setTime(false,false,data.getSpike().getNowTime(),data.getSpike().getStartTime(),data.getSpike().getEndTime());
                                snap.start();
                            }else {
                                ll_skill.setVisibility(View.GONE);
                            }

                            //组合
                            if(data.getTeam()!=null) {
                                HomeBaseModel.DataBean.TeamBean team = data.getTeam();
                                tv_team.setText(team.getTitle());
                                tv_team_small.setText(team.getSmallTitle());

                                teamList.clear();
                                teamList.addAll(team.getActiveList());

                                if(teamList.size()>3) {
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity,3);
                                    hylTeamAdapter = new HylTeamAdapter(R.layout.item_active_hyl,teamList);
                                    rv_team.setLayoutManager(gridLayoutManager);
                                }else {
                                    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false);
                                    hylTeamAdapter = new HylTeamAdapter(R.layout.item_active_hyl,teamList);
                                    rv_team.setLayoutManager(linearLayoutManager2);
                                }

                                rv_team.setAdapter(hylTeamAdapter);
                                hylTeamAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent = new Intent(mActivity,HylTeamActivity.class);
                                        startActivity(intent);
                                    }
                                });

                                hylTeamAdapter.notifyDataSetChanged();
                                ll_team.setVisibility(View.VISIBLE);
                            }else {
                                ll_team.setVisibility(View.GONE);
                            }

                            //满赠
                            if(data.getFullActive()!=null) {
                                HomeBaseModel.DataBean.FullActiveBean fullActive = data.getFullActive();
                                tv_full.setText(fullActive.getTitle());
                                tv_full_small.setText(fullActive.getSmallTitle());

                                fullList .clear();
                                fullList.addAll(fullActive.getActiveList());


                                if(fullList.size()>3) {
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity,3);
                                    rv_full.setLayoutManager(gridLayoutManager);
                                    hylFullAdapter = new HylFullAdapter(R.layout.item_active_hyl,fullList);
                                }else {
                                    LinearLayoutManager linearLayoutManage3 = new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false);
                                    hylFullAdapter = new HylFullAdapter(R.layout.item_active_hyl,fullList);
                                    rv_full.setLayoutManager(linearLayoutManage3);
                                }

                                rv_full.setAdapter(hylFullAdapter);

                                hylFullAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent = new Intent(mActivity,HylFullListActivity.class);
                                        startActivity(intent);
                                    }
                                });

                                hylFullAdapter.notifyDataSetChanged();
                                ll_full.setVisibility(View.VISIBLE);
                            }else {
                                ll_full.setVisibility(View.GONE);
                            }

                            if(data.getTeam()!=null) {
                                if(baseModel.getData().getTeam().getPic()!=null) {
                                    Glide.with(mActivity).load(baseModel.getData().getTeam().getPic()).into(iv_team);
                                }
                            }

                            if(data.getFullActive()!=null) {
                                if(baseModel.getData().getFullActive().getPic()!=null) {
                                    Glide.with(mActivity).load(baseModel.getData().getFullActive().getPic()).into(iv_full);
                                }
                            }

                            //是否展示弹窗
                            if(data.getPopUpsFlag()==1) {
                                //弹窗
                                if(data.getPopUps()!=null) {
                                    if(data.getPopUps().getType()==2) {
                                        //优惠券弹窗
                                        CouponListDialog couponListDialog = new CouponListDialog(mActivity,data.getPopUps());
                                        couponListDialog.show();
                                    }else if(data.getPopUps().getType()==3){
                                        //系统弹窗
                                        SystemActiveDialog systemActiveDialog = new SystemActiveDialog(mActivity,data.getPopUps());
                                        systemActiveDialog.show();
                                    }else {
                                        Privacy4Dialog privacysDialog = new Privacy4Dialog(mActivity,data.getPopUps().getId(),data.getPopUps().getType());
                                        privacysDialog.show();
                                        //点击弹窗外部或物理返回键都不消失
                                        privacysDialog.setCancelable(false);
                                    }
                                }
                            }

                        }else if(baseModel.getCode()==-10001) {
                            Intent intent = new Intent(mActivity,LoginActivity.class);
                            startActivity(intent);
                        }else {
                            ToastUtil.showSuccessMsg(mActivity, baseModel.getMessage());
                        }
                    }
                });
    }

    private void getClassify(HomeBaseModel.DataBean data) {
        final List<HomeBaseModel.DataBean.ClassifiesBean> classifyList = data.getClassifies();
        rv_icon.setLayoutManager(new LinearLayoutManager(mActivity));
        hylRvIconAdapter = new HylRvIconAdapter(R.layout.item_home_icon_hyl,classifyList);
        rv_icon.setAdapter(hylRvIconAdapter);
        if(classifyList.size()==5||classifyList.size()==9||classifyList.size()==10) {
            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(mActivity,5);
            rv_icon.setLayoutManager(gridLayoutManager1);
            rv_icon.setAdapter(hylRvIconAdapter);
            indicator.setVisibility(View.GONE);
        }else if(classifyList.size()<=4||classifyList.size()<=8 &&classifyList.size()!=5){
            GridLayoutManager gridLayoutManager2 = new GridLayoutManager(mActivity,4);
            rv_icon.setLayoutManager(gridLayoutManager2);
            rv_icon.setAdapter(hylRvIconAdapter);
            indicator.setVisibility(View.GONE);
        }else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2, RecyclerView.HORIZONTAL, false);
            rv_icon.setLayoutManager(gridLayoutManager);
            rv_icon.setAdapter(hylRvIconAdapter);
            indicator.setVisibility(View.VISIBLE);
        }

        hylRvIconAdapter.notifyDataSetChanged();
        hylRvIconAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().post(new GoToMarketHylEvent());
                EventBus.getDefault().postSticky(new GoTopHylEvent(classifyList.get(position).getId()+"",position));
            }
        });
    }

    //banner集合
    List<String> bannerList = new ArrayList<>();
    List<String> detailList = new ArrayList<>();
    private void scrollBanner(List<HomeBaseModel.DataBean.BannersBean> banners) {
        bannerList.clear();
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        banner.setImageLoader(new GlideImageLoader());
        for (int i = 0; i < banners.size(); i++) {
            bannerList.add(banners.get(i).getBanner());
            if(banners.get(i).getType()==2) {
                detailList.add(banners.get(i).getDetailPic());
            }
        }

        banner.setImages(bannerList);
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.isAutoPlay(true);
        banner.setDelayTime(3000);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        ClickBanner(banners);
        banner.start();
    }

    int showType;
    private void ClickBanner(final List<HomeBaseModel.DataBean.BannersBean> banners) {
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                showType = banners.get(position).getType();

                if (showType == 0) {
                    return;
                } else if (showType == 2) {
                    //图片
                    AppHelper.showPhotoDetailDialog(mActivity,detailList, detailList.size());
                } else if (showType == 4) {
                    //商品
                    String businessId = banners.get(position).getBusinessId();
                    Intent intent = new Intent(getActivity(), HylCommonGoodsActivity.class);
                    intent.putExtra("mainId", businessId+"");
                    startActivity(intent);
                } else if (showType == 5) {
                    //活动
                    String businessId = banners.get(position).getBusinessId();
                    Intent intent = new Intent(getActivity(), HylActiveDetailActivity.class);
                    intent.putExtra(AppConstant.ACTIVEID, businessId);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void setClickListener() {
        ll_search.setOnClickListener(this);
        rl_message.setOnClickListener(this);
        tv_company.setOnClickListener(this);
        iv_location.setOnClickListener(this);
        tv_call.setOnClickListener(this);
        tv_stop.setOnClickListener(this);
    }

    /**
     * 获取未读消息数量
     */
    private void getMessageNum() {
        HomeApi.getMessageNum(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylMessageNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylMessageNumModel messageModel) {
                        if (messageModel.code==1) {
                            if(messageModel.getData()!=null&&!messageModel.getData().equals("0")) {
                                tv_num.setText(messageModel.getData());
                                tv_num.setVisibility(View.VISIBLE);
                            }else {
                                tv_num.setVisibility(View.GONE);
                            }

                        }else if(messageModel.code==-10001) {
//                            Intent intent = new Intent(mActivity,LoginActivity.class);
//                            startActivity(intent);
                        } else {

                            ToastUtil.showSuccessMsg(mActivity, messageModel.message);
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_company:
                Intent intent = new Intent(mActivity, ChooseCompanyActivity.class);
                startActivity(intent);
                break;

            case R.id.iv_location:
                Intent intent1 = new Intent(mActivity, ChooseCompanyActivity.class);
                startActivity(intent1);
                break;

            case R.id.rl_message:
                Intent messageIntent = new Intent(mActivity, HylMessageCenterActivity.class);
                startActivity(messageIntent);
                break;
            case R.id.tv_call:
                if(data!=null && data.getCompanyPhone()!=null || !data.getCompanyPhone().equals("")) {
                    Intent intentCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.getCompanyPhone()));
                    intentCall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentCall);
                }
                break;

            case R.id.tv_stop:
                if(data!=null && data.getCompanyPhone()!=null || !data.getCompanyPhone().equals("")) {
                    Intent intentCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.getCompanyPhone()));
                    intentCall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentCall);
                }
                break;

            case R.id.ll_search:
                Intent intents = new Intent(mActivity,HylSearchStartActivity.class);
                intents.putExtra("test1",2);
                startActivity(intents);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.start();
        getMessageNum();
        marqueeView.startScroll();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        banner.stopAutoPlay();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 账号切换时信息更新/切换公司信息时更新
     * @param changeAccountHylEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCategory(ChangeAccountHylEvent changeAccountHylEvent) {
        smart.autoRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void goTop(GoTopEvent goTopEvent) {
        appbar.setExpanded(true);
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
