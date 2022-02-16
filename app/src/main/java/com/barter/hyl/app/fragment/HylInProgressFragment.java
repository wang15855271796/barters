package com.barter.hyl.app.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylInProgressAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.api.HomeApi;
import com.barter.hyl.app.base.BaseFragment;
import com.barter.hyl.app.event.CartListHylEvent;
import com.barter.hyl.app.event.CartNumHylEvent;
import com.barter.hyl.app.model.HylLoginModel;
import com.barter.hyl.app.model.HylTeamListModel;
import com.barter.hyl.app.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

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
 * Created by ${王涛} on 2021/7/29
 */
public class HylInProgressFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    View emptyView;
    HylInProgressAdapter hylInProgressAdapter;
    public static HylInProgressFragment getInstance() {
        HylInProgressFragment fragment = new HylInProgressFragment();
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.inprogress_fragment_hyl;
    }

    @Override
    public void setViewData() {
        getTeamList();
        emptyView = View.inflate(mActivity, R.layout.no_view, null);
        hylInProgressAdapter = new HylInProgressAdapter(R.layout.item_inprogress_hyl,list, new HylInProgressAdapter.OnAddClickListener() {
            @Override
            public void onAddClick(int activeType,int activeId,int num) {
                addCartNum(activeType,-100,activeId,1);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(hylInProgressAdapter);


    }

    @Override
    public void setClickListener() {

    }

    /**
     * 组合数据
     */
    HylTeamListModel hylTeamListModels;
    List<HylTeamListModel.DataBean.ActivesBeanX> list = new ArrayList<>();
    private void getTeamList() {
        HomeApi.getTeamList(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylTeamListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylTeamListModel hylTeamListModel) {
                        if (hylTeamListModel.getCode()==1) {
                            if(hylTeamListModel.getData()!=null&& hylTeamListModel.getData().size()>0) {
                                hylTeamListModels = hylTeamListModel;
                                list.clear();
                                for (int i = 0; i < hylTeamListModel.getData().size(); i++) {
                                    if(hylTeamListModel.getData().get(i).getFlag()==0) {
                                        list.addAll(hylTeamListModel.getData().get(i).getActives());
                                    }
                                }
                                hylInProgressAdapter.notifyDataSetChanged();
                            }else {
                                hylInProgressAdapter.setEmptyView(emptyView);
                            }

                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylTeamListModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 加入购物车
     * @param businessType
     * @param priceId
     * @param businessId
     * @param num
     */
    private void addCartNum(int businessType, int priceId, int businessId, final int num) {
        DetailApi.getAddCarts(mActivity,businessType,priceId,businessId,num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylLoginModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylLoginModel hylLoginModel) {
                        if (hylLoginModel.code==1) {
                            EventBus.getDefault().post(new CartListHylEvent());
                            EventBus.getDefault().post(new CartNumHylEvent());
                            ToastUtil.showSuccessMsg(mActivity, hylLoginModel.message);
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylLoginModel.message);
                        }
                    }
                });
    }

//    private void getCartNum() {
//        DetailApi.getCartNum(mActivity,0)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<HylCartNumModel>() {
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
//                    public void onNext(HylCartNumModel cartNumModel) {
//
//                        if (cartNumModel.getCode()==1) {
//                            if(cartNumModel.getData().getProdNum()>0) {
//                                tv_number.setVisibility(View.VISIBLE);
//                                tv_number.setText(cartNumModel.getData().getProdNum()+"");
//                            }else {
//                                tv_number.setVisibility(View.GONE);
//                            }
//                        } else {
//                            ToastUtil.showSuccessMsg(mActivity, cartNumModel.getMessage());
//                        }
//                    }
//                });
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
