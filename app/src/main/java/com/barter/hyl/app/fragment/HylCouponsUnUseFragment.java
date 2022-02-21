package com.barter.hyl.app.fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.CouDanUnAdapter;
import com.barter.hyl.app.api.CategoriApi;
import com.barter.hyl.app.base.BaseFragment;
import com.barter.hyl.app.model.HylChooseCouponModel;
import com.barter.hyl.app.utils.ToastUtil;

import java.io.Serializable;
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
 * Created by ${王涛} on 2020/11/13(不可使用)
 */
public class HylCouponsUnUseFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public static HylCouponsUnUseFragment newInstance(List<Integer> cartIds) {
        Bundle args = new Bundle();
        args.putSerializable("cartIds", (Serializable) cartIds);
        HylCouponsUnUseFragment fragment = new HylCouponsUnUseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_coupon_unuse_hyl;
    }

    List<Integer> cartIds;
    CouDanUnAdapter adapter1;
    @Override
    public void setViewData() {
        cartIds = (List<Integer>) getArguments().getSerializable("cartIds");
        userChooseDeduct();

        //本身不可用
        adapter1 = new CouDanUnAdapter(R.layout.item_my_coupons_hyl,dataBean2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter1);

    }

    @Override
    public void setClickListener() {

    }


    HylChooseCouponModel models;
    List<HylChooseCouponModel.DataBean>dataBean2 = new ArrayList<>();
    private void userChooseDeduct() {
        CategoriApi.couponList(mActivity, 1,cartIds.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylChooseCouponModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylChooseCouponModel couponModel) {
                        if (couponModel.getCode()==1) {
                            models = couponModel;
                            for (int i = 0; i < couponModel.getData().size(); i++) {
                                dataBean2.add(couponModel.getData().get(i));
                                adapter1.notifyDataSetChanged();
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mActivity,couponModel.getMessage());
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
