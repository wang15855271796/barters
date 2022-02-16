package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylFullListAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.api.HomeApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.event.JumpCartHylEvent;
import com.barter.hyl.app.model.HylCartNumModel;
import com.barter.hyl.app.model.HylFullListModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

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
 * Created by ${王涛} on 2021/8/6
 */
public class HylFullListActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.rl_cart)
    RelativeLayout rl_cart;
    HylFullListAdapter fullAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_full_list_hyl);
    }

    @Override
    public void setViewData() {
        tv_title.setText("满赠特惠");
        iv_image.setVisibility(View.VISIBLE);
        getFullList();
        getCartNum();
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        fullAdapter = new HylFullListAdapter(R.layout.item_full_hyl,list);
        recyclerView.setAdapter(fullAdapter);

        fullAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity,HylCommonGoodsActivity.class);
                intent.putExtra("mainId",list.get(position).getMainId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
        rl_cart.setOnClickListener(this);
    }


    private void getCartNum() {
        DetailApi.getCartNum(mActivity,0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylCartNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylCartNumModel hylCartNumModel) {
                        if (hylCartNumModel.getCode()==1) {
                            if(hylCartNumModel.getData().getProdNum()>0) {
                                tv_number.setVisibility(View.VISIBLE);
                                tv_number.setText(hylCartNumModel.getData().getProdNum()+"");
                            }else {
                                tv_number.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylCartNumModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 组合数据
     */
    List<HylFullListModel.DataBean> list = new ArrayList<>();
    private void getFullList() {
        HomeApi.getFullList(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylFullListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylFullListModel hylFullListModel) {
                        if (hylFullListModel.getCode()==1) {
                            if(hylFullListModel.getData()!=null) {
                                list.clear();
                                list.addAll(hylFullListModel.getData());
                                fullAdapter.notifyDataSetChanged();
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylFullListModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.rl_cart:
                EventBus.getDefault().post(new JumpCartHylEvent());
                finish();
                break;
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
