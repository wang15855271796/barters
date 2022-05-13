package com.barter.hyl.app.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.InfoSearchActivity;
import com.barter.hyl.app.activity.IssueActivity;
import com.barter.hyl.app.adapter.MarketsAdapter;
import com.barter.hyl.app.api.InfoListAPI;
import com.barter.hyl.app.base.BaseFragment;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.event.CityEvent;
import com.barter.hyl.app.event.SearchShopEvent;
import com.barter.hyl.app.listener.CascadingMenuViewOnSelectListener;
import com.barter.hyl.app.listener.PopWindowListener;
import com.barter.hyl.app.model.CityChangeModel;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.InfoListModel;
import com.barter.hyl.app.utils.SharedPreferencesUtil;
import com.barter.hyl.app.utils.Time;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.CatePopWindow;
import com.barter.hyl.app.view.ChooseCityPopWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by ${王涛} on 2020/1/5
 */
public class InfoFragment extends BaseFragment {
    Unbinder bind;
    @BindView(R.id.linearLayoutData)
    LinearLayout linearLayoutData;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_issue)
    TextView tv_issue;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    @BindView(R.id.ll_cate)
    LinearLayout ll_cate;
    @BindView(R.id.tv_cate)
    TextView tv_cate;
    @BindView(R.id.mask)
    FrameLayout mask;
    @BindView(R.id.ll_area)
    LinearLayout ll_area;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_search)
    TextView tv_search;
    List<InfoListModel.DataBean.ListBean> list = new ArrayList<>();
    int pageNum = 1;
    int pageSize = 10;
    int pos = 0;
    MarketsAdapter marketsAdapter;
    InfoListModel infoListModel1;
    String[] data = {"全部分类","店铺转让","器具转让","厨师招聘","其它信息"};
    boolean isOpen = false;
    CatePopWindow catePopWindow;
    ChooseCityPopWindow cascadingMenuPopWindow;
    ArrayList<CityChangeModel.DataBean> listCity = new ArrayList<>();
    String search;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_info;
    }

    @Override
    public void setViewData() {
        provinceName = SharedPreferencesUtil.getString(mActivity,"provinceName");
        EventBus.getDefault().register(this);
        cityName = UserInfoHelper.getCity(mActivity);
        marketsAdapter = new MarketsAdapter(R.layout.item_shop,list);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity,2));
        recyclerView.setAdapter(marketsAdapter);
        List<String> strings = Arrays.asList(data);
        getCityChoose();
        search = tv_search.getText().toString();
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, InfoSearchActivity.class);
                startActivity(intent);
            }
        });


        ll_cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mask.setVisibility(View.VISIBLE);
                if(catePopWindow==null) {
                    catePopWindow = new CatePopWindow(mActivity,strings);
                }

                if(cascadingMenuPopWindow!=null) {
                    if(cascadingMenuPopWindow.isShowing()) {
                        cascadingMenuPopWindow.dismiss();
                    }
                }

                catePopWindow.setBackgroundDrawable(null);
                catePopWindow.setPopWindowListener(new PopWindowListener() {
                    @Override
                    public void getCateStyle(String cate,int position) {
                        tv_cate.setText(cate);
                        pos = position;
                        catePopWindow.dismiss();
                        list.clear();
                        mask.setVisibility(View.GONE);
                        if(position==0) {
                            getCityList(search,"",cityCode,provinceCode);
                        }else if(position==1) {
                            getCityList(search,"1",cityCode,provinceCode);
                        }else if(position==2) {
                            getCityList(search,"2",cityCode,provinceCode);
                        }else if(position==3){
                            getCityList(search,"3",cityCode,provinceCode);
                        }else {
                            getCityList(search,"4",cityCode,provinceCode);
                        }

                    }
                });

                if(catePopWindow.isShowing()) {
                    catePopWindow.dismiss();
                    mask.setVisibility(View.GONE);
                }else {
                    catePopWindow.showAsDropDown(ll_cate,0,0);
                }
            }
        });

        ll_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mask.setVisibility(View.VISIBLE);
                if(cascadingMenuPopWindow==null) {
                    cascadingMenuPopWindow = new ChooseCityPopWindow(mActivity, listCity);
                }
                if(catePopWindow!=null) {
                    if(catePopWindow.isShowing()) {
                        catePopWindow.dismiss();
                    }
                }

                cascadingMenuPopWindow.setBackgroundDrawable(null);
                cascadingMenuPopWindow.setMenuViewOnSelectListener(new NMCascadingMenuViewOnSelectListener());
                cascadingMenuPopWindow.setOnDismissListener(new popupDismissListener());


                if(cascadingMenuPopWindow.isShowing()) {
                    cascadingMenuPopWindow.dismiss();
                    mask.setVisibility(View.GONE);
                }else {
                    cascadingMenuPopWindow.showAsDropDown(ll_cate,0,0);
                }

            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });

        tv_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, IssueActivity.class);
                startActivity(intent);
            }
        });

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                list.clear();
                pageNum=1;
                if(pos==0) {
                    getCityList(search,"",cityCode,provinceCode);
                }else if(pos==1) {
                    getCityList(search,"1",cityCode,provinceCode);
                }else if(pos==2) {
                    getCityList(search,"2",cityCode,provinceCode);
                }else if(pos==3){
                    getCityList(search,"3",cityCode,provinceCode);
                }else {
                    getCityList(search,"4",cityCode,provinceCode);
                }
                smart.finishRefresh();

            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(infoListModel1.getData()!=null) {
                    if(infoListModel1.getData().isHasNextPage()) {
                        pageNum++;
                        if(pos==0) {
                            getCityList(search,"",cityCode,provinceCode);
                        }else if(pos==1) {
                            getCityList(search,"1",cityCode,provinceCode);
                        }else if(pos==2) {
                            getCityList(search,"2",cityCode,provinceCode);
                        }else if(pos==3){
                            getCityList(search,"3",cityCode,provinceCode);
                        }else {
                            getCityList(search,"4",cityCode,provinceCode);
                        }
                        refreshLayout.finishLoadMore();
                    }else {
                        smart.finishLoadMoreWithNoMoreData();
                    }

                }
            }
        });

        getCityList("","","","");
    }


    /**
     * 资讯列表
     */
    private void getCityList(String keyword,String type,String cityCode,String provinceCode) {
        InfoListAPI.requestData(mActivity,keyword,type,pageNum,pageSize,provinceCode,cityCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InfoListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(InfoListModel infoListModel) {
                        if (infoListModel.isSuccess()) {
                           if(infoListModel.getData()!=null&&infoListModel.getData().getList().size()>0) {
                               infoListModel1 = infoListModel;
                               List<InfoListModel.DataBean.ListBean> lists = infoListModel.getData().getList();
                               list.addAll(lists);
                               marketsAdapter.notifyDataSetChanged();
                               linearLayoutData.setVisibility(View.GONE);
                           }else {
                               list.clear();
                               marketsAdapter.notifyDataSetChanged();
                               linearLayoutData.setVisibility(View.VISIBLE);
                           }
                           if(infoListModel.getData().getList().size()>0) {
                               smart.setEnableLoadMore(true);
                           }else {
                               smart.setEnableLoadMore(false);
                           }


                        } else {
                            AppHelper.showMsg(mActivity, infoListModel.getMessage());
                        }
                    }
                });
    }

    private void getCityChoose() {
        CityChangeAPI.requestCity(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CityChangeModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(CityChangeModel cityChangeModel) {
                        if (cityChangeModel.isSuccess()) {
                            listCity.clear();
                            List<CityChangeModel.DataBean> data = cityChangeModel.getData();
                            listCity.addAll(data);

                        } else {
                            AppHelper.showMsg(mActivity, cityChangeModel.getMessage());
                        }
                    }
                });
    }

    String provinceName;
    String cityName;
    String provinceCode = "";
    String cityCode = "";
    class NMCascadingMenuViewOnSelectListener implements CascadingMenuViewOnSelectListener {
        @Override
        public void getValue(CityChangeModel.DataBean area) {
            provinceName = area.getProvinceName();
            provinceCode = area.getProvinceCode();

        }

        @Override
        public void getValues(CityChangeModel.DataBean.CityNamesBean area) {
            if(provinceName == null) {
                ToastUtil.showSuccessMsg(mActivity,"请选择上一级城市");
                return;
            }
            list.clear();
            mask.setVisibility(View.GONE);
            cityName = area.getCityName();
            tv_address.setText(provinceName+cityName);
            cityCode = area.getCityCode();

            if(pos==0) {
                getCityList(search,"",cityCode,provinceCode);
            }else if(pos==1) {
                getCityList(search,"1",cityCode,provinceCode);
            }else if(pos==2) {
                getCityList(search,"2",cityCode,provinceCode);
            }else if(pos==3){
                getCityList(search,"3",cityCode,provinceCode);
            }else {
                getCityList(search,"4",cityCode,provinceCode);
            }


        }

        @Override
        public void cloese() {
            mask.setVisibility(View.GONE);
            cityCode = "";
            provinceCode = "";
            getCityList(search,"","","");
            tv_address.setText("全部");
        }

    }

    private class popupDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            mask.setVisibility(View.GONE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(cascadingMenuPopWindow!=null) {
            cascadingMenuPopWindow.dismiss();
            mask.setVisibility(View.GONE);
        }
        if(catePopWindow!=null) {
            catePopWindow.dismiss();
            mask.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCitys(CityEvent event) {
        //刷新UI
        provinceName = SharedPreferencesUtil.getString(mActivity,"provinceName");
        cityName = UserInfoHelper.getCity(mActivity);
        tv_address.setText(provinceName+cityName);
        smart.autoRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getShop(SearchShopEvent event) {
        String keyWord = event.getKeyWord();
        search = keyWord;
        tv_search.setText(keyWord);
        smart.autoRefresh();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
