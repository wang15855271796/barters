package com.barter.hyl.app.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.barter.hyl.app.R;
import com.barter.hyl.app.activity.HylCommonGoodsActivity;
import com.barter.hyl.app.activity.HylSearchStartActivity;
import com.barter.hyl.app.activity.LoginActivity;
import com.barter.hyl.app.adapter.HylBrandsAdapter;
import com.barter.hyl.app.adapter.HylCateAdapter;
import com.barter.hyl.app.adapter.HylGoodsAdapter;
import com.barter.hyl.app.adapter.HylSecondAdapter;
import com.barter.hyl.app.api.CategoriApi;
import com.barter.hyl.app.base.BaseFragment;
import com.barter.hyl.app.constant.StringHelper;
import com.barter.hyl.app.dialog.MarketDialog;
import com.barter.hyl.app.event.ChangeAccountHylEvent;
import com.barter.hyl.app.event.GoTopHylEvent;
import com.barter.hyl.app.model.HylGoodCateModel;
import com.barter.hyl.app.model.HylGoodListModel;
import com.barter.hyl.app.model.GoodNameModel;
import com.barter.hyl.app.model.HylMarketBeanModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.CustomPopWindow;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.xrecyclerview.XRecyclerView;

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
 * Created by ${??????} on 2021/8/4
 */
public class HylMarketsFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.rv_cate)
    RecyclerView rv_cate;
    @BindView(R.id.rv_second)
    RecyclerView rv_second;
    @BindView(R.id.x_rv)
    XRecyclerView x_rv;
    @BindView(R.id.tv_select)
    TextView tv_select;
    @BindView(R.id.tv_sale)
    TextView tv_sale;
    @BindView(R.id.ll_price)
    LinearLayout ll_price;
    @BindView(R.id.iv_tip)
    ImageView iv_tip;
    @BindView(R.id.ll_all)
    LinearLayout ll_all;
    @BindView(R.id.ll_search)
    RelativeLayout ll_search;
    @BindView(R.id.v_shadow)
    View v_shadow;
    @BindView(R.id.ll_market)
    LinearLayout ll_market;
    HylCateAdapter hylCateAdapter;
    HylSecondAdapter hylSecondAdapter;
    int pageNum = 1;
    int pageSize = 10;
    //????????????
    String brandName = "";
    String minPrice = "";
    String maxPrice = "";
    //???????????? 1??????
    int saleFlag = 0;
    //???????????? 1?????? 2??????
    int priceFlag = 0;
    int firstId;
    int secondId;

    //??????Id???position
    int selectIdPosition = 0;
    private boolean hasPage = true;
    HylGoodsAdapter hylGoodsAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_market;
    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        showLoadding();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false);
        rv_cate.setLayoutManager(linearLayoutManager);
        hylCateAdapter = new HylCateAdapter(R.layout.item_icon_hyl,list);
        rv_cate.setAdapter(hylCateAdapter);
        hylCateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                brandName = "";
                pageNum = 1;
                dialog.show();
                selectIdPosition = position;
                getCategories();
            }
        });

        hylSecondAdapter = new HylSecondAdapter(R.layout.item_left_classify_hyl,seconds);
        rv_second.setLayoutManager(new LinearLayoutManager(mActivity));
        rv_second.setAdapter(hylSecondAdapter);

        hylSecondAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                brandName = "";
                dialog.show();
                pageNum = 1;
                secondId = data.get(selectIdPosition).getSeconds().get(position).getSecondId();
                hylSecondAdapter.selectPosition(position);
                getGoodList();
            }
        });

        //??????adapter
        hylGoodsAdapter = new HylGoodsAdapter(R.layout.item_market_goods, cate_list, new HylGoodsAdapter.OnAddClickListener() {
            @Override
            public void onAddClick(int mainId) {
                MarketDialog marketDialog = new MarketDialog(mActivity,mainId);
                marketDialog.show();
            }
        });

        hylGoodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity,HylCommonGoodsActivity.class);
                intent.putExtra("mainId",cate_list.get(position-1).getMainId());
                startActivity(intent);
            }
        });

        x_rv.setLayoutManager(new LinearLayoutManager(mActivity));
        x_rv.setAdapter(hylGoodsAdapter);
        x_rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getGoodList();
//                if (isCheck) {
//                    pageNum = 1;
//                    hasPage  = true;
//                    getGoodList();
//                } else {
//                    pageNum = 1;
//                    hasPage  = true;
//                    getGoodList();
//                }
            }

            @Override
            public void onLoadMore() {
                if(hasPage) {
                    pageNum++;
                    getGoodList();
                }else {
                    hasPage = false;
                    x_rv.noMoreLoading(true);
                }
//                if (isCheck) {
//                    if (hasPage) {
//                        pageNum++;
//                        getGoodList();
//
//                    } else {
//                        hasPage = false;
//                        x_rv.noMoreLoading();
//                    }
//                } else {
//
//
//                    if (hasPage) {
//                        pageNum++;
//                        getGoodList();
//
//                    } else {
//                        hasPage = false;
//                        x_rv.noMoreLoading();
//                    }
//                }
            }
        });
        dialog.show();
    }



    @Override
    public void setClickListener() {
        tv_sale.setOnClickListener(this);
        tv_select.setOnClickListener(this);
        ll_price.setOnClickListener(this);
        ll_all.setOnClickListener(this);
        ll_search.setOnClickListener(this);
    }

    /**
     * ??????????????????
     */
    List<HylGoodCateModel.DataBean> data;
    List<HylGoodCateModel.DataBean> list = new ArrayList<>();
    List<HylGoodCateModel.DataBean.SecondsBean> seconds = new ArrayList<>();
    private void getCategori() {
        CategoriApi.goodsCate(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylGoodCateModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(HylGoodCateModel hylGoodCateModel) {
                        if (hylGoodCateModel.getCode()==1) {
                            dialog.dismiss();
                            if(hylGoodCateModel.getData()!=null&& hylGoodCateModel.getData().size()>0) {
                                data = hylGoodCateModel.getData();
                                if(id!="") {
                                    //????????????????????????
                                    secondId = data.get(selectIdPosition).getSeconds().get(selectIdPosition).getSecondId();

                                    list.clear();
                                    list.addAll(hylGoodCateModel.getData());

                                    seconds.clear();
                                    seconds.addAll(hylGoodCateModel.getData().get(0).getSeconds());

                                    firstId = Integer.parseInt(id);
                                    for (int i = 0; i < list.size(); i++) {
                                        if(Integer.parseInt(id)==list.get(i).getFirstId()) {
                                            secondId = list.get(i).getSeconds().get(0).getSecondId();
                                            hylCateAdapter.selectPosition(i);
                                        }
                                    }

                                    getGoodList();

                                    hylSecondAdapter.notifyDataSetChanged();
                                    hylCateAdapter.notifyDataSetChanged();
                                }else {
                                    //????????????????????????
                                    firstId = data.get(selectIdPosition).getFirstId();
                                    secondId = data.get(selectIdPosition).getSeconds().get(selectIdPosition).getSecondId();

                                    list.clear();
                                    list.addAll(hylGoodCateModel.getData());

                                    seconds.clear();
                                    seconds.addAll(hylGoodCateModel.getData().get(0).getSeconds());

                                    getGoodList();

                                    hylSecondAdapter.notifyDataSetChanged();
                                    hylCateAdapter.notifyDataSetChanged();
                                }
                            }else {
                                seconds.clear();
                                list.clear();
                                cate_list.clear();
                                hylSecondAdapter.notifyDataSetChanged();
                                hylCateAdapter.notifyDataSetChanged();
                                hylGoodsAdapter.notifyDataSetChanged();
                            }
                        }else if(hylGoodCateModel.getCode() ==-10001) {
//                            Intent intent = new Intent(mActivity,LoginActivity.class);
//                            startActivity(intent);
                            dialog.dismiss();
                            ToastUtil.showSuccessMsg(mActivity, hylGoodCateModel.getMessage());
                        } else {
                            dialog.dismiss();
                            ToastUtil.showSuccessMsg(mActivity, hylGoodCateModel.getMessage());
                        }
                    }
                });
    }

    //?????????????????????????????????
    private void getCategories() {
        CategoriApi.goodsCate(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylGoodCateModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(HylGoodCateModel hylGoodCateModel) {
                        if (hylGoodCateModel.getCode()==1) {
                            if(hylGoodCateModel.getData()!=null) {
                                dialog.dismiss();
                                data = hylGoodCateModel.getData();

                                //????????????????????????
                                firstId = data.get(selectIdPosition).getFirstId();
                                secondId = data.get(selectIdPosition).getSeconds().get(0).getSecondId();

                                list.clear();
                                list.addAll(hylGoodCateModel.getData());

                                seconds.clear();
                                seconds.addAll(data.get(selectIdPosition).getSeconds());

                                hylSecondAdapter.selectPosition(0);
                                hylSecondAdapter.notifyDataSetChanged();
                                getGoodList();
                                if(mCustomPopWindow!=null) {
                                    mCustomPopWindow.dissmiss();
                                }
                                hylCateAdapter.selectPosition(selectIdPosition);
                            }

                        }else if(hylGoodCateModel.getCode()==-10001) {
                            dialog.dismiss();
//                            Intent intent = new Intent(mActivity,LoginActivity.class);
//                            startActivity(intent);
                            ToastUtil.showSuccessMsg(mActivity, hylGoodCateModel.getMessage());
                        } else {
                            dialog.dismiss();
                            ToastUtil.showSuccessMsg(mActivity, hylGoodCateModel.getMessage());
                        }
                    }
                });
    }

    /**
     * ??????????????????
     */
    HylGoodListModel.DataBean rightData;
    List<HylGoodListModel.DataBean.ListBean> cate_list = new ArrayList<>();
    private void getGoodList() {
        CategoriApi.goodsList(mActivity,pageNum,pageSize,brandName,minPrice,maxPrice,saleFlag,priceFlag,firstId,secondId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylGoodListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(HylGoodListModel hylGoodListModel) {
                        if (hylGoodListModel.getCode()==1) {
                            rightData = hylGoodListModel.getData();
                            if(pageNum==1) {
                                cate_list.clear();
                            }
                            cate_list.addAll(rightData.getList());
                            hylGoodsAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                            if(hylGoodListModel.getData().isHasNextPage()) {
                                hasPage = true;
                                x_rv.noMoreLoading(false);
                            }else {
                                hasPage = false;
                                x_rv.noMoreLoading(true);
                            }

                            x_rv.refreshComplete();
                        } else if(hylGoodListModel.getCode()==-10001) {
                            dialog.dismiss();
                            Intent intent = new Intent(mActivity,LoginActivity.class);
                            startActivity(intent);
                        }else {
                            ToastUtil.showSuccessMsg(mActivity, hylGoodListModel.getMessage());
                            dialog.dismiss();
                        }
                    }
                });
    }

    //??????????????????
    int saleNum = 0;
    //??????????????????
    int priceNum = 0;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_all:
                shopListView();
                break;

            case R.id.ll_search:
                Intent intent = new Intent(mActivity,HylSearchStartActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_sale:
                saleNum++;
                if(saleNum%2==0) {
                    tv_sale.setTextColor(Color.parseColor("#F6391A"));
                    saleFlag = 1;
                    getGoodList();
                }else {
                    tv_sale.setTextColor(Color.parseColor("#333333"));
                    saleFlag = 2;
                    getGoodList();
                }

                break;

            case R.id.ll_price:
                priceNum++;
                if(priceNum%3==0) {
                    iv_tip.setImageResource(R.mipmap.icon_grey);
                    priceFlag = 0;
                    getGoodList();
                }else if(priceNum%3==1) {
                    iv_tip.setImageResource(R.mipmap.icon_top);
                    priceFlag = 2;
                    getGoodList();
                }else {
                    iv_tip.setImageResource(R.mipmap.icon_bt);
                    priceFlag = 1;
                    getGoodList();
                }

                break;

            case R.id.tv_select:
                showSelectDialog();
                break;

        }
    }


    /**
     * ????????????
     */
    private RecyclerView mRyGetGoodName;
    private EditText mEtLowPrice;
    private EditText mEtHighPrice;
    private EditText mEtSearchGood;
    private TextView mTvReresh;
    private TextView mTvOk;
    private ImageView ivSearch;
    private PopupWindow popupWindow;

    private void showSelectDialog() {
        // ????????????????????????????????????????????????
        View contentView = LayoutInflater.from(mActivity).inflate(
                R.layout.market_select_draw, null);
        mEtSearchGood = contentView.findViewById(R.id.et_activity_search_word);//??????????????????
        mEtLowPrice = contentView.findViewById(R.id.et_low_price);//???????????????
        mEtHighPrice = contentView.findViewById(R.id.rt_high_price);//???????????????
        mRyGetGoodName = contentView.findViewById(R.id.recyclerView_search_good);//?????????????????????
        mTvReresh = contentView.findViewById(R.id.tv_refresh_good);//??????
        mTvOk = contentView.findViewById(R.id.tv_ok);//??????
        ivSearch = contentView.findViewById(R.id.iv_search);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int width1 = mActivity.getWindowManager().getDefaultDisplay().getWidth();


        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        popupWindow = new PopupWindow(contentView, width, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setWidth(width1 * 3 / 4);
        popupWindow.setAnimationStyle(R.style.AnimationRightFade);
        //??????
        popupWindow.setClippingEnabled(false);
        backgroundAlpha(0.3f);
        //????????????
        popupWindow.setOnDismissListener(new popupDismissListener());

        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(ll_market, Gravity.RIGHT, 0, 0);

        brandName = "";
        getGoodName();

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringHelper.notEmptyAndNull(mEtSearchGood.getText().toString())) {
                    brandName = mEtSearchGood.getText().toString();
                    getGoodName();
                } else {
                    brandName = "";
                    getGoodName();
                }
            }
        });


        mTvReresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtSearchGood.setText("");
                mEtLowPrice.setText("");
                mEtHighPrice.setText("");
                if(brandAdapter!=null) {
                    brandAdapter.setStat();
                }
            }
        });
        mTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtSearchGood.setText("");
                minPrice = mEtLowPrice.getText().toString();
                maxPrice = mEtHighPrice.getText().toString();
                pageNum = 1;
                getGoodList();
                popupWindow.dismiss();
            }

        });
    }


    /**
     * ??????????????????
     */
    List<HylMarketBeanModel> brandList = new ArrayList<>();
    HylBrandsAdapter brandAdapter;
    private void getGoodName() {
        CategoriApi.goodsName(mActivity,brandName,firstId,secondId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GoodNameModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(GoodNameModel goodNameModel) {
                        if (goodNameModel.getCode()==1) {
                            dialog.dismiss();
                            brandList.clear();
                            for (int i = 0;i<goodNameModel.getData().size();i++){
                                HylMarketBeanModel bean = new HylMarketBeanModel(goodNameModel.getData().get(i));
                                brandList.add(bean);
                            }
                            //?????????
                            brandAdapter = new HylBrandsAdapter(mActivity,brandList);
                            mRyGetGoodName.setLayoutManager(new GridLayoutManager(mActivity, 3));
                            mRyGetGoodName.setAdapter(brandAdapter);
                            brandAdapter.notifyDataSetChanged();
//                            brandList.clear();
                            brandAdapter.setOnItemClickListener(new HylBrandsAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(List<HylMarketBeanModel> list) {
                                    brandName = "";
                                    if (list.size() > 0) {
                                        if(list.size()>1) {
                                            for (int i = 0; i < list.size(); i++) {
                                                brandName += list.get(i).getS()+ ",";
                                            }
                                            brandName = brandName.substring(0, brandName.length()-1);
                                        }else {
                                            brandName =  list.get(0).getS();
                                        }
                                    }
                                }
                            });
                        }else if(goodNameModel.getCode() ==-10001) {
                            popupWindow.dismiss();
                            ToastUtil.showSuccessMsg(mActivity,goodNameModel.getMessage());
                            dialog.dismiss();
                            Intent intent = new Intent(mActivity,LoginActivity.class);
                            startActivity(intent);
                        } else {
                            dialog.dismiss();
                            ToastUtil.showSuccessMsg(mActivity, goodNameModel.getMessage());
                        }
                    }
                });
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    }

    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
//            et_goods.clearFocus();
            v_shadow.setVisibility(View.GONE);
        }

    }

    CustomPopWindow mCustomPopWindow;
    private void shopListView() {
        View contentView = LayoutInflater.from(mActivity).inflate(R.layout.cate_list_hyl,null);
        ImageView iv_close = contentView.findViewById(R.id.iv_close);
        RecyclerView recyclerView = contentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity,5));
        recyclerView.setAdapter(hylCateAdapter);
        hylCateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                getCategories();
//                selectIdPosition = position;
//
                dialog.show();
                selectIdPosition = position;
//                hylCateAdapter.selectPosition(position);
                getCategories();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomPopWindow.dissmiss();
            }
        });
        //???????????????????????????????????????????????????
        mCustomPopWindow= new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setFocusable(false)
                .setOutsideTouchable(true)
                .setTouchable(true)
                .setView(contentView)
                .create()
                .showAsDropDown(ll_search);
    }
    //?????????????????????????????????????????????
    String id;
    int pos = 0;
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void goTop(GoTopHylEvent goTopHylEvent) {
        id = goTopHylEvent.getId();
        pos = goTopHylEvent.getPosition();
        getCategori();
//        int id = goTopHylEvent.getId();
//        for (int i = 0; i < list.size(); i++) {
//            if (id == list.get(i).getFirstId()) {
//                hylCateAdapter.selectPosition(i);
//            }
//        }
    }

//    private void getCategoris(final int id) {
//        CategoriApi.goodsCate(mActivity)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<HylGoodCateModel>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onNext(HylGoodCateModel goodCateModel) {
//                        if (goodCateModel.getCode()==1) {
//                            data = goodCateModel.getData();
////                            firstId = id;
////                            secondId = data.get(selectIdPosition).getSeconds().get(selectIdPosition).getSecondId();
//                            list.clear();
//                            list.addAll(goodCateModel.getData());
//                            for (int i = 0; i < list.size(); i++) {
//                            if(id==list.get(i).getFirstId()) {
//                                hylCateAdapter.selectPosition(i);
//                                firstId = id;
//                                secondId = 0;
//                                seconds.clear();
//                                seconds.addAll(goodCateModel.getData().get(0).getSeconds());
//                            }
//
//
//
//                            getGoodList();
//
//                            hylSecondAdapter.notifyDataSetChanged();
//                            hylCateAdapter.notifyDataSetChanged();
//                        }
//
//                        } else {
//                            ToastUtil.showSuccessMsg(mActivity, goodCateModel.getMessage());
//                        }
//                    }
//                });
//    }

    /**
     * ???????????????????????????
     * @param changeAccountHylEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCategory(ChangeAccountHylEvent changeAccountHylEvent) {
        getCategori();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    LoadingDailog dialog;
    private void showLoadding() {
        //??????????????????????????????????????????
        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(getContext())
                .setMessage("???????????????")
                .setCancelable(false)
                .setCancelOutside(true);
        dialog = loadBuilder.create();
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
     * ??????????????????
     *
     * @return????????????????????? yyyy-MM-dd
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
     * ??????????????????
     *
     * @return????????????????????? yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTimes = new Date();
        SimpleDateFormat formatters = new SimpleDateFormat("yyyy");
        String dateStrings = formatters.format(currentTimes);
        return dateStrings;
    }
}
