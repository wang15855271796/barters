package com.barter.hyl.app.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylStickyListAdapter;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.model.HylGetWallertRecordByPageModel;
import com.barter.hyl.app.model.HylSearchBillModel;
import com.barter.hyl.app.view.FVHelper;
import com.barter.hyl.app.view.MenuBarTwo;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/9/24
 */
public class HylBillsActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView textViewDetailed;
    private LinearLayout linearLayoutOnclick;
    //    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private RecyclerView recyclerView;
    private LinearLayout noData;// 没有数据的界面
    private LinearLayout data; // 没有数据的界面
    private TextView textScreen;// 筛选
    private int pageNum = 1;

    private List<HylGetWallertRecordByPageModel.DataBean.ListBean.RecordsBean> mListData;
    private LoadingDailog dialog;
    StickyListHeadersListView lv;
    private String selectDate;
    private String year;
    private String month;
    private LinearLayout mLlTimeSelect;
//    private MyWallDetailPopuwindow popuwindow;
    List listPopuwindow = new ArrayList();
    private String recordType;
//    private CustomDatePicker mCpDate;
    private ImageView detailedImage;
    //筛选
    private TextView tv_select;
    //账户选择
    private MenuBarTwo mb_bar;
    //明细选择
    private TextView tv_detail_select;
    //月份选择
    private TextView tv_month_select;
    //收入
    private TextView tv_income;
    //支出
    private TextView tv_expenditure;
    //筛选弹窗
    private PopupWindow popupWindow;
    //明细弹窗
    private PopupWindow mPopupWindowOne;

    private List<HylSearchBillModel.DataBean.List1Bean> mList1 = new ArrayList<>();//筛选
    private List<HylSearchBillModel.DataBean.List2Bean> mList2 = new ArrayList<>();//账户
    private List<HylSearchBillModel.DataBean.List3Bean> mList3 = new ArrayList<>();//全部明细
    HylGetWallertRecordByPageModel hylGetWallertRecordByPageModels;
    private LinearLayout ll_account;
    private boolean isSelected;
    private String phone;
    private String walletRecordChannelType;

    private ArrayList<View> viewList = new ArrayList<>();
    private ArrayList<String> contentThree = new ArrayList<>();
    private ArrayList<String> titles;

    private int showType;
    List<String> list = new ArrayList<>();
    private TextView tv_tool;
    HylStickyListAdapter adapters;
    SmartRefreshLayout refreshLayout;
    private List<HylGetWallertRecordByPageModel.DataBean.ListBean.RecordsBean> records;
    private List<HylGetWallertRecordByPageModel.DataBean.ListBean.RecordsBean> record = new ArrayList<>();
    private HylGetWallertRecordByPageModel.DataBean.ListBean.RecordsBean recordsBean;
    private int isrefreshormore = 1;//1刷新  2加载

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_my_wallet_detail_hyl);
    }


    @Override
    public void setViewData() {
        mListData = new ArrayList<>();
        lv = FVHelper.fv(this, R.id.lv);
        refreshLayout = FVHelper.fv(this, R.id.refreshLayout);
        mLlTimeSelect = FVHelper.fv(this, R.id.ll_activity_wallet_time);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textViewDetailed = (TextView) findViewById(R.id.textViewDetailed);
        linearLayoutOnclick = (LinearLayout) findViewById(R.id.linearLayoutOnclick);
//        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptrClassicFrameLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        noData = (LinearLayout) findViewById(R.id.noData);
        data = (LinearLayout) findViewById(R.id.data);
        textScreen = (TextView) findViewById(R.id.textScreen);
        detailedImage = (ImageView) findViewById(R.id.detailedImage);

        tv_select = findViewById(R.id.tv_select);
        mb_bar = findViewById(R.id.mb_bar);
        tv_detail_select = findViewById(R.id.tv_detail_select);
        tv_month_select = findViewById(R.id.tv_month_select);
        tv_income = findViewById(R.id.tv_income);
        tv_expenditure = findViewById(R.id.tv_expenditure);
        ll_account = findViewById(R.id.ll_account);
        tv_tool = findViewById(R.id.tv_tool);

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                if(hylGetWallertRecordByPageModels.getData().getLastMonth()!=null) {
//                    HylGetWallertRecordByPageModel.DataBean data = hylGetWallertRecordByPageModels.getData();
//                    isrefreshormore = 2;
//                    getWallertRecord(recordType,data.getLastYear(),data.getLastMonth(),null,showType,walletRecordChannelType);
//                    Log.e("123",data.getLastMonth()+"ss");
//                }else {
//                    refreshLayout.finishLoadMoreWithNoMoreData();
//                }
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                mListData.clear();
//                isrefreshormore = 1;
//                getWallertRecord(recordType,year, month, phone,showType,walletRecordChannelType);
//                Log.e("123",month+"ss"+year);
//                refreshLayout.finishRefresh();
            }
        });
        adapters = new HylStickyListAdapter(mActivity, mListData, lists, new HylStickyListAdapter.Onclick() {
            @Override
            public void clicks() {
                mLlTimeSelect.setVisibility(View.VISIBLE);
//                mCpDate.show(selectDate);
            }
        });
        lv.setAdapter(adapters);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(!mListData.get(position).isNullData()) {
//                    Intent intent = new Intent(mContext, MyCountDetailActivity.class);
//                    intent.putExtra("id", mListData.get(position).getId());
//                    intent.putExtra("type", mListData.get(position).getType());
//                    mContext.startActivity(intent);
//                }
            }
        });
        showType = getIntent().getIntExtra("showType", 0);

        if (showType == 1) {
            tv_tool.setText("我的账单  ");
        } else if (showType == 2) {
            tv_tool.setText("余额明细");
        }


        final Calendar mCalendar = Calendar.getInstance();
        long time = System.currentTimeMillis();
        mCalendar.setTimeInMillis(time);
        int i = mCalendar.get(Calendar.MONTH) + 1;

        month = i + "";
        year = mCalendar.get(Calendar.YEAR) + "";


        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(mContext)
                .setMessage("获取数据中")
                .setCancelable(false)
                .setCancelOutside(false);
        dialog = loadBuilder.create();
        setRecyclerView();
        requsetSearchMsg();
//        setTime();

//        requsetPrice(recordType, year, month, phone, walletRecordChannelType,showType);
        titles = new ArrayList<>();
        titles.add("全部账户");


    }

    @Override
    public void setClickListener() {

    }

//    MyListView myListView2;

    /**
     * 获取搜索条件
     */
    private void requsetSearchMsg() {
        OrderApi.getSearchBill(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylSearchBillModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylSearchBillModel hylSearchBillModel) {
                        if (hylSearchBillModel.isSuccess()) {
                            mList1.clear();
                            mList2.clear();
                            mList3.clear();
                            contentThree.clear();
                            viewList.clear();

                            if (hylSearchBillModel.getData().getList1().size() > 0) {
                                mList1.addAll(hylSearchBillModel.getData().getList1());
                            }
                            if (hylSearchBillModel.getData().getList3().size() > 0) {
                                mList3.addAll(hylSearchBillModel.getData().getList3());
                            }
                            if (hylSearchBillModel.getData().getList2() != null && hylSearchBillModel.getData().getList2().size() > 0) {
                                mb_bar.setVisibility(View.VISIBLE);

                                mList2.addAll(hylSearchBillModel.getData().getList2());
                                if (mList2.size() > 0) {
                                    for (int j = 0; j < mList2.size(); j++) {
                                        contentThree.add(mList2.get(j).getValue());
                                    }
                                }

//                                if (myListView2 == null) {
//                                    myListView2 = new MyListView(mActivity, contentThree);
//                                }
//                                viewList.add(0, myListView2);
                                mb_bar.setView(titles, viewList);
//                                myListView2.setOnSelectListener(new MyListView.OnSelectListener() {
//                                    @Override
//                                    public void getValue(String value, int position) {
//                                        mb_bar.setTitle(value);
//                                        pageNum = 1;
//                                        phone = mList2.get(position).getKey();
//                                        mListData.clear();
////                                        requsetPrice(recordType, year, month, phone, walletRecordChannelType,showType);
//                                    }
//                                });


                            } else {
                                mb_bar.setVisibility(View.GONE);
                            }
                            year = mList3.get(0).getYear()+"";

                            month = mList3.get(0).getMonths().get(0).getMonth()+"";
                            payChannel = mList2.get(0).getKey();
                            recordType = mList1.get(0).getKey();

                            getWallertRecord();
                        } else {
                            AppHelper.showMsg(mContext, hylSearchBillModel.getMessage());
                        }


                    }
                });


    }

//    @Override
//    public void setClickEvent() {
//
//        linearLayoutOnclick.setOnClickListener(noDoubleClickListener);
//        textScreen.setOnClickListener(noDoubleClickListener);
//        tv_month_select.setOnClickListener(noDoubleClickListener);
//
//        tv_select.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //筛选弹窗
//                showSelectPup();
//            }
//        });
//
//        tv_detail_select.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //明细弹窗
//                showDetailPup();
//            }
//        });
//    }

    /**
     * 明细弹窗
     */
//    private void showDetailPup() {
//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View vPopupWindow = inflater.inflate(R.layout.pop_account_detail, null, false);//引入弹窗布局
//        mPopupWindowOne = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
//        //设置背景透明
//        bgAlpha(0.3f);
//
//        //设置进出动画
//        mPopupWindowOne.setAnimationStyle(R.style.PopupWindowAni);
//        View parentView = LayoutInflater.from(mContext).inflate(R.layout.activity_my_wallet_detail_hyl, null);
//
//        mPopupWindowOne.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
//
//        RecyclerView rl_select_two = vPopupWindow.findViewById(R.id.rl_select_two);
//
//
//        rl_select_two.setLayoutManager(new LinearLayoutManager(mContext));
//
//        AccountSelectAdapter accountSelectAdapter = new AccountSelectAdapter(R.layout.search_account_select, mList3);
//
//        rl_select_two.setAdapter(accountSelectAdapter);
//
//        accountSelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//                walletRecordChannelType = mList3.get(position).getKey();
//                mPopupWindowOne.dismiss();
//                dialog.show();
//                tv_detail_select.setText(mList3.get(position).getValue());
//                mListData.clear();
//                getWallertRecord(recordType,year, month, phone,showType,walletRecordChannelType);
////                requsetPrice(recordType, year, month, phone, walletRecordChannelType,showType);
//            }
//        });
//        //添加分隔线
//        DividerItemDecoration dividerPreKillDecoration = new DividerItemDecoration(mActivity,
//                DividerItemDecoration.VERTICAL_LIST);
//        dividerPreKillDecoration.setDivider(R.drawable.app_divider);
//        rl_select_two.addItemDecoration(dividerPreKillDecoration);
//
//        mPopupWindowOne.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                bgAlpha(1f);
//            }
//        });
//    }

//    private void bgAlpha(float bgAlpha) {
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = bgAlpha; //0.0-1.0
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        getWindow().setAttributes(lp);
//    }

    /**
     * 筛选
     */
//    private void showSelectPup() {
//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View vPopupWindow = inflater.inflate(R.layout.popup_select, null, false);//引入弹窗布局
//        popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
//        //设置背景透明
//        //  addBackground();
//
//        //设置进出动画
//        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
//
//        //引入依附的布局
//        //  View parentView = LayoutInflater.from(mContext).inflate(R.layout.activity_my_wallet_detail_hyl, null);
//        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
//
//
//        RecyclerView rl_select = vPopupWindow.findViewById(R.id.rl_select);
//        TextView tv_cancel = vPopupWindow.findViewById(R.id.tv_cancel);
//        TextView tv_ok = vPopupWindow.findViewById(R.id.tv_ok);
//        HylSearchAccountAdapter madater;
//        tv_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
//
//        rl_select.setLayoutManager(new GridLayoutManager(mContext, 3));
//        madater = new HylSearchAccountAdapter(R.layout.search_list, mList1);
//
//        rl_select.setAdapter(madater);
//
//        popupWindow.showAsDropDown(ll_account, 0, 0);
//
//
//        madater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                madater.selectPosition(position);
//                isSelected = true;
//                recordType = mList1.get(position).getKey();
//            }
//        });
//
//
//        tv_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isSelected) {
//                    isSelected = false;
//                    popupWindow.dismiss();
//                    pageNum = 1;
//                    mListData.clear();
//                    isrefreshormore = 1;
//                    refreshLayout.autoRefresh();
//                    getWallertRecord(recordType,year,month,"",showType,null);
////                    requsetPrice(recordType, year, month, phone, walletRecordChannelType,1);
//
//                } else {
//                    AppHelper.showMsg(mContext, "请选择筛选类型");
//                }
//            }
//        });
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                bgAlpha(1f);
//            }
//        });
//
//    }

//    private void addBackground() {
//        // 设置背景颜色变暗
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = 0.3f;//调节透明度
//        getWindow().setAttributes(lp);
//        //dismiss时恢复原样
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//
//            @Override
//            public void onDismiss() {
//                WindowManager.LayoutParams lp = getWindow().getAttributes();
//                lp.alpha = 1f;
//                getWindow().setAttributes(lp);
//            }
//        });
//    }


//    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
//        @Override
//        public void onNoDoubleClick(View view) {
//            switch (view.getId()) {
//                case R.id.linearLayoutOnclick:
//                    detailedImage.setImageResource(R.mipmap.ic_mine_detailed);
//
//                    if (null == popuwindow) {
//                        setPopuwindow();
//                    }
//                    popuwindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
//                    break;
//                case R.id.textScreen:
//                    if ("up".equals(textScreen.getTag())) {
//
//                        textScreen.setTag("down");
//                        mLlTimeSelect.setVisibility(View.GONE);
//                    } else {
//                        detailedImage.setImageResource(R.mipmap.ic_waill_up);
//
//                        textScreen.setTag("up");
//                        mLlTimeSelect.setVisibility(View.VISIBLE);
//                        mCpDate.show(selectDate);
//                    }
//                    break;
//                case R.id.tv_month_select:
//                    mLlTimeSelect.setVisibility(View.VISIBLE);
//                    mCpDate.show(selectDate);
//
//                    break;
//            }
//        }
//    };

    /**
     * 网络请求
     */
    List<HylGetWallertRecordByPageModel.DataBean> lists = new ArrayList();
    String payChannel = "";
    private void getWallertRecord() {
//        OrderApi.getMyBills(mContext, types, year, month, phone, showType,walletRecordChannelType)
        OrderApi.getMyBills(mActivity,pageNum,10,payChannel,recordType,year,month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylGetWallertRecordByPageModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylGetWallertRecordByPageModel hylGetWallertRecordByPageModel) {
                        if (hylGetWallertRecordByPageModel.isSuccess()) {
                            hylGetWallertRecordByPageModels = hylGetWallertRecordByPageModel;
                            if (isrefreshormore == 1){
                                lists.clear();
//                                if (hylGetWallertRecordByPageModel.getData().getRecords() != null && hylGetWallertRecordByPageModel.getData().getRecords().size() > 0) {
//                                    data.setVisibility(View.VISIBLE);
//                                    noData.setVisibility(View.GONE);
//                                    records = hylGetWallertRecordByPageModel.getData().getRecords();
//                                    for (int i = 0; i < records.size(); i++) {
//                                        recordsBean = records.get(i);
////                                        HylGetWallertRecordByPageModel.DataBean data = hylGetWallertRecordByPageModel.getData();
//                                        HylGetWallertRecordByPageModel.DataBean data = hylGetWallertRecordByPageModel.getData();
//                                        lists.add(data);
//                                    }
//                                    mListData.addAll(records);
                                if (hylGetWallertRecordByPageModel.getData().getList() != null) {
                                    data.setVisibility(View.VISIBLE);
                                    noData.setVisibility(View.GONE);

                                    for (int i = 0; i < hylGetWallertRecordByPageModel.getData().getList().size(); i++) {
                                        records = hylGetWallertRecordByPageModel.getData().getList().get(i).getRecords();
                                    }
//                                    record.addAll(records);
                                    for (int i = 0; i < records.size(); i++) {
                                        recordsBean = records.get(i);
//                                        HylGetWallertRecordByPageModel.DataBean data = hylGetWallertRecordByPageModel.getData();
                                        HylGetWallertRecordByPageModel.DataBean data = hylGetWallertRecordByPageModel.getData();
                                        lists.add(data);
                                    }
                                    mListData.addAll(records);

                                }else {
                                    HylGetWallertRecordByPageModel.DataBean data = hylGetWallertRecordByPageModel.getData();
                                    lists.add(data);

//                                    HylGetWallertRecordByPageModel.DataBean.RecordsBean recordsBean = new HylGetWallertRecordByPageModel.DataBean.RecordsBean();
//                                    recordsBean.setNullData(true);
//                                    recordsBean.setDateTime(data.getNowYear()+"-"+data.getNowMonth());
//                                    mListData.add(recordsBean);
                                    for (int i = 0; i < hylGetWallertRecordByPageModel.getData().getList().size(); i++) {
                                        recordsBean = new HylGetWallertRecordByPageModel.DataBean.ListBean.RecordsBean();
                                    }
//                                    recordsBean.setNullData(true);
//                                    recordsBean.setDateTime(data.getNowYear()+"-"+data.getNowMonth());
                                    mListData.add(recordsBean);
                                }
                            } else {
                                if(hylGetWallertRecordByPageModels.getData()!=null&& hylGetWallertRecordByPageModels.getData().getList().size()>0) {
                                    for (int i = 0; i < hylGetWallertRecordByPageModels.getData().getList().size(); i++) {
                                        records = hylGetWallertRecordByPageModel.getData().getList().get(i).getRecords();
                                    }
                                    for (int i = 0; i < records.size(); i++) {
                                        recordsBean = records.get(i);
                                        HylGetWallertRecordByPageModel.DataBean data = hylGetWallertRecordByPageModel.getData();
                                        lists.add(data);
                                    }
                                    mListData.addAll(records);
                                    refreshLayout.finishLoadMore();
                                }else {
                                    adapters.notifyDataSetChanged();
                                    refreshLayout.finishLoadMore();
                                }
                            }
                            adapters.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            AppHelper.showMsg(mActivity, hylGetWallertRecordByPageModel.getMessage());
                        }
                    }
                });
    }

    private void setRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (recyclerView.canScrollVertically(-1)) {
//                    ptrClassicFrameLayout.setEnabled(false);
//                } else {
//                    ptrClassicFrameLayout.setEnabled(true);
//                }
//            }
//        });
////        recyclerView.setAdapter(adapter);
//        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
//            @Override
//            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
//
//            }
//
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//                pageNum = 1;
//                mListData.clear();
////                getWallertRecord(year, month, phone, walletRecordChannelType, showType);
//                requsetSearchMsg(showType);
//                requsetPrice(recordType, year, month, phone, walletRecordChannelType,showType);
//
//            }
//        });
    }

    //  明细筛选
//    private void setPopuwindow() {
//        listPopuwindow.add("全部明细");
//        listPopuwindow.add("收入明细");
//        listPopuwindow.add("支出明细");
//        popuwindow = new MyWallDetailPopuwindow(mContext, listPopuwindow, new MyWallDetailPopuwindow.ItemClick() {
//            @Override
//            public void GridviewOnClick(int position) {
//
//                switch (position) {
//                    case 0: // 全部明细
//                        popuwindow.dismiss();
//                        detailedImage.setImageResource(R.mipmap.ic_mine_detailed);
//                        textViewDetailed.setText("全部明细");
//                        recordType = 0 + "";
//                        pageNum = 1;
//                        mListData.clear();
////                        getWallertRecord( year, month, phone, walletRecordChannelType, showType);
//                        break;
//                    case 1: // 收入明细
//                        popuwindow.dismiss();
//                        detailedImage.setImageResource(R.mipmap.ic_mine_detailed);
//                        textViewDetailed.setText("收入明细");
//                        recordType = 2 + "";
//                        pageNum = 1;
//                        mListData.clear();
////                        getWallertRecord(year, month, phone, walletRecordChannelType, showType);
//                        break;
//                    case 2: //支出明细
//                        popuwindow.dismiss();
//                        detailedImage.setImageResource(R.mipmap.ic_mine_detailed);
//                        textViewDetailed.setText("支出明细");
//                        recordType = 1 + "";
//                        pageNum = 1;
//                        mListData.clear();
////                        getWallertRecord(year, month, phone, walletRecordChannelType, showType);
//                        break;
//                }
//
//            }
//        });
//    }

//    private void setTime() {
//
//        View hiddenView = getLayoutInflater().inflate(R.layout.view_date_picker, mLlTimeSelect, false); //hiddenView是隐藏的View，
//        mLlTimeSelect.addView(hiddenView);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
//        String now = sdf.format(new Date());
//        selectDate = now.split(" ")[0];
//        mCpDate = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
//            @Override
//            public void handle(String time) { // 回调接口，获得选中的时间
//                selectDate = time.split(" ")[0];
//                year = selectDate.split("-")[0];
//                month = selectDate.split("-")[1];
//                tv_month_select.setText(year + "-" + month);
//                dialog.show();
//                mListData.clear();
//                getWallertRecord(recordType,year,month,null,showType,null);
////                requsetPrice(recordType, year, month, phone, walletRecordChannelType,showType);
//                textScreen.setTag("down");
//                mLlTimeSelect.setVisibility(View.GONE);
//
//            }
//        }, "2017-12-01 00:00", now, hiddenView); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
//        mCpDate.setCancleClickListener(new CustomDatePicker.OnCancleClickListener() {
//            @Override
//            public void onItemClick(View view) {
//
//                textScreen.setTag("down");
//                mLlTimeSelect.setVisibility(View.GONE);
//            }
//        });
//        mCpDate.showSpecificTime(false); // 不显示时和分
//        mCpDate.setIsLoop(false); // 不允许循环滚动
//    }


    /**
     * 账单价格总计
     */

//    public void requsetPrice(String types, String year, String month, String phone, String walletRecordChannelType,int showType) {
//        GetSumPriceAPI.requestSumPrice(mContext, types, year, month, phone, showType, walletRecordChannelType)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<GetSumPriceModel>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(GetSumPriceModel getSumPriceModel) {
//                        if (getSumPriceModel.isSuccess()) {
//                            tv_income.setText("收入" + "￥ " + String.valueOf(getSumPriceModel.getData().getIn()));
//                            tv_expenditure.setText("支出" + "￥ " + String.valueOf(getSumPriceModel.getData().getOut()));
//                        } else {
//
//                            AppHelper.showMsg(mContext, getSumPriceModel.getMessage());
//
//
//                        }
//
//                    }
//                });
//
//    }

















//    @BindView(R.id.sticky)
//    StickyListHeadersListView sticky;
//    @BindView(R.id.smart)
//    SmartRefreshLayout smart;
//    int pageNum = 1;
//    int pageSize = 10;
//    String payChannel = "";
//    String recordType = "";
//    String year = "";
//    String month = "";
//
//    @Override
//    public boolean handleExtra(Bundle savedInstanceState) {
//        return false;
//    }
//
//    @Override
//    public void setContentView() {
//        setContentView(R.layout.bill_activity_hyl);
//    }
//
//    @Override
//    public void setViewData() {
//        getMyBill();
//
//        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                getMyBill();
//            }
//        });
//    }
//
//    @Override
//    public void setClickListener() {
//
//    }
//
//    private void getMyBill() {
//        OrderApi.getMyBills(mActivity,pageNum,pageSize,payChannel,recordType,year,month)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<HylGetWallertRecordByPageModel>() {
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
//                    public void onNext(HylGetWallertRecordByPageModel billModel) {
//                        if(billModel.getCode()==1) {
//                            if(billModel.getData()!=null) {
////                                list.addAll(billModel.getData().getList());
////                                for (int i = 0; i < billModel.getData().getList().size(); i++) {
////                                    recordsList = billModel.getData().getList().get(i).getRecords();
////                                }
////
////                                HylStickyListssAdapter stickyListAdapter = new HylStickyListssAdapter(mActivity,list,recordsList);
////                                sticky.setAdapter(stickyListAdapter);
////                                Log.d("swddsdsada......",recordsList.size()+"a");
//                            }
//                        }else {
//                            ToastUtil.showSuccessMsg(mContext,billModel.getMessage());
//                        }
//                    }
//                });
//    }
}
