package com.barter.hyl.app.activity;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.AccountSelectAdapter;
import com.barter.hyl.app.adapter.HylStickyListssAdapter;
import com.barter.hyl.app.adapter.SearchAccountAdapter;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.model.HylMyBillModel;
import com.barter.hyl.app.model.HylSearchBillModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.CustomDatePicker;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/9/18
 */
public class HylBillActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.sticky)
    StickyListHeadersListView sticky;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    @BindView(R.id.tv_select)
    TextView tv_select;
    @BindView(R.id.ll_account)
    LinearLayout ll_account;
    @BindView(R.id.tv_detail_select)
    TextView tv_detail_select;
    @BindView(R.id.ll_activity_wallet_time)
    LinearLayout ll_activity_wallet_time;
    @BindView(R.id.noData)
    LinearLayout noData;
    @BindView(R.id.rl_default)
    RelativeLayout rl_default;
    @BindView(R.id.tv_month)
    TextView tv_month;
    @BindView(R.id.ll_test)
    LinearLayout ll_test;
    @BindView(R.id.iv_select)
    ImageView iv_select;
    int pageNum = 1;
    int pageSize = 6;
    String payChannel = "";
    String recordType = "";
    String year = "";
    String month = "";
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.bill_activity_hyl);
    }

    @Override
    public void setViewData() {
        getSearch();

        stickyListAdapter = new HylStickyListssAdapter(this, list, new HylStickyListssAdapter.OnclickListener() {
            @Override
            public void headClick() {
                ll_activity_wallet_time.setVisibility(View.VISIBLE);
                mCpDate.show(selectDate);
            }
        });

        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(hasPage) {
                    pageNum++;
                    getMyBill();
                    refreshLayout.finishLoadMore();
                }else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
        });

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                list.clear();
                getMyBill();
                smart.finishRefresh();
            }
        });

        sticky.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
            }
        });


        sticky.setAdapter(stickyListAdapter);
        setTime();


        ll_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_activity_wallet_time.setVisibility(View.VISIBLE);
                mCpDate.show(selectDate);
            }
        });
    }


    private List<HylSearchBillModel.DataBean.List1Bean> mList1 = new ArrayList<>();//筛选
    private List<HylSearchBillModel.DataBean.List2Bean> mList2 = new ArrayList<>();//账户
    private List<HylSearchBillModel.DataBean.List3Bean> mList3 = new ArrayList<>();//全部明细
    private void getSearch() {
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
                        if (hylSearchBillModel.getCode()==1) {
                            if(hylSearchBillModel.getData()!=null) {
                                HylSearchBillModel.DataBean data = hylSearchBillModel.getData();
                                if(data.getList1()!=null) {
                                    mList1.addAll(data.getList1());
                                }

                                if(data.getList2()!=null) {
                                    mList2.addAll(data.getList2());
                                }

                                if(data.getList3()!=null) {
                                    mList3.addAll(data.getList3());
                                }
                            }

                            getMyBill();

                        } else {
                            AppHelper.showMsg(mContext, hylSearchBillModel.getMessage());
                        }


                    }
                });

    }

    @Override
    public void setClickListener() {
        tv_select.setOnClickListener(this);
        tv_detail_select.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    /**
     * 我的账单
     */
    boolean hasPage;
    List<HylMyBillModel.DataBean.ListBean> list = new ArrayList<>();
    HylStickyListssAdapter stickyListAdapter;
    HylMyBillModel billsModel;
    List<HylMyBillModel.DataBean.ListBean.RecordsBean> recordss = new ArrayList<>();
    private void getMyBill() {
        OrderApi.getMyBill(mActivity,pageNum,pageSize,payChannel,recordType,year,month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylMyBillModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(final HylMyBillModel billModel) {
                        if(billModel.getCode()==1) {
                            if(billModel.getData()!=null) {
                                billsModel = billModel;
                                if(billModel.getData().getList()!=null&&billModel.getData().getList().size()>0) {
                                    rl_default.setVisibility(View.GONE);
                                    noData.setVisibility(View.GONE);
                                    recordss.clear();
                                    list.addAll(billModel.getData().getList());

                                    if(billModel.getData().isHasNextPage()) {
                                        hasPage = true;
                                    }else {
                                        hasPage = false;
                                    }
                                }else {
                                    noData.setVisibility(View.VISIBLE);
                                    rl_default.setVisibility(View.VISIBLE);
                                }
                            }


                            stickyListAdapter.notifyDataSetChanged();
                        }else {
                            ToastUtil.showSuccessMsg(mContext,billModel.getMessage());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select:
                //筛选弹窗
                showSelectPup();
                break;

            case R.id.tv_detail_select:
                //明细弹窗
                showDetailPup();
                break;

            case R.id.iv_back:
                finish();
                break;
        }
    }

    /**
     * 明细弹窗
     */
    PopupWindow mPopupWindowOne;
    private void showDetailPup() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.popup_select_detail, null, false);//引入弹窗布局
        mPopupWindowOne = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        bgAlpha(0.3f);
        //设置进出动画
        mPopupWindowOne.setAnimationStyle(R.style.PopupWindowAnimation);
        View parentView = LayoutInflater.from(mContext).inflate(R.layout.bill_activity_hyl, null);
        mPopupWindowOne.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

        RecyclerView rl_select_two = vPopupWindow.findViewById(R.id.rl_select_two);
        rl_select_two.setLayoutManager(new LinearLayoutManager(mContext));
        AccountSelectAdapter madater = new AccountSelectAdapter(R.layout.search_list_detail, mList2);
        rl_select_two.setAdapter(madater);

        madater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                isSelected = true;
                payChannel = mList2.get(position).getKey();
                mPopupWindowOne.dismiss();
                smart.autoRefresh();
                tv_detail_select.setText(mList2.get(position).getValue());
            }
        });


//        tv_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isSelected) {
//                    isSelected = false;
//                    mPopupWindowOne.dismiss();
//                    smart.autoRefresh();
//
//                } else {
//                    AppHelper.showMsg(mContext, "请选择筛选类型");
//                }
//            }
//        });
        mPopupWindowOne.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                bgAlpha(1f);
            }
        });
    }

    /**
     * 筛选
     */
    PopupWindow popupWindow;
    private boolean isSelected;
    private void showSelectPup() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.popup_select, null, false);
        popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
//        bgAlpha(0.3f);
        //设置进出动画
        FrameLayout fl_mark = vPopupWindow.findViewById(R.id.fl_mark);
        ll_account.setBackgroundResource(R.drawable.shape_white1);
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        RecyclerView rl_select = vPopupWindow.findViewById(R.id.rl_select);
        TextView tv_ok = vPopupWindow.findViewById(R.id.tv_ok);
        SearchAccountAdapter madater;
        rl_select.setLayoutManager(new GridLayoutManager(mContext, 3));
        madater = new SearchAccountAdapter(R.layout.search_list, mList1);
        rl_select.setAdapter(madater);
        iv_select.setImageResource(R.mipmap.ic_up);
        tv_detail_select.setVisibility(View.GONE);
        popupWindow.showAsDropDown(tv_select, 0, 0);
        madater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                madater.selectPosition(position);
                isSelected = true;
                recordType = mList1.get(position).getKey();
                smart.autoRefresh();
                tv_select.setText(mList1.get(position).getValue());
            }
        });
        fl_mark.setVisibility(View.VISIBLE);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fl_mark.setVisibility(View.GONE);
                if (isSelected) {
                    isSelected = false;
                    popupWindow.dismiss();
                    smart.autoRefresh();
                    tv_detail_select.setVisibility(View.VISIBLE);
                } else {
                    AppHelper.showMsg(mContext, "请选择筛选类型");
                }
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ll_account.setBackgroundResource(R.mipmap.bg_bill);
                tv_detail_select.setVisibility(View.VISIBLE);
                fl_mark.setVisibility(View.GONE);
                iv_select.setImageResource(R.mipmap.ic_down);
                bgAlpha(1f);
            }
        });
    }

    private void bgAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    CustomDatePicker mCpDate;
    private String selectDate;
    private void setTime() {
        View hiddenView = getLayoutInflater().inflate(R.layout.view_date_picker, ll_activity_wallet_time, false); //hiddenView是隐藏的View，
        ll_activity_wallet_time.addView(hiddenView);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        selectDate = now.split(" ")[0];
        mCpDate = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                selectDate = time.split(" ")[0];
                year = selectDate.split("-")[0];
                month = selectDate.split("-")[1];
                tv_month.setText(year + "-" + month);
                ll_activity_wallet_time.setVisibility(View.GONE);
                smart.autoRefresh();
                sticky.smoothScrollToPosition(0);

            }
        }, "2017-12-01 00:00", now, hiddenView);
        // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        mCpDate.setCancleClickListener(new CustomDatePicker.OnCancleClickListener() {
            @Override
            public void onItemClick(View view) {
                ll_activity_wallet_time.setVisibility(View.GONE);
            }
        });
        mCpDate.showSpecificTime(false); // 不显示时和分
        mCpDate.setIsLoop(false); // 不允许循环滚动
    }
}
