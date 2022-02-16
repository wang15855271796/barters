package com.barter.hyl.app.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylInProgressAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.api.HomeApi;
import com.barter.hyl.app.base.BaseFragment;
import com.barter.hyl.app.event.CartNumHylEvent;
import com.barter.hyl.app.model.HylAddToCartModel;
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
 * Created by ${王涛} on 2021/7/30
 */
public class HylImmedFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    HylInProgressAdapter hylInProgressAdapter;
    View emptyView;
    public static HylImmedFragment getInstance() {
        HylImmedFragment fragment = new HylImmedFragment();
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
        hylInProgressAdapter = new HylInProgressAdapter(R.layout.item_inprogress_hyl,list,new HylInProgressAdapter.OnAddClickListener() {
            @Override
            public void onAddClick(int activeType,int activeId,int num) {
                addCartNum(activeType,-100,activeId,num);
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
                                    if(hylTeamListModel.getData().get(i).getFlag()==1) {
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

    //加入购物车
    private void addCartNum(int businessType, int priceId, int businessId, final int num) {
        DetailApi.addCart(mActivity,businessType,priceId,businessId,num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylAddToCartModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylAddToCartModel hylAddToCartModel) {
                        if (hylAddToCartModel.getCode()==1) {
                            if(hylAddToCartModel.getData().getAddFlag()==0) {
                                EventBus.getDefault().post(new CartNumHylEvent());
                                ToastUtil.showSuccessMsg(mActivity, hylAddToCartModel.getMessage());
                            }else {
                                EventBus.getDefault().post(new CartNumHylEvent());
                                ToastUtil.showSuccessMsg(mActivity, hylAddToCartModel.getData().getMessage());
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylAddToCartModel.getMessage());
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

}
