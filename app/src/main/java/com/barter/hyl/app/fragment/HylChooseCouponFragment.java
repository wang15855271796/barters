package com.barter.hyl.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylChooseCouponsAdapter;
import com.barter.hyl.app.api.CategoriApi;
import com.barter.hyl.app.base.BaseFragment;
import com.barter.hyl.app.event.ChooseCoupon1HylEvent;
import com.barter.hyl.app.event.ChooseCouponHylEvent;
import com.barter.hyl.app.model.HylChooseCouponModel;
import com.barter.hyl.app.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

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
 * Created by ${王涛} on 2020/11/24
 */
public class HylChooseCouponFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_select_all)
    ImageView iv_select_all;
    public static HylChooseCouponFragment newInstance(List<Integer> cartIds) {
        Bundle args = new Bundle();
        args.putSerializable("cartIds", (Serializable) cartIds);
        HylChooseCouponFragment fragment = new HylChooseCouponFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_choose_coupons_hyl;
    }

    boolean statModel;
    String giftDetailNo;

    HylChooseCouponsAdapter adapter;
    List<Integer> cartIds;
    @Override
    public void setViewData() {

        cartIds = (List<Integer>) getArguments().getSerializable("cartIds");

        userChooseDeduct();

        adapter = new HylChooseCouponsAdapter(R.layout.item_choose_copons, list, new HylChooseCouponsAdapter.ImageOnclick() {
            @Override
            public void Onclick(int position, String giftDetailNo) {
                HylChooseCouponModel.DataBean info = list.get(position);
                statModel = false;
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        list.get(i).setFlags(!list.get(i).isFlags());
                        if (list.get(i).isFlags()) {
                            EventBus.getDefault().post(new ChooseCouponHylEvent(info.getGiftDetailNo()));
                            getActivity().finish();
                        } else {
                            getActivity().finish();
                        }
                    } else {
                        list.get(i).setFlags(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);

        iv_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list!=null) {
                    //未选
                    adapter.setStat();
                    EventBus.getDefault().post(new ChooseCoupon1HylEvent());
                    getActivity().finish();
                }

                statModel = true;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setClickListener() {

    }

    HylChooseCouponModel models;
    private List<HylChooseCouponModel.DataBean> list = new ArrayList<>();
    private void userChooseDeduct() {
        CategoriApi.couponList(mActivity, 0,cartIds.toString())
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
                    public void onNext(HylChooseCouponModel hylChooseCouponModel) {
                        if (hylChooseCouponModel.getCode()==1) {
                            models = hylChooseCouponModel;
                            if (hylChooseCouponModel.getData().size()> 0) {
                                list.addAll(hylChooseCouponModel.getData());
                                adapter.notifyDataSetChanged();

                                for (int i = 0; i < list.size(); i++) {
                                    if (hylChooseCouponModel.getData().get(i).getGiftDetailNo().equals(giftDetailNo)) {
                                        hylChooseCouponModel.getData().get(i).setFlags(true);
                                        if(statModel) {
                                            iv_select_all.setBackgroundResource(R.mipmap.ic_pay_ok);
                                        }else {
                                            iv_select_all.setBackgroundResource(R.mipmap.ic_pay_no);
                                        }
                                    } else {
                                        hylChooseCouponModel.getData().get(i).setFlags(false);
                                        if(statModel) {
                                            iv_select_all.setBackgroundResource(R.mipmap.ic_pay_ok);
                                        }else {
                                            iv_select_all.setBackgroundResource(R.mipmap.ic_pay_no);
                                        }
                                    }
                                }
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylChooseCouponModel.getMessage());
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
