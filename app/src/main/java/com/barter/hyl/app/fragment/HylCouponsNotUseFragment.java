package com.barter.hyl.app.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylMyCouponsAdapter;
import com.barter.hyl.app.api.MyInfoApi;
import com.barter.hyl.app.base.BaseFragment;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.model.HylMyCouponModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import org.greenrobot.eventbus.EventBus;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/8/7我的优惠券界面（未使用）
 */
public class HylCouponsNotUseFragment extends BaseFragment {

    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.data)
    LinearLayout data;
    @BindView(R.id.noData)
    LinearLayout noData;
    @BindView(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;

    private HylMyCouponsAdapter adapter;
    private int pageNum = 1;

    private List<HylMyCouponModel.DataBean.ListBean > lists =new ArrayList<>();


    @Override
    public int setLayoutId() {
        return R.layout.fragment_cupons_overdue_hyl;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setViewData() {
        pageNum = 1;
        requestMyCoupons();
        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNum = 1;
                lists.clear();
                requestMyCoupons();
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new HylMyCouponsAdapter(R.layout.item_my_coupons_hyl,lists,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(-1)) {
                    ptrClassicFrameLayout.setEnabled(false);
                } else {
                    ptrClassicFrameLayout.setEnabled(true);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum++;
                requestMyCoupons();
            }
        }, recyclerView);
    }

    @Override
    public void setClickListener() {

    }


    private void requestMyCoupons() {
        MyInfoApi.getMyCouponList(getActivity(), pageNum, 10,"ENABLED")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylMyCouponModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HylMyCouponModel info) {

                        ptrClassicFrameLayout.refreshComplete();
                        if (info.getCode()==1) {

                            updateNoticeList(info);
                        } else {
                            AppHelper.showMsg(mActivity, info.getMessage());
                        }


                    }
                });
    }

    private void updateNoticeList(HylMyCouponModel info) {

        if (pageNum == 1) {
            if (info.getData() != null && info.getData().getList().size() > 0) {
                data.setVisibility(View.VISIBLE);
                noData.setVisibility(View.GONE);
                lists.addAll(info.getData().getList());
                adapter.notifyDataSetChanged();

            } else {
                data.setVisibility(View.GONE);
                tv_desc.setText("您还没有优惠券可以使用哦");
                noData.setVisibility(View.VISIBLE);
            }

        } else {
            lists.addAll(info.getData().getList());
            adapter.notifyDataSetChanged();
        }
        if (info.getData().isHasNextPage()) {
            //还有下一页数据
            adapter.loadMoreComplete();
        } else {
            //没有下一页数据了
            adapter.loadMoreEnd();
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getList(CityEvent cityEvent) {
//        ptrClassicFrameLayout.autoRefresh();
//    }


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
