package com.barter.hyl.app.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylMyCouponsOverAdapter;
import com.barter.hyl.app.api.MyInfoApi;
import com.barter.hyl.app.base.BaseFragment;
import com.barter.hyl.app.model.HylMyCouponModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

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
 * Created by ${daff} on 2018/9/20
 * 我的优惠券界面不可使用(过期)
 */
public class HylCouponsOverdueFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.data)
    LinearLayout data;
    @BindView(R.id.noData)
    LinearLayout noData;
    @BindView(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    private HylMyCouponsOverAdapter adapter;
    private int pageNum = 1;
    int pageSize = 10;
    TextView tv_desc;
    private List<HylMyCouponModel.DataBean.ListBean> lists = new ArrayList<>();

    @Override
    public int setLayoutId() {
        return R.layout.fragment_cupons_overdues_hyl;
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
                requestMyCoupons();
            }
        });

        adapter = new HylMyCouponsOverAdapter(R.layout.item_my_coupons_hyl,lists, getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        MyInfoApi.getMyCouponList(getActivity(), pageNum, pageSize, "OVERTIME")
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
                    public void onNext(HylMyCouponModel hylMyCouponModel) {
                        ptrClassicFrameLayout.refreshComplete();
                        if(hylMyCouponModel.getCode()==1) {
                            updateNoticeList(hylMyCouponModel);
                        }else {
                            ToastUtil.showSuccessMsg(mActivity, hylMyCouponModel.getMessage());
                        }



                    }
                });
    }

    private void updateNoticeList(HylMyCouponModel hylMyCouponModel) {
        if (pageNum == 1) {
            if (hylMyCouponModel.getData() != null && hylMyCouponModel.getData().getList().size() > 0) {
                data.setVisibility(View.VISIBLE);
                noData.setVisibility(View.GONE);
                lists.clear();
                lists.addAll(hylMyCouponModel.getData().getList());
                adapter.notifyDataSetChanged();
            } else {
                data.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
                tv_desc.setText("您还没有失效的优惠券哦");
            }
        } else {
            lists.addAll(hylMyCouponModel.getData().getList());
            adapter.notifyDataSetChanged();
        }
        if (hylMyCouponModel.getData().isHasNextPage()) {
            //还有下一页数据
            adapter.loadMoreComplete();
        } else {
            //没有下一页数据了
            adapter.loadMoreEnd();
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
